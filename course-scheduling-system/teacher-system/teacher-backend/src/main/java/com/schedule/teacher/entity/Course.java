package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableId
    private Long id;
    private String name;
    private Integer credits;
    private Integer minEnrollment;
    private Integer maxEnrollment;
    private String campus;
    private String type;
}
