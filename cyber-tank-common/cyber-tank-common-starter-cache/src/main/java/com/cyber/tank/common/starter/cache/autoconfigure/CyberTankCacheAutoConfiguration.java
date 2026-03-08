package com.cyber.tank.common.starter.cache.autoconfigure;

import com.cyber.tank.common.cache.config.CyberTankCacheProperties;
import com.cyber.tank.common.cache.config.RedisConfig;
import com.cyber.tank.common.cache.utils.RedisKeyBuilder;
import com.cyber.tank.common.cache.utils.RedisService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@AutoConfiguration
@EnableConfigurationProperties(CyberTankCacheProperties.class)
@Import(RedisConfig.class)
public class CyberTankCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate, CyberTankCacheProperties cacheProperties) {
        RedisKeyBuilder.setDefaultPrefix(cacheProperties.getKeyPrefix());
        return new RedisService(redisTemplate);
    }
}
