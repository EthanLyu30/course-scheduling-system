package com.schedule.student.controller;

import com.schedule.student.entity.Course;
import com.schedule.student.service.CourseService;
import com.schedule.student.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程接口
 */
@Tag(name = "课程管理", description = "课程查询相关接口")
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "获取课程列表", description = "获取指定学期的所有课程")
    @GetMapping
    public Result<List<Course>> getCourseList(
            @Parameter(description = "学期，如2024-2025-2") 
            @RequestParam(defaultValue = "2024-2025-2") String semester) {
        List<Course> courses = courseService.getCourseList(semester);
        return Result.success(courses);
    }

    @Operation(summary = "获取课程详情", description = "根据课程ID获取详细信息")
    @GetMapping("/{id}")
    public Result<Course> getCourseDetail(
            @Parameter(description = "课程ID") @PathVariable Long id) {
        Course course = courseService.getCourseDetail(id);
        if (course == null) {
            return Result.notFound("课程不存在");
        }
        return Result.success(course);
    }

    @Operation(summary = "按类型筛选课程", description = "根据课程类型筛选")
    @GetMapping("/type/{courseType}")
    public Result<List<Course>> getCoursesByType(
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester,
            @Parameter(description = "课程类型: 1-必修, 2-选修, 3-实验, 4-体育") @PathVariable Integer courseType) {
        List<Course> courses = courseService.getCoursesByType(semester, courseType);
        return Result.success(courses);
    }

    @Operation(summary = "搜索课程", description = "根据关键词搜索课程")
    @GetMapping("/search")
    public Result<List<Course>> searchCourses(
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester,
            @Parameter(description = "搜索关键词") @RequestParam String keyword) {
        List<Course> courses = courseService.searchCourses(semester, keyword);
        return Result.success(courses);
    }
}
