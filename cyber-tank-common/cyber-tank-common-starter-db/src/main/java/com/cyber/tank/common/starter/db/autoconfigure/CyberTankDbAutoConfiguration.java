package com.cyber.tank.common.starter.db.autoconfigure;

import com.cyber.tank.common.db.config.MybatisPlusConfig;
import com.cyber.tank.common.db.utils.MySqlService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 数据库 starter 自动配置。
 */
@AutoConfiguration
@Import(MybatisPlusConfig.class)
@ConditionalOnClass(MySqlService.class)
public class CyberTankDbAutoConfiguration {

    /**
     * 统一暴露数据库通用服务，业务模块直接注入即可使用。
     */
    @Bean
    @ConditionalOnMissingBean
    public MySqlService mySqlService() {
        return new MySqlService();
    }
}
