package com.geekplus.webapp.system.service.impl;

import com.geekplus.webapp.system.entity.dto.ResourceMetaDataDto;
import com.geekplus.webapp.system.mapper.ResourceMetaDataMapper;
import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.service.ResourceMetaDataService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by geekplus on 2025/12/02.
 */
@Service
@Transactional
public class ResourceMetaDataServiceImpl implements ResourceMetaDataService {
    @Resource
    private ResourceMetaDataMapper resourceMetaDataMapper;

    /**
    * 查询全部
    */
    @Override
    public List<ResourceMetaData> queryResourceMetaDataList(ResourceMetaData resourceMetaData){
        return resourceMetaDataMapper.selectResourceMetaDataList(resourceMetaData);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    @Override
    public List<ResourceMetaData> queryUnionResourceMetaDataList(ResourceMetaData resourceMetaData){
        return resourceMetaDataMapper.selectUnionResourceMetaDataList(resourceMetaData);
    }

    /**
    * 根据Id查询单条数据
    */
    @Override
    public ResourceMetaData queryResourceMetaDataById(Long id){
        return resourceMetaDataMapper.selectResourceMetaDataById(id);
    }

    /**
    * 增加
    * @param resourceMetaData
    * @return 资源数据表
    */
    @Override
    public Integer addResourceMetaData(ResourceMetaData resourceMetaData){
        return resourceMetaDataMapper.insertResourceMetaData(resourceMetaData);
    }

    /**
    * 批量增加
    * @param resourceMetaDataList
    * @return 资源数据表
    */
    @Override
    public Integer batchAddResourceMetaDataList(List<ResourceMetaData> resourceMetaDataList){
        return resourceMetaDataMapper.batchInsertResourceMetaDataList(resourceMetaDataList);
    }

    /**
    * 删除
    * @param id
    */
    @Override
    public Integer removeResourceMetaDataById(Long id){
        return resourceMetaDataMapper.deleteResourceMetaDataById(id);
    }


    /**
    * 批量删除
    */
    @Override
    public Integer removeResourceMetaDataByIds(Long[] ids){
        return resourceMetaDataMapper.deleteResourceMetaDataByIds(ids);
    }

    @Override
    public Integer modifyResourceMetaByFilePath(ResourceMetaData resourceMetaData) {
        return resourceMetaDataMapper.updateResourceMetaByFilePath(resourceMetaData);
    }

    @Override
    public Integer batchModifyResourceMetaListByFilePath(ResourceMetaDataDto resourceMetaData) {
        return resourceMetaDataMapper.batchUpdateResourceMetaListByFilePath(resourceMetaData);
    }

    /**
    * 修改
    * @param resourceMetaData
    */
    @Override
    public Integer modifyResourceMetaData(ResourceMetaData resourceMetaData){
        return resourceMetaDataMapper.updateResourceMetaData(resourceMetaData);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    @Override
    public Integer batchModifyResourceMetaDataList(Long[] ids){
        return resourceMetaDataMapper.batchUpdateResourceMetaDataList(ids);
    }
}
