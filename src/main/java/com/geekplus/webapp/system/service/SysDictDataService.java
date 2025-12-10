package com.geekplus.webapp.system.service;

import com.geekplus.webapp.system.entity.SysDictData;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 数据字典数据项 数据字典数据项
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysDictDataService {

    /**
    * 查询全部
    */
    public List<SysDictData> querySysDictDataList(SysDictData sysDictData);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<SysDictData> queryUnionSysDictDataList(SysDictData sysDictData);

    /**
    * 根据Id查询单条数据
    */
    public SysDictData querySysDictDataById(Long id);

    /**
    * 增加
    * @param sysDictData
    * @return 数据字典数据项
    */
    public Integer addSysDictData(SysDictData sysDictData);

    /**
    * 批量增加
    * @param sysDictDataList
    * @return 数据字典数据项
    */
    public Integer batchAddSysDictDataList(List<SysDictData> sysDictDataList);

    /**
    * 删除
    * @param id
    */
    public Integer removeSysDictDataById(Long id);


    /**
    * 批量删除
    * @param ids
    */
    public Integer removeSysDictDataByIds(Long[] ids);


    /**
    * 修改
    * @param sysDictData
    */
    public Integer modifySysDictData(SysDictData sysDictData);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifySysDictDataList(Long[] ids);
}
