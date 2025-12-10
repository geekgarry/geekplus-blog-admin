package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.mapper.RemindSubTaskMapper;
import com.geekplus.webapp.function.entity.RemindSubTask;
import com.geekplus.webapp.function.service.RemindSubTaskService;
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
public class RemindSubTaskServiceImpl implements RemindSubTaskService {
    @Resource
    private RemindSubTaskMapper remindSubTaskMapper;

    /**
    * 查询全部
    */
    public List<RemindSubTask> queryRemindSubTaskList(RemindSubTask remindSubTask){
        return remindSubTaskMapper.selectRemindSubTaskList(remindSubTask);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<RemindSubTask> queryUnionRemindSubTaskList(RemindSubTask remindSubTask){
        return remindSubTaskMapper.selectUnionRemindSubTaskList(remindSubTask);
    }

    /**
    * 根据Id查询单条数据
    */
    public RemindSubTask queryRemindSubTaskById(Integer id){
        return remindSubTaskMapper.selectRemindSubTaskById(id);
    }

    /**
    * 增加
    * @param remindSubTask
    * @return 提醒任务子任务
    */
    public Integer addRemindSubTask(RemindSubTask remindSubTask){
        return remindSubTaskMapper.insertRemindSubTask(remindSubTask);
    }

    /**
    * 批量增加
    * @param remindSubTaskList
    * @return 提醒任务子任务
    */
    public Integer batchAddRemindSubTaskList(List<RemindSubTask> remindSubTaskList){
        return remindSubTaskMapper.batchInsertRemindSubTaskList(remindSubTaskList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer removeRemindSubTaskById(Integer id){
        return remindSubTaskMapper.deleteRemindSubTaskById(id);
    }


    /**
    * 批量删除
    */
    public Integer removeRemindSubTaskByIds(Integer[] ids){
        return remindSubTaskMapper.deleteRemindSubTaskByIds(ids);
    }


    /**
    * 修改
    * @param remindSubTask
    */
    public Integer modifyRemindSubTask(RemindSubTask remindSubTask){
        return remindSubTaskMapper.updateRemindSubTask(remindSubTask);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchModifyRemindSubTaskList(Integer[] ids){
        return remindSubTaskMapper.batchUpdateRemindSubTaskList(ids);
    }
}
