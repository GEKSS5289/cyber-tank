package com.cyber.tank.wechat.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 在 common-thirdparty-wechat 模块中
@Configuration
@EnableConfigurationProperties(WechatProperties.class)
public class WechatAutoConfiguration {
    @Bean
    public WechatTemplate wechatTemplate(WechatProperties properties) {
        return new WechatTemplate(properties);
    }
}