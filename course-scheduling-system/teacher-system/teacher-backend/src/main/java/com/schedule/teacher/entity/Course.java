package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("course")
public class Course {
    @TableId
    private Long id;
    private String courseCode;
    private String name;
    private BigDecimal credits;
    private Integer hours;
    private Integer courseType;
    private String description;
    private Integer capacity;
    private String prerequisiteIds;
    private Long teacherId;
    private String semester;
    private BigDecimal popularityIndex;
    private Integer status;

    // 非表字段（占位，防止 MP 生成不存在的列）
    @TableField(exist = false)
    private Object ext;
}
