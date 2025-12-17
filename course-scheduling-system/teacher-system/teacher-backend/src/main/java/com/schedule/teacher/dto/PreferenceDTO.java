package com.schedule.teacher.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferenceDTO {
    @NotBlank
    private String teacherId;
    @NotNull
    private Long courseId;
    @NotNull
    private Long timeSlotId;
    @Min(1)
    @Max(5)
    private Integer score;
}
