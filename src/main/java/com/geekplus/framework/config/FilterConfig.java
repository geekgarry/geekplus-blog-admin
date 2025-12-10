package com.geekplus.framework.config;

import com.geekplus.framework.filter.IpRateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author     : geekplus
 * date       : 6/15/25 5:43 AM
 * description: 过滤器配置，ip限流器等
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<IpRateLimitFilter> ipRateLimitFilterRegistration() {
        FilterRegistrationBean<IpRateLimitFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new IpRateLimitFilter());
        registration.addUrlPatterns("/**"); // 对所有请求进行限流
        registration.setOrder(1);
        return registration;
    }
}
