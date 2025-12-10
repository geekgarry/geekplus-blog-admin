package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.mapper.RemindTaskMapper;
import com.geekplus.webapp.function.entity.RemindTask;
import com.geekplus.webapp.function.service.RemindTaskService;
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
public class RemindTaskServiceImpl implements RemindTaskService {
    @Resource
    private RemindTaskMapper remindTaskMapper;

    /**
    * 查询全部
    */
    public List<RemindTask> queryRemindTaskList(RemindTask remindTask){
        return remindTaskMapper.selectRemindTaskList(remindTask);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<RemindTask> queryUnionRemindTaskList(RemindTask remindTask){
        return remindTaskMapper.selectUnionRemindTaskList(remindTask);
    }

    /**
    * 根据Id查询单条数据
    */
    public RemindTask queryRemindTaskById(Integer id){
        return remindTaskMapper.selectRemindTaskById(id);
    }

    /**
    * 增加
    * @param remindTask
    * @return 提醒任务
    */
    public Integer addRemindTask(RemindTask remindTask){
        return remindTaskMapper.insertRemindTask(remindTask);
    }

    /**
    * 批量增加
    * @param remindTaskList
    * @return 提醒任务
    */
    public Integer batchAddRemindTaskList(List<RemindTask> remindTaskList){
        return remindTaskMapper.batchInsertRemindTaskList(remindTaskList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer removeRemindTaskById(Integer id){
        return remindTaskMapper.deleteRemindTaskById(id);
    }


    /**
    * 批量删除
    */
    public Integer removeRemindTaskByIds(Integer[] ids){
        return remindTaskMapper.deleteRemindTaskByIds(ids);
    }


    /**
    * 修改
    * @param remindTask
    */
    public Integer modifyRemindTask(RemindTask remindTask){
        return remindTaskMapper.updateRemindTask(remindTask);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchModifyRemindTaskList(Integer[] ids){
        return remindTaskMapper.batchUpdateRemindTaskList(ids);
    }
}
