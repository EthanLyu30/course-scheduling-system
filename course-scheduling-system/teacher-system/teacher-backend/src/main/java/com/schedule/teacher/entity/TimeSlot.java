package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("time_slot")
public class TimeSlot {
    @TableId
    private Long id;
    private Integer dayOfWeek;
    private Integer slotNumber;
    private Integer durationMinutes;
}
