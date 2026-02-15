package com.cyber.tank.tooltest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyber.tank.tooltest.entity.ToolTestUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ToolTestUserMapper extends BaseMapper<ToolTestUser> {
}
