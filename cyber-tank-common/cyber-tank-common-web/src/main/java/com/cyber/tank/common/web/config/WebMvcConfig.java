package com.cyber.tank.common.web.config;

import com.cyber.tank.common.web.interceptor.UserHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String[] EXCLUDE_PATH_PATTERNS = {
            "/auth/login",
            "/auth/register",
            "/auth/captcha",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS);
    }
}
