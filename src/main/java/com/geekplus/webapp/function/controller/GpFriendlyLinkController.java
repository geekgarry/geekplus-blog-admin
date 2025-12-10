package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.service.IGpFriendlyLinkService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.GpFriendlyLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网站友情链接Controller
 *
 * @author 佚名
 * @date 2023-03-12
 */
@RestController
@RequestMapping("/geekplus/link")
public class GpFriendlyLinkController extends BaseController
{
    @Autowired
    private IGpFriendlyLinkService gpFriendlyLinkService;

    /**
     * 查询网站友情链接列表
     */
    @GetMapping("/list")
    public PageDataInfo list(GpFriendlyLink gpFriendlyLink)
    {
        startPage();
        List<GpFriendlyLink> list = gpFriendlyLinkService.selectGpFriendlyLinkList(gpFriendlyLink);
        return getDataTable(list);
    }

    /**
     * 导出网站友情链接列表
     */
    @Log(title = "网站友情链接", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpFriendlyLink gpFriendlyLink)
    {
        List<GpFriendlyLink> list = gpFriendlyLinkService.selectGpFriendlyLinkList(gpFriendlyLink);
        ExcelUtil<GpFriendlyLink> util = new ExcelUtil<GpFriendlyLink>(GpFriendlyLink.class);
        return util.exportExcel(list, "link");
    }

    /**
     * 获取网站友情链接详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(gpFriendlyLinkService.selectGpFriendlyLinkById(id));
    }

    /**
     * 新增网站友情链接
     */
    @Log(title = "新增网站友情链接", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody GpFriendlyLink gpFriendlyLink)
    {
        return toResult(gpFriendlyLinkService.insertGpFriendlyLink(gpFriendlyLink));
    }

    /**
     * 修改网站友情链接
     */
    @Log(title = "修改网站友情链接", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody GpFriendlyLink gpFriendlyLink)
    {
        return toResult(gpFriendlyLinkService.updateGpFriendlyLink(gpFriendlyLink));
    }

    /**
     * 删除网站友情链接
     */
    @Log(title = "删除网站友情链接", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(gpFriendlyLinkService.deleteGpFriendlyLinkByIds(ids));
    }
}
