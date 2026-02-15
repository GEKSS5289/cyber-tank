package com.cyber.tank.tooltest.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
/**
 * ToolTestUser 的核心定义。
 */
public class ToolTestUser {

    @TableId
    private Long id;

    private String name;
}
