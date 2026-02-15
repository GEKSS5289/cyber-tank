package com.cyber.tank.common.cache.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Redis Key 构建工具。
 * <p>
 * 规范 Key 的拼接方式，避免各业务线随意拼接造成冲突。
 */
public final class RedisKeyBuilder {

    private static final String DELIMITER = ":";

    /**
     * 构造 RedisKeyBuilder 实例。
     */
    private RedisKeyBuilder() {
    }

    /**
     * 按统一分隔符拼接 Redis Key。
     *
     * @param segments key 的分段，例如 biz、module、id
     * @return 拼接后的 key
     */
    public static String build(Object... segments) {
        return Arrays.stream(segments)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }
}
