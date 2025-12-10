package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.service.IGpArticleMapTagService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.GpArticleMapTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章标签映射Controller
 *
 * @author 佚名
 * @date 2023-03-12
 */
@RestController
@RequestMapping("/geekplus/articlemaptag")
public class GpArticleMapTagController extends BaseController
{
    @Autowired
    private IGpArticleMapTagService gpArticleMapTagService;

    /**
     * 查询文章标签映射列表
     */
    @GetMapping("/list")
    public PageDataInfo list(GpArticleMapTag gpArticleMapTag)
    {
        startPage();
        List<GpArticleMapTag> list = gpArticleMapTagService.selectGpArticleMapTagList(gpArticleMapTag);
        return getDataTable(list);
    }

    /**
     * 导出文章标签映射列表
     */
    @Log(title = "文章标签映射", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpArticleMapTag gpArticleMapTag)
    {
        List<GpArticleMapTag> list = gpArticleMapTagService.selectGpArticleMapTagList(gpArticleMapTag);
        ExcelUtil<GpArticleMapTag> util = new ExcelUtil<GpArticleMapTag>(GpArticleMapTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 获取文章标签映射详细信息
     */
    @GetMapping(value = "/{aticleId}")
    public Result getInfo(@PathVariable("aticleId") Long aticleId)
    {
        return Result.success(gpArticleMapTagService.selectGpArticleMapTagById(aticleId));
    }

    /**
     * 新增文章标签映射
     */
    @Log(title = "新增文章标签映射", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody GpArticleMapTag gpArticleMapTag)
    {
        return toResult(gpArticleMapTagService.insertGpArticleMapTag(gpArticleMapTag));
    }

    /**
     * 修改文章标签映射
     */
    @Log(title = "修改文章标签映射", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody GpArticleMapTag gpArticleMapTag)
    {
        return toResult(gpArticleMapTagService.updateGpArticleMapTag(gpArticleMapTag));
    }

    /**
     * 删除文章标签映射
     */
    @Log(title = "删除文章标签映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{aticleIds}")
    public Result remove(@PathVariable Long[] aticleIds)
    {
        return toResult(gpArticleMapTagService.deleteGpArticleMapTagByIds(aticleIds));
    }

    /**
     * 删除文章标签映射
     */
    @Log(title = "删除文章标签映射", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteArticleTagMap")
    public Result remove(@RequestBody GpArticleMapTag gpArticleMapTag)
    {
        return toResult(gpArticleMapTagService.deleteGpArticleMapTagBy(gpArticleMapTag));
    }
}
