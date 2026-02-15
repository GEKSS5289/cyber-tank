package com.cyber.tank.messagecenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息枢纽 RabbitMQ 配置项。
 */
@Data
@ConfigurationProperties(prefix = "cyber.tank.message-center.rabbit")
/**
 * MessageCenterRabbitProperties 的核心定义。
 */
public class MessageCenterRabbitProperties {

    private String exchange = "cyber.tank.message.center.topic";
    private String queue = "cyber.tank.message.center.queue";
    private String routingKey = "cyber.tank.message.#";
    private boolean durable = true;
}
