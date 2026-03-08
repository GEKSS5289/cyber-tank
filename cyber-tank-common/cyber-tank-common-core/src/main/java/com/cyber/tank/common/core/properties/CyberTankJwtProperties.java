package com.cyber.tank.common.core.properties;

import com.cyber.tank.common.core.constant.SecurityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cyber.tank.security.jwt")
public class CyberTankJwtProperties {

    private String secret = "CyberTank-Modern-Security-Key-2026-666888";
    private long expireMillis = 24 * 60 * 60 * 1000L;
    private String tokenPrefix = SecurityConstants.TOKEN_PREFIX;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpireMillis() {
        return expireMillis;
    }

    public void setExpireMillis(long expireMillis) {
        this.expireMillis = expireMillis;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
