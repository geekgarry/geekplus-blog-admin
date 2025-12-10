package com.geekplus.webapp.function.service;

import com.geekplus.webapp.function.entity.UserAppointment;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 用户预约任务时间表 用户预约任务时间表
 * Created by CodeGenerator on 2025/09/12.
 */
public interface UserAppointmentService {

    /**
    * 增加
    * @param userAppointment
    * @return 用户预约任务时间表
    */
    public Integer insertUserAppointment(UserAppointment userAppointment);

    /**
    * 批量增加
    * @param userAppointmentList
    * @return 用户预约任务时间表
    */
    public Integer batchInsertUserAppointmentList(List<UserAppointment> userAppointmentList);

    /**
    * 删除
    * @param id
    */
    public Integer deleteUserAppointmentById(String id);

    /**
     * 逻辑删除
     * @param id
     */
    public Integer modifyDelFlagById(String id);

    /**
    * 批量删除某几个字段
    */
    public Integer deleteUserAppointmentByIds(String[] ids);

    /**
    * 修改
    * @param userAppointment
    */
    public Integer updateUserAppointment(UserAppointment userAppointment);

    /**
    * 批量修改
    * @param ids
    */
    public Integer batchUpdateUserAppointmentList(String[] ids);

    /**
    * 查询全部
    */
    public List<UserAppointment> selectUserAppointmentList(UserAppointment userAppointment);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<UserAppointment> selectUnionUserAppointmentList(UserAppointment userAppointment);

    /**
    * 根据Id查询单条数据
    */
    public UserAppointment selectUserAppointmentById(String id);

    /**
     * @Author geekplus
     * @Description 更新用户预约任务表状态
     */
    public Integer modifyStatus(String status, String id);

    /**
     * @Author geekplus
     * @Description 用户查询自己的预约任务
     */
    public List<UserAppointment> userQueryAllByUserId(String userId);
}
