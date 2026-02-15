package com.cyber.tank.messagecenter.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息枢纽消费者。
 */
@Slf4j
@Component
/**
 * MessageCenterConsumer 的核心定义。
 */
public class MessageCenterConsumer {

    /**
     * onMessage 方法。
     * @param payload 参数。
     * @param headers 参数。
     */
    @RabbitListener(queues = "#{@messageCenterQueue.name}")
    public void onMessage(@Payload String payload, @Headers Map<String, Object> headers) {
        log.info("[MessageCenter] 收到消息, payload={}, headers={}", payload, headers);
    }
}
