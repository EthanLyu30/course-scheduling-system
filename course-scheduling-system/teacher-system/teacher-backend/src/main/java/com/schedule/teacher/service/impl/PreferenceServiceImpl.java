package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.entity.Preference;
import com.schedule.teacher.mapper.PreferenceMapper;
import com.schedule.teacher.service.PreferenceService;
import com.schedule.teacher.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceMapper preferenceMapper;

    @Override
    public Result<Void> savePreference(PreferenceDTO dto) {
        Preference pref = new Preference();
        pref.setEntityType("TEACHER");
        pref.setEntityId(dto.getTeacherId());
        pref.setCourseId(dto.getCourseId());
        pref.setTimeSlotId(dto.getTimeSlotId());
        pref.setScore(dto.getScore());
        preferenceMapper.insert(pref);
        return Result.ok();
    }

    @Override
    public Result<?> listPreferences(String teacherId) {
        return Result.ok(preferenceMapper.selectList(new LambdaQueryWrapper<Preference>()
                .eq(Preference::getEntityType, "TEACHER")
                .eq(Preference::getEntityId, teacherId)));
    }
}
