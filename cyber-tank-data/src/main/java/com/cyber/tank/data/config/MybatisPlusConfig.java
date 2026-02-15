package com.cyber.tank.data.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 3.5.16 分页插件配置
 */
@Configuration
@MapperScan("com.cyber.tank")
/**
 * MybatisPlusConfig 的核心定义。
 */
public class MybatisPlusConfig {

    @Bean
    /**
     * mybatisPlusInterceptor 方法。
     * @return 执行结果。
     */
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 1. 初始化分页拦截器
        // 这里的 DbType.MYSQL 会让插件生成符合 MySQL 语法的 limit 语句
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);

        // 2. 设置单页最大限制 (避免前端传参过大导致内存溢出)
        paginationInterceptor.setMaxLimit(1000L);

        // 3. 开启溢出处理 (如果请求页码大于总页数，默认返回第一页或最后一页，可选)
        paginationInterceptor.setOverflow(true);

        interceptor.addInnerInterceptor(paginationInterceptor);
        return interceptor;
    }
}