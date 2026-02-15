package com.cyber.tank.wechat.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 在 common-thirdparty-wechat 模块中
@Configuration
@EnableConfigurationProperties(WechatProperties.class)
/**
 * WechatAutoConfiguration 的核心定义。
 */
public class WechatAutoConfiguration {
    @Bean
    /**
     * wechatTemplate 方法。
     * @param properties 参数。
     * @return 执行结果。
     */
    public WechatTemplate wechatTemplate(WechatProperties properties) {
        return new WechatTemplate(properties);
    }
}