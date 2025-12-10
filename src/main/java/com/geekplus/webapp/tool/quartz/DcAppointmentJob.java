package com.geekplus.webapp.tool.quartz;

import com.geekplus.common.util.email.EmailUtil;
import com.geekplus.common.util.http.ServletUtil;
import com.geekplus.framework.jwtshiro.JwtUtil;
import com.geekplus.webapp.function.service.impl.UserAppointmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * author     : geekplus
 * email      :
 * date       : 9/12/25 2:47 PM
 * description: //TODO
 */
@Slf4j
@Component
public class DcAppointmentJob implements Job {
    @Autowired
    private UserAppointmentServiceImpl userAppointmentService;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        String appointmentId = map.getString("appointmentId");
        String payload = map.getString("payload");
        String userId = map.getString("userId");
        String description = map.getString("description");
        //开始执行预约任务
        emailUtil.sendEmail("1982448788@qq.com", "这是用户("+userId+")预约的一个定时发送的邮件:" + description , "text", "GeekPlus："+ payload);
        log.info("执行预约任务 id={}, payload={}", appointmentId, payload);

        userAppointmentService.modifyStatus("DONE", appointmentId);
    }
}
