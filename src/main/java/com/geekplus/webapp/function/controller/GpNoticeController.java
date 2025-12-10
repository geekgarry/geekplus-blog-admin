package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.service.IGpNoticeService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.GpNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网站通知Controller
 *
 * @author 佚名
 * @date 2023-03-18
 */
@RestController
@RequestMapping("/geekplus/notice")
public class GpNoticeController extends BaseController
{
    @Autowired
    private IGpNoticeService gpNoticeService;

    /**
     * 查询网站通知列表
     */
    @GetMapping("/list")
    public PageDataInfo list(GpNotice gpNotice)
    {
        startPage();
        List<GpNotice> list = gpNoticeService.selectGpNoticeList(gpNotice);
        return getDataTable(list);
    }

    /**
     * 导出网站通知列表
     */
    @Log(title = "网站通知", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(GpNotice gpNotice)
    {
        List<GpNotice> list = gpNoticeService.selectGpNoticeList(gpNotice);
        ExcelUtil<GpNotice> util = new ExcelUtil<GpNotice>(GpNotice.class);
        return util.exportExcel(list, "notice");
    }

    /**
     * 获取网站通知详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(gpNoticeService.selectGpNoticeById(id));
    }

    /**
     * 新增网站通知
     */
    @Log(title = "新增网站通知", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody GpNotice gpNotice)
    {
        return toResult(gpNoticeService.insertGpNotice(gpNotice));
    }

    /**
     * 修改网站通知
     */
    @Log(title = "修改网站通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody GpNotice gpNotice)
    {
        return toResult(gpNoticeService.updateGpNotice(gpNotice));
    }

    /**
     * 删除网站通知
     */
    @Log(title = "删除网站通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(gpNoticeService.deleteGpNoticeByIds(ids));
    }
}
