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

    /**
     * set 方法。
     * @param key 参数。
     * @param value 参数。
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    /**
     * get 方法。
     * @param key 参数。
     * @return 执行结果。
     */
    public static String get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) return "";
        return Convert.toStr(map.getOrDefault(key, ""));
    }

    /**
     * getUserId 方法。
     * @return 执行结果。
     */
    public static Long getUserId() {
        return Convert.toLong(get(SecurityConstants.DETAILS_USER_ID));
    }

    /**
     * remove 方法。
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}