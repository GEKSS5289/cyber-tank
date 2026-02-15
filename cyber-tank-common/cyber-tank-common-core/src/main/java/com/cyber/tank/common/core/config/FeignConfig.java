package com.cyber.tank.common.core.config;

import com.cyber.tank.common.core.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 关键：只有当 classpath 下有 RequestInterceptor 类时（即引入了 OpenFeign 依赖时）才加载此配置
@ConditionalOnClass(RequestInterceptor.class)
/**
 * FeignConfig 的核心定义。
 */
public class FeignConfig {

    @Bean
    /**
     * requestInterceptor 方法。
     * @return 执行结果。
     */
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}