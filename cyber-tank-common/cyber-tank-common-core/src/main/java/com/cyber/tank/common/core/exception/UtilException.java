package com.cyber.tank.common.core.exception;

import java.io.Serial;

/**
 * 工具类异常
 */
public class UtilException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造 UtilException 实例。
     * @param e 参数。
     */
    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    /**
     * 构造 UtilException 实例。
     * @param message 参数。
     */
    public UtilException(String message) {
        super(message);
    }

    /**
     * 构造 UtilException 实例。
     * @param message 参数。
     * @param throwable 参数。
     */
    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
