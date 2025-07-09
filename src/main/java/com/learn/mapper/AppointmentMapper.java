package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName -> AppointmentMapper
 * @Description
 * @Author soso
 * @Date 2025/7/7 10:52 星期一
 * @Version 1.0
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}
