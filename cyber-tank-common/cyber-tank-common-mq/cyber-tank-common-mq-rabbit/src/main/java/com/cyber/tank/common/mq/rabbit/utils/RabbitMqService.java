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
public class RabbitMqService {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    public void send(String queueName, Object payload) {
        rabbitTemplate.convertAndSend(queueName, payload);
    }

    public void send(String exchange, String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

    public void send(String exchange, String routingKey, Object payload, Map<String, Object> headers) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload, message -> {
            if (headers != null && !headers.isEmpty()) {
                message.getMessageProperties().getHeaders().putAll(headers);
            }
            return message;
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T receive(String queueName) {
        return (T) rabbitTemplate.receiveAndConvert(queueName);
    }

    public Message receiveMessage(String queueName) {
        return rabbitTemplate.receive(queueName);
    }

    public Queue declareQueue(String queueName, boolean durable) {
        Queue queue = new Queue(queueName, durable);
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    public TopicExchange declareTopicExchange(String exchangeName, boolean durable) {
        TopicExchange exchange = new TopicExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    public DirectExchange declareDirectExchange(String exchangeName, boolean durable) {
        DirectExchange exchange = new DirectExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    public FanoutExchange declareFanoutExchange(String exchangeName, boolean durable) {
        FanoutExchange exchange = new FanoutExchange(exchangeName, durable, false);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    public Binding bindTopic(String queueName, String exchangeName, String routingKey) {
        Binding binding = BindingBuilder
                .bind(new Queue(queueName))
                .to(new TopicExchange(exchangeName))
                .with(routingKey);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    public Binding bindDirect(String queueName, String exchangeName, String routingKey) {
        Binding binding = BindingBuilder
                .bind(new Queue(queueName))
                .to(new DirectExchange(exchangeName))
                .with(routingKey);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    public void sendText(String exchange, String routingKey, String text) {
        MessageProperties properties = new MessageProperties();
        properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        Message message = new Message(text.getBytes(StandardCharsets.UTF_8), properties);
        rabbitTemplate.send(exchange, routingKey, message);
    }
}
