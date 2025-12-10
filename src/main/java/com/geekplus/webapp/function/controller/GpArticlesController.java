package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.service.IGpArticleTagsService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.util.file.FileUtils;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.framework.config.ServerConfig;
import com.geekplus.webapp.function.entity.GpArticleTags;
import com.geekplus.webapp.function.entity.GpArticles;
import com.geekplus.webapp.function.service.IGpArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文章Controller
 *
 * @author 佚名
 * @date 2023-03-12
 */
@RestController
@RequestMapping("/geekplus/articles")
public class GpArticlesController extends BaseController
{
    @Autowired
    private IGpArticlesService gpArticlesService;
    @Resource
    IGpArticleTagsService gpArticleTagsService;
    @Resource
    private ServerConfig serverConfig;

    /**
     * 查询文章列表
     */
    @GetMapping("/list")
    public PageDataInfo list(GpArticles gpArticles)
    {
        startPage();
        List<GpArticles> list = gpArticlesService.selectGpArticlesList(gpArticles);
        return getDataTable(list);
    }

    /**
     * 导出文章列表
     */
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpArticles gpArticles)
    {
        List<GpArticles> list = gpArticlesService.selectGpArticlesList(gpArticles);
        ExcelUtil<GpArticles> util = new ExcelUtil<GpArticles>(GpArticles.class);
        return util.exportExcel(list, "articles");
    }

    /**
     * 获取文章详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(gpArticlesService.selectGpArticlesById(id));
    }

    /**
     * 新增文章
     */
    @Log(title = "新增文章", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody GpArticles gpArticles)
    {
        Result result=toResult(gpArticlesService.insertGpArticles(gpArticles));
        //System.out.println("controller返回主键ID："+gpArticles.getId());
        result.put("articleId",gpArticles.getId());
        return result;
    }

    /**
     * 修改文章
     */
    @Log(title = "修改文章", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody GpArticles gpArticles)
    {
        return toResult(gpArticlesService.updateGpArticles(gpArticles));
    }

    /**
     * 删除文章
     */
    @Log(title = "删除文章", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        int rows=gpArticlesService.deleteGpArticlesByIds(ids);
        List<GpArticleTags> list=new ArrayList<>();
        if(rows>0){
            List<Map> mapTag=new ArrayList<>();
            for (Long id:ids) {
                list = gpArticleTagsService.selectTagByArticleId(String.valueOf(id));
                if(list.size()>0) {
                    for (GpArticleTags articleTag : list) {
                        Map map = new HashMap();
                        map.put("aticleId", id);
                        map.put("articleTag", articleTag.getId());
                        gpArticleTagsService.deleteGpArticleMapTagByIdTag(map);
                    }
                }
                //map.put("articleId", id)
            }
            return Result.success();
        }else{
            return Result.error();
        }
        //return toResult(rows);
    }

    /**
      * @Author geekplus
      * @Description //读取文件夹下的所有文件和文件夹
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/readFileList")
    public Result readFileList(String folder) throws IOException {
        if(folder==null) {
            folder = "article";
        }
        List<Map> mapList=FileUtils.readFileList(WebAppConfig.getProfile()+File.separator +folder);
        return Result.success(mapList);
    }

    /**
     * 查询文件里的所有图片，读取某个文件夹下的所有文件
     */
    @GetMapping("/getImageList")
    public Result listFileImage(String fileFolder)
    {
//        try{
//            List<String> listImage= FileUtils.readFileImage(WebAppConfig.getProfile(), File.separator + fileFolder);
//            return Result.success(listImage);
//        }catch(IOException e){
//            return Result.success(e.getMessage());
//        }
        File file=new File(WebAppConfig.getProfile()+ File.separator + fileFolder);
        List<String> list= new ArrayList<>();
        FileUtils.getDirectoryAllFile(file,list);
        return Result.success(list);
    }

    /**
      * @Author geekplus
      * @Description //一键查询所有文章替换所有Image的src代理服务/api,也可以自定义替换内容replaceMap
      * @Param
      * @Throws
      * @Return {@link }
      */
    @PostMapping("/replaceNeedContent")
    public Result replaceArticleImg(@RequestBody(required = false) Map<String,Object> replaceMap){
        return Result.success(gpArticlesService.replaceGpArticleImgSrc(replaceMap));
    }
}
