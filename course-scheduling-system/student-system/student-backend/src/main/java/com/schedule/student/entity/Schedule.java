package com.schedule.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 排课结果实体类
 */
@Data
@TableName("schedule")
public class Schedule {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 课程ID */
    private Long courseId;

    /** 教师ID */
    private Long teacherId;

    /** 教室ID */
    private Long classroomId;

    /** 学期 */
    private String semester;

    /** 星期: 1-7 */
    private Integer dayOfWeek;

    /** 开始时间 */
    private LocalTime startTime;

    /** 结束时间 */
    private LocalTime endTime;

    /** 起始周 */
    private Integer startWeek;

    /** 结束周 */
    private Integer endWeek;

    /** 周类型: 0-每周, 1-单周, 2-双周 */
    private Integer weekType;

    /** 状态: 0-待发布, 1-已发布, 2-已调整 */
    private Integer status;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 课程信息（非数据库字段） */
    @TableField(exist = false)
    private Course course;

    /** 教室名称（非数据库字段） */
    @TableField(exist = false)
    private String classroomName;

    /** 课程名称（非数据库字段） */
    @TableField(exist = false)
    private String courseName;

    /** 课程代码（非数据库字段） */
    @TableField(exist = false)
    private String courseCode;

    /** 学分（非数据库字段） */
    @TableField(exist = false)
    private Double credits;

    /** 课程类型（非数据库字段） */
    @TableField(exist = false)
    private Integer courseType;

    /** 教师姓名（非数据库字段） */
    @TableField(exist = false)
    private String teacherName;

    /** 教学楼名称（非数据库字段） */
    @TableField(exist = false)
    private String building;
}
