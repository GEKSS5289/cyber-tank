package com.cyber.tank.common.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "cyber.tank.web")
public class CyberTankWebProperties {

    private List<String> excludePathPatterns = new ArrayList<>(List.of(
            "/auth/login",
            "/auth/register",
            "/auth/captcha",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    ));

    public List<String> getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
}
