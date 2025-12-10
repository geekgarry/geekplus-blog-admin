package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.mapper.UserAppointmentMapper;
import com.geekplus.webapp.function.entity.UserAppointment;
import com.geekplus.webapp.function.service.UserAppointmentService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2025/09/12.
 */
@Service
@Transactional
public class UserAppointmentServiceImpl implements UserAppointmentService {
    @Resource
    private UserAppointmentMapper userAppointmentMapper;

    /**
    * 增加
    * @param userAppointment
    * @return 用户预约任务时间表
    */
    public Integer insertUserAppointment(UserAppointment userAppointment){
        return userAppointmentMapper.insertUserAppointment(userAppointment);
    }

    /**
    * 批量增加
    * @param userAppointmentList
    * @return 用户预约任务时间表
    */
    public Integer batchInsertUserAppointmentList(List<UserAppointment> userAppointmentList){
        return userAppointmentMapper.batchInsertUserAppointmentList(userAppointmentList);
    }

    /**
    * 删除
    * @param id
    */
    public Integer deleteUserAppointmentById(String id){
        return userAppointmentMapper.deleteUserAppointmentById(id);
    }

    /**
     * 逻辑删除
     * @param id
     */
    public Integer modifyDelFlagById(String id){
        return userAppointmentMapper.updateDelFlagById(id);
    }

    /**
    * 批量删除
    */
    public Integer deleteUserAppointmentByIds(String[] ids){
        return userAppointmentMapper.deleteUserAppointmentByIds(ids);
    }

    /**
    * 修改
    * @param userAppointment
    */
    public Integer updateUserAppointment(UserAppointment userAppointment){
        return userAppointmentMapper.updateUserAppointment(userAppointment);
    }

    /**
    * 批量修改某几个字段
    * @param ids
    */
    public Integer batchUpdateUserAppointmentList(String[] ids){
        return userAppointmentMapper.batchUpdateUserAppointmentList(ids);
    }

    /**
    * 查询全部
    */
    public List<UserAppointment> selectUserAppointmentList(UserAppointment userAppointment){
        return userAppointmentMapper.selectUserAppointmentList(userAppointment);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<UserAppointment> selectUnionUserAppointmentList(UserAppointment userAppointment){
        return userAppointmentMapper.selectUnionUserAppointmentList(userAppointment);
    }

    /**
    * 根据Id查询单条数据
    */
    public UserAppointment selectUserAppointmentById(String id){
        return userAppointmentMapper.selectUserAppointmentById(id);
    }

    @Override
    public Integer modifyStatus(String status, String id) {
        return userAppointmentMapper.updateStatus(status, id);
    }

    @Override
    public List<UserAppointment> userQueryAllByUserId(String userId) {
        return userAppointmentMapper.userFindAllByUserId(userId);
    }
}
