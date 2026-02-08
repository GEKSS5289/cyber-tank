package com.cyber.tank.common.core.interceptor;

import com.cyber.tank.common.core.constant.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * Feign 请求拦截器
 * 1. 增加内部调用标识
 * 2. 自动透传当前请求的 Authorization Token
 */
// 注意：如果你使用了我之前建议的 FeignConfig 配置类方式，这里就不需要 @Component
// 如果你没有用配置类，请保留 @Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 1. 统一添加内部调用标识（防止外部直接访问内部接口）
        template.header(SecurityConstants.FROM_SOURCE, SecurityConstants.INTERNAL);

        // 2. 【新增】链路追踪传递
        // 从当前 MDC 中获取 TraceId，传递给下一个服务
        String traceId = MDC.get(SecurityConstants.LOG_TRACE_ID);
        if (traceId != null) {
            template.header(SecurityConstants.TRACE_ID_HEADER, traceId);
        }

        // 2. 获取当前线程的 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();

            // 3. 获取 Authorization 头（Bearer token...）
            String token = request.getHeader(SecurityConstants.AUTHORIZATION);

            // 4. 如果 Token 存在，则透传给下游服务
            if (StringUtils.hasText(token)) {
                template.header(SecurityConstants.AUTHORIZATION, token);
            }
        }
    }
}





