package com.geekplus.webapp.function.mapper;

import com.geekplus.webapp.function.entity.UserAppointment;
import java.util.List;

/**
 * 用户预约任务时间表 用户预约任务时间表
 * Created by CodeGenerator on 2025/09/12.
 */
public interface UserAppointmentMapper {

    /**
    * 增加
    * @param userAppointment
    * @return 用户预约任务时间表
    */
    Integer insertUserAppointment(UserAppointment userAppointment);

    /**
    * 批量增加
    * @param userAppointmentList
    * @return
    */
    public int batchInsertUserAppointmentList(List<UserAppointment> userAppointmentList);

    /**
    * 删除
    * @param id
    */
    Integer deleteUserAppointmentById(String id);

    /**
     * 逻辑删除
     * @param id
     */
    Integer updateDelFlagById(String id);
    /**
    * 批量删除
    */
    Integer deleteUserAppointmentByIds(String[] ids);

    /**
    * 修改
    * @param userAppointment
    */
    Integer updateUserAppointment(UserAppointment userAppointment);

    /**
    * 批量修改魔偶几个字段
    * @param ids
    */
    Integer batchUpdateUserAppointmentList(String[] ids);

    /**
    * 查询全部
    */
    List<UserAppointment> selectUserAppointmentList(UserAppointment userAppointment);

    /**
    * 查询全部,联合查询使用
    */
    List<UserAppointment> selectUnionUserAppointmentList(UserAppointment userAppointment);

    /**
    * 根据Id查询单条数据
    */
    UserAppointment selectUserAppointmentById(String id);

    /**
      * @Author geekplus
      * @Description //更新用户预约任务表状态
      */
    Integer updateStatus(String status, String id);

    /**
     * @Author geekplus
     * @Description //用户查询自己的预约任务
     */
    List<UserAppointment> userFindAllByUserId(String userId);
}
