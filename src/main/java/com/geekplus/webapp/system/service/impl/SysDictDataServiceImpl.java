package com.geekplus.webapp.system.service.impl;

import com.geekplus.webapp.system.mapper.SysDictDataMapper;
import com.geekplus.webapp.system.entity.SysDictData;
import com.geekplus.webapp.system.service.SysDictDataService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2025/09/20.
 */
@Service
@Transactional
public class SysDictDataServiceImpl implements SysDictDataService {
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    /**
    * 查询全部
    */
    public List<SysDictData> querySysDictDataList(SysDictData sysDictData){
        return sysDictDataMapper.selectSysDictDataList(sysDictData);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<SysDictData> queryUnionSysDictDataList(SysDictData sysDictData){
        return sysDictDataMapper.selectUnionSysDictDataList(sysDictData);
    }

    /**
    * 根据Id查询单条数据
    */
    public SysDictData querySysDictDataById(Long id){
        return sysDictDataMapper.selectSysDictDataById(id);
    }

    /**
    * 增加
    * @param sysDictData
    * @return 数据字典数据项
    */
    public Integer addSysDictData(SysDictData sysDictData){
        return sysDictDataMapper.insertSysDictData(sysDictData);
    }

    /**
    * 批量增加
    * @param sysDictDataList
    * @return 数据字典数据项
    */
    public Integer batchAddSysDictDataList(List<SysDictData> sysDictDataList){
        return sysDictDataMapper.batchInsertSysDictDataList(sysDictDataList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer removeSysDictDataById(Long id){
        return sysDictDataMapper.deleteSysDictDataById(id);
    }


    /**
    * 批量删除
    */
    public Integer removeSysDictDataByIds(Long[] ids){
        return sysDictDataMapper.deleteSysDictDataByIds(ids);
    }


    /**
    * 修改
    * @param sysDictData
    */
    public Integer modifySysDictData(SysDictData sysDictData){
        return sysDictDataMapper.updateSysDictData(sysDictData);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchModifySysDictDataList(Long[] ids){
        return sysDictDataMapper.batchUpdateSysDictDataList(ids);
    }
}
