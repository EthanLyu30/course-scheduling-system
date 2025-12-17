package com.schedule.teacher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConflictResolveDTO {
    @NotBlank
    private String conflictId;
    private String suggestion;
    private String action;
}
