package com.schedule.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生选课意愿实体类
 */
@Data
@TableName("student_wish")
public class Wish {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 学生ID */
    private Long studentId;

    /** 课程ID */
    private Long courseId;

    /** 学期 */
    private String semester;

    /** 优先级: 1-一般想选, 2-强烈想选, 3-必选 */
    private Integer priority;

    /** 选课理由 */
    private String reason;

    /** 状态: 0-待提交, 1-已提交, 2-已匹配, 3-未匹配 */
    private Integer status;

    /** 提交时间 */
    private LocalDateTime submittedAt;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /** 课程信息（非数据库字段） */
    @TableField(exist = false)
    private Course course;
}
