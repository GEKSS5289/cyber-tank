package com.cyber.tank.common.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 通用能力（兼容旧调用）。
 * <p>
 * 为避免历史业务受影响，本类暂时保留。
 * 新业务建议优先使用 cyber-tank-common-cache 模块下的
 * {@code com.cyber.tank.common.cache.utils.RedisService}，能力更完整。
 */
@Deprecated
@Component("coreRedisService")
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 存入普通对象。
     */
    public <T> void setObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入普通对象并设置过期时间。
     */
    public <T> void setObject(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取对象。
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除对象。
     */
    public boolean deleteObject(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 自增操作（用于限流或计数）。
     */
    public long increment(String key) {
        Long result = redisTemplate.opsForValue().increment(key);
        return result == null ? 0L : result;
    }
}
