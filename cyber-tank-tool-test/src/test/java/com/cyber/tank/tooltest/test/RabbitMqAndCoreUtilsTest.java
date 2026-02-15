package com.cyber.tank.tooltest.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.utils.DateUtils;
import com.cyber.tank.common.core.utils.JsonUtils;
import com.cyber.tank.common.core.utils.JwtUtils;
import com.cyber.tank.common.core.utils.StringUtils;
import com.cyber.tank.common.mq.rabbit.utils.RabbitMqService;
import com.cyber.tank.tooltest.config.ToolIntegrationTestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 运行方式：
 * mvn -pl cyber-tank-tool-test -Dtest=RabbitMqAndCoreUtilsTest -Dtool.integration.enabled=true test
 */
@SpringBootTest(classes = ToolIntegrationTestApplication.class)
@ActiveProfiles("test")
@EnabledIfSystemProperty(named = "tool.integration.enabled", matches = "true")
class RabbitMqAndCoreUtilsTest {

    @Autowired
    private RabbitMqService rabbitMqService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldUseRabbitMqServiceApis() {
        rabbitMqService.send("tool.test.queue", "hello");
        rabbitMqService.send("tool.test.exchange", "tool.test.key", "payload");
        rabbitMqService.send("tool.test.exchange", "tool.test.key", "payload", Map.of("traceId", "trace-1"));

        when(rabbitTemplate.receiveAndConvert("tool.test.queue")).thenReturn("msg");
        assertEquals("msg", rabbitMqService.receive("tool.test.queue"));

        Message message = new Message("body".getBytes(), new MessageProperties());
        when(rabbitTemplate.receive("tool.test.queue")).thenReturn(message);
        assertNotNull(rabbitMqService.receiveMessage("tool.test.queue"));

        Queue queue = rabbitMqService.declareQueue("tool.test.queue", true);
        assertEquals("tool.test.queue", queue.getName());

        rabbitMqService.declareTopicExchange("tool.test.topic", true);
        rabbitMqService.declareDirectExchange("tool.test.direct", true);
        rabbitMqService.declareFanoutExchange("tool.test.fanout", true);

        rabbitMqService.bindTopic("tool.test.queue", "tool.test.topic", "test.*");
        rabbitMqService.bindDirect("tool.test.queue", "tool.test.direct", "test.key");
        rabbitMqService.sendText("tool.test.exchange", "tool.test.key", "plain text");

        verify(rabbitTemplate).convertAndSend("tool.test.queue", "hello");
        verify(rabbitTemplate).convertAndSend("tool.test.exchange", "tool.test.key", "payload");
        verify(rabbitTemplate).convertAndSend(eq("tool.test.exchange"), eq("tool.test.key"), eq("payload"), any());
        verify(rabbitTemplate).send(eq("tool.test.exchange"), eq("tool.test.key"), any(Message.class));
        verify(amqpAdmin).declareQueue(any(Queue.class));
    }

    @Test
    void shouldUseCoreUtils() {
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("cyber"));
        assertEquals("user_name", StringUtils.toUnderScoreCase("userName"));

        LocalDate date = DateUtils.parseDate("2026-02-06");
        assertEquals("2026-02-06", DateUtils.formatDate(date));

        LocalDateTime dateTime = DateUtils.parseDateTime("2026-02-06 11:22:33");
        assertEquals("2026-02-06 11:22:33", DateUtils.formatDateTime(dateTime));
        assertNotNull(DateUtils.toDate(dateTime));

        String json = JsonUtils.toJson(Map.of("id", 1L, "name", "cyber"));
        Map<String, Object> jsonMap = JsonUtils.toObject(json, new TypeReference<>() {
        });
        assertEquals("cyber", jsonMap.get("name"));

        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.DETAILS_USER_ID, 1001L);
        claims.put(SecurityConstants.DETAILS_USERNAME, "cyber-admin");
        claims.put("tenant", "default");

        String token = JwtUtils.createToken(claims, 60_000L);
        assertTrue(JwtUtils.validateToken(token));
        assertEquals(1001L, JwtUtils.getUserId(token));
        assertEquals("cyber-admin", JwtUtils.getUserName(token));
        assertEquals("default", JwtUtils.parseToken(token).get("tenant"));
    }
}
