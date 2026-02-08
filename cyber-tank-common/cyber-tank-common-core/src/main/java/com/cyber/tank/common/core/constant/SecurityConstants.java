package com.cyber.tank.common.core.constant;

public interface SecurityConstants {
    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌头 Key
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 用户ID 字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名 字段
     */
    String DETAILS_USERNAME = "username";

    /**
     * 内部调用标识
     */
    String FROM_SOURCE = "from-source";
    String INTERNAL = "internal";

    /**
     * 链路追踪ID Key
     */
    String TRACE_ID_HEADER = "trace-id";

    /**
     * 日志中的 MDC Key
     */
    String LOG_TRACE_ID = "traceId";
}
