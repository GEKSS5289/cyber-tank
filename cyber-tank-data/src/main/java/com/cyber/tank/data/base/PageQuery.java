package com.cyber.tank.data.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

@Data
/**
 * PageQuery 的核心定义。
 */
public class PageQuery implements Serializable {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    /**
     * toPage 方法。
     * @return 执行结果。
     */
    public <T> Page toPage() {return new Page(pageNo, pageSize);}
}