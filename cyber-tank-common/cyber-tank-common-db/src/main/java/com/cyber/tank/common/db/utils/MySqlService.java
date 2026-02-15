package com.cyber.tank.common.db.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * MySQL 通用工具（基于 MyBatis-Plus 封装）。
 */
@Component
/**
 * MySqlService 的核心定义。
 */
public class MySqlService {

    /**
     * 新增单条数据。
     */
    public <T> int insert(BaseMapper<T> mapper, T entity) {
        return mapper.insert(entity);
    }

    /**
     * 批量新增（逐条插入，适用于通用小批量场景）。
     */
    public <T> int insertBatch(BaseMapper<T> mapper, Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return 0;
        }
        int affectedRows = 0;
        for (T entity : entities) {
            affectedRows += mapper.insert(entity);
        }
        return affectedRows;
    }

    /**
     * 根据主键更新。
     */
    public <T> int updateById(BaseMapper<T> mapper, T entity) {
        return mapper.updateById(entity);
    }

    /**
     * 根据主键查询。
     */
    public <T> T selectById(BaseMapper<T> mapper, Serializable id) {
        return mapper.selectById(id);
    }

    /**
     * 按条件查询列表。
     */
    public <T> List<T> selectList(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> wrapperCustomizer) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        if (wrapperCustomizer != null) {
            wrapperCustomizer.accept(wrapper);
        }
        List<T> list = mapper.selectList(wrapper);
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 分页查询。
     */
    public <T> Page<T> selectPage(BaseMapper<T> mapper,
                                  long pageNo,
                                  long pageSize,
                                  Consumer<LambdaQueryWrapper<T>> wrapperCustomizer) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        if (wrapperCustomizer != null) {
            wrapperCustomizer.accept(wrapper);
        }
        Page<T> page = new Page<>(pageNo, pageSize);
        return mapper.selectPage(page, wrapper);
    }

    /**
     * 根据主键删除。
     */
    public <T> int deleteById(BaseMapper<T> mapper, Serializable id) {
        return mapper.deleteById(id);
    }

    /**
     * 统计数量。
     */
    public <T> long count(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> wrapperCustomizer) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        if (wrapperCustomizer != null) {
            wrapperCustomizer.accept(wrapper);
        }
        Long count = mapper.selectCount(wrapper);
        return count == null ? 0L : count;
    }
}
