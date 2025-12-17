package com.schedule.teacher.controller;

import com.schedule.teacher.service.ScheduleService;
import com.schedule.teacher.vo.Result;
import com.schedule.teacher.vo.ScheduleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/schedules")
@RequiredArgsConstructor
@Tag(name = "Teacher Schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{teacherId}")
    @Operation(summary = "获取教师课表")
    public Result<List<ScheduleVO>> getSchedule(@PathVariable String teacherId) {
        return scheduleService.getSchedule(teacherId);
    }

    @PostMapping("/{planId}/confirm")
    @Operation(summary = "教师确认方案")
    public Result<Void> confirm(@PathVariable String planId) {
        return scheduleService.confirm(planId);
    }
}
