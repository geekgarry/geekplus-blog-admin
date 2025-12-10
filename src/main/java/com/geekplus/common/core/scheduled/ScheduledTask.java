package com.geekplus.common.core.scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekplus.common.core.socket.WebSocketServer;
import com.geekplus.common.domain.Result;
import com.geekplus.webapp.system.service.SysNoticeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * author     : geekplus
 * description: 定时任务
 */
@Configuration
public class ScheduledTask {
    @Resource
    SysNoticeService sysNoticeService;
    /**
     * @throws Exception
     */
    //10秒传递一次
    //@Scheduled(cron="*/10 * * * * ? ")
    @Scheduled(cron = "0 0 1,9,17 * * ?")
    public void JqcaseSearch() {
        try {
            Result result = new Result();
            result.setCode(200);
            //List<SysNotice> userList = sysNoticeService.findAll();
            //result.setData(userList);
            result.setMsg("查询成功！");
            String resultJson= new ObjectMapper().writeValueAsString(result);
            WebSocketServer.sendInfo(resultJson,null);
            System.out.println("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
