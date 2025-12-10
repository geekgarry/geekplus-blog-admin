package com.geekplus.webapp.system.service.impl;

import com.geekplus.common.util.DictUtils;
import com.geekplus.webapp.system.entity.SysDictData;
import com.geekplus.webapp.system.mapper.SysDictDataMapper;
import com.geekplus.webapp.system.mapper.SysDictMapper;
import com.geekplus.webapp.system.entity.SysDict;
import com.geekplus.webapp.system.service.SysDictService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2025/09/20.
 */
@Service
@Transactional
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private SysDictDataMapper dictDataMapper;

    @PostConstruct
    public void init()
    {
        loadingDictCache();
    }

    /**
    * 查询全部
    */
    public List<SysDict> querySysDictList(SysDict sysDict){
        return sysDictMapper.selectSysDictList(sysDict);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<SysDict> queryUnionSysDictList(SysDict sysDict){
        return sysDictMapper.selectUnionSysDictList(sysDict);
    }

    /**
    * 根据Id查询单条数据
    */
    public SysDict querySysDictById(Long id){
        return sysDictMapper.selectSysDictById(id);
    }

    /**
    * 增加
    * @param sysDict
    * @return 数据字典
    */
    public Integer addSysDict(SysDict sysDict){
        return sysDictMapper.insertSysDict(sysDict);
    }

    /**
    * 批量增加
    * @param sysDictList
    * @return 数据字典
    */
    public Integer batchAddSysDictList(List<SysDict> sysDictList){
        return sysDictMapper.batchInsertSysDictList(sysDictList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer removeSysDictById(Long id){
        return sysDictMapper.deleteSysDictById(id);
    }

    /**
    * 逻辑删除,更新删除标志字段
    * @param id
    */
    public Integer modifyDelFlagById(Long id){
        return sysDictMapper.updateDelFlagById(id);
    }

    /**
    * 批量删除
    */
    public Integer removeSysDictByIds(Long[] ids){
        return sysDictMapper.deleteSysDictByIds(ids);
    }

    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ids
    */
    public Integer modifyDelFlagByIds(Long[] ids){
        return sysDictMapper.updateDelFlagByIds(ids);
    }

    /**
    * 修改
    * @param sysDict
    */
    public Integer modifySysDict(SysDict sysDict){
        return sysDictMapper.updateSysDict(sysDict);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchModifySysDictList(Long[] ids){
        return sysDictMapper.batchUpdateSysDictList(ids);
    }

    @Override
    public void loadingDictCache()
    {
        SysDictData dictData = new SysDictData();
        dictData.setStatus("0");
        Map<String, List<SysDictData>> dictDataMap = dictDataMapper.selectDictDataList(dictData).stream().collect(Collectors.groupingBy(SysDictData::getDictCode));
        for (Map.Entry<String, List<SysDictData>> entry : dictDataMap.entrySet())
        {
            DictUtils.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList()));
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache()
    {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache()
    {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDict> selectDictTypeAll()
    {
        return sysDictMapper.selectDictTypeAll();
    }
}
