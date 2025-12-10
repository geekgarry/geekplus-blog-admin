package com.geekplus.webapp.system.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.system.entity.SysDictData;
import com.geekplus.webapp.system.service.SysDictDataService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 数据字典数据项 数据字典数据项
 * Created by CodeGenerator on 2025/09/20.
 */
@RestController
@RequestMapping("/sys/dictData")
public class SysDictDataController extends BaseController {
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 增加 数据字典数据项
     */
    @RequiresPermissions("system:dictData:add")
    @Log(title = "新增数据字典数据项", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody SysDictData sysDictData) {
        sysDictData.setCreateBy(getUsername());
        return toResult(sysDictDataService.addSysDictData(sysDictData));
    }

    /**
     * 增加 数据字典数据项
     */
    @RequiresPermissions("system:dictData:add")
    @Log(title = "批量新增数据字典数据项", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<SysDictData> sysDictData) {
    return toResult(sysDictDataService.batchAddSysDictDataList(sysDictData));
    }

    /**
     * 删除 数据字典数据项
     */
    @RequiresPermissions("system:dictData:delete")
    @Log(title = "删除数据字典数据项", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Long id) {
        return toResult(sysDictDataService.removeSysDictDataById(id));
    }


    /**
     * 批量删除 数据字典数据项
     */
    @RequiresPermissions("system:dictData:delete")
    @Log(title = "批量删除数据字典数据项", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        return toResult(sysDictDataService.removeSysDictDataByIds(ids));
    }


    /**
     * 更新 数据字典数据项
     */
    @RequiresPermissions("system:dictData:update")
    @Log(title = "修改数据字典数据项", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(getUsername());
        sysDictData.setUpdateTime(new Date());
        return toResult(sysDictDataService.modifySysDictData(sysDictData));
    }

    /**
     * 单条数据详情 数据字典数据项
     */
    @RequiresPermissions("system:dictData:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam Long id) {
        SysDictData sysDictData = sysDictDataService.querySysDictDataById(id);
        return Result.success(sysDictData);
    }

    /**
     * 条件查询所有 数据字典数据项
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(SysDictData sysDictData) {
        //PageHelper.startPage(page, size);
        List<SysDictData> list = sysDictDataService.querySysDictDataList(sysDictData);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 数据字典数据项
     */
    @RequiresPermissions("system:dictData:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,SysDictData sysDictData) {
    public PageDataInfo list(SysDictData sysDictData) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<SysDictData> list = sysDictDataService.queryUnionSysDictDataList(sysDictData);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("system:dictData:export")
    @Log(title = "导出数据字典数据项", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(SysDictData sysDictData){
        List<SysDictData> list = sysDictDataService.querySysDictDataList(sysDictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "sysDictData");
    }
}
