package com.geekplus.webapp.system.mapper;

import com.geekplus.webapp.system.entity.SysQuartzJob;
import java.util.List;

/**
 * quartz系统调度任务 quartz系统调度任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface SysQuartzJobMapper {

    /**
    * 查询全部
    */
    List<SysQuartzJob> selectSysQuartzJobList(SysQuartzJob sysQuartzJob);

    /**
    * 查询全部,联合查询使用
    */
    List<SysQuartzJob> selectUnionSysQuartzJobList(SysQuartzJob sysQuartzJob);

    /**
    * 根据Id查询单条数据
    */
    SysQuartzJob selectSysQuartzJobById(String id);

    /**
    * 增加
    * @param sysQuartzJob
    * @return quartz系统调度任务
    */
    Integer insertSysQuartzJob(SysQuartzJob sysQuartzJob);

    /**
    * 批量增加
    * @param sysQuartzJobList
    * @return
    */
    public int batchInsertSysQuartzJobList(List<SysQuartzJob> sysQuartzJobList);

    /**
    * 删除
    * @param id
    */
    Integer deleteSysQuartzJobById(String id);

    /**
    * 逻辑删除
    * @param id
    */
    Integer updateDelFlagById(String id);

    /**
    * 批量删除
    * @param ids
    */
    Integer deleteSysQuartzJobByIds(String[] ids);

    /**
    * 逻辑批量删除
    * @param ids
    */
    Integer updateDelFlagByIds(String[] ids);

    /**
    * 修改
    * @param sysQuartzJob
    */
    Integer updateSysQuartzJob(SysQuartzJob sysQuartzJob);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateSysQuartzJobList(String[] ids);
}
