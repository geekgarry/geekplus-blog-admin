package com.geekplus.framework.interceptor;

import com.geekplus.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * author     : geekplus
 * email      :
 * date       : 12/9/25 2:42 PM
 * description: //TODO
 */
@Slf4j
@Component
public class CookieToHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("Intercepting request: {}", request.getRequestURI());
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                // 将所有请求头复制到目标请求
                request.setAttribute(headerName, headerValue);
            }
        }
        // 从Cookie中提取数据并注入请求头
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                if(Constant.USER_HEADER_TOKEN.equals(cookieName)) {
                    request.setAttribute(cookieName, cookieValue);
                }
            }
        }
        return true;
    }
}
