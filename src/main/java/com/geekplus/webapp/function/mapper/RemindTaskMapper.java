package com.geekplus.webapp.function.mapper;

import com.geekplus.webapp.function.entity.RemindTask;
import java.util.List;

/**
 * 提醒任务 提醒任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface RemindTaskMapper {

    /**
    * 查询全部
    */
    List<RemindTask> selectRemindTaskList(RemindTask remindTask);

    /**
    * 查询全部,联合查询使用
    */
    List<RemindTask> selectUnionRemindTaskList(RemindTask remindTask);

    /**
    * 根据Id查询单条数据
    */
    RemindTask selectRemindTaskById(Integer id);

    /**
    * 增加
    * @param remindTask
    * @return 提醒任务
    */
    Integer insertRemindTask(RemindTask remindTask);

    /**
    * 批量增加
    * @param remindTaskList
    * @return
    */
    public int batchInsertRemindTaskList(List<RemindTask> remindTaskList);

    /**
    * 删除
    * @param id
    */
    Integer deleteRemindTaskById(Integer id);


    /**
    * 批量删除
    * @param ids
    */
    Integer deleteRemindTaskByIds(Integer[] ids);


    /**
    * 修改
    * @param remindTask
    */
    Integer updateRemindTask(RemindTask remindTask);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateRemindTaskList(Integer[] ids);
}
