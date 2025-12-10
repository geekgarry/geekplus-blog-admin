package com.geekplus.webapp.common.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * author     : geekplus
 * email      :
 * date       : 6/15/25 12:17 AM
 * description: //TODO
 */
@Service
public class IPLimitService {

    //每秒允许的访问次数
    private final double permitsPerSecond = 100;

    // 使用 LoadingCache 存储每个IP的RateLimiter
    private LoadingCache<String, RateLimiter> ipRateLimiterCache;

    @PostConstruct
    public void init() {
        ipRateLimiterCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES) // 缓存有效期1分钟
                .build(new CacheLoader<String, RateLimiter>() {
                    @Override
                    public RateLimiter load(String ip) {
                        // 创建 RateLimiter，设置每秒允许的请求数量
                        return RateLimiter.create(permitsPerSecond); // 创建RateLimiter
                    }
                });
    }

    /**
     * 尝试获取令牌，IP是否允许访问
     * @param ip 客户端 IP 地址
     * @return true: 允许访问, false: 限流
     */
    public boolean tryAcquire(String ip) {
        // 从缓存中获取 RateLimiter
        RateLimiter rateLimiter = ipRateLimiterCache.getUnchecked(ip);
        // 尝试获取令牌
        return rateLimiter.tryAcquire();
    }
}
