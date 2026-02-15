package com.cyber.tank.data.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * PageResult 的核心定义。
 */
public class PageResult<T> implements Serializable {
    private List<T> list;
    private Long total;

    /**
     * of 方法。
     * @param list 参数。
     * @param total 参数。
     * @return 执行结果。
     */
    public static <T> PageResult<T> of(List<T> list, Long total) {
        return new PageResult<>(list, total);
    }
}