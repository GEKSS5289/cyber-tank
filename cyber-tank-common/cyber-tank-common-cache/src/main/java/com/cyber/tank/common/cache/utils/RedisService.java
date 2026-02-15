package com.cyber.tank.common.cache.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis 通用工具。
 * <p>
 * 封装项目常用的 KV / Hash / 过期控制等能力，
 * 统一业务层对 Redis 的使用方式。
 */
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 写入普通对象。
     */
    public <T> void setObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写入普通对象并设置过期时间。
     */
    public <T> void setObject(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 读取普通对象。
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量删除 key。
     */
    public long deleteObjects(Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        return count == null ? 0L : count;
    }

    /**
     * 删除单个 key。
     */
    public boolean deleteObject(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 设置 Key 过期时间。
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获取 Key 剩余过期时间，单位秒。
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire == null ? -1L : expire;
    }

    /**
     * 判断 Key 是否存在。
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 自增操作（常用于计数、限流）。
     */
    public long increment(String key) {
        Long result = redisTemplate.opsForValue().increment(key);
        return result == null ? 0L : result;
    }

    /**
     * Hash 批量写入。
     */
    public void putHashAll(String key, Map<String, Object> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    /**
     * Hash 按字段读取。
     */
    @SuppressWarnings("unchecked")
    public <T> T getHashValue(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }
}
