package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("scheduling_plan")
public class SchedulingPlan {
    @TableId
    private String id;
    private String name;
    private String strategy;
    private String status;
    private Double satisfactionScore;
    private Integer conflictCount;
    private LocalDateTime createdAt;
    private LocalDateTime finalizedAt;
}
