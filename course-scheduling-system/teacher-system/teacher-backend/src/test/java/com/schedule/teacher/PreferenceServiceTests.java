package com.schedule.teacher;

import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.service.PreferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PreferenceServiceTests {

    @Autowired
    private PreferenceService preferenceService;

    @Test
    void upsertPreference() {
        PreferenceDTO dto = new PreferenceDTO();
        dto.setTeacherId("t-1");
        dto.setCourseId(1L);
        dto.setTimeSlotId(11L);
        dto.setScore(4);
        preferenceService.savePreference(dto);
    }
}
