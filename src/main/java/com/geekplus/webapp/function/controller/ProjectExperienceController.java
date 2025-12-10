package com.geekplus.webapp.function.controller;

import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.ProjectExperience;
import com.geekplus.webapp.function.service.IProjectExperienceService;
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
 * 项目经验Controller
 *
 * @author 佚名
 * @date 2021-01-08
 */
@RestController
@RequestMapping("/function/projectexperience")
public class ProjectExperienceController extends BaseController
{
    @Autowired
    private IProjectExperienceService projectExperienceService;

    /**
     * 查询项目经验列表
     */
    @GetMapping("/list")
    public PageDataInfo list(ProjectExperience projectExperience)
    {
        startPage();
        List<ProjectExperience> list = projectExperienceService.selectProjectExperienceList(projectExperience);
        return getDataTable(list);
    }

    /**
     * 导出项目经验列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(ProjectExperience projectExperience)
    {
        List<ProjectExperience> list = projectExperienceService.selectProjectExperienceList(projectExperience);
        ExcelUtil<ProjectExperience> util = new ExcelUtil<ProjectExperience>(ProjectExperience.class);
        return util.exportExcel(list, "experience");
    }

    /**
     * 获取项目经验详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(projectExperienceService.selectProjectExperienceById(id));
    }

    /**
     * 新增项目经验
     */
    @Log(title = "新增项目经验", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody ProjectExperience projectExperience)
    {
        return toResult(projectExperienceService.insertProjectExperience(projectExperience));
    }

    /**
     * 修改项目经验
     */
    @Log(title = "修改项目经验", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody ProjectExperience projectExperience)
    {
        return toResult(projectExperienceService.updateProjectExperience(projectExperience));
    }

    /**
     * 删除项目经验
     */
    @Log(title = "删除项目经验", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(projectExperienceService.deleteProjectExperienceByIds(ids));
    }
}
