package com.geekplus.webapp.system.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.entity.dto.ResourceMetaDataDto;
import com.geekplus.webapp.system.service.ResourceMetaDataService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源数据表
 * Created by geekplus on 2025/12/02.
 */
@RestController
@RequestMapping("/resource/metaData")
public class ResourceMetaDataController extends BaseController {
    @Resource
    private ResourceMetaDataService resourceMetaDataService;

    /**
     * 增加 资源数据表
     */
    @RequiresPermissions("system:metaData:add")
    @Log(title = "新增资源数据表", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody ResourceMetaData resourceMetaData) {
        return toResult(resourceMetaDataService.addResourceMetaData(resourceMetaData));
    }

    /**
     * 增加 资源数据表
     */
    @RequiresPermissions("system:metaData:add")
    @Log(title = "批量新增资源数据表", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<ResourceMetaData> resourceMetaData) {
    return toResult(resourceMetaDataService.batchAddResourceMetaDataList(resourceMetaData));
    }

    /**
     * 删除 资源数据表
     */
    @RequiresPermissions("system:metaData:delete")
    @Log(title = "删除资源数据表", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Long id) {
        return toResult(resourceMetaDataService.removeResourceMetaDataById(id));
    }


    /**
     * 批量删除 资源数据表
     */
    @RequiresPermissions("system:metaData:delete")
    @Log(title = "批量删除资源数据表", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        return toResult(resourceMetaDataService.removeResourceMetaDataByIds(ids));
    }


    /**
     * 更新 资源数据表
     */
    @RequiresPermissions("system:metaData:update")
    @Log(title = "修改资源数据表", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody ResourceMetaData resourceMetaData) {
        return toResult(resourceMetaDataService.modifyResourceMetaData(resourceMetaData));
    }

    /**
     * 更新 资源数据表
     */
    @RequiresPermissions("system:metaData:update")
    @Log(title = "修改资源数据表", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/updateByFilePath")
    public Result editByFilePath(@RequestBody ResourceMetaData resourceMetaData) {
        return toResult(resourceMetaDataService.modifyResourceMetaByFilePath(resourceMetaData));
    }

    /**
     * 批量更新 资源数据表
     */
    @RequiresPermissions("system:metaData:update")
    @Log(title = "批量修改资源数据表", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/updateByFilePaths")
    public Result editByFilePath(@RequestBody ResourceMetaDataDto resourceMetaData) {
        return toResult(resourceMetaDataService.batchModifyResourceMetaListByFilePath(resourceMetaData));
    }

    /**
     * 单条数据详情 资源数据表
     */
    @RequiresPermissions("system:metaData:info")
    @GetMapping("/{id}")
    public Result info(@PathVariable Long id) {
        ResourceMetaData resourceMetaData = resourceMetaDataService.queryResourceMetaDataById(id);
        return Result.success(resourceMetaData);
    }

    /**
     * 条件查询所有 资源数据表
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(ResourceMetaData resourceMetaData) {
        //PageHelper.startPage(page, size);
        List<ResourceMetaData> list = resourceMetaDataService.queryResourceMetaDataList(resourceMetaData);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 资源数据表
     */
    @RequiresPermissions("system:metaData:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,ResourceMetaData resourceMetaData) {
    public PageDataInfo list(ResourceMetaData resourceMetaData) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<ResourceMetaData> list = resourceMetaDataService.queryResourceMetaDataList(resourceMetaData);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("system:metaData:export")
    @Log(title = "导出资源数据表", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(ResourceMetaData resourceMetaData){
        List<ResourceMetaData> list = resourceMetaDataService.queryResourceMetaDataList(resourceMetaData);
        ExcelUtil<ResourceMetaData> util = new ExcelUtil<ResourceMetaData>(ResourceMetaData.class);
        return util.exportExcel(list, "resourceMetaData");
    }
}
