package com.schedule.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.student.entity.TimeSlot;
import org.apache.ibatis.annotations.Mapper;

/**
 * 时间段Mapper接口
 */
@Mapper
public interface TimeSlotMapper extends BaseMapper<TimeSlot> {

}
