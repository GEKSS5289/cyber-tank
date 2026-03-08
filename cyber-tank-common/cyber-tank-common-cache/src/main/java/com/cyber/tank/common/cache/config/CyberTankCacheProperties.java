package com.cyber.tank.common.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cyber.tank.cache")
public class CyberTankCacheProperties {

    private String keyPrefix = "";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
