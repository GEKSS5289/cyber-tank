package com.cyber.tank.common.core.interceptor;

import cn.hutool.core.util.StrUtil;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.context.SecurityContextHolder;
import com.cyber.tank.common.core.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户信息拦截器
 */
@Slf4j
/**
 * UserHeaderInterceptor 的核心定义。
 */
public class UserHeaderInterceptor implements HandlerInterceptor {

    @Override
    /**
     * preHandle 方法。
     * @param request 参数。
     * @param response 参数。
     * @param handler 参数。
     * @return 执行结果。
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION);

        // 如果没有 token，直接放行，上下文保持为空
        if (StrUtil.isBlank(token)) {
            return true;
        }

        try {
            // 尝试解析
            Long userId = JwtUtils.getUserId(token);
            if (userId != null) {
                SecurityContextHolder.set(SecurityConstants.DETAILS_USER_ID, userId);
                SecurityContextHolder.set(SecurityConstants.DETAILS_USERNAME, JwtUtils.getUserName(token));
            }
        } catch (Exception e) {
            // 即使 Token 解析失败（如过期或伪造），也直接放行
            // 具体的“拒绝访问”逻辑由网关或 @InnerAuth 等切面处理
            log.debug("Token 解析失败或不存在，当前请求上下文将为空: {}", e.getMessage());
        }
        return true;
    }

    @Override
    /**
     * afterCompletion 方法。
     * @param request 参数。
     * @param response 参数。
     * @param handler 参数。
     * @param ex 参数。
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }
}