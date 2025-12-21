package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.entity.CourseAssignment;
import com.schedule.teacher.entity.Preference;
import com.schedule.teacher.entity.TimeSlot;
import com.schedule.teacher.entity.Course;
import com.schedule.teacher.mapper.CourseAssignmentMapper;
import com.schedule.teacher.mapper.PreferenceMapper;
import com.schedule.teacher.mapper.TimeSlotMapper;
import com.schedule.teacher.mapper.CourseMapper;
import com.schedule.teacher.service.PreferenceService;
import com.schedule.teacher.vo.Result;
import com.schedule.teacher.event.DomainEventPublisher;
import com.schedule.teacher.event.PreferenceChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceMapper preferenceMapper;
    private final CourseAssignmentMapper assignmentMapper;
    private final TimeSlotMapper timeSlotMapper;
    private final CourseMapper courseMapper;
    private final DomainEventPublisher eventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> savePreference(PreferenceDTO dto) {
        // 基础校验，避免无效 ID 触发外键异常
        if (dto.getCourseId() == null || dto.getCourseId() <= 0) {
            // 前端未传课程时，回退为该教师已有的课程安排
            CourseAssignment anyAssign = assignmentMapper.selectOne(new LambdaQueryWrapper<CourseAssignment>()
                    .eq(CourseAssignment::getTeacherId, dto.getTeacherId())
                    .last("limit 1"));
            if (anyAssign == null) {
                return Result.fail("无效的课程ID，且该教师暂无课程安排，无法保存偏好");
            }
            dto.setCourseId(anyAssign.getCourseId());
        }
        if (dto.getTimeSlotId() == null || dto.getTimeSlotId() <= 0) {
            return Result.fail("无效的时间片ID，无法保存偏好");
        }

        // 确保时间片存在：前端使用 day-slot 编码(如 11 表示周一第1节)
        TimeSlot ts = timeSlotMapper.selectById(dto.getTimeSlotId());
        if (ts == null) {
            long code = dto.getTimeSlotId();
            int day = (int) (code / 10);
            int slot = (int) (code % 10);
            if (day < 1 || day > 7 || slot < 1 || slot > 9) {
                return Result.fail("时间片编码不合法，无法保存偏好");
            }
            TimeSlot newTs = new TimeSlot();
            newTs.setId(dto.getTimeSlotId());
            newTs.setDayOfWeek(day);
            newTs.setSlotNumber(slot);
            newTs.setDurationMinutes(90);
            timeSlotMapper.insert(newTs);
        }

        // 确保课程存在
        Course course = courseMapper.selectById(dto.getCourseId());
        if (course == null) {
            return Result.fail("课程不存在，无法保存偏好");
        }

        // UPSERT：同一教师+课程+时间片唯一
        Preference existing = preferenceMapper.selectOne(new LambdaQueryWrapper<Preference>()
                .eq(Preference::getEntityType, "TEACHER")
                .eq(Preference::getEntityId, dto.getTeacherId())
                .eq(Preference::getCourseId, dto.getCourseId())
                .eq(Preference::getTimeSlotId, dto.getTimeSlotId()));
        if (existing == null) {
            Preference pref = new Preference();
            pref.setEntityType("TEACHER");
            pref.setEntityId(dto.getTeacherId());
            pref.setCourseId(dto.getCourseId());
            pref.setTimeSlotId(dto.getTimeSlotId());
            pref.setScore(dto.getScore());
            pref.setVersion(1);
            preferenceMapper.insert(pref);
            eventPublisher.publishPreferenceChanged(new PreferenceChangedEvent(dto.getTeacherId(), dto.getCourseId(), dto.getTimeSlotId(), 1));
        } else {
            existing.setScore(dto.getScore());
            existing.setVersion((existing.getVersion() == null ? 1 : existing.getVersion() + 1));
            preferenceMapper.updateById(existing);
            eventPublisher.publishPreferenceChanged(new PreferenceChangedEvent(dto.getTeacherId(), dto.getCourseId(), dto.getTimeSlotId(), existing.getVersion()));
        }
        return Result.ok();
    }

    @Override
    public Result<?> listPreferences(String teacherId) {
        return Result.ok(preferenceMapper.selectList(new LambdaQueryWrapper<Preference>()
                .eq(Preference::getEntityType, "TEACHER")
                .eq(Preference::getEntityId, teacherId)));
    }
}
