package com.cyber.tank.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * PageResult 的核心定义。
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<T> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    /**
     * 分页响应构造
     */
    public static <T> PageResult<T> build(List<T> list, long total) {
        PageResult<T> rspData = new PageResult<>();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }
}
