package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.PersonalTechnology;
import com.geekplus.webapp.function.service.IPersonalTechnologyService;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人技能Controller
 *
 * @author 佚名
 * @date 2021-01-08
 */
@RestController
@RequestMapping("/function/technology")
public class PersonalTechnologyController extends BaseController
{
    @Autowired
    private IPersonalTechnologyService personalTechnologyService;

    /**
     * 查询个人技能列表
     */
    @GetMapping("/list")
    public PageDataInfo list(PersonalTechnology personalTechnology)
    {
        startPage();
        List<PersonalTechnology> list = personalTechnologyService.selectPersonalTechnologyList(personalTechnology);
        return getDataTable(list);
    }

    /**
     * 导出个人技能列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(PersonalTechnology personalTechnology)
    {
        List<PersonalTechnology> list = personalTechnologyService.selectPersonalTechnologyList(personalTechnology);
        ExcelUtil<PersonalTechnology> util = new ExcelUtil<PersonalTechnology>(PersonalTechnology.class);
        return util.exportExcel(list, "technology");
    }

    /**
     * 获取个人技能详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(personalTechnologyService.selectPersonalTechnologyById(id));
    }

    /**
     * 新增个人技能
     */
    @Log(title = "新增个人技能", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody PersonalTechnology personalTechnology)
    {
        return toResult(personalTechnologyService.insertPersonalTechnology(personalTechnology));
    }

    /**
     * 修改个人技能
     */
    @Log(title = "修改个人技能", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody PersonalTechnology personalTechnology)
    {
        return toResult(personalTechnologyService.updatePersonalTechnology(personalTechnology));
    }

    /**
     * 删除个人技能
     */
    @Log(title = "删除个人技能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(personalTechnologyService.deletePersonalTechnologyByIds(ids));
    }
}
