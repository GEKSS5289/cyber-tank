package com.cyber.tank.common.starter.web.autoconfigure;

import com.cyber.tank.common.core.config.JacksonConfig;
import com.cyber.tank.common.core.filter.TraceFilter;
import com.cyber.tank.common.core.properties.CyberTankTraceProperties;
import com.cyber.tank.common.web.aspect.InnerAuthAspect;
import com.cyber.tank.common.web.aspect.LogAspect;
import com.cyber.tank.common.web.config.WebMvcConfig;
import com.cyber.tank.common.web.exception.GlobalExceptionHandler;
import com.cyber.tank.common.web.interceptor.UserHeaderInterceptor;
import com.cyber.tank.common.web.properties.CyberTankWebProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
        CyberTankWebProperties.class,
        CyberTankTraceProperties.class
})
@Import({
        JacksonConfig.class
})
public class CyberTankWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserHeaderInterceptor userHeaderInterceptor() {
        return new UserHeaderInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebMvcConfig webMvcConfig(UserHeaderInterceptor userHeaderInterceptor, CyberTankWebProperties webProperties) {
        return new WebMvcConfig(userHeaderInterceptor, webProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public InnerAuthAspect innerAuthAspect() {
        return new InnerAuthAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(prefix = "cyber.tank.trace", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "traceFilterRegistration")
    public FilterRegistrationBean<TraceFilter> traceFilterRegistration(CyberTankTraceProperties traceProperties) {
        // TraceFilter 属于 Servlet 链路过滤器，只有在 Web MVC 应用里才注册。
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceFilter(traceProperties.getHeaderName(), traceProperties.getMdcKey()));
        registration.setOrder(traceProperties.getOrder());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
