package com.cyber.tank.common.core.context;

import cn.hutool.core.convert.Convert;
import com.cyber.tank.common.core.constant.SecurityConstants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 核心用户信息上下文：利用 ThreadLocal 存储当前线程的用户信息
 */
public class SecurityContextHolder {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    public static String get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) return "";
        return Convert.toStr(map.getOrDefault(key, ""));
    }

    public static Long getUserId() {
        return Convert.toLong(get(SecurityConstants.DETAILS_USER_ID));
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}