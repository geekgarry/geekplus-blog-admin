package com.geekplus.webapp.function.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.RemindSubTask;
import com.geekplus.webapp.function.service.RemindSubTaskService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提醒任务子任务 提醒任务子任务
 * Created by CodeGenerator on 2025/09/20.
 */
@RestController
@RequestMapping("/remind/subTask")
public class RemindSubTaskController extends BaseController {
    @Resource
    private RemindSubTaskService remindSubTaskService;

    /**
     * 增加 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:add")
    @Log(title = "新增提醒任务子任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody RemindSubTask remindSubTask) {
        return toResult(remindSubTaskService.addRemindSubTask(remindSubTask));
    }

    /**
     * 增加 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:add")
    @Log(title = "批量新增提醒任务子任务", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<RemindSubTask> remindSubTask) {
    return toResult(remindSubTaskService.batchAddRemindSubTaskList(remindSubTask));
    }

    /**
     * 删除 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:delete")
    @Log(title = "删除提醒任务子任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Integer id) {
        return toResult(remindSubTaskService.removeRemindSubTaskById(id));
    }


    /**
     * 批量删除 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:delete")
    @Log(title = "批量删除提醒任务子任务", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Integer[] ids) {
        return toResult(remindSubTaskService.removeRemindSubTaskByIds(ids));
    }


    /**
     * 更新 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:update")
    @Log(title = "修改提醒任务子任务", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody RemindSubTask remindSubTask) {
        return toResult(remindSubTaskService.modifyRemindSubTask(remindSubTask));
    }

    /**
     * 单条数据详情 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        RemindSubTask remindSubTask = remindSubTaskService.queryRemindSubTaskById(id);
        return Result.success(remindSubTask);
    }

    /**
     * 条件查询所有 提醒任务子任务
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(RemindSubTask remindSubTask) {
        //PageHelper.startPage(page, size);
        List<RemindSubTask> list = remindSubTaskService.queryRemindSubTaskList(remindSubTask);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 提醒任务子任务
     */
    @RequiresPermissions("function:subTask:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,RemindSubTask remindSubTask) {
    public PageDataInfo list(RemindSubTask remindSubTask) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<RemindSubTask> list = remindSubTaskService.queryRemindSubTaskList(remindSubTask);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("function:subTask:export")
    @Log(title = "导出提醒任务子任务", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(RemindSubTask remindSubTask){
        List<RemindSubTask> list = remindSubTaskService.queryRemindSubTaskList(remindSubTask);
        ExcelUtil<RemindSubTask> util = new ExcelUtil<RemindSubTask>(RemindSubTask.class);
        return util.exportExcel(list, "remindSubTask");
    }
}
