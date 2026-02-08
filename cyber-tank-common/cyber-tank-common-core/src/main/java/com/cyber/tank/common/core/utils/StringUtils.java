package com.cyber.tank.common.core.utils;

/**
 * 字符串工具类
 */
public class StringUtils extends org.springframework.util.StringUtils {
    /**
     * 判断是否为空字符串
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 驼峰转下划线（数据库字段匹配常用）
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) return null;
        return cn.hutool.core.util.StrUtil.toUnderlineCase(str);
    }
}
