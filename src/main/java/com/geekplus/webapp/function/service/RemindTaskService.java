package com.geekplus.webapp.function.service;

import com.geekplus.webapp.function.entity.RemindTask;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 提醒任务 提醒任务
 * Created by CodeGenerator on 2025/09/20.
 */
public interface RemindTaskService {

    /**
    * 查询全部
    */
    public List<RemindTask> queryRemindTaskList(RemindTask remindTask);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<RemindTask> queryUnionRemindTaskList(RemindTask remindTask);

    /**
    * 根据Id查询单条数据
    */
    public RemindTask queryRemindTaskById(Integer id);

    /**
    * 增加
    * @param remindTask
    * @return 提醒任务
    */
    public Integer addRemindTask(RemindTask remindTask);

    /**
    * 批量增加
    * @param remindTaskList
    * @return 提醒任务
    */
    public Integer batchAddRemindTaskList(List<RemindTask> remindTaskList);

    /**
    * 删除
    * @param id
    */
    public Integer removeRemindTaskById(Integer id);


    /**
    * 批量删除
    * @param ids
    */
    public Integer removeRemindTaskByIds(Integer[] ids);


    /**
    * 修改
    * @param remindTask
    */
    public Integer modifyRemindTask(RemindTask remindTask);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchModifyRemindTaskList(Integer[] ids);
}
