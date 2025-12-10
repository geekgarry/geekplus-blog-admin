package com.geekplus.webapp.function.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.ResumeWorkExperience;
import com.geekplus.webapp.function.service.IResumeWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作经验Controller
 *
 * @author 佚名
 * @date 2021-01-08
 */
@RestController
@RequestMapping("/function/workexperience")
public class ResumeWorkExperienceController extends BaseController
{
    @Autowired
    private IResumeWorkExperienceService resumeWorkExperienceService;

    /**
     * 查询工作经验列表
     */
    @GetMapping("/list")
    public PageDataInfo list(ResumeWorkExperience resumeWorkExperience)
    {
        startPage();
        List<ResumeWorkExperience> list = resumeWorkExperienceService.selectResumeWorkExperienceList(resumeWorkExperience);
        return getDataTable(list);
    }

    /**
     * 导出工作经验列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(ResumeWorkExperience resumeWorkExperience)
    {
        List<ResumeWorkExperience> list = resumeWorkExperienceService.selectResumeWorkExperienceList(resumeWorkExperience);
        ExcelUtil<ResumeWorkExperience> util = new ExcelUtil<ResumeWorkExperience>(ResumeWorkExperience.class);
        return util.exportExcel(list, "experience");
    }

    /**
     * 获取工作经验详细信息
     */
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(resumeWorkExperienceService.selectResumeWorkExperienceById(id));
    }

    /**
     * 新增工作经验
     */
    @Log(title = "新增工作经验", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody ResumeWorkExperience resumeWorkExperience)
    {
        return toResult(resumeWorkExperienceService.insertResumeWorkExperience(resumeWorkExperience));
    }

    /**
     * 修改工作经验
     */
    @Log(title = "修改工作经验", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody ResumeWorkExperience resumeWorkExperience)
    {
        return toResult(resumeWorkExperienceService.updateResumeWorkExperience(resumeWorkExperience));
    }

    /**
     * 删除工作经验
     */
    @Log(title = "删除工作经验", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(resumeWorkExperienceService.deleteResumeWorkExperienceByIds(ids));
    }
}
