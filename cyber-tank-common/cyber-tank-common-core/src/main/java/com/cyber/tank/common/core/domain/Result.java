package com.cyber.tank.common.core.domain;


import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Result 的核心定义。
 */
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.HTTP_OK;

    /** 失败 */
    public static final int FAIL = HttpStatus.HTTP_INTERNAL_ERROR;

    private int code;

    private String msg;

    private T data;

    /**
     * ok 方法。
     * @return 执行结果。
     */
    public static <T> Result<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    /**
     * ok 方法。
     * @param data 参数。
     * @return 执行结果。
     */
    public static <T> Result<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    /**
     * ok 方法。
     * @param data 参数。
     * @param msg 参数。
     * @return 执行结果。
     */
    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    /**
     * fail 方法。
     * @return 执行结果。
     */
    public static <T> Result<T> fail() {
        return restResult(null, FAIL, "操作失败");
    }

    /**
     * fail 方法。
     * @param msg 参数。
     * @return 执行结果。
     */
    public static <T> Result<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    /**
     * fail 方法。
     * @param code 参数。
     * @param msg 参数。
     * @return 执行结果。
     */
    public static <T> Result<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    /**
     * restResult 方法。
     * @param data 参数。
     * @param code 参数。
     * @param msg 参数。
     * @return 执行结果。
     */
    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
