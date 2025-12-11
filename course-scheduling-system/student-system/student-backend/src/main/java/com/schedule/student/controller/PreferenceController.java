package com.schedule.student.controller;

import com.schedule.student.dto.PreferenceDTO;
import com.schedule.student.entity.TimePreference;
import com.schedule.student.entity.TimeSlot;
import com.schedule.student.service.PreferenceService;
import com.schedule.student.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间偏好接口
 */
@Tag(name = "时间偏好管理", description = "学生时间偏好设置相关接口")
@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @Operation(summary = "获取当前时间偏好", description = "获取学生当前学期的时间偏好设置")
    @GetMapping("/current")
    public Result<Map<String, Object>> getCurrentPreference(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        
        TimePreference preference = preferenceService.getCurrentPreference(studentId, semester);
        
        Map<String, Object> result = new HashMap<>();
        result.put("preference", preference);
        
        if (preference != null) {
            List<TimeSlot> slots = preferenceService.getTimeSlots(preference.getId());
            result.put("timeSlots", slots);
        }
        
        return Result.success(result);
    }

    @Operation(summary = "保存时间偏好", description = "保存或更新学生的时间偏好设置")
    @PostMapping
    public Result<TimePreference> savePreference(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @RequestBody PreferenceDTO preferenceDTO) {
        
        // 先检测冲突
        List<String> conflicts = preferenceService.detectConflicts(preferenceDTO.getTimeSlots());
        if (!conflicts.isEmpty()) {
            return Result.badRequest("存在时间冲突: " + String.join("; ", conflicts));
        }
        
        TimePreference preference = preferenceService.savePreference(
            studentId, 
            preferenceDTO.getSemester(), 
            preferenceDTO.getTimeSlots()
        );
        return Result.success("保存成功", preference);
    }

    @Operation(summary = "获取历史偏好", description = "获取学生的历史时间偏好记录")
    @GetMapping("/history")
    public Result<List<TimePreference>> getHistoryPreferences(
            @Parameter(description = "学生ID") @RequestParam Long studentId) {
        List<TimePreference> preferences = preferenceService.getHistoryPreferences(studentId);
        return Result.success(preferences);
    }

    @Operation(summary = "应用偏好模板", description = "应用系统预设或自定义的偏好模板")
    @PostMapping("/apply-template")
    public Result<List<TimeSlot>> applyTemplate(
            @Parameter(description = "模板ID") @RequestParam Long templateId) {
        List<TimeSlot> slots = preferenceService.applyTemplate(templateId);
        return Result.success(slots);
    }

    @Operation(summary = "检测偏好冲突", description = "检测时间偏好设置中的冲突")
    @PostMapping("/detect-conflicts")
    public Result<List<String>> detectConflicts(@RequestBody List<TimeSlot> timeSlots) {
        List<String> conflicts = preferenceService.detectConflicts(timeSlots);
        return Result.success(conflicts);
    }
}
