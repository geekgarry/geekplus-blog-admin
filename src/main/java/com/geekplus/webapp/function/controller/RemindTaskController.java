package com.geekplus.webapp.function.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.RemindTask;
import com.geekplus.webapp.function.service.RemindTaskService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提醒任务 提醒任务
 * Created by CodeGenerator on 2025/09/20.
 */
@RestController
@RequestMapping("/remind/task")
public class RemindTaskController extends BaseController {
    @Resource
    private RemindTaskService remindTaskService;

    /**
     * 增加 提醒任务
     */
    @RequiresPermissions("function:remindTask:add")
    @Log(title = "新增提醒任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody RemindTask remindTask) {
        return toResult(remindTaskService.addRemindTask(remindTask));
    }

    /**
     * 增加 提醒任务
     */
    @RequiresPermissions("function:remindTask:add")
    @Log(title = "批量新增提醒任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<RemindTask> remindTask) {
    return toResult(remindTaskService.batchAddRemindTaskList(remindTask));
    }

    /**
     * 删除 提醒任务
     */
    @RequiresPermissions("function:remindTask:delete")
    @Log(title = "删除提醒任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Integer id) {
        return toResult(remindTaskService.removeRemindTaskById(id));
    }


    /**
     * 批量删除 提醒任务
     */
    @RequiresPermissions("function:remindTask:delete")
    @Log(title = "批量删除提醒任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Integer[] ids) {
        return toResult(remindTaskService.removeRemindTaskByIds(ids));
    }


    /**
     * 更新 提醒任务
     */
    @RequiresPermissions("function:remindTask:update")
    @Log(title = "修改提醒任务", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody RemindTask remindTask) {
        return toResult(remindTaskService.modifyRemindTask(remindTask));
    }

    /**
     * 单条数据详情 提醒任务
     */
    @RequiresPermissions("function:remindTask:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        RemindTask remindTask = remindTaskService.queryRemindTaskById(id);
        return Result.success(remindTask);
    }

    /**
     * 条件查询所有 提醒任务
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(RemindTask remindTask) {
        //PageHelper.startPage(page, size);
        List<RemindTask> list = remindTaskService.queryRemindTaskList(remindTask);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 提醒任务
     */
    @RequiresPermissions("function:remindTask:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,RemindTask remindTask) {
    public PageDataInfo list(RemindTask remindTask) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<RemindTask> list = remindTaskService.queryRemindTaskList(remindTask);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("function:remindTask:export")
    @Log(title = "导出提醒任务", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(RemindTask remindTask){
        List<RemindTask> list = remindTaskService.queryRemindTaskList(remindTask);
        ExcelUtil<RemindTask> util = new ExcelUtil<RemindTask>(RemindTask.class);
        return util.exportExcel(list, "remindTask");
    }
}
