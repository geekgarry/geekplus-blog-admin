package com.geekplus.webapp.tool.quartz.service;

import com.geekplus.common.constant.Constant;
import com.geekplus.framework.web.exception.BusinessException;
import com.geekplus.webapp.function.entity.UserAppointment;
import com.geekplus.webapp.function.service.impl.UserAppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * author     : geekplus
 * email      :
 * date       : 9/12/25 8:47 PM
 * description: //TODO
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlusAppointmentService {
    @Autowired
    private UserAppointmentServiceImpl userAppointmentService;
    @Autowired
    private Scheduler scheduler;

    @Transactional(rollbackFor = Exception.class)
    public boolean create(UserAppointment appointment) {
        appointment.setStatus("SCHEDULED");
        appointment.setDelFlag(Constant.DEL_FLAG_0);
        int flag = userAppointmentService.insertUserAppointment(appointment);
        if(flag > 0) {
            if(Constant.STATUS_NORMAL_I.equals(appointment.getDelFlag())) {
                try {
                    addUserAppTask(appointment);
                    log.info("预约成功："+appointment.getPayload());
                    return true;
                } catch (SchedulerException e) {
                    log.info("预约失败："+e.getMessage());
                    throw new BusinessException("预约失败", e);
                }
            }
        }
        return false;
    }

    //用户添加任务
    @Transactional(rollbackFor = Exception.class)
    public void addUserAppTask(UserAppointment appointment) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(com.geekplus.webapp.tool.quartz.DcAppointmentJob.class)
                .withIdentity("job-" + appointment.getId(), "appointments")
                .usingJobData("appointmentId", appointment.getId())
                .usingJobData("payload", appointment.getPayload())
                .usingJobData("userId", appointment.getUserId())
                .usingJobData("description", appointment.getDescription())
                .storeDurably(false)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + appointment.getId(), "appointments")
                .startAt(Date.from(appointment.getScheduleAt().atZone(ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withMisfireHandlingInstructionFireNow())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void cancel(String id) {
        try {
            scheduler.deleteJob(new JobKey("job-" + id, "appointments"));
            userAppointmentService.modifyStatus("CANCELLED", id);
        } catch (SchedulerException e) {
            throw new BusinessException("取消预约任务失败", e);
        }
    }

    /**
     * 取消任务（逻辑删除 + Quartz unschedule）
     */
    @Transactional
    public void cancelAppointment(String appointmentId) throws Exception {
        UserAppointment appointment = userAppointmentService.selectUserAppointmentById(appointmentId);
        if (appointment != null) {
            // 1. 更新逻辑删除
            userAppointmentService.modifyDelFlagById(appointmentId);

            // 2. 从 Quartz 中移除任务
            JobKey jobKey = new JobKey("job_" + appointmentId, "appointments");
            TriggerKey triggerKey = new TriggerKey("trigger_" + appointmentId, "appointments");

            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);

            log.info("已取消任务: " + appointmentId);
        }
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void pause(String id) {
        try {
            scheduler.pauseJob(new JobKey("job-" + id, "appointments"));
            userAppointmentService.modifyStatus("PAUSED", id);
        } catch (SchedulerException e) {
            throw new BusinessException("中断预约任务失败", e);
        }
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void resume(String id) {
        try {
            scheduler.resumeJob(new JobKey("job-" + id, "appointments"));
            userAppointmentService.modifyStatus("SCHEDULED", id);
        } catch (SchedulerException e) {
            throw new BusinessException("继续预约任务失败", e);
        }
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void delete(String id) {
        try {
            scheduler.deleteJob(new JobKey("job-" + id, "appointments"));
            //逻辑删除用户的预约任务,修改del_flag字段为1
            userAppointmentService.modifyDelFlagById(id);
        } catch (SchedulerException e) {
            throw new BusinessException("删除预约任务失败", e);
        }
    }

    public List<UserAppointment> listByUserId(String userId) {

        return userAppointmentService.userQueryAllByUserId(userId);
    }
}
