package com.geekplus.webapp.system.service;

import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.entity.dto.ResourceMetaDataDto;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 资源数据表 资源数据表
 * Created by geekplus on 2025/12/02.
 */
public interface ResourceMetaDataService {

    /**
    * 查询全部
    */
    public List<ResourceMetaData> queryResourceMetaDataList(ResourceMetaData resourceMetaData);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<ResourceMetaData> queryUnionResourceMetaDataList(ResourceMetaData resourceMetaData);

    /**
    * 根据Id查询单条数据
    */
    public ResourceMetaData queryResourceMetaDataById(Long id);

    /**
    * 增加
    * @param resourceMetaData
    * @return 资源数据表
    */
    public Integer addResourceMetaData(ResourceMetaData resourceMetaData);

    /**
    * 批量增加
    * @param resourceMetaDataList
    * @return 资源数据表
    */
    public Integer batchAddResourceMetaDataList(List<ResourceMetaData> resourceMetaDataList);

    /**
    * 删除
    * @param id
    */
    public Integer removeResourceMetaDataById(Long id);


    /**
    * 批量删除
    * @param ids
    */
    public Integer removeResourceMetaDataByIds(Long[] ids);

    /**
     * 修改,根据逻辑文件地址
     * @param resourceMetaData
     */
    Integer modifyResourceMetaByFilePath(ResourceMetaData resourceMetaData);

    /**
     * 批量修改几个字段
     * @param resourceMetaData
     */
    Integer batchModifyResourceMetaListByFilePath(ResourceMetaDataDto resourceMetaData);

    /**
    * 修改
    * @param resourceMetaData
    */
    public Integer modifyResourceMetaData(ResourceMetaData resourceMetaData);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifyResourceMetaDataList(Long[] ids);
}
