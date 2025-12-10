package com.geekplus.webapp.system.mapper;

import com.geekplus.webapp.system.entity.SysDict;
import java.util.List;

/**
 * 数据字典 数据字典
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysDictMapper {

    /**
    * 查询全部
    */
    List<SysDict> selectSysDictList(SysDict sysDict);

    /**
    * 查询全部,联合查询使用
    */
    List<SysDict> selectUnionSysDictList(SysDict sysDict);

    /**
    * 根据Id查询单条数据
    */
    SysDict selectSysDictById(Long id);

    /**
    * 增加
    * @param sysDict
    * @return 数据字典
    */
    Integer insertSysDict(SysDict sysDict);

    /**
    * 批量增加
    * @param sysDictList
    * @return
    */
    public int batchInsertSysDictList(List<SysDict> sysDictList);

    /**
    * 删除
    * @param id
    */
    Integer deleteSysDictById(Long id);

    /**
    * 逻辑删除
    * @param id
    */
    Integer updateDelFlagById(Long id);

    /**
    * 批量删除
    * @param ids
    */
    Integer deleteSysDictByIds(Long[] ids);

    /**
    * 逻辑批量删除
    * @param ids
    */
    Integer updateDelFlagByIds(Long[] ids);

    /**
    * 修改
    * @param sysDict
    */
    Integer updateSysDict(SysDict sysDict);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateSysDictList(Long[] ids);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    public List<SysDict> selectDictTypeAll();
}
