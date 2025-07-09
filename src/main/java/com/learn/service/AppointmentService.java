package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.entity.Appointment;
/**
 * @ClassName -> AppointmentService
 * @Description
 * @Author soso
 * @Date 2025/7/7 10:45 星期一
 * @Version 1.0
 */
public interface AppointmentService extends IService<Appointment> {


    Appointment getOne(Appointment appointment);

}
