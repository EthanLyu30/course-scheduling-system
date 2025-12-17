package com.schedule.teacher.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreferenceChangedEvent {
    private String entityId;
    private Long courseId;
    private Long timeSlotId;
    private Integer version;
}
