//package com.cyber.tank.common.starter.db.autoconfigure;
//
//import com.cyber.tank.common.db.config.MybatisPlusConfig;
//import com.cyber.tank.common.db.utils.MySqlService;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//
//@AutoConfiguration
//@Import(MybatisPlusConfig.class)
//public class CyberTankDbAutoConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean
//    public MySqlService mySqlService() {
//        return new MySqlService();
//    }
//}
