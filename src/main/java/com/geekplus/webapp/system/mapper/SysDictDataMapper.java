package com.geekplus.webapp.system.mapper;

import com.geekplus.webapp.system.entity.SysDictData;

import java.util.Collection;
import java.util.List;

/**
 * 数据字典数据项 数据字典数据项
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysDictDataMapper {

    /**
    * 查询全部
    */
    List<SysDictData> selectSysDictDataList(SysDictData sysDictData);

    /**
    * 查询全部,联合查询使用
    */
    List<SysDictData> selectUnionSysDictDataList(SysDictData sysDictData);

    /**
    * 根据Id查询单条数据
    */
    SysDictData selectSysDictDataById(Long id);

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
    * 增加
    * @param sysDictData
    * @return 数据字典数据项
    */
    Integer insertSysDictData(SysDictData sysDictData);

    /**
    * 批量增加
    * @param sysDictDataList
    * @return
    */
    public int batchInsertSysDictDataList(List<SysDictData> sysDictDataList);

    /**
    * 删除
    * @param id
    */
    Integer deleteSysDictDataById(Long id);


    /**
    * 批量删除
    * @param ids
    */
    Integer deleteSysDictDataByIds(Long[] ids);


    /**
    * 修改
    * @param sysDictData
    */
    Integer updateSysDictData(SysDictData sysDictData);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateSysDictDataList(Long[] ids);
}
