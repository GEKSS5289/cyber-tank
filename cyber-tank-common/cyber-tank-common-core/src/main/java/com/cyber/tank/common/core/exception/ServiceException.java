package com.cyber.tank.common.core.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 业务异常
 */
@Getter
public final class ServiceException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认构造函数（500 错误）
     */
    public ServiceException(String message) {super("service", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message);}

    /**
     * 自定义状态码的业务异常
     */
    public ServiceException(String message, Integer code) {
        super("service", code, null, message);
    }
}
