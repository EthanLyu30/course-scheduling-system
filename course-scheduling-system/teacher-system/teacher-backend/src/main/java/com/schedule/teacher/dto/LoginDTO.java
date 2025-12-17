package com.schedule.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String teacherNo;
    @NotBlank
    private String password;
}
