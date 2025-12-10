package com.geekplus.webapp.system.service;

import com.geekplus.webapp.system.entity.SysQuartzJob;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * quartz系统调度任务 quartz系统调度任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysQuartzJobService {

    /**
    * 查询全部
    */
    public List<SysQuartzJob> querySysQuartzJobList(SysQuartzJob sysQuartzJob);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<SysQuartzJob> queryUnionSysQuartzJobList(SysQuartzJob sysQuartzJob);

    /**
    * 根据Id查询单条数据
    */
    public SysQuartzJob querySysQuartzJobById(String id);

    /**
    * 增加
    * @param sysQuartzJob
    * @return quartz系统调度任务
    */
    public Integer addSysQuartzJob(SysQuartzJob sysQuartzJob);

    /**
    * 批量增加
    * @param sysQuartzJobList
    * @return quartz系统调度任务
    */
    public Integer batchAddSysQuartzJobList(List<SysQuartzJob> sysQuartzJobList);

    /**
    * 删除
    * @param id
    */
    public Integer removeSysQuartzJobById(String id);

    /**
    * 逻辑删除,更新删除标志字段
    * @param id
    */
    Integer modifyDelFlagById(String id);

    /**
    * 批量删除
    * @param ids
    */
    public Integer removeSysQuartzJobByIds(String[] ids);

    /**
    * 逻辑批量删除,更新删除标志字段
    * @param ids
    */
    Integer modifyDelFlagByIds(String[] ids);

    /**
    * 修改
    * @param sysQuartzJob
    */
    public Integer modifySysQuartzJob(SysQuartzJob sysQuartzJob);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifySysQuartzJobList(String[] ids);
}
