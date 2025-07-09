package com.learn.service;

import com.learn.entity.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @ClassName -> AppointmentServiceImplTest
 * @Description
 * @Author soso
 * @Date 2025/7/7 10:45 星期一
 * @Version 1.0
 */
@SpringBootTest
public class AppointmentServiceImplTest {

    @Autowired
    private AppointmentService appointmentService;


    @Test
    public void getOne() {
        Appointment query = new Appointment();
        query.setUsername("小明");
        query.setIdCard("511025199001010001");
        query.setDepartment("内科");
        query.setDoctorName("张医生");
        query.setDate("2025-07-07");
        query.setTime("上午");
        Appointment appointment = appointmentService.getOne(query);
        System.out.println(appointment);
    }

    /**
     * 测试保存预约信息
     */
    @Test
    public void save() {
        Appointment appointment = new Appointment();
        appointment.setUsername("小明");
        appointment.setIdCard("511025199001010001");
        appointment.setDepartment("内科");
        appointment.setDoctorName("张医生");
        appointment.setDate("2025-07-07");
        appointment.setTime("上午");
        appointmentService.save(appointment);
    }

}