package com.geekplus.webapp.system.service.impl;

import com.geekplus.webapp.system.mapper.SysQuartzJobMapper;
import com.geekplus.webapp.system.entity.SysQuartzJob;
import com.geekplus.webapp.system.service.SysQuartzJobService;
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
public class SysQuartzJobServiceImpl implements SysQuartzJobService {
    @Resource
    private SysQuartzJobMapper sysQuartzJobMapper;

    /**
    * 查询全部
    */
    public List<SysQuartzJob> querySysQuartzJobList(SysQuartzJob sysQuartzJob){
        return sysQuartzJobMapper.selectSysQuartzJobList(sysQuartzJob);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<SysQuartzJob> queryUnionSysQuartzJobList(SysQuartzJob sysQuartzJob){
        return sysQuartzJobMapper.selectUnionSysQuartzJobList(sysQuartzJob);
    }

    /**
    * 根据Id查询单条数据
    */
    public SysQuartzJob querySysQuartzJobById(String id){
        return sysQuartzJobMapper.selectSysQuartzJobById(id);
    }

    /**
    * 增加
    * @param sysQuartzJob
    * @return quartz系统调度任务
    */
    public Integer addSysQuartzJob(SysQuartzJob sysQuartzJob){
        return sysQuartzJobMapper.insertSysQuartzJob(sysQuartzJob);
    }

    /**
    * 批量增加
    * @param sysQuartzJobList
    * @return quartz系统调度任务
    */
    public Integer batchAddSysQuartzJobList(List<SysQuartzJob> sysQuartzJobList){
        return sysQuartzJobMapper.batchInsertSysQuartzJobList(sysQuartzJobList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer removeSysQuartzJobById(String id){
        return sysQuartzJobMapper.deleteSysQuartzJobById(id);
    }

    /**
    * 逻辑删除,更新删除标志字段
    * @param id
    */
    public Integer modifyDelFlagById(String id){
        return sysQuartzJobMapper.updateDelFlagById(id);
    }

    /**
    * 批量删除
    */
    public Integer removeSysQuartzJobByIds(String[] ids){
        return sysQuartzJobMapper.deleteSysQuartzJobByIds(ids);
    }

    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ids
    */
    public Integer modifyDelFlagByIds(String[] ids){
        return sysQuartzJobMapper.updateDelFlagByIds(ids);
    }

    /**
    * 修改
    * @param sysQuartzJob
    */
    public Integer modifySysQuartzJob(SysQuartzJob sysQuartzJob){
        return sysQuartzJobMapper.updateSysQuartzJob(sysQuartzJob);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchModifySysQuartzJobList(String[] ids){
        return sysQuartzJobMapper.batchUpdateSysQuartzJobList(ids);
    }
}
