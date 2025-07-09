package com.learn.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.entity.Appointment;
import com.learn.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName -> AppointmentServiceImpl
 * @Description
 * @Author soso
 * @Date 2025/7/7 11:08 星期一
 * @Version 1.0
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    /**
     * 根据预约信息查询预约信息
     *
     * @param appointment 预约信息
     * @return 预约信息
     */
    @Override
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());
        //        queryWrapper.eq(Appointment::getDoctorName, appointment.getDoctorName());

        return super.getOne(queryWrapper);
    }
}
