package com.schedule.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
@Data
@TableName("course")
public class Course {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 课程代码 */
    private String courseCode;

    /** 课程名称 */
    private String name;

    /** 学分 */
    private BigDecimal credits;

    /** 学时 */
    private Integer hours;

    /** 课程类型: 1-必修, 2-选修, 3-实验, 4-体育 */
    private Integer courseType;

    /** 课程描述 */
    private String description;

    /** 教学大纲链接 */
    private String syllabusUrl;

    /** 教材 */
    private String textbook;

    /** 考核方式 */
    private String assessment;

    /** 容量上限 */
    private Integer capacity;

    /** 先修课程ID */
    private String prerequisiteIds;

    /** 授课教师ID */
    private Long teacherId;

    /** 学期 */
    private String semester;

    /** 选课热度指数 */
    private BigDecimal popularityIndex;

    /** 状态: 0-停开, 1-正常 */
    private Integer status;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 授课教师姓名（非数据库字段） */
    @TableField(exist = false)
    private String teacherName;
}
