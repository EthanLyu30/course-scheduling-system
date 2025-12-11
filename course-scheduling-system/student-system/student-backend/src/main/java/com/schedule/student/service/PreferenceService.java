package com.schedule.student.service;

import com.schedule.student.dto.PreferenceDTO;
import com.schedule.student.entity.TimePreference;
import com.schedule.student.entity.TimeSlot;
import java.util.List;

/**
 * 时间偏好服务接口
 */
public interface PreferenceService {

    /**
     * 获取学生当前时间偏好
     */
    TimePreference getCurrentPreference(Long studentId, String semester);

    /**
     * 获取偏好的时间段明细
     */
    List<TimeSlot> getTimeSlots(Long preferenceId);

    /**
     * 保存时间偏好
     */
    TimePreference savePreference(Long studentId, String semester, List<TimeSlot> timeSlots);

    /**
     * 获取历史偏好列表
     */
    List<TimePreference> getHistoryPreferences(Long studentId);

    /**
     * 应用偏好模板
     */
    List<TimeSlot> applyTemplate(Long templateId);

    /**
     * 检测偏好冲突
     */
    List<String> detectConflicts(List<TimeSlot> timeSlots);
}
