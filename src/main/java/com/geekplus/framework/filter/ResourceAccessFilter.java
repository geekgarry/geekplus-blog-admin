package com.geekplus.framework.filter;

import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.constant.Constant;
import com.geekplus.common.util.encrypt.SignatureUtil;
import com.geekplus.common.util.http.CookieUtil;
import com.geekplus.common.util.http.IPUtils;
import com.geekplus.common.util.http.ServletUtil;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.framework.redis.RedisRateLimiter;
import com.geekplus.webapp.system.entity.ResourceMetaData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author     : geekplus
 * email      :
 * date       : 12/4/25 1:31 PM
 * description: //TODO
 */
@Component
public class ResourceAccessFilter extends OncePerRequestFilter {

    @Autowired
    private SignatureUtil signer;

    @Autowired
    private RedisRateLimiter rateLimiter;

    // 文件实际存储的根目录，应从配置中读取
    //private final String FILE_STORAGE_ROOT_DIR = WebAppConfig.getProfile(); // 示例路径

    // 定义某些绝对不允许访问的路径模式（黑名单）
    private final Set<String> ABSOLUTE_BLACKLIST_PATTERNS = new HashSet<>(Arrays.asList(
            "/.git", "/WEB-INF", "/META-INF", "*.sh", "*.exe", // 敏感目录或文件类型
            "/config/", "/log/" // 敏感文件目录
    ));

    private static final int DRAFT = 0;
    private static final int PUBLISHED = 1;
    private static final int UNLISTED = 2;
    private static final int ARCHIVED = 3;

    // 正则表达式用于解析路径                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          a
    private static final Pattern ARTICLE_RESOURCE_PATTERN = Pattern.compile("^/article(/\\w+)+(/\\w+)*/(.+)$");
    private static final Pattern USER_AVATAR_PATTERN = Pattern.compile("^/avatar(/\\w+)+(/\\w+)*/(.+)$");
    private static final Pattern DOCUMENT_RESOURCE_PATTERN = Pattern.compile("^/document(/\\w+)+(/\\w+)*/(.+)$");
    private static final Pattern CAROUSEL_RESOURCE_PATTERN = Pattern.compile("^/carousel(/\\w+)+(/\\w+)*/(.+)$");
    private static final Pattern MUSIC_RESOURCE_PATTERN = Pattern.compile("^/music(/\\\\w+)+(/\\\\w+)*/(.+)$");
    private static final Pattern VIDEO_RESOURCE_PATTERN = Pattern.compile("^/video(/\\w+)+(/\\w+)*/(.+)$");
    private static final Pattern THUMBNAIL_RESOURCE_PATTERN = Pattern.compile("^/thumbnail(/\\w+)+(/\\w+)*/(.+)$");

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        //String uri = req.getRequestURI();
        //String fullPath = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //String bestMatchingPattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String logicalPath = new UrlPathHelper().getPathWithinApplication(req);
        PathMatcher pathMatcher = new AntPathMatcher();
        // 拦截所有资源访问
        if (pathMatcher.match("/profile/**", logicalPath) || logicalPath.startsWith("/profile/")) {

            // 获取签名参数
            String tsStr = req.getParameter("ts");
            //String exp = req.getParameter("exp");
            String sign = req.getParameter("sign");
            String ip = req.getParameter("ip");
            String skid = req.getParameter("skid");

            // 必须带签名
//            if (ts == null || exp == null || sign == null) {
//                res.setStatus(401);
//                res.getWriter().write("Missing signature");
//                return;
//            }

            // 验证签名是否合法
            //long expL = Long.parseLong(exp);
            //long now = System.currentTimeMillis() / 1000;

//            if (now > tsL + expL) {
//                res.setStatus(401);
//                res.getWriter().write("URL expired");
//                return;
//            }

//            if (!signer.verify(uri, tsL, expL, sign)) {
//                res.setStatus(401);
//                res.getWriter().write("Invalid signature");
//                return;
//            }
            String authorization = req.getHeader(Constant.USER_HEADER_TOKEN);
            if(StringUtils.isEmpty(authorization)) {
                authorization = CookieUtil.getCookieValue(req, Constant.USER_HEADER_TOKEN);
            }
            String clientIp = IPUtils.getIp(req);
            if(StringUtils.isEmpty(authorization)) {
                // 1. 参数基本校验
                if ((StringUtils.isEmpty(tsStr) || StringUtils.isEmpty(sign))) {
                    //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing signature parameters");
                    return;
                }

                long ts;
                try {
                    ts = Long.parseLong(tsStr);
                } catch (NumberFormatException e) {
                    //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid timestamp");
                    return;
                }

//        long now = Instant.now().getEpochSecond();
//        if (now > ts) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "URL expired");
//        }

                // If IP bound sign used, prefer ipInUrl; otherwise use clientIp
                String ipToVerify = ip != null ? ip : null;

                //String requestURI = request.getRequestURI();
                //String contextPath = request.getContextPath();

                // 2. signature verify (production: lookup UrlSigner by skid)
                boolean ok = signer.verify(logicalPath, ts, sign);
                if (!ok) {
                    //throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid signature");
                    return;
                }
            }
            // 3. rate limit by ip + path (adjust params as needed)
            String rateKey = "limit:" + clientIp + ":" + extractResourcePath(req);
            boolean allow = rateLimiter.tryAcquire(rateKey, 60, 60); // example: 60 requests per 60s
            if (!allow) {
                //throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
                return;
            }

            // 4. read file from disk
            Path file = Paths.get(WebAppConfig.getProfile(), extractResourcePath(req));
            if (!Files.exists(file) || Files.isDirectory(file)) {
                //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                return;
            }
        }

