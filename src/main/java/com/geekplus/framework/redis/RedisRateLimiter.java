package com.geekplus.framework.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 非精确限流示例：每个 key 在 windowSeconds 时间窗口只允许 maxCount 次访问。
 * key 可为 ip:path 或 userId:path
 */
@Component
public class RedisRateLimiter {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisRateLimiter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean tryAcquire(String key, int maxCount, int windowSeconds) {
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1L) {
            redisTemplate.expire(key, Duration.ofSeconds(windowSeconds));
        }
        return count != null && count <= maxCount;
    }
}
