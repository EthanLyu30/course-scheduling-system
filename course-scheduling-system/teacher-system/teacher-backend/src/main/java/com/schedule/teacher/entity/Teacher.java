package com.schedule.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    @TableId
    private Long id;
    private String teacherNo;
    private String name;
    private String department;
    private String password;
    private Integer status;
}
