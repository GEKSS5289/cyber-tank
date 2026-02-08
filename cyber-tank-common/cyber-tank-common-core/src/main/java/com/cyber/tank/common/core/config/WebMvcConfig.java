package com.cyber.tank.common.core.config;


import com.cyber.tank.common.core.interceptor.UserHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 不需要拦截的用户路径
     */
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
                // 关键点：排除登录等接口，不走 Token 解析逻辑
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS);
    }
}