package com.geekplus.webapp.function.controller;

import com.geekplus.common.domain.LoginUser;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.service.IGpCarouselService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.constant.Constant;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.common.util.file.FileUploadUtils;
import com.geekplus.common.util.file.FileUtils;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.common.util.uuid.IdUtils;
import com.geekplus.framework.config.ServerConfig;
import com.geekplus.webapp.function.entity.GpCarousel;
import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.service.ResourceMetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 首页跑马灯轮播图Controller
 *
 * @author 佚名
 * @date 2023-03-12
 */
@Slf4j
@RestController
@RequestMapping("/geekplus/carousel")
public class GpCarouselController extends BaseController
{
    @Autowired
    private IGpCarouselService gpCarouselService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ResourceMetaDataService resourceService;

    /**
     * 查询首页跑马灯轮播图列表
     */
    @GetMapping("/list")
    public PageDataInfo list(GpCarousel gpCarousel)
    {
        startPage();
        List<GpCarousel> list = gpCarouselService.selectGpCarouselList(gpCarousel);
        return getDataTable(list);
    }

    /**
     * 导出首页跑马灯轮播图列表
     */
    @Log(title = "首页跑马灯轮播图", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpCarousel gpCarousel)
    {
        List<GpCarousel> list = gpCarouselService.selectGpCarouselList(gpCarousel);
        ExcelUtil<GpCarousel> util = new ExcelUtil<GpCarousel>(GpCarousel.class);
        return util.exportExcel(list, "carousel");
    }

    /**
     * 获取首页跑马灯轮播图详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Integer id)
    {
        return Result.success(gpCarouselService.selectGpCarouselById(id));
    }

    /**
     * 新增首页跑马灯轮播图
     */
    @Log(title = "新增首页跑马灯轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody GpCarousel gpCarousel)
    {
        if(gpCarouselService.insertGpCarousel(gpCarousel)>0){
            ResourceMetaData resourceMetaData2 = new ResourceMetaData();
            resourceMetaData2.setLogicalPath(gpCarousel.getCarouselImg());
            if(gpCarousel.getIsDisplay().equals('1')) {
                resourceMetaData2.setAccessLevel("PUBLIC");
                resourceMetaData2.setIsAvailable(0);
            }
            resourceService.modifyResourceMetaByFilePath(resourceMetaData2);
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 修改首页跑马灯轮播图
     */
    @Log(title = "修改首页跑马灯轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody GpCarousel gpCarousel)
    {
        if(gpCarouselService.updateGpCarousel(gpCarousel)>0){
            ResourceMetaData resourceMetaData = new ResourceMetaData();
            resourceMetaData.setLogicalPath(gpCarousel.getCarouselImg());
            if(gpCarousel.getIsDisplay().equals('1')) {
                resourceMetaData.setAccessLevel("PUBLIC");
                resourceMetaData.setIsAvailable(0);
            }
            resourceService.modifyResourceMetaByFilePath(resourceMetaData);
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除首页跑马灯轮播图
     */
    @Log(title = "删除首页跑马灯轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Integer[] ids)
    {
        return toResult(gpCarouselService.deleteGpCarouselByIds(ids));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/uploadCarousel")
    public Result uploadFile( MultipartFile file) throws Exception
    {
        LoginUser loginUser= (LoginUser) SecurityUtils.getSubject().getPrincipal();
        try
        {
            // 上传文件路径
            //String filePath = WebAppConfig.getUploadPath();
            String uploadDir=WebAppConfig.getProfile()+"/carousel";
            // 上传并获取文件名称
            String fileName = file.getOriginalFilename();
            ResourceMetaData resource =new ResourceMetaData();
            resource.setOriginalFileName(fileName);
            String extension = FileUploadUtils.getExtension(file);
            fileName = DateUtil.dateTime() + IdUtils.getSHAFileULID() + "." + extension;

            // 上传并返回新文件名称
            //String fileName = FileUploadUtils.upload(filePath, file);
            File desc = new File(uploadDir + File.separator + fileName);
            file.transferTo(desc);
            //String pathFileName = getPathFileName(baseDir, fileName);
            String resultFileName= Constant.RESOURCE_PREFIX+"/carousel/"+fileName;
            //log.info("用户请求URL信息："+serverConfig.getUrl());//当前网站的网址
            Result ajax = Result.success();
            ajax.put("fileName", fileName);
            ajax.put("url", resultFileName);
            resource.setAccessLevel("PRIVATE");
            resource.setActualStoragePath(desc.getAbsolutePath());
            resource.setContentType("image");
            resource.setEntityType(2);
            resource.setIsAvailable(1);
            resource.setLogicalPath(resultFileName);
            resource.setStoredFileName(fileName);
            resource.setOwnerUserId(loginUser.getUserId());
            resourceService.addResourceMetaData(resource);
            return ajax;
        }
        catch (Exception e)
        {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询文件里的所有图片，读取某个文件夹下的所有文件
     */
    @Log(title = "首页跑马灯轮播图", businessType = BusinessType.DELETE)
    @GetMapping("/getImageList")
    public Result listFileImage()
    {
        try{
            List<String> listImage= FileUtils.readFileImage(WebAppConfig.getProfile(),File.separator +"carousel");
            return Result.success(listImage);
        }catch(IOException e){
            return Result.success(e.getMessage());
        }
    }

    /**
     * 查询文件里的所有图片，删除某个图片文件
     */
    @Log(title = "删除文件夹里的图片文件", businessType = BusinessType.DELETE)
    @GetMapping("/deleteFile")
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

    /**
     * 去掉指定字符串的开头的指定字符
     * @param stream 原始字符串
     * @param trim 要删除的字符串
     * @return
     */
    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

}
