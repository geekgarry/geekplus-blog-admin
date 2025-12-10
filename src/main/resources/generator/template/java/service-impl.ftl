package ${basePackage}.webapp.${moduleName}.service.impl;

import ${basePackage}.webapp.${moduleName}.mapper.${modelNameUpperCamel}Mapper;
import ${basePackage}.webapp.${moduleName}.entity.${modelNameUpperCamel};
import ${basePackage}.webapp.${moduleName}.service.${modelNameUpperCamel}Service;
//import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    /**
    * 查询全部
    */
    @Override
    public List<${modelNameUpperCamel}> query${modelNameUpperCamel}List(${modelNameUpperCamel} ${modelNameLowerCamel}){
        return ${modelNameLowerCamel}Mapper.select${modelNameUpperCamel}List(${modelNameLowerCamel});
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    @Override
    public List<${modelNameUpperCamel}> queryUnion${modelNameUpperCamel}List(${modelNameUpperCamel} ${modelNameLowerCamel}){
        return ${modelNameLowerCamel}Mapper.selectUnion${modelNameUpperCamel}List(${modelNameLowerCamel});
    }

    /**
    * 根据Id查询单条数据
    */
    @Override
    public ${modelNameUpperCamel} query${modelNameUpperCamel}ById(${pkColumn.javaType} ${pkColumn.smallColumnName}){
        return ${modelNameLowerCamel}Mapper.select${modelNameUpperCamel}ById(${pkColumn.smallColumnName});
    }

    /**
    * 增加
    * @param ${modelNameLowerCamel}
    * @return ${functionName}
    */
    @Override
    public Integer add${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel}){
        return ${modelNameLowerCamel}Mapper.insert${modelNameUpperCamel}(${modelNameLowerCamel});
    }

    /**
    * 批量增加
    * @param ${modelNameLowerCamel}List
    * @return ${functionName}
    */
    @Override
    public Integer batchAdd${modelNameUpperCamel}List(List<${modelNameUpperCamel}> ${modelNameLowerCamel}List){
        return ${modelNameLowerCamel}Mapper.batchInsert${modelNameUpperCamel}List(${modelNameLowerCamel}List);
    }

    /**
    * 删除
    * @param ${pkColumn.smallColumnName}
    */
    @Override
    public Integer remove${modelNameUpperCamel}ById(${pkColumn.javaType} ${pkColumn.smallColumnName}){
        return ${modelNameLowerCamel}Mapper.delete${modelNameUpperCamel}ById(${pkColumn.smallColumnName});
    }

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 逻辑删除,更新删除标志字段
    * @param ${pkColumn.smallColumnName}
    */
    @Override
    public Integer modifyDelFlagById(${pkColumn.javaType} ${pkColumn.smallColumnName}){
        return ${modelNameLowerCamel}Mapper.updateDelFlagById(${pkColumn.smallColumnName});
    }
    </#if>
    </#list>

    /**
    * 批量删除
    */
    @Override
    public Integer remove${modelNameUpperCamel}ByIds(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s){
        return ${modelNameLowerCamel}Mapper.delete${modelNameUpperCamel}ByIds(${pkColumn.smallColumnName}s);
    }

    <#list allColumn as column>
    <#if column.columnName=='del_flag'>
    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ${pkColumn.smallColumnName}s
    */
    @Override
    public Integer modifyDelFlagByIds(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s){
        return ${modelNameLowerCamel}Mapper.updateDelFlagByIds(${pkColumn.smallColumnName}s);
    }
    </#if>
    </#list>

    /**
    * 修改
    * @param ${modelNameLowerCamel}
    */
    @Override
    public Integer modify${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel}){
        return ${modelNameLowerCamel}Mapper.update${modelNameUpperCamel}(${modelNameLowerCamel});
    }

    /**
    * 批量修改某几个字段
    * @param ${pkColumn.smallColumnName}s
    */
    @Override
    public Integer batchModify${modelNameUpperCamel}List(${pkColumn.javaType}[] ${pkColumn.smallColumnName}s){
        return ${modelNameLowerCamel}Mapper.batchUpdate${modelNameUpperCamel}List(${pkColumn.smallColumnName}s);
    }
}
