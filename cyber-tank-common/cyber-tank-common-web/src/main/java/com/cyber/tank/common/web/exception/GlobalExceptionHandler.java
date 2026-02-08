package com.cyber.tank.common.web.exception;

import com.cyber.tank.common.core.domain.Result;
import com.cyber.tank.common.core.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理器
 * 作用：将所有未捕获的异常转换为标准 Result JSON
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. 处理自定义业务异常 (ServiceException)
     * 场景：AuthService 中抛出的 "密码错误"、"用户不存在"
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Void> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.warn("业务异常 [{}]: {}", request.getRequestURI(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 2. 处理参数校验异常 (@NotNull, @Size)
     * 场景：DTO 字段校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.warn("参数校验异常: {}", msg);
        return Result.fail(400, msg);
    }

    /**
     * 3. 处理不支持的请求方式 (405)
     * 场景：接口只写了 @PostMapping，你却用 GET 请求
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn("请求方式不支持 [{}]: {}", request.getRequestURI(), e.getMethod());
        return Result.fail(405, "不支持 " + e.getMethod() + " 请求");
    }

    /**
     * 4. 兜底处理：所有未知的系统异常 (500)
     * 场景：空指针、数据库连不上、代码 Bug
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, HttpServletRequest request) {
        // 打印完整堆栈信息，方便排查 Bug (配合之前的 TraceId)
        log.error("系统异常 [{}]: ", request.getRequestURI(), e);
        return Result.fail(500, "系统繁忙，请稍后重试");
    }
}
