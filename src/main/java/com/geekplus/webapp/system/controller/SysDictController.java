package com.geekplus.webapp.system.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.system.entity.SysDict;
import com.geekplus.webapp.system.service.SysDictService;
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
 * 数据字典 数据字典
 * Created by CodeGenerator on 2025/09/20.
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends BaseController {
    @Resource
    private SysDictService sysDictService;

    /**
     * 增加 数据字典
     */
    @RequiresPermissions("system:sysDict:add")
    @Log(title = "新增数据字典", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody SysDict sysDict) {
        sysDict.setCreateBy(getUsername());
        return toResult(sysDictService.addSysDict(sysDict));
    }

    /**
     * 增加 数据字典
     */
    @RequiresPermissions("system:sysDict:add")
    @Log(title = "批量新增数据字典", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<SysDict> sysDict) {
    return toResult(sysDictService.batchAddSysDictList(sysDict));
    }

    /**
     * 删除 数据字典
     */
    @RequiresPermissions("system:sysDict:delete")
    @Log(title = "删除数据字典", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Long id) {
        return toResult(sysDictService.removeSysDictById(id));
    }

    /**
    * 逻辑删除 数据字典
    */
    @RequiresPermissions("system:sysDict:delete")
    @Log(title = "删除数据字典", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/logical_delete")
    public Result modifyDelFlagById(@RequestParam Long id) {
        return toResult(sysDictService.modifyDelFlagById(id));
    }

    /**
     * 批量删除 数据字典
     */
    @RequiresPermissions("system:sysDict:delete")
    @Log(title = "批量删除数据字典", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        return toResult(sysDictService.removeSysDictByIds(ids));
    }

    /**
    * 批量逻辑删除 数据字典
    */
    @RequiresPermissions("system:sysDict:delete")
    @Log(title = "删除数据字典", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/logical/{ids}")
    public Result modifyDelFlagByIds(@PathVariable Long[] ids) {
        return toResult(sysDictService.modifyDelFlagByIds(ids));
    }

    /**
     * 更新 数据字典
     */
    @RequiresPermissions("system:sysDict:update")
    @Log(title = "修改数据字典", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody SysDict sysDict) {
        sysDict.setUpdateBy(getUsername());
        sysDict.setUpdateTime(new Date());
        return toResult(sysDictService.modifySysDict(sysDict));
    }

    /**
     * 单条数据详情 数据字典
     */
    @RequiresPermissions("system:sysDict:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam Long id) {
        SysDict sysDict = sysDictService.querySysDictById(id);
        return Result.success(sysDict);
    }

    /**
     * 条件查询所有 数据字典
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(SysDict sysDict) {
        //PageHelper.startPage(page, size);
        List<SysDict> list = sysDictService.querySysDictList(sysDict);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 数据字典
     */
    @RequiresPermissions("system:sysDict:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,SysDict sysDict) {
    public PageDataInfo list(SysDict sysDict) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<SysDict> list = sysDictService.querySysDictList(sysDict);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("system:sysDict:export")
    @Log(title = "导出数据字典", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(SysDict sysDict){
        List<SysDict> list = sysDictService.querySysDictList(sysDict);
        ExcelUtil<SysDict> util = new ExcelUtil<SysDict>(SysDict.class);
        return util.exportExcel(list, "sysDict");
    }

    /**
     * 刷新字典缓存
     */
    @RequiresPermissions("system:sysDict:delete")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @GetMapping("/refreshCache")
    public Result refreshCache()
    {
        sysDictService.resetDictCache();
        return Result.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public Result optionselect()
    {
        List<SysDict> dictTypes = sysDictService.selectDictTypeAll();
        return Result.success(dictTypes);
    }
}
