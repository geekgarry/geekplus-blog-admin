package com.geekplus.webapp.common;

import cn.hutool.core.codec.Base64Encoder;
import com.geekplus.common.util.http.ServletUtil;
import com.geekplus.framework.jwtshiro.JwtUtil;
import com.geekplus.webapp.common.service.SysUserTokenService;
import com.geekplus.webapp.function.service.IGpArticlesService;
import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.service.ResourceMetaDataService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.*;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.constant.Constant;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.common.util.google.TranslateTTS;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.common.util.file.FileUploadUtils;
import com.geekplus.common.util.file.FileUtils;
import com.geekplus.common.util.translate.TranslatorUtil;
import com.geekplus.common.util.uuid.UUIDUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用请求处理
 *
 * @author
 */
@RestController
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SysUserTokenService userService;

    @Autowired
    private IGpArticlesService  articlesService;

    @Autowired
    private ResourceMetaDataService resourceService;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = WebAppConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFileCategory(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public Result uploadFile(@RequestPart("file") MultipartFile file, @RequestParam(name = "pathName", required = false) String pathName) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = WebAppConfig.getUploadPath();
            if(pathName==null||"".equals(pathName)){
                if(FileUtils.isImageFile(file)){
                    filePath=WebAppConfig.getProfile() + File.separator+"article";
                }else if(FileUtils.isVideoFile(file)){
                    filePath=WebAppConfig.getProfile() + File.separator+"video";
                }else if(FileUtils.isAudioFile(file)){
                    filePath=WebAppConfig.getProfile() + File.separator+"music";
                }else {
                    filePath=WebAppConfig.getProfile() + File.separator+"document";
                }
                //filePath = WebAppConfig.getProfile() + File.separator + pathName;
            }else{
                filePath = WebAppConfig.getProfile() + File.separator + pathName;
            }
            // 上传并返回新文件名称
            //String fileName = FileUploadUtils.upload(filePath, file);
            Map fileMap = FileUploadUtils.upload2(filePath + File.separator + DateUtil.datePath(), file);
            //String url = serverConfig.getUrl() + fileName;
            Result ajax = Result.success();
            ajax.put("fileName", fileMap.get("fileName"));
            ajax.put("originalName",file.getOriginalFilename());
            ajax.put("url", fileMap.get("fileUrl"));
            return ajax;
        }
        catch (Exception e)
        {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传文件请求适用于文章等特殊场景
     */
    @PostMapping("/common/uploadFile")
    public Result uploadFileForArticle(@RequestPart("file") MultipartFile file, @RequestParam(name = "fileTitle", required = false) String fileTitle) throws Exception
    {
//        if(!checkFormats(file.getOriginalFilename())){
//            return Result.error("上传图片格式不是png,jpg或jpeg！");
//        }
        try
        {
            // 上传文件路径,加上以日期为路径的一个目录
            //String filePath = WebAppConfig.getUploadPath();
            String realFilePath;
            if(FileUtils.isImageFile(file)){
                realFilePath= File.separator+"article"+File.separator+ DateUtil.datePath();
            }else if(FileUtils.isVideoFile(file)){
                realFilePath=File.separator+"video"+File.separator+ DateUtil.datePath();
            }else if(FileUtils.isAudioFile(file)){
                realFilePath=File.separator+"music"+File.separator+ DateUtil.datePath();
            }else {
                realFilePath=File.separator+"document"+File.separator+ DateUtil.datePath();
            }

            String uploadDir= WebAppConfig.getProfile()+realFilePath;
            // 上传并获取文件名称
            String fileName = "";
            String originalName=file.getOriginalFilename();
            String extension = FileUploadUtils.getExtension(file);
            //String uuidFileName = UUID.randomUUID().toString() + ".png";
            //目标文件
            //File dest = new File(uploadDir + "head_img" ,uuidFileName);
            //保存文件
            //file.transferTo(dest);
            fileName = UUIDUtil.getFileUUID() + "." + extension;

            // 上传并返回新文件名称
            //String fileName = FileUploadUtils.upload(filePath, file);
            //File desc = new File(uploadDir + File.separator + fileName);
            File desc =FileUtils.getExistFileCategory(uploadDir + File.separator + fileName);
            file.transferTo(desc);
            //String pathFileName = getPathFileName(baseDir, fileName);
            String resultFileName= Constant.RESOURCE_PREFIX+realFilePath+File.separator+fileName;
            //String url = serverConfig.getUrl() + resultFileName;
            //log.info("用户请求URL信息："+serverConfig.getUrl());
            Result ajax = Result.success();
            ajax.put("fileName", fileName);
            //ajax.put("imgTitle",title);
            ajax.put("originalFileName", originalName);
            ajax.put("url", resultFileName);
            return ajax;
        }
        catch (Exception e)
        {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = WebAppConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constant.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 删除单个文件
     */
    @Log(title = "删除文件夹里的文件", businessType = BusinessType.DELETE)
    @GetMapping("/common/deleteFile")
    public Result deleteFile(String filePath)
    {
        String profile= Constant.RESOURCE_PREFIX;//profile
        String allFilePath=WebAppConfig.getProfile()+filePath.replace(profile,"");
        int flag=FileUtils.deleteFileCategory(allFilePath);
        if(flag>0){
            return Result.success("删除文件成功！");
        }else{
            return Result.success("删除文件失败！");
        }
    }

//    @PostConstruct
//    public void setupStorageDir() {
//        // 确保文件存储目录存在
//        File dir = new File(FILE_STORAGE_ROOT_DIR);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//    }

    /**
     * 统一的文件资源代理访问接口，捕获所有子路径
     * 例如：/api/resource/articles/123/image.jpg
     *
     * @param request HttpServletRequest 用于获取路径变量和 Accept 头
     * @return 文件内容或下载响应
     */
//    @GetMapping("/profile/**")
//    public Callable<ResponseEntity<?>> getSignedImageAsync(HttpServletRequest request,
//                                                           @RequestParam(name = "ts", required = false, defaultValue = "") String tsStr,
//                                                           @RequestParam(name = "sign", required = false, defaultValue = "") String sign,
//                                                           @RequestParam(name = "ip", required = false, defaultValue = "") String ipInUrl,
//                                                           @RequestParam(name = "skid", required = false, defaultValue = "") String skid) {
//        return () -> getSignedImageInternal(request, tsStr, sign, ipInUrl, skid);
//    }

//    public ResponseEntity<?> getSignedImageInternal(HttpServletRequest request, String tsStr, String sign, String ipInUrl, String skid
//                                                      ) throws Exception {
//        //String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//        //String bestMatchingPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
//        String logicalPath = new UrlPathHelper().getPathWithinApplication(request);
//        String authorization = request.getHeader(Constant.USER_HEADER_TOKEN);
//        if(StringUtils.isEmpty(authorization)) {
//            authorization = CookieUtil.getCookieValue(request, Constant.USER_HEADER_TOKEN);
//        }
//        String clientIp = IPUtils.getIp(request);
//        if(StringUtils.isEmpty(authorization)) {
//            // 1. 参数基本校验
//            if ((StringUtils.isEmpty(tsStr) || StringUtils.isEmpty(sign))) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing signature parameters");
//            }
//
//            long ts;
//            try {
//                ts = Long.parseLong(tsStr);
//            } catch (NumberFormatException e) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid timestamp");
//            }
//
////        long now = Instant.now().getEpochSecond();
////        if (now > ts) {
////            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "URL expired");
////        }
//
//            // If IP bound sign used, prefer ipInUrl; otherwise use clientIp
//            String ipToVerify = ipInUrl != null ? ipInUrl : null;
//
//            //String requestURI = request.getRequestURI();
//            //String contextPath = request.getContextPath();
//
//            // 2. signature verify (production: lookup UrlSigner by skid)
//            boolean ok = signer.verify(logicalPath, ts, sign);
//            if (!ok) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid signature");
//            }
//        }
//        // 3. rate limit by ip + path (adjust params as needed)
//        String rateKey = "limit:" + clientIp + ":" + extractResourcePath(request);
//        boolean allow = rateLimiter.tryAcquire(rateKey, 60, 60); // example: 60 requests per 60s
//        if (!allow) {
//            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
//        }
//
//        // 4. read file from disk
//        Path file = Paths.get(WebAppConfig.getProfile(), extractResourcePath(request));
//        if (!Files.exists(file) || Files.isDirectory(file)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//
//        //PathResource resource = new PathResource(file);
//        byte[] bytes = Files.readAllBytes(file);
//        //Resource resource = new FileSystemResource(actualFilePath.toFile());
//        //String contentType = Files.probeContentType(actualFilePath); // 尝试探测文件类型
//        //InputStream input = Files.newInputStream(file);
//        //InputStreamResource body = new InputStreamResource(input);
//        String contentType = Files.probeContentType(file);
//        if (contentType == null) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        // 如果是图片或文本，尝试在浏览器内联显示
////        if (contentType.startsWith("image/") || contentType.startsWith("video/") || contentType.startsWith("audio/") || contentType.startsWith("text/")) {
////            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
////            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pathInfo.getFileName() + "\"");
////        } else { // 其他文件类型，建议下载
////            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
////            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pathInfo.getFileName() + "\"");
////        }
//        //headers.setContentType(MediaTypeFactory.getMediaType(file.getName()).orElse(MediaType.APPLICATION_OCTET_STREAM));
//        //return new ResponseEntity<>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);
//        headers.setContentType(MediaType.parseMediaType(contentType));
//        headers.setContentLength(bytes.length);
//        // Cache control: since URL includes ts, can set caching; or set no-cache if dynamic
//        headers.setCacheControl(CacheControl.maxAge(2592000, TimeUnit.SECONDS).cachePublic());
//        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
//        //ResponseEntity.ok().headers(headers)
//                //.contentType(MediaType.parseMediaType(contentType))
//                //.body(body);
//        //String realPath = Constant.RESOURCE_PLUS + extractResourcePath(request);
//        //return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(URI.create(realPath)).build();
//    }

    /**
     * 批量删除文件List
     */
    @Log(title = "删除文件夹里的文件List", businessType = BusinessType.DELETE)
    @PostMapping("/common/deleteFileList")
    public Result deleteFileList(@RequestBody List<Map> filePaths)
    {
        int length=filePaths.size();
        for (int i = 0; i < filePaths.size(); i++) {
            String filePath=filePaths.get(i).get("filePath").toString();
            String profile= Constant.RESOURCE_PREFIX;//profile
            String allFilePath=WebAppConfig.getProfile()+filePath.replace(profile,"");
            int ds=FileUtils.deleteFileCategory(allFilePath);
            length-=ds;
        }
        if(length==0){
            return Result.success("删除文件成功！");
        }else{
            return Result.success("删除文件失败！");
        }
    }

    /**
     * 查询文件里的所有图片，删除某个图片文件
     */
    @Log(title = "删除文件夹里的图片文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/common/batchDeleteFile")
    public Result batchDeleteFile(String[] filePaths)
    {
        for(String filePath:filePaths) {
            String profile = Constant.RESOURCE_PREFIX;//profile
            String allFilePath = WebAppConfig.getProfile() + filePath.replace(profile, "");
            FileUtils.deleteFileCategory(allFilePath);
//            if (flag == true) {
//                return Result.success("删除文件成功！");
//            } else {
//                return Result.success("删除文件失败！");
//            }
        }
        return Result.success("删除文件成功！");
    }

    /**
      * @Author geekplus
      * @Description //文字生成二维码图片
      * @Param [qrCodeText]
      * @Throws
      * @Return {@link java.lang.String}
      */
    @GetMapping("/common/getQRCode")
    public Result getQRCodeImg(@RequestParam String qrCodeText){
        String base64 = "";
        // 需要生成的二维码的文字、地址 qrCodeText
        // 创建二维码
        try {
            Map<EncodeHintType, String> character = new HashMap<>();
            // 设置字符集
            character.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 设置二维码的四个参数
            // 需要生成的字符串，类型设置为二维码，二维码宽度，二维码高度，字符串字符集
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeText,
                    BarcodeFormat.QR_CODE, Constant.QRCODE_SIZE, Constant.QRCODE_SIZE, character);
            // 二维码像素，也就是上面设置的 500
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // 创建二维码对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // 按照上面定义好的二维码颜色编码生成二维码
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            // 1、第一种方式// 生成的二维码图片对象转 base64
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // 设置图片的格式
            ImageIO.write(image, "png", stream);
            // 生成的二维码base64
            base64 = Base64Encoder.encode(stream.toByteArray());
            } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success("生成二维码成功",base64);
    }

    /**
      * @Author geekplus
      * @Description //英译汉，翻译api
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/translate/en2zh")
    public Result translateLang(@RequestParam("words") String englishWords){
        String chineseWords =TranslatorUtil.translate(englishWords);
        return Result.success(chineseWords);
    }

    /**
     * @Author geekplus
     * @Description //翻译api
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/translate/all")
    public Result translateLanguage(@RequestParam("words") String words,@RequestParam("sl") String sl,@RequestParam("tl") String tl){
        String fanYiWords =TranslatorUtil.translate(words,sl,tl);
        return Result.success(fanYiWords);
    }

    /**
     * @Author geekplus
     * @Description //翻译api
     * @Param
     * @Throws
     * @Return {@link }
     */
    @PostMapping("/translate/ttsZH_CN")
    public void translateWordsTTS(HttpServletResponse response, @RequestBody String ttsText){
        TranslateTTS.getGoogleTTSVoice(response,"",ttsText);
    }

    /**
     * @Author geekplus
     * @Description //翻译api
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/translate/ttsChinese")
    public void translateChineseTTS(HttpServletResponse response, @RequestParam("ttsText") String ttsText){
        TranslateTTS.getGoogleTTSVoice(response,"",ttsText);
    }
}
