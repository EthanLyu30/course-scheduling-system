package com.schedule.student.dto;

import com.schedule.student.entity.TimeSlot;
import lombok.Data;
import java.util.List;

/**
 * 时间偏好DTO
 */
@Data
public class PreferenceDTO {

    /** 学期 */
    private String semester;

    /** 时间段列表 */
    private List<TimeSlot> timeSlots;
}
