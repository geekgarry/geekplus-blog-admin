package com.geekplus.webapp.system.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.system.entity.SysQuartzJob;
import com.geekplus.webapp.system.service.SysQuartzJobService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * quartz系统调度任务 quartz系统调度任务
 * Created by CodeGenerator on 2025/09/20.
 */
@RestController
@RequestMapping("/sys/quartzJob")
public class SysQuartzJobController extends BaseController {
    @Resource
    private SysQuartzJobService sysQuartzJobService;

    /**
     * 增加 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:add")
    @Log(title = "新增quartz系统调度任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody SysQuartzJob sysQuartzJob) {
        return toResult(sysQuartzJobService.addSysQuartzJob(sysQuartzJob));
    }

    /**
     * 增加 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:add")
    @Log(title = "批量新增quartz系统调度任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<SysQuartzJob> sysQuartzJob) {
    return toResult(sysQuartzJobService.batchAddSysQuartzJobList(sysQuartzJob));
    }

    /**
     * 删除 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:delete")
    @Log(title = "删除quartz系统调度任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam String id) {
        return toResult(sysQuartzJobService.removeSysQuartzJobById(id));
    }

    /**
    * 逻辑删除 quartz系统调度任务
    */
    @RequiresPermissions("system:quartzJob:delete")
    @Log(title = "删除quartz系统调度任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/logical_delete")
    public Result modifyDelFlagById(@RequestParam String id) {
        return toResult(sysQuartzJobService.modifyDelFlagById(id));
    }

    /**
     * 批量删除 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:delete")
    @Log(title = "批量删除quartz系统调度任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable String[] ids) {
        return toResult(sysQuartzJobService.removeSysQuartzJobByIds(ids));
    }

    /**
    * 批量逻辑删除 quartz系统调度任务
    */
    @RequiresPermissions("system:quartzJob:delete")
    @Log(title = "删除quartz系统调度任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/logical/{ids}")
    public Result modifyDelFlagByIds(@PathVariable String[] ids) {
        return toResult(sysQuartzJobService.modifyDelFlagByIds(ids));
    }

    /**
     * 更新 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:update")
    @Log(title = "修改quartz系统调度任务", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody SysQuartzJob sysQuartzJob) {
        return toResult(sysQuartzJobService.modifySysQuartzJob(sysQuartzJob));
    }

    /**
     * 单条数据详情 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam String id) {
        SysQuartzJob sysQuartzJob = sysQuartzJobService.querySysQuartzJobById(id);
        return Result.success(sysQuartzJob);
    }

    /**
     * 条件查询所有 quartz系统调度任务
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(SysQuartzJob sysQuartzJob) {
        //PageHelper.startPage(page, size);
        List<SysQuartzJob> list = sysQuartzJobService.querySysQuartzJobList(sysQuartzJob);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 quartz系统调度任务
     */
    @RequiresPermissions("system:quartzJob:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,SysQuartzJob sysQuartzJob) {
    public PageDataInfo list(SysQuartzJob sysQuartzJob) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<SysQuartzJob> list = sysQuartzJobService.querySysQuartzJobList(sysQuartzJob);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("system:quartzJob:export")
    @Log(title = "导出quartz系统调度任务", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(SysQuartzJob sysQuartzJob){
        List<SysQuartzJob> list = sysQuartzJobService.querySysQuartzJobList(sysQuartzJob);
        ExcelUtil<SysQuartzJob> util = new ExcelUtil<SysQuartzJob>(SysQuartzJob.class);
        return util.exportExcel(list, "sysQuartzJob");
    }
}
