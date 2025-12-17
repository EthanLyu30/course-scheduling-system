package com.schedule.teacher.service;

import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.vo.Result;

public interface PreferenceService {
    Result<Void> savePreference(PreferenceDTO dto);
    Result<?> listPreferences(String teacherId);
}
