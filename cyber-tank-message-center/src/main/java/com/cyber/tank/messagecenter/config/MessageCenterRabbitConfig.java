package com.cyber.tank.messagecenter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息枢纽 RabbitMQ 声明。
 */
@Configuration
@EnableConfigurationProperties(MessageCenterRabbitProperties.class)
/**
 * MessageCenterRabbitConfig 的核心定义。
 */
public class MessageCenterRabbitConfig {

    /**
     * messageCenterExchange 方法。
     * @param properties 参数。
     * @return 执行结果。
     */
    @Bean
    public TopicExchange messageCenterExchange(MessageCenterRabbitProperties properties) {
        return new TopicExchange(properties.getExchange(), properties.isDurable(), false);
    }

    /**
     * messageCenterQueue 方法。
     * @param properties 参数。
     * @return 执行结果。
     */
    @Bean
    public Queue messageCenterQueue(MessageCenterRabbitProperties properties) {
        return new Queue(properties.getQueue(), properties.isDurable());
    }

    /**
     * messageCenterBinding 方法。
     * @param messageCenterQueue 参数。
     * @param messageCenterExchange 参数。
     * @param properties 参数。
     * @return 执行结果。
     */
    @Bean
    public Binding messageCenterBinding(Queue messageCenterQueue,
                                        TopicExchange messageCenterExchange,
                                        MessageCenterRabbitProperties properties) {
        return BindingBuilder
                .bind(messageCenterQueue)
                .to(messageCenterExchange)
                .with(properties.getRoutingKey());
    }
}
