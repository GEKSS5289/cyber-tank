package com.cyber.tank.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import java.util.List;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "security.ignore")
public class IgnoreWhiteConfig {
    /** 白名单列表 */
    private List<String> whites;
}