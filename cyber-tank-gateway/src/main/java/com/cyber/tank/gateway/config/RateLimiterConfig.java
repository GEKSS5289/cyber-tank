package com.cyber.tank.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
/**
 * RateLimiterConfig 的核心定义。
 */
public class RateLimiterConfig {

    /**
     * IP 限流：防止单个 IP 恶意刷接口
     */
    @Bean
    /**
     * ipKeyResolver 方法。
     * @return 执行结果。
     */
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress()
        );
    }

    /**
     * 用户限流：防止单个用户高频操作
     */
    @Bean
    /**
     * userKeyResolver 方法。
     * @return 执行结果。
     */
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest().getHeaders().getFirst("user-id"))
        );
    }

    /**
     * 接口限流：针对特定 URL 进行整体并发控制
     */
    @Bean
    @Primary
    /**
     * pathKeyResolver 方法。
     * @return 执行结果。
     */
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }
}