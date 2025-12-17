package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.entity.Preference;
import com.schedule.teacher.mapper.PreferenceMapper;
import com.schedule.teacher.service.PreferenceService;
import com.schedule.teacher.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceMapper preferenceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> savePreference(PreferenceDTO dto) {
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
        } else {
            existing.setScore(dto.getScore());
            existing.setVersion((existing.getVersion() == null ? 1 : existing.getVersion() + 1));
            preferenceMapper.updateById(existing);
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
