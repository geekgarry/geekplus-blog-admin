package com.geekplus.framework.interceptor;

import com.geekplus.common.core.visit.VisitCounter;
import com.geekplus.webapp.common.service.IPLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * author     : geekplus
 * date       : 6/15/25 12:13 AM
 * description: 用户访问系统的统计拦截器
 */
@Component
public class VisitInterceptor implements HandlerInterceptor {

    @Autowired
    private VisitCounter visitCounter;

//    @Autowired
//    private IPRecordService ipRecordService;

    @Autowired(required = false)//设置为false,允许IPLimitService为null
    private IPLimitService ipLimitService;

    private static final String VISIT_SESSION_ATTRIBUTE = "hasVisited";
    private static final long VISIT_THRESHOLD = 5000; // 5 秒

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 获取IP地址
        String ipAddress = getClientIp(request);
        HttpSession session = request.getSession();
        Long lastVisitTime = (Long) session.getAttribute("lastVisitTime");
        boolean hasVisited = session.getAttribute(VISIT_SESSION_ATTRIBUTE) != null;
        long currentTime = System.currentTimeMillis();

        // 尝试获取令牌,IP是否允许访问
//        if (!ipLimitService.tryAcquire(ipAddress)) {
//            //response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            response.setContentType("application/json;charset=UTF-8");
//            //out.write("Too many requests from this IP. Please try again later.");
//            Map<String,Object> map=new HashMap<>();
//            //String urlPath = httpRequest.getRequestURI();
//            map.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
//            map.put("msg","当前IP太多请求，请稍后再试！");//登录状态已失效，请重新登录！
//            response.getWriter().write(JSON.toJSONString(map));
//            response.getWriter().flush();
//            response.getWriter().close();
//            return false; // 拒绝请求
//        }

        //如果已经访问过，判断距离上次访问时间是否小于5秒
        if (hasVisited && lastVisitTime != null && (currentTime - lastVisitTime) < VISIT_THRESHOLD) {
            session.setAttribute("lastVisitTime", currentTime); //更新访问时间
            return true; // 5秒内的重复访问，不增加计数
        }

        // 增加访问计数器
        visitCounter.increment();

        // 记录IP地址
        //ipRecordService.recordIP(ipAddress);

        // 设置 Session 标记和时间
        session.setAttribute(VISIT_SESSION_ATTRIBUTE, true);
        session.setAttribute("lastVisitTime", currentTime);
        return true; // 继续执行请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 可以在这里记录访问次数，例如使用AOP或者直接调用统计服务
        // 例如，你可以在这里调用一个服务来增加访问计数
    }

    /**
     * 获取客户端 IP 地址
     * @param request HttpServletRequest
     * @return IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
