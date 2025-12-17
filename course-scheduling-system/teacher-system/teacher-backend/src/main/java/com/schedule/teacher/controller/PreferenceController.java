package com.schedule.teacher.controller;

import com.schedule.teacher.dto.PreferenceDTO;
import com.schedule.teacher.service.PreferenceService;
import com.schedule.teacher.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/preferences")
@RequiredArgsConstructor
@Tag(name = "Teacher Preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PostMapping
    @Operation(summary = "保存教师偏好")
    public Result<Void> save(@RequestBody @Valid PreferenceDTO dto) {
        return preferenceService.savePreference(dto);
    }

    @GetMapping("/{teacherId}")
    @Operation(summary = "获取教师偏好")
    public Result<?> list(@PathVariable String teacherId) {
        return preferenceService.listPreferences(teacherId);
    }
}