        chain.doFilter(req, res);
    }

    private String extractResourcePath(HttpServletRequest request) {
        String uri = request.getRequestURI(); // e.g. /secure-image/images/foo.jpg
        return uri.replaceFirst("/profile", "");
    }

    /**
     * 【系统级黑名单检查逻辑】
     */
    private boolean isPathBlacklisted(String logicalPath) {
        String lowerCasePath = logicalPath.toLowerCase();
        for (String pattern : ABSOLUTE_BLACKLIST_PATTERNS) {
            if (lowerCasePath.contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 内部类：解析后的路径信息dff
     */
    private static class PathInfo {
        enum ResourceType { ARTICLE_ATTACHMENT, USER_AVATAR, PUBLIC_RESOURCE, USER_PRIVATE_FILE }
        ResourceType type;
        String fullPath;
        Long entityId; // 如 articleId 或 userId
        String fileName;
        // ... 其他可能信息

        public PathInfo(String fullPath, Long entityId, String fileName) {
            this.fullPath = fullPath;
            this.entityId = entityId;
            this.fileName = fileName;
        }

        public ResourceType getType() { return type; }
        public String getFullPath() { return fullPath; }
        public Long getEntityId() { return entityId; }
        public String getFileName() { return fileName; }
    }

    /**
     * 【核心】根据逻辑路径解析文件类型和相关ID
     */
    private PathInfo parseLogicalPath(String logicalPath) {
        Matcher articleMatcher = ARTICLE_RESOURCE_PATTERN.matcher(logicalPath);
        if (articleMatcher.matches()) {
            return new PathInfo(logicalPath, null, articleMatcher.group(3));
        }

        Matcher avatarMatcher = USER_AVATAR_PATTERN.matcher(logicalPath);
        if (avatarMatcher.matches()) {
            return new PathInfo(logicalPath, null, avatarMatcher.group(3));
        }

        Matcher documentMatcher = DOCUMENT_RESOURCE_PATTERN.matcher(logicalPath);
        if (documentMatcher.matches()) {
            return new PathInfo(logicalPath, null, documentMatcher.group(3));
        }

        Matcher carouselMatcher = CAROUSEL_RESOURCE_PATTERN.matcher(logicalPath);
        if (carouselMatcher.matches()) {
            return new PathInfo(logicalPath, null, carouselMatcher.group(3));
        }

        Matcher thumbnailMatcher = THUMBNAIL_RESOURCE_PATTERN.matcher(logicalPath);
        if (thumbnailMatcher.matches()) {
            return new PathInfo(logicalPath, null, thumbnailMatcher.group(3));
        }

        Matcher videoMatcher = VIDEO_RESOURCE_PATTERN.matcher(logicalPath);
        if (videoMatcher.matches()) {
            return new PathInfo(logicalPath, null, videoMatcher.group(3));
        }

        Matcher musicMatcher = MUSIC_RESOURCE_PATTERN.matcher(logicalPath);
        if (musicMatcher.matches()) {
            return new PathInfo(logicalPath, null, musicMatcher.group(3));
        }
        return null; // 无法识别的路径模式
    }

    /**
     * 【核心】动态权限控制逻辑
     * 根据解析出的 PathInfo 和 Shiro 的当前 Subject 进行权限判断
     */
//    private boolean checkDynamicResourceAccessPermission(PathInfo pathInfo) {
//        Subject currentUser = SecurityUtils.getSubject();
//        Long currentUserId = userService.getSysUserId(ServletUtil.getRequest());
//        // 获取当前登录用户ID (可能为 null)
//        ResourceMetaData resourceMetaData = new ResourceMetaData();
//        resourceMetaData.setLogicalPath(Constant.RESOURCE_PREFIX+pathInfo.getFullPath());
//        ResourceMetaData resourceMetaDataObj = Optional.ofNullable(resourceService.queryResourceMetaDataList(resourceMetaData).get(0)).orElse(null);
//        if(resourceMetaDataObj.getIsAvailable().equals(0)) {
//            if(resourceMetaDataObj.getAccessLevel().equals("PUBLIC")) {
//                return true;
//            }else if(resourceMetaDataObj.getAccessLevel().equals("AUTHENTICATED")) {
//                return false;
//            }else if(resourceMetaDataObj.getAccessLevel().equals("PRIVATE") && resourceMetaDataObj.getOwnerUserId().equals(currentUserId)) {
//                return true;
//            }
//        }
//        return false; // 默认情况下，无权限
//    }
}
