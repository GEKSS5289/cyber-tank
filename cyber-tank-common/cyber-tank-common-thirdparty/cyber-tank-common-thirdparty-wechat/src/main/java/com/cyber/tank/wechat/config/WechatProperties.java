package com.cyber.tank.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置属性映射
 */
@Data
@Component
@ConfigurationProperties(prefix = "thirdparty.wechat")
/**
 * WechatProperties 的核心定义。
 */
public class WechatProperties {

    /**
     * 微信公众号/小程序的 AppID
     */
    private String appId;

    /**
     * 微信公众号/小程序的 Secret
     */
    private String secret;

    /**
     * 微信支付的商户号 (未来对接支付时使用)
     */
    private String mchId;

    /**
     * 微信支付的 API 证书路径
     */
    private String keyPath;
}