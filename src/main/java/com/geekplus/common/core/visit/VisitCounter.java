package com.geekplus.common.core.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * author     : geekplus
 * description: 系统访问计数器
 */
@Component
public class VisitCounter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String VISIT_COUNT_KEY = "visit_count";

    @PostConstruct
    public void init() {
        // 从 Redis 加载访问次数
        if (Objects.isNull(redisTemplate.opsForValue().get(VISIT_COUNT_KEY))) {
            redisTemplate.opsForValue().set(VISIT_COUNT_KEY, "0");
        }
    }

    public long increment() {
        return redisTemplate.opsForValue().increment(VISIT_COUNT_KEY);
    }

    public long getCount() {
        String countStr = redisTemplate.opsForValue().get(VISIT_COUNT_KEY);
        return countStr != null ? Long.parseLong(countStr) : 0L;
    }
}
