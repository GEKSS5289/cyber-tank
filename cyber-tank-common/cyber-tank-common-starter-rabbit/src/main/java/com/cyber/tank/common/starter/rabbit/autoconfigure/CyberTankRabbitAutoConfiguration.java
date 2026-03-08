package com.cyber.tank.common.starter.rabbit.autoconfigure;

import com.cyber.tank.common.mq.rabbit.utils.RabbitMqService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({RabbitTemplate.class, AmqpAdmin.class})
public class CyberTankRabbitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RabbitMqService rabbitMqService(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        return new RabbitMqService(rabbitTemplate, amqpAdmin);
    }
}
