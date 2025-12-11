package com.schedule.student.controller;

import com.schedule.student.entity.Schedule;
import com.schedule.student.service.ScheduleService;
import com.schedule.student.vo.Result;
import com.schedule.student.vo.ScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排课结果接口
 */
@Tag(name = "排课结果", description = "课表查看与导出相关接口")
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "获取个人课表", description = "获取学生当前学期的课表")
    @GetMapping
    public Result<List<Schedule>> getStudentSchedule(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        List<Schedule> schedules = scheduleService.getStudentSchedule(studentId, semester);
        return Result.success(schedules);
    }

    @Operation(summary = "获取满足率分析", description = "获取时间偏好满足率及对比数据")
    @GetMapping("/satisfaction")
    public Result<Map<String, Object>> getSatisfactionAnalysis(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        
        Double rate = scheduleService.getSatisfactionRate(studentId, semester);
        
        Map<String, Object> result = new HashMap<>();
        result.put("satisfactionRate", rate);
        result.put("averageRate", 82.3); // 全校平均（模拟数据）
        result.put("rank", "前20%"); // 排名（模拟数据）
        
        return Result.success(result);
    }

    @Operation(summary = "获取课程匹配状态", description = "获取每门课程的匹配状态详情")
    @GetMapping("/match-status")
    public Result<List<ScheduleVO>> getCourseMatchStatus(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        List<ScheduleVO> status = scheduleService.getCourseMatchStatus(studentId, semester);
        return Result.success(status);
    }

    @Operation(summary = "导出课表", description = "以指定格式导出课表")
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportSchedule(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester,
            @Parameter(description = "导出格式: pdf/png/ical") @RequestParam(defaultValue = "pdf") String format) {
        
        byte[] data = scheduleService.exportSchedule(studentId, semester, format);
        
        String filename = "schedule_" + semester + "." + format;
        MediaType mediaType = switch (format.toLowerCase()) {
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "png" -> MediaType.IMAGE_PNG;
            case "ical" -> MediaType.parseMediaType("text/calendar");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(mediaType)
                .body(data);
    }
}
