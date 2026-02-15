package com.cyber.tank.gateway.filter;

import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.utils.JwtUtils;
import com.cyber.tank.gateway.config.IgnoreWhiteConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 网关统一鉴权过滤器 (基于 Nimbus JWT 重构)
 */
@Component
@RequiredArgsConstructor
/**
 * AuthFilter 的核心定义。
 */
public class AuthFilter implements GlobalFilter, Ordered {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private final IgnoreWhiteConfig ignoreWhiteConfig;

    @Override
    /**
     * filter 方法。
     * @param exchange 参数。
     * @param chain 参数。
     * @return 执行结果。
     */
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. 白名单直接放行
        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        // 2. 校验令牌有效性
        String token = extractToken(exchange.getRequest());
        if (!StringUtils.hasText(token)) {
            return unAuthorized(exchange);
        }

        try {
            // 3. 解析并继续业务流程
            Map<String, Object> claims = JwtUtils.parseToken(token);
            return proceedWithUser(exchange, chain, claims);
        } catch (Exception e) {
            return unAuthorized(exchange);
        }
    }

    /**
     * isWhiteList 方法。
     * @param path 参数。
     * @return 执行结果。
     */
    private boolean isWhiteList(String path) {
        List<String> whites = ignoreWhiteConfig.getWhites();
        return whites != null && whites.stream().anyMatch(p -> PATH_MATCHER.match(p, path));
    }

    /**
     * extractToken 方法。
     * @param request 参数。
     * @return 执行结果。
     */
    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * proceedWithUser 方法。
     * @param exchange 参数。
     * @param chain 参数。
     * @param claims 参数。
     * @return 执行结果。
     */
    private Mono<Void> proceedWithUser(ServerWebExchange exchange, GatewayFilterChain chain, Map<String, Object> claims) {
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(SecurityConstants.DETAILS_USER_ID, getClaim(claims, SecurityConstants.DETAILS_USER_ID))
                .header(SecurityConstants.DETAILS_USERNAME, getClaim(claims, SecurityConstants.DETAILS_USERNAME))
                .headers(h -> h.remove(SecurityConstants.FROM_SOURCE)) // 清洗非法内部头
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * getClaim 方法。
     * @param claims 参数。
     * @param key 参数。
     * @return 执行结果。
     */
    private String getClaim(Map<String, Object> claims, String key) {
        Object value = claims.get(key);
        return value == null ? "" : value.toString();
    }

    /**
     * unAuthorized 方法。
     * @param exchange 参数。
     * @return 执行结果。
     */
    private Mono<Void> unAuthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    /**
     * getOrder 方法。
     * @return 执行结果。
     */
    public int getOrder() {
        return -100;
    }
}