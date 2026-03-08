package com.cyber.tank.common.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.context.SecurityContextHolder;
import com.cyber.tank.common.core.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class UserHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            return true;
        }

        try {
            Long userId = JwtUtils.getUserId(token);
            if (userId != null) {
                SecurityContextHolder.set(SecurityConstants.DETAILS_USER_ID, userId);
                SecurityContextHolder.set(SecurityConstants.DETAILS_USERNAME, JwtUtils.getUserName(token));
            }
        } catch (Exception e) {
            log.debug("Token parse failed, request context remains empty: {}", e.getMessage());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }
}
