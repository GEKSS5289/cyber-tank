package com.cyber.tank.common.mq.rabbit.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * RabbitMQ 通用工具。
 */
@Component
@RequiredArgsConstructor
/**
 * RabbitMqService 的核心定义。
 */
public class RabbitMqService {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    /**
     * send 方法。
     * @param queueName 参数。
     * @param payload 参数。
     */
    public void send(String queueName, Object payload) {
        rabbitTemplate.convertAndSend(queueName, payload);
    }

    /**
     * send 方法。
     * @param exchange 参数。
     * @param routingKey 参数。
     * @param payload 参数。
     */
    public void send(String exchange, String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

    /**
     * send 方法。
     * @param exchange 参数。
     * @param routingKey 参数。
     * @param payload 参数。
     * @param headers 参数。
     */
    public void send(String exchange, String routingKey, Object payload, Map<String, Object> headers) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload, message -> {
            if (headers != null && !headers.isEmpty()) {
                message.getMessageProperties().getHeaders().putAll(headers);
            }
            return message;
        });
    }

    @SuppressWarnings("unchecked")
    /**
     * receive 方法。
     * @param queueName 参数。
     * @return 执行结果。
     */
    public <T> T receive(String queueName) {
        return (T) rabbitTemplate.receiveAndConvert(queueName);
    }

    /**
     * receiveMessage 方法。
     * @param queueName 参数。
     * @return 执行结果。
     */
    public Message receiveMessage(String queueName) {
        return rabbitTemplate.receive(queueName);
    }

    /**
     * declareQueue 方法。
     * @param queueName 参数。
     * @param durable 参数。
     * @return 执行结果。
     */
    public Queue declareQueue(String queueName, boolean durable) {
        Queue queue = new Queue(queueName, durable);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * declareTopicExchange 方法。
     * @param exchangeName 参数。
     * @param durable 参数。
     * @return 执行结果。
     */
    public TopicExchange declareTopicExchange(String exchangeName, boolean durable) {
        TopicExchange exchange = new TopicExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    /**
     * declareDirectExchange 方法。
     * @param exchangeName 参数。
     * @param durable 参数。
     * @return 执行结果。
     */
    public DirectExchange declareDirectExchange(String exchangeName, boolean durable) {
        DirectExchange exchange = new DirectExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    /**
     * declareFanoutExchange 方法。
     * @param exchangeName 参数。
     * @param durable 参数。
     * @return 执行结果。
     */
    public FanoutExchange declareFanoutExchange(String exchangeName, boolean durable) {
        FanoutExchange exchange = new FanoutExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    /**
     * bindTopic 方法。
     * @param queueName 参数。
     * @param exchangeName 参数。
     * @param routingKey 参数。
     * @return 执行结果。
     */
    public Binding bindTopic(String queueName, String exchangeName, String routingKey) {
        Binding binding = BindingBuilder
                .bind(new Queue(queueName))
                .to(new TopicExchange(exchangeName))
                .with(routingKey);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * bindDirect 方法。
     * @param queueName 参数。
     * @param exchangeName 参数。
     * @param routingKey 参数。
     * @return 执行结果。
     */
    public Binding bindDirect(String queueName, String exchangeName, String routingKey) {
        Binding binding = BindingBuilder
                .bind(new Queue(queueName))
                .to(new DirectExchange(exchangeName))
                .with(routingKey);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    /**
     * sendText 方法。
     * @param exchange 参数。
     * @param routingKey 参数。
     * @param text 参数。
     */
    public void sendText(String exchange, String routingKey, String text) {
        MessageProperties properties = new MessageProperties();
        properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        Message message = new Message(text.getBytes(StandardCharsets.UTF_8), properties);
        rabbitTemplate.send(exchange, routingKey, message);
    }
}
