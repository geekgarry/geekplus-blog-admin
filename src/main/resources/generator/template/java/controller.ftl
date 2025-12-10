package ${basePackage}.webapp.${moduleName}.controller;

import ${basePackage}.common.annotation.Log;
import ${basePackage}.common.annotation.RepeatSubmit;
import ${basePackage}.common.core.controller.BaseController;
import ${basePackage}.common.domain.Result;
import ${basePackage}.common.enums.BusinessType;
import ${basePackage}.common.enums.OperatorType;
import ${basePackage}.common.util.poi.ExcelUtil;
import ${basePackage}.webapp.${moduleName}.entity.${modelNameUpperCamel};
import ${basePackage}.webapp.${moduleName}.service.${modelNameUpperCamel}Service;
import ${basePackage}.common.page.PageDataInfo;
import ${basePackage}.common.annotation.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${functionName}
 * Created by ${author} on ${date}.
 */
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends BaseController {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    /**
     * 增加 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:add")
    @Log(title = "新增${title}", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/add")
    @RepeatSubmit
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return toResult(${modelNameLowerCamel}Service.add${modelNameUpperCamel}(${modelNameLowerCamel}));
    }

    /**
     * 增加 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:add")
    @Log(title = "批量新增${title}", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    @PostMapping("/batchAdd")
    @RepeatSubmit
    public Result batchAdd(@RequestBody List<${modelNameUpperCamel}> ${modelNameLowerCamel}) {
    return toResult(${modelNameLowerCamel}Service.batchAdd${modelNameUpperCamel}List(${modelNameLowerCamel}));
    }

    /**
     * 删除 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:delete")
    @Log(title = "删除${title}", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/delete")
    public Result remove(@RequestParam ${pkColumn.javaType} ${pkColumn.smallColumnName}) {
        return toResult(${modelNameLowerCamel}Service.remove${modelNameUpperCamel}ById(${pkColumn.smallColumnName}));
    }

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 逻辑删除 ${functionName}
    */
    @RequiresPermissions("${permissionPrefix}:delete")
    @Log(title = "删除${title}", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @GetMapping("/logical_delete")
    public Result modifyDelFlagById(@RequestParam ${pkColumn.javaType} ${pkColumn.smallColumnName}) {
        return toResult(${modelNameLowerCamel}Service.modifyDelFlagById(${pkColumn.smallColumnName}));
    }
    </#if>
    </#list>

    /**
     * 批量删除 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:delete")
    @Log(title = "批量删除${title}", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/{${pkColumn.smallColumnName}s}")
    public Result remove(@PathVariable ${pkColumn.javaType}[] ${pkColumn.smallColumnName}s) {
        return toResult(${modelNameLowerCamel}Service.remove${modelNameUpperCamel}ByIds(${pkColumn.smallColumnName}s));
    }

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 批量逻辑删除 ${functionName}
    */
    @RequiresPermissions("${permissionPrefix}:delete")
    @Log(title = "删除${title}", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    @DeleteMapping("/logical/{${pkColumn.smallColumnName}s}")
    public Result modifyDelFlagByIds(@PathVariable ${pkColumn.javaType}[] ${pkColumn.smallColumnName}s) {
        return toResult(${modelNameLowerCamel}Service.modifyDelFlagByIds(${pkColumn.smallColumnName}s));
    }
    </#if>
    </#list>

    /**
     * 更新 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:update")
    @Log(title = "修改${title}", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/update")
    public Result edit(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return toResult(${modelNameLowerCamel}Service.modify${modelNameUpperCamel}(${modelNameLowerCamel}));
    }

    /**
     * 单条数据详情 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:info")
    @GetMapping("/{${pkColumn.smallColumnName}}")
    public Result info(@PathVariable ${pkColumn.javaType} ${pkColumn.smallColumnName}) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.query${modelNameUpperCamel}ById(${pkColumn.smallColumnName});
        return Result.success(${modelNameLowerCamel});
    }

    /**
     * 条件查询所有 ${functionName}
     */
    @GetMapping("/listAll")
    public PageDataInfo listAll(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        //PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.query${modelNameUpperCamel}List(${modelNameLowerCamel});
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        //PageInfo pageInfo = new PageInfo(list);
        return rspData;
    }

    /**
     * 条件查询所有 ${functionName}
     */
    @RequiresPermissions("${permissionPrefix}:list")
    @GetMapping("/list")
    //public PageDataInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,${modelNameUpperCamel} ${modelNameLowerCamel}) {
    public PageDataInfo list(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        //PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        startPage();
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.query${modelNameUpperCamel}List(${modelNameLowerCamel});
        //PageInfo pageInfo = new PageInfo(list);
        return getDataTable(list);
    }

    /**
    * 导出数据字典类型
    */
    @RequiresPermissions("${permissionPrefix}:export")
    @Log(title = "导出${title}", businessType = BusinessType.EXPORT, operatorType = OperatorType.MANAGE)
    @GetMapping("/export")
    public Result export(${modelNameUpperCamel} ${modelNameLowerCamel}){
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.query${modelNameUpperCamel}List(${modelNameLowerCamel});
        ExcelUtil<${modelNameUpperCamel}> util = new ExcelUtil<${modelNameUpperCamel}>(${modelNameUpperCamel}.class);
        return util.exportExcel(list, "${modelNameLowerCamel}");
    }
}
