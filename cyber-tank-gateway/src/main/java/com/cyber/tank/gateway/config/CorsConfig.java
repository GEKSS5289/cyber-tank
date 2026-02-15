package com.cyber.tank.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 全局跨域配置
 * 必须配置在网关层，后续微服务层不需要再处理跨域
 */
@Configuration
/**
 * CorsConfig 的核心定义。
 */
public class CorsConfig {

    @Bean
    /**
     * corsFilter 方法。
     * @return 执行结果。
     */
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有方法 (GET, POST, PUT, DELETE...)
        config.addAllowedMethod("*");
        // 允许所有来源 (生产环境建议替换为具体的域名，如 http://www.cyber-tank.com)
        config.addAllowedOriginPattern("*");
        // 允许所有头信息
        config.addAllowedHeader("*");
        // 允许携带 Cookie
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 对所有路径生效
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
