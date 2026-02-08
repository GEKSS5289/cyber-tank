package com.cyber.tank.common.core.exception;


import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 内部认证异常
 */
public class InnerAuthException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InnerAuthException(String message) {
        super("auth", HttpStatus.UNAUTHORIZED.value(), null, message);
    }
}
