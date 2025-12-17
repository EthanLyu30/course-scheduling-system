package com.schedule.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScheduleConfirmDTO {
    @NotBlank
    private String planId;
    private String comment;
}
