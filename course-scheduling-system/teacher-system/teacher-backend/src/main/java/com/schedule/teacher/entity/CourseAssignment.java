package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course_assignment")
public class CourseAssignment {
    @TableId
    private String id;
    private String planId;
    private Long courseId;
    private String teacherId;
    private String roomId;
    private Long timeSlotId;
    private String status;
}
