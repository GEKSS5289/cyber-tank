package com.cyber.tank.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 基础异常
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
/**
 * BaseException 的核心定义。
 */
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    @Override
    /**
     * getMessage 方法。
     * @return 执行结果。
     */
    public String getMessage() {
        return defaultMessage;
    }
}
