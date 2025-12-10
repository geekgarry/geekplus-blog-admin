package com.geekplus.webapp.system.mapper;

import com.geekplus.webapp.system.entity.ResourceMetaData;
import com.geekplus.webapp.system.entity.dto.ResourceMetaDataDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源数据表 资源数据表
 * Created by geekplus on 2025/12/02.
 */
public interface ResourceMetaDataMapper {

    /**
    * 查询全部
    */
    List<ResourceMetaData> selectResourceMetaDataList(ResourceMetaData resourceMetaData);

    /**
    * 查询全部,联合查询使用
    */
    List<ResourceMetaData> selectUnionResourceMetaDataList(ResourceMetaData resourceMetaData);

    /**
    * 根据Id查询单条数据
    */
    ResourceMetaData selectResourceMetaDataById(Long id);

    /**
    * 增加
    * @param resourceMetaData
    * @return 资源数据表
    */
    Integer insertResourceMetaData(ResourceMetaData resourceMetaData);

    /**
    * 批量增加
    * @param resourceMetaDataList
    * @return
    */
    public int batchInsertResourceMetaDataList(List<ResourceMetaData> resourceMetaDataList);

    /**
    * 删除
    * @param id
    */
    Integer deleteResourceMetaDataById(Long id);


    /**
    * 批量删除
    * @param ids
    */
    Integer deleteResourceMetaDataByIds(Long[] ids);

    /**
     * 修改,根据逻辑文件地址
     * @param resourceMetaData
     */
    Integer updateResourceMetaByFilePath(ResourceMetaData resourceMetaData);

    /**
     * 批量修改几个字段
     * @param resourceMetaData
     */
    Integer batchUpdateResourceMetaListByFilePath(ResourceMetaDataDto resourceMetaData);

    /**
    * 修改
    * @param resourceMetaData
    */
    Integer updateResourceMetaData(ResourceMetaData resourceMetaData);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateResourceMetaDataList(Long[] ids);
}
