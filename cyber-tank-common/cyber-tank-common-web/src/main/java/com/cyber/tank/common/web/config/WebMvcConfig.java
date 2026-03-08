package com.cyber.tank.common.web.config;

import com.cyber.tank.common.web.interceptor.UserHeaderInterceptor;
import com.cyber.tank.common.web.properties.CyberTankWebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserHeaderInterceptor userHeaderInterceptor;
    private final CyberTankWebProperties webProperties;

    public WebMvcConfig(UserHeaderInterceptor userHeaderInterceptor, CyberTankWebProperties webProperties) {
        this.userHeaderInterceptor = userHeaderInterceptor;
        this.webProperties = webProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathPatterns = webProperties.getExcludePathPatterns();

        registry.addInterceptor(userHeaderInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns.toArray(String[]::new));
    }
}
