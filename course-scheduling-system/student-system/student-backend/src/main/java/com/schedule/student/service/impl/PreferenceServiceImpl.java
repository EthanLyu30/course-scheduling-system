package com.schedule.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.student.dto.PreferenceDTO;
import com.schedule.student.entity.TimePreference;
import com.schedule.student.entity.TimeSlot;
import com.schedule.student.mapper.PreferenceMapper;
import com.schedule.student.mapper.TimeSlotMapper;
import com.schedule.student.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间偏好服务实现类
 */
@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceMapper preferenceMapper;
    private final TimeSlotMapper timeSlotMapper;

    @Override
    public TimePreference getCurrentPreference(Long studentId, String semester) {
        LambdaQueryWrapper<TimePreference> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimePreference::getStudentId, studentId)
               .eq(TimePreference::getSemester, semester)
               .eq(TimePreference::getIsCurrent, 1);
        return preferenceMapper.selectOne(wrapper);
    }

    @Override
    public List<TimeSlot> getTimeSlots(Long preferenceId) {
        LambdaQueryWrapper<TimeSlot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimeSlot::getPreferenceId, preferenceId)
               .orderByAsc(TimeSlot::getDayOfWeek)
               .orderByAsc(TimeSlot::getStartTime);
        return timeSlotMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public TimePreference savePreference(Long studentId, String semester, List<TimeSlot> timeSlots) {
        // 查找是否已有当前偏好
        TimePreference existing = getCurrentPreference(studentId, semester);
        
        TimePreference preference;
        if (existing != null) {
            // 更新版本，将旧的设为历史
            existing.setIsCurrent(0);
            preferenceMapper.updateById(existing);
            
            // 创建新版本
            preference = new TimePreference();
            preference.setStudentId(studentId);
            preference.setSemester(semester);
            preference.setVersion(existing.getVersion() + 1);
            preference.setIsCurrent(1);
        } else {
            preference = new TimePreference();
            preference.setStudentId(studentId);
            preference.setSemester(semester);
            preference.setVersion(1);
            preference.setIsCurrent(1);
        }
        preferenceMapper.insert(preference);

        // 保存时间段
        for (TimeSlot slot : timeSlots) {
            if (slot == null) {
                continue;
            }
            slot.setId(null);
            slot.setPreferenceId(preference.getId());
            // 若缺少时间，根据节次填充默认时段（可按学校作息调整）
            if (slot.getStartTime() == null || slot.getEndTime() == null) {
                fillTimeBySlotNumber(slot);
            }
            timeSlotMapper.insert(slot);
        }

        return preference;
    }

    @Override
    public List<TimePreference> getHistoryPreferences(Long studentId) {
        LambdaQueryWrapper<TimePreference> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TimePreference::getStudentId, studentId)
               .orderByDesc(TimePreference::getCreatedAt)
               .last("LIMIT 8"); // 最近4个学期，每学期可能2个版本
        return preferenceMapper.selectList(wrapper);
    }

    @Override
    public List<TimeSlot> applyTemplate(Long templateId) {
        // TODO: 从模板表读取数据并转换为TimeSlot列表
        return new ArrayList<>();
    }

    @Override
    public List<String> detectConflicts(List<TimeSlot> timeSlots) {
        List<String> conflicts = new ArrayList<>();
        
        if (timeSlots == null || timeSlots.isEmpty()) {
            return conflicts;
        }

        // 检测同一时间段是否有矛盾的偏好设置
        for (int i = 0; i < timeSlots.size(); i++) {
            for (int j = i + 1; j < timeSlots.size(); j++) {
                TimeSlot slot1 = timeSlots.get(i);
                TimeSlot slot2 = timeSlots.get(j);

                if (slot1 == null || slot2 == null) {
                    continue;
                }
                
                if (slot1.getDayOfWeek() != null && slot1.getDayOfWeek().equals(slot2.getDayOfWeek())) {
                    // 检查时间是否重叠
                    if (hasValidTime(slot1) && hasValidTime(slot2) && isTimeOverlap(slot1, slot2)) {
                        conflicts.add(String.format("周%d %s-%s 与 %s-%s 时间段重叠",
                            slot1.getDayOfWeek(),
                            slot1.getStartTime(), slot1.getEndTime(),
                            slot2.getStartTime(), slot2.getEndTime()));
                    }
                }
            }
        }
        
        return conflicts;
    }

    private boolean isTimeOverlap(TimeSlot slot1, TimeSlot slot2) {
        if (!hasValidTime(slot1) || !hasValidTime(slot2)) {
            return false;
        }
        return slot1.getStartTime().isBefore(slot2.getEndTime()) 
            && slot2.getStartTime().isBefore(slot1.getEndTime());
    }

    private boolean hasValidTime(TimeSlot slot) {
        return slot != null && slot.getStartTime() != null && slot.getEndTime() != null;
    }

    /**
     * 根据节次补全时间（可按校历调整）
     */
    private void fillTimeBySlotNumber(TimeSlot slot) {
        if (slot == null || slot.getSlotNumber() == null) {
            return;
        }
        switch (slot.getSlotNumber()) {
            case 1 -> {
                slot.setStartTime(java.time.LocalTime.of(8, 0));
                slot.setEndTime(java.time.LocalTime.of(9, 35));
            }
            case 2 -> {
                slot.setStartTime(java.time.LocalTime.of(9, 50));
                slot.setEndTime(java.time.LocalTime.of(12, 5));
            }
            case 3 -> {
                slot.setStartTime(java.time.LocalTime.of(13, 30));
                slot.setEndTime(java.time.LocalTime.of(15, 5));
            }
            case 4 -> {
                slot.setStartTime(java.time.LocalTime.of(15, 20));
                slot.setEndTime(java.time.LocalTime.of(17, 0));
            }
            case 5 -> {
                slot.setStartTime(java.time.LocalTime.of(18, 30));
                slot.setEndTime(java.time.LocalTime.of(20, 5));
            }
            case 6 -> {
                slot.setStartTime(java.time.LocalTime.of(20, 10));
                slot.setEndTime(java.time.LocalTime.of(21, 40));
            }
            default -> {
                // 留空，不覆盖
            }
        }
    }
}
