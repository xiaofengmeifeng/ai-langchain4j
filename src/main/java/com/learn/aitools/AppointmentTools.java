package com.learn.aitools;

import com.learn.entity.Appointment;
import com.learn.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

/**
 * @ClassName -> AppointmentTools
 * @Description
 * @Author soso
 * @Date 2025/7/7 12:53 星期一
 * @Version 1.0
 */
@Component
public class AppointmentTools {

    /**
     * 预约挂号服务
     */
    @Autowired
    private AppointmentService appointmentService;

    @Tool(name = "bookAppointment", value = "根据参数，先执行queryDepartment查询是否有号源，并直接给用户回答是否可预约，并让用户确认所有的预约信息，用户确认信息后再进行预约。如果用户没有提供具体的医生姓名，请从\n" +
            "向量存储中找到一位医生。")
    public String bookAppointment(Appointment appointment) {
        System.out.println("预约信息：" + appointment);
        Appointment dbAppointment = appointmentService.getOne(appointment);
        if (dbAppointment != null) {
            return "您在" + appointment.getDate() + "已经预约过了";
        }
        appointment.setId(null);
        appointmentService.save(appointment);
        return "预约成功";
    }

    @Tool(name = "cancelAppointment", value = "根据参数，查询预约挂号记录是否存在，如果存在则取消预约挂号，并返回true，否则返回false")
    public String cancelAppointment(Appointment appointment) {
        System.out.println("取消预约信息：" + appointment);
        Appointment dbAppointment = appointmentService.getOne(appointment);
        if (dbAppointment != null) {
            appointmentService.removeById(dbAppointment.getId());
            return "取消预约成功";
        }
        return "您在" + appointment.getDate() + "不存在挂号信息";
    }

    @Tool(name = "queryDepartment", value = "根据科室名称、医生、日期、时间查询是否有号源，并返回给用户，如果有号源则返回true，否则返回false")
    public Boolean queryDepartment(@P(value = "科室名称") String department,
                                    @P(value = "挂号日期") String date,
                                    @P(value = "挂号时间，可选值：上午、下午") String time,
                                    @P(value = "医生姓名", required = false) String doctorName) {
        System.out.println("查询是否有号源");
        System.out.println("科室名称：" + department);
        System.out.println("挂号日期：" + date);
        System.out.println("挂号时间：" + time);
        System.out.println("医生姓名：" + doctorName);
        // TODO 维护医生的排班信息：
        // 如果没有指定医生名字，则根据其他条件查询是否有可以预约的医生（有返回true，否则返回false）；
        // 如果指定了医生名字，则判断医生是否有排班（没有排版返回false）
        // 如果有排班，则判断医生排班时间段是否己约满（约满返回false，有空闲时间返回true）
        return true;
    }
}
