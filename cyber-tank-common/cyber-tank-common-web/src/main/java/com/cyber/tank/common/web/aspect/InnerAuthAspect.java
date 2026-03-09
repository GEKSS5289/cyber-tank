package com.cyber.tank.common.web.aspect;

import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.utils.ServletUtils;
import com.cyber.tank.common.security.annotation.InnerAuth;
import com.cyber.tank.common.security.exception.InnerAuthException;
import com.cyber.tank.common.security.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * 内部服务调用授权处理
 */
@Aspect
public class InnerAuthAspect implements Ordered {

    @Before("@annotation(innerAuth)")
    /**
     * doBefore 方法。
     * @param innerAuth 参数。
     */
    public void doBefore(InnerAuth innerAuth) {
        HttpServletRequest request = ServletUtils.getRequest();

        // 1. 获取来源标识
        String source = request.getHeader(SecurityConstants.FROM_SOURCE);

        // 2. 校验是否来自内部调用
        if (!StringUtils.hasText(source) || !SecurityConstants.INTERNAL.equals(source)) {
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        // 3. 如果注解要求校验用户信息 (isUser = true)
        if (innerAuth.isUser()) {
            verifyUser(request);
        }
    }

    /**
     * 校验并解析用户信息
     */
    private void verifyUser(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            throw new InnerAuthException("没有内部访问权限，缺少 Token 信息");
        }
        JwtUtils.parseAuthenticatedUser(token);
    }

    @Override
    /**
     * getOrder 方法。
     * @return 执行结果。
     */
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
