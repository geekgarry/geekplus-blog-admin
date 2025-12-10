package ${basePackage}.webapp.${moduleName}.service;

import ${basePackage}.webapp.${moduleName}.entity.${modelNameUpperCamel};
//import ${basePackage}.core.Service;
import java.util.List;


/**
 * ${title} ${functionName}
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service {

    /**
    * 查询全部
    */
    public List<${modelNameUpperCamel}> query${modelNameUpperCamel}List(${modelNameUpperCamel} ${modelNameLowerCamel});

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<${modelNameUpperCamel}> queryUnion${modelNameUpperCamel}List(${modelNameUpperCamel} ${modelNameLowerCamel});

    /**
    * 根据Id查询单条数据
    */
    public ${modelNameUpperCamel} query${modelNameUpperCamel}ById(${pkColumn.javaType} ${pkColumn.smallColumnName});

    /**
    * 增加
    * @param ${modelNameLowerCamel}
    * @return ${functionName}
    */
    public Integer add${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel});

    /**
    * 批量增加
    * @param ${modelNameLowerCamel}List
    * @return ${functionName}
    */
    public Integer batchAdd${modelNameUpperCamel}List(List<${modelNameUpperCamel}> ${modelNameLowerCamel}List);

    /**
    * 删除
    * @param ${pkColumn.smallColumnName}
    */
    public Integer remove${modelNameUpperCamel}ById(${pkColumn.javaType} ${pkColumn.smallColumnName});

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 逻辑删除,更新删除标志字段
    * @param ${pkColumn.smallColumnName}
    */
    Integer modifyDelFlagById(${pkColumn.javaType} ${pkColumn.smallColumnName});
    </#if>
    </#list>

    /**
    * 批量删除
    * @param ${pkColumn.smallColumnName}s
    */
    public Integer remove${modelNameUpperCamel}ByIds(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s);

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ${pkColumn.smallColumnName}s
    */
    Integer modifyDelFlagByIds(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s);
    </#if>
    </#list>

    /**
    * 修改
    * @param ${modelNameLowerCamel}
    */
    public Integer modify${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel});

    /**
    * 批量修改
    * @param ${pkColumn.smallColumnName}s
    */
    public Integer batchModify${modelNameUpperCamel}List(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s);
}
