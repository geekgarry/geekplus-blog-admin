package com.geekplus.webapp.function.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.annotation.RepeatSubmit;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.Users;
import com.geekplus.webapp.function.service.UsersService;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息表 用户信息表
 * Created by CodeGenerator on 2024/09/20.
 */
@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {
    @Resource
    private UsersService usersService;

    /**
     * 增加 用户信息表
     */
    @RequiresPermissions("function:users:add")
    @Log(title = "新增用户信息表", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody Users users) {
        return toResult(usersService.insertUsers(users));
    }

    /**
     * 增加 用户信息表
     */
    @RequiresPermissions("function:users:add")
    @Log(title = "批量新增用户信息表", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<Users> users) {
    return toResult(usersService.batchInsertUsersList(users));
    }

    /**
     * 删除 用户信息表
     */
    @RequiresPermissions("function:users:delete")
    @Log(title = "删除用户信息表", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam Long userId) {
        return toResult(usersService.deleteUsersById(userId));
    }

    /**
     * 批量删除 用户信息表
     */
    @RequiresPermissions("function:users:delete")
    @Log(title = "批量删除用户信息表", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{userIds}")
    public Result remove(@PathVariable Long[] userIds) {
        return toResult(usersService.deleteUsersByIds(userIds));
    }

    /**
     * 更新 用户信息表
     */
    @RequiresPermissions("function:users:update")
    @Log(title = "修改用户信息表", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody Users users) {
        return toResult(usersService.updateUsers(users));
    }

    /**
     * 单条数据详情 用户信息表
     */
    @RequiresPermissions("function:users:detail")
    @GetMapping("/detail")
    public Result detail(@RequestParam Long userId) {
        Users users = usersService.selectUsersById(userId);
        return Result.success(users);
    }

    /**
     * 条件查询所有 用户信息表
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(Users users) {
        //PageHelper.startPage(page, size);
        List<Users> list = usersService.selectUsersList(users);
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 用户信息表
     */
    @RequiresPermissions("function:users:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,Users users) {
    public PageDataInfo list(Users users) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<Users> list = usersService.selectUsersList(users);
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("function:users:export")
    @Log(title = "导出用户信息表", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(Users users){
        List<Users> list = usersService.selectUsersList(users);
        ExcelUtil<Users> util = new ExcelUtil<Users>(Users.class);
        return util.exportExcel(list, "users");
    }
}
