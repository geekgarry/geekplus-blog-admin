package com.geekplus.webapp.function.service;

import com.geekplus.webapp.function.entity.RemindSubTask;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 提醒任务子任务 提醒任务子任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface RemindSubTaskService {

    /**
    * 查询全部
    */
    public List<RemindSubTask> queryRemindSubTaskList(RemindSubTask remindSubTask);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<RemindSubTask> queryUnionRemindSubTaskList(RemindSubTask remindSubTask);

    /**
    * 根据Id查询单条数据
    */
    public RemindSubTask queryRemindSubTaskById(Integer id);

    /**
    * 增加
    * @param remindSubTask
    * @return 提醒任务子任务
    */
    public Integer addRemindSubTask(RemindSubTask remindSubTask);

    /**
    * 批量增加
    * @param remindSubTaskList
    * @return 提醒任务子任务
    */
    public Integer batchAddRemindSubTaskList(List<RemindSubTask> remindSubTaskList);

    /**
    * 删除
    * @param id
    */
    public Integer removeRemindSubTaskById(Integer id);


    /**
    * 批量删除
    * @param ids
    */
    public Integer removeRemindSubTaskByIds(Integer[] ids);


    /**
    * 修改
    * @param remindSubTask
    */
    public Integer modifyRemindSubTask(RemindSubTask remindSubTask);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifyRemindSubTaskList(Integer[] ids);
}
