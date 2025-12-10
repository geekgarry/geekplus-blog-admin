package com.geekplus.webapp.function.controller;

import com.geekplus.common.annotation.Log;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
//import com.geekplus.common.domain.model.LoginUser;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
//import com.geekplus.common.util.poi.ExcelUtil;
//import com.geekplus.framework.web.service.TokenService;
import com.geekplus.common.util.poi.ExcelUtil;
import com.geekplus.webapp.function.entity.PersonalResume;
import com.geekplus.webapp.function.service.IPersonalResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人简历Controller
 *
 * @author 佚名
 * @date 2021-01-08
 */
@RestController
@RequestMapping("/function/resume")
public class PersonalResumeController extends BaseController
{
    @Autowired
    private IPersonalResumeService personalResumeService;
//    @Resource
//    private TokenService tokenService;

    /**
     * 查询个人简历列表
     */
    @GetMapping("/list")
    public PageDataInfo list(PersonalResume personalResume)
    {
        startPage();
        List<PersonalResume> list = personalResumeService.selectPersonalResumeList(personalResume);
        return getDataTable(list);
    }

    /**
     * 导出个人简历列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public Result export(PersonalResume personalResume)
    {
        List<PersonalResume> list = personalResumeService.selectPersonalResumeList(personalResume);
        ExcelUtil<PersonalResume> util = new ExcelUtil<PersonalResume>(PersonalResume.class);
        return util.exportExcel(list, "resume");
    }

    /**
     * 获取个人简历详细信息
     */
    @Log(title = "查询个人简历",businessType = BusinessType.SELECT,operatorType = OperatorType.MANAGE)
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.success(personalResumeService.selectPersonalResumeById(id));
    }

    /**
     * 新增个人简历
     */
    @Log(title = "新增个人简历", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody PersonalResume personalResume)
    {
        return toResult(personalResumeService.insertPersonalResume(personalResume));
    }

    /**
     * 修改个人简历
     */
    @Log(title = "修改个人简历信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result edit(@RequestBody PersonalResume personalResume)
    {
        return toResult(personalResumeService.updatePersonalResume(personalResume));
    }

    /**
     * 删除 个人简历
     */
    @Log(title = "删除个人简历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids)
    {
        return toResult(personalResumeService.deletePersonalResumeByIds(ids));
    }

    @GetMapping("/getPersonalResumeInfo")
    public Result getPersonalResumeInfoByResumeId(Long resumeId){
        return Result.success(personalResumeService.getPersonalResumeInfo(resumeId));
    }
    /**
     * 获取导入模板
     * @return
     */
//    @GetMapping("/importTemplate")
//    public Result importTemplate(){
//        ExcelUtil<PersonalResume> excelUtil=new ExcelUtil<>(PersonalResume.class);
//        return excelUtil.importTemplateExcel("简历信息");
//    }

//    @Log(title = "个人简历", businessType = BusinessType.IMPORT)
//    @PostMapping("/importData")
//    public Result importData(MultipartFile file, boolean updateSupport) throws Exception {
//        ExcelUtil<PersonalResume> util = new ExcelUtil<>(PersonalResume.class);
//        List<PersonalResume> fireCompanies = util.importExcel(file.getInputStream());
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        String message = personalResumeService.importPersonalResume(fireCompanies, updateSupport, loginUser);
//        return Result.success(message);
//    }
}
