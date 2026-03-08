package com.cyber.tank.common.core.filter;

import com.cyber.tank.common.core.constant.SecurityConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 通用日志链路过滤器
 * 只在 Servlet 环境下生效（网关不加载这个）
 */
public class TraceFilter implements Filter {

    private final String traceIdHeader;
    private final String mdcKey;

    public TraceFilter(String traceIdHeader, String mdcKey) {
        this.traceIdHeader = traceIdHeader;
        this.mdcKey = mdcKey;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // 1. 尝试从 Header 获取上游传来的 TraceId
        String traceId = httpRequest.getHeader(StringUtils.hasText(traceIdHeader)
                ? traceIdHeader
                : SecurityConstants.TRACE_ID_HEADER);
        
        // 2. 如果是外部直接访问（非网关），可能没有 ID，需补全
        if (!StringUtils.hasText(traceId)) {
            traceId = cn.hutool.core.util.IdUtil.fastSimpleUUID();
        }

        // 3. 放入 MDC
        MDC.put(StringUtils.hasText(mdcKey) ? mdcKey : SecurityConstants.LOG_TRACE_ID, traceId);

        try {
            chain.doFilter(request, response);
        } finally {
            // 4. 清理 MDC，防止线程复用导致数据污染
            MDC.remove(StringUtils.hasText(mdcKey) ? mdcKey : SecurityConstants.LOG_TRACE_ID);
        }
    }
}
