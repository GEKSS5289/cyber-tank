package com.cyber.tank.gateway.filter;

import cn.hutool.core.util.IdUtil;
import com.cyber.tank.common.core.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关生成 TraceId 并输出请求摘要
 */
@Slf4j
@Component
public class GatewayTraceFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 生成 TraceId
        String traceId = IdUtil.fastSimpleUUID();
        MDC.put(SecurityConstants.LOG_TRACE_ID, traceId);

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().name();

        // 2. 打印请求摘要（当前线程执行，MDC 尚存）
        log.info("接收请求: [{}] {}, 来源: {}", method, path, request.getRemoteAddress());

        // 3. 传递给下游
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(SecurityConstants.TRACE_ID_HEADER, traceId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .doFinally(signal -> {
                    // 4. 清理 MDC
                    MDC.remove(SecurityConstants.LOG_TRACE_ID);
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
