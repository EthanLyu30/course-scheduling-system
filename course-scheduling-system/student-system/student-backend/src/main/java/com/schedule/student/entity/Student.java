package com.schedule.student.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@TableName("student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 学号 */
    private String studentNo;

    /** 姓名 */
    private String name;

    /** 性别: 0-未知, 1-男, 2-女 */
    private Integer gender;

    /** 专业 */
    private String major;

    /** 年级 */
    private String grade;

    /** 班级 */
    private String className;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 密码 */
    private String password;

    /** 状态: 0-禁用, 1-正常 */
    private Integer status;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
