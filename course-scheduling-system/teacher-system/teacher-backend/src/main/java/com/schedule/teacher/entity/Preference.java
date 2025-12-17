package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("preference")
public class Preference {
    @TableId
    private String id;
    private String entityType; // TEACHER
    private String entityId;
    private Long courseId;
    private Long timeSlotId;
    private Integer score;
    private Integer version;
}
