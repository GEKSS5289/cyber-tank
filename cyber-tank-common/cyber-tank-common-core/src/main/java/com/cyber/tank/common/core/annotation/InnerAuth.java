package com.cyber.tank.common.core.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 * 只有具备内部特定 Header (如 from=source) 的请求才允许访问
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth {
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}
