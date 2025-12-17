package com.schedule.teacher.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleConfirmedEvent {
    private String planId;
    private String teacherId;
}
