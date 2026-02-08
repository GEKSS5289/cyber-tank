package com.cyber.tank.common.web.aspect;

import com.cyber.tank.common.core.annotation.InnerAuth;
import com.cyber.tank.common.core.constant.SecurityConstants;
import com.cyber.tank.common.core.exception.InnerAuthException;
import com.cyber.tank.common.core.utils.JwtUtils;
import com.cyber.tank.common.core.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 内部服务调用授权处理
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered {

    @Before("@annotation(innerAuth)")
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
        // 从 Header 获取 Token
        String token = request.getHeader(SecurityConstants.AUTHORIZATION);

        if (!StringUtils.hasText(token)) {
            throw new InnerAuthException("没有内部访问权限，缺少 Token 信息");
        }

        // 解析 Token 获取 UserID
        Long userId = JwtUtils.getUserId(token);

        // 如果解析失败（Token 格式错误或被篡改）
        if (userId == null) {
            throw new InnerAuthException("没有内部访问权限，Token 非法或用户信息缺失");
        }

        // 成功解析到 UserID 后，通常我们需要将其放入 Request Header
        // 这样后续的 Controller 或 Service 可以通过 request.getHeader("user-id") 拿到 ID
        // 注意：HttpServletRequest 是只读的，通常我们会封装一个 ContextHolder 或者 MutableRequestWrapper
        // 这里为了演示，我们假设后续逻辑会重新从 Token 解析，或者使用 ThreadLocal 上下文传递

        // [可选]：如果你确实需要在这个切面里“查询数据库判断用户是否存在”
        // 由于 Common 模块不能依赖 Service 模块，你需要定义一个 Bean 接口，由各个服务自己实现
        // 比如: userValidationService.checkUserExists(userId);
        // 但通常微服务架构下，信任网关校验过的 Token 即可。
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}