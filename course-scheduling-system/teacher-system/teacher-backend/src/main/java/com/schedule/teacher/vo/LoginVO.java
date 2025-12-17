package com.schedule.teacher.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private String teacherId;
    private String teacherNo;
    private String name;
}
