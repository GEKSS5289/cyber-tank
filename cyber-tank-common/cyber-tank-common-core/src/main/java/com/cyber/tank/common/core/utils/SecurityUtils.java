package com.cyber.tank.common.core.utils;

import cn.hutool.core.convert.Convert;
import com.cyber.tank.common.core.constant.SecurityConstants;

/**
 * 安全服务工具类
 */
public class SecurityUtils {
    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return Convert.toLong(ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USER_ID));
    }

    /**
     * 获取用户名称
     */
    public static String getUsername() {
        return ServletUtils.getRequest().getHeader(SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 是否为管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
