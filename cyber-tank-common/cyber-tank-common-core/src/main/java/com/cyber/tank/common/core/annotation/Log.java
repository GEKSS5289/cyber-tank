package com.cyber.tank.common.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块名称
     */
    public String title() default "";

    /**
     * 功能描述
     */
    public String businessType() default "OTHER"; // 例如：INSERT, UPDATE, DELETE

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的数据
     */
    public boolean isSaveResponseData() default true;
}
