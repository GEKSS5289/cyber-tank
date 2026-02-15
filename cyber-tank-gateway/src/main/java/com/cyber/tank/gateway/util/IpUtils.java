package com.cyber.tank.gateway.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * IpUtils 的核心定义。
 */
public final class IpUtils {
    /**
     * 构造 IpUtils 实例。
     */
    private IpUtils() {}

    /**
     * resolveClientIp 方法。
     * @param request 参数。
     * @return 执行结果。
     */
    public static String resolveClientIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String xff = headers.getFirst("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            int comma = xff.indexOf(',');
            return comma > 0 ? xff.substring(0, comma).trim() : xff.trim();
        }
        String realIp = headers.getFirst("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        if (request.getRemoteAddress() != null) {
            return request.getRemoteAddress().getAddress().getHostAddress();
        }
        return "unknown";
    }
}
