package com.geekplus.framework.filter;

import com.alibaba.fastjson2.JSON;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * author     : geekplus
 * date       : 6/15/25 5:40 AM
 * description: ip限流
 */
@Component
public class IpRateLimitFilter extends OncePerRequestFilter {
    private static final int MAX_REQUESTS_PER_SECOND = 70; // 每个 IP 每秒最大请求数
    private final Map<String, RateLimiter> rateLimiters = new HashMap<>();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = getClientIp(request);
        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(clientIp, k -> RateLimiter.create(MAX_REQUESTS_PER_SECOND));
        if (!rateLimiter.tryAcquire()) {
            //response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            Map<String,Object> map=new HashMap<>();
            map.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
            map.put("msg","当前IP请求超过限制，请稍后再试！");//登录状态已失效，请重新登录！
            response.getWriter().write(JSON.toJSONString(map));
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        filterChain.doFilter(request, response);
    }
    private String getClientIp(HttpServletRequest request) {
        String xffHeader = request.getHeader("X-Forwarded-For");
        if (xffHeader == null) {
            return request.getRemoteAddr();
        }
        return xffHeader.split(",")[0];
    }
}
