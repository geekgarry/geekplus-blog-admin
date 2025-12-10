package com.geekplus.webapp.function.mapper;

import com.geekplus.webapp.function.entity.RemindSubTask;
import java.util.List;

/**
 * 提醒任务子任务 提醒任务子任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface RemindSubTaskMapper {

    /**
    * 查询全部
    */
    List<RemindSubTask> selectRemindSubTaskList(RemindSubTask remindSubTask);

    /**
    * 查询全部,联合查询使用
    */
    List<RemindSubTask> selectUnionRemindSubTaskList(RemindSubTask remindSubTask);

    /**
    * 根据Id查询单条数据
    */
    RemindSubTask selectRemindSubTaskById(Integer id);

    /**
    * 增加
    * @param remindSubTask
    * @return 提醒任务子任务
    */
    Integer insertRemindSubTask(RemindSubTask remindSubTask);

    /**
    * 批量增加
    * @param remindSubTaskList
    * @return
    */
    public int batchInsertRemindSubTaskList(List<RemindSubTask> remindSubTaskList);

    /**
    * 删除
    * @param id
    */
    Integer deleteRemindSubTaskById(Integer id);


    /**
    * 批量删除
    * @param ids
    */
    Integer deleteRemindSubTaskByIds(Integer[] ids);


    /**
    * 修改
    * @param remindSubTask
    */
    Integer updateRemindSubTask(RemindSubTask remindSubTask);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateRemindSubTaskList(Integer[] ids);
}
