//package com.geekplus.framework.filter;
//
//import com.geekplus.common.util.encrypt.SignatureUtil;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * author     : geekplus
// * email      :
// * date       : 12/4/25 1:31 PM
// * description: //TODO
// */
//@Component
//public class ResourceAccessFilter extends OncePerRequestFilter {
//    // demo: 实例化，生产请通过配置注入 / KMS 获取 secretKey
//    private final SignatureUtil signer = new SignatureUtil("geek-plus-admin-123456", "v1");
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        String uri = req.getRequestURI();
//
//        // 拦截所有资源访问
//        if (uri.startsWith("/profile/")) {
//
//            // 获取签名参数
//            String ts = req.getParameter("ts");
//            String exp = req.getParameter("exp");
//            String sign = req.getParameter("sign");
//
//            // 必须带签名
//            if (ts == null || exp == null || sign == null) {
//                res.setStatus(401);
//                res.getWriter().write("Missing signature");
//                return;
//            }
//
//            // 验证签名是否合法
//            long tsL = Long.parseLong(ts);
//            long expL = Long.parseLong(exp);
//            long now = System.currentTimeMillis() / 1000;
//
//            if (now > tsL + expL) {
//                res.setStatus(401);
//                res.getWriter().write("URL expired");
//                return;
//            }
//
//            if (!signer.verify(uri, tsL, expL, sign)) {
//                res.setStatus(401);
//                res.getWriter().write("Invalid signature");
//                return;
//            }
//        }
//
//        chain.doFilter(req, res);
//    }
//}
