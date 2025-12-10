package com.geekplus.webapp.tool.quartz.controller;

import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.util.uuid.IdUtils;
import com.geekplus.webapp.common.service.SysUserTokenService;
import com.geekplus.webapp.function.entity.UserAppointment;
import com.geekplus.webapp.tool.quartz.service.PlusAppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.geekplus.common.core.controller.BaseController.startPage;

/**
 * author     : geekplus
 * email      :
 * date       : 9/12/25 2:45 PM
 * description: //TODO
 */
@RestController
@RequestMapping("/tool/user/appointment")
@RequiredArgsConstructor
public class PlusAppointmentController extends BaseController {

    @Autowired
    private PlusAppointmentService appointmentService;
    @Autowired
    private SysUserTokenService userTokenService;

    @PostMapping
    public Result create(@RequestBody UserAppointment appointment, HttpServletRequest request) {
        appointment.setId(IdUtils.getNowDateStr()+IdUtils.createCount());
        appointment.setUserId(userTokenService.getSysUserId(request).toString());
        if(appointmentService.create(appointment)) {
            return Result.success("预约成功");
        }
        return Result.error("预约失败");
    }

    @PostMapping("/{id}/pause")
    public void pause(@PathVariable String id) {
        appointmentService.pause(id);
    }

    @PostMapping("/{id}/resume")
    public void resume(@PathVariable String id) {
        appointmentService.resume(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        appointmentService.delete(id);
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable String id) {
        appointmentService.cancel(id);
    }

    @GetMapping("/list")
    public PageDataInfo listByUserId(HttpServletRequest request) {
        startPage();
        String userId = userTokenService.getSysUserId(request).toString();
        return getDataTable(appointmentService.listByUserId(userId));
    }
}
