package com.geekplus.webapp.function.controller;

import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.constant.Constant;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.util.file.FileUploadUtils;
import com.geekplus.common.util.file.FileUtils;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.common.util.uuid.UUIDUtil;
import com.geekplus.framework.config.ServerConfig;
import com.geekplus.webapp.function.entity.GpMusic;
import com.geekplus.webapp.function.service.GpMusicService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在线音乐 在线音乐
 * Created by CodeGenerator on 2023/09/29.
 */
@RestController
@RequestMapping("/geekplus/music")
public class GpMusicController extends BaseController {
    @Resource
    private GpMusicService gpMusicService;
    @Resource
    private ServerConfig serverConfig;

    /**
     * 增加 在线音乐
     */
    @PostMapping("/add")
    public Result add(@RequestBody GpMusic gpMusic) {
        return toResult(gpMusicService.insertGpMusic(gpMusic));
    }

    /**
     * 增加 在线音乐
     */
    @PostMapping("/batchAdd")
    public Result batchAdd(@RequestBody List<GpMusic> gpMusic) {
    return toResult(gpMusicService.batchInsertGpMusicList(gpMusic));
    }

    /**
     * 通用上传文件请求
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestPart("file") MultipartFile file) throws Exception
    {
        //if(!checkFormats(file.getOriginalFilename())){
        //    return Result.error("上传图片格式不是png,jpg或jpeg！");
        //}
        try
        {
            String fileName = "";
            String originalName=file.getOriginalFilename();
            String originalFileName=originalName.substring(0,originalName.lastIndexOf("."));
            String extension = FileUploadUtils.getExtension(file);
            // 上传文件路径,加上以日期为路径的一个目录
            //String filePath = WebAppConfig.getUploadPath();
            String realFilePath=File.separator+"music"+File.separator+originalFileName;
//            if(FileUtils.isImageFile(file)){
//                realFilePath="/article/"+ DateUtils.dateTime();
//            }else {
//                realFilePath="/document/"+DateUtils.dateTime();
//            }
            String uploadDir= WebAppConfig.getProfile()+realFilePath;
            // 上传并获取文件名称
            //String uuidFileName = UUID.randomUUID().toString() + ".png";
            //目标文件
            //File dest = new File(uploadDir + "head_img" ,uuidFileName);
            //保存文件
            //file.transferTo(dest);
            fileName = UUIDUtil.getUUIDTimeStamp() + "." + extension;

            // 上传并返回新文件名称
            //String fileName = FileUploadUtils.upload(filePath, file);
            //File desc = new File(uploadDir + File.separator + fileName);
            File desc =FileUtils.getExistFileCategory(uploadDir + File.separator + fileName);
            file.transferTo(desc);
            //String pathFileName = getPathFileName(baseDir, fileName);
            String resultFileName= Constant.RESOURCE_PREFIX+realFilePath+File.separator+fileName;
            //log.info("用户请求URL信息："+serverConfig.getUrl());//当前网站的网址
            Result ajax = Result.success();
            ajax.put("fileName", fileName);
            ajax.put("originalFileName", originalFileName);
            ajax.put("url", resultFileName);
            return ajax;
        }
        catch (Exception e)
        {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除 在线音乐
     */
    @Log(title = "删除", businessType = BusinessType.DELETE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Integer id) {
        return toResult(gpMusicService.deleteGpMusicById(id));
    }

    /**
     * 批量删除 在线音乐
     */
    @Log(title = "批量删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Integer[] ids) {
        return toResult(gpMusicService.deleteGpMusicByIds(ids));
    }

    /**
     * 更新 在线音乐
     */
    @Log(title = "修改", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public Result edit(@RequestBody GpMusic gpMusic) {
        return toResult(gpMusicService.updateGpMusic(gpMusic));
    }

    /**
     * 单条数据详情 在线音乐
     */
    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        GpMusic gpMusic = gpMusicService.selectGpMusicById(id);
        return Result.success(gpMusic);
    }

    /**
     * @Author geekplus
     * @Description //读取文件夹下的所有音乐文件
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/readMusicFileList")
    public Result readCurrentFileList(String folder) throws IOException {
        String readFolder=WebAppConfig.getProfile()+folder;
        if(folder==null||"".equals(folder)) {
            readFolder=WebAppConfig.getProfile();
        }if(folder=="/"||"/".equals(folder)) {
            readFolder=WebAppConfig.getProfile();
        }else if(folder.contains(WebAppConfig.getProfile())) {
            readFolder=folder;
        }
        //List<Map> mapList=FileUtils.readFileList(WebAppConfig.getProfile()+File.separator +folder);
        File file = new File(readFolder);
        List<Map<String,Object>> mapList= new ArrayList<>();
        FileUtils.getAllMusicFileInfo(file,mapList);
        return Result.success(mapList);
    }

    /**
     * 条件查询所有 在线音乐
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(GpMusic gpMusic) {
        //PageHelper.startPage(page, size);
        List<GpMusic> list = gpMusicService.selectGpMusicList(gpMusic);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 在线音乐
     */
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,GpMusic gpMusic) {
    public PageDataInfo list(GpMusic gpMusic) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<GpMusic> list = gpMusicService.selectGpMusicList(gpMusic);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @Log(title = "导出数据字典类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpMusic gpMusic){
        List<GpMusic> list = gpMusicService.selectGpMusicList(gpMusic);
        ExcelUtil<GpMusic> util = new ExcelUtil<GpMusic>(GpMusic.class);
        return util.exportExcel(list, "gpMusic");
    }
}
