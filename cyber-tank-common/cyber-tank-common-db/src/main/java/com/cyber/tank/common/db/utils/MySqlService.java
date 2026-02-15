package com.cyber.tank.common.db.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * MySQL 通用工具。
 * <p>
 * 该工具基于 JdbcTemplate 封装，适用于跨业务的轻量 SQL 访问场景，
 * 如：批量更新、公共字典读取、健康检查等。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MySqlService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 执行更新类 SQL（insert/update/delete）。
     *
     * @param sql    SQL 语句
     * @param params 参数
     * @return 受影响行数
     */
    public int update(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    /**
     * 执行批量更新 SQL。
     *
     * @param sql       SQL 模板
     * @param batchArgs 批量参数
     * @return 每条 SQL 的影响行数
     */
    public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        if (batchArgs == null || batchArgs.isEmpty()) {
            return new int[0];
        }
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 查询单条记录。
     *
     * @param sql       SQL 语句
     * @param rowMapper 行映射器
     * @param params    参数
     * @return 查询结果，不存在时返回 null
     */
    public <T> T queryOne(String sql, RowMapper<T> rowMapper, Object... params) {
        List<T> list = queryList(sql, rowMapper, params);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 查询列表。
     *
     * @param sql       SQL 语句
     * @param rowMapper 行映射器
     * @param params    参数
     * @return 列表结果
     */
    public <T> List<T> queryList(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            return jdbcTemplate.query(sql, rowMapper, params);
        } catch (Exception e) {
            log.error("MySQL 查询失败, sql: {}", sql, e);
            return Collections.emptyList();
        }
    }

    /**
     * 执行快速可用性探测（select 1）。
     *
     * @return 数据库可访问返回 true
     */
    public boolean ping() {
        Integer result = jdbcTemplate.queryForObject("select 1", Integer.class);
        return result != null && result == 1;
    }
}
