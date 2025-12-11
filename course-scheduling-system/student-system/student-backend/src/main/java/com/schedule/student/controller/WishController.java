package com.schedule.student.controller;

import com.schedule.student.dto.WishDTO;
import com.schedule.student.entity.Wish;
import com.schedule.student.service.WishService;
import com.schedule.student.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 意愿表达接口
 */
@Tag(name = "意愿表达", description = "学生选课意愿相关接口")
@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @Operation(summary = "获取意愿列表", description = "获取学生当前学期的选课意愿列表")
    @GetMapping
    public Result<List<Wish>> getWishList(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        List<Wish> wishes = wishService.getWishList(studentId, semester);
        return Result.success(wishes);
    }

    @Operation(summary = "添加课程意愿", description = "将课程添加到意愿列表")
    @PostMapping
    public Result<Wish> addWish(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @RequestBody WishDTO wishDTO) {
        try {
            Wish wish = wishService.addWish(studentId, wishDTO);
            return Result.success("添加成功", wish);
        } catch (RuntimeException e) {
            return Result.badRequest(e.getMessage());
        }
    }

    @Operation(summary = "更新意愿", description = "更新课程意愿的优先级或理由")
    @PutMapping("/{wishId}")
    public Result<Wish> updateWish(
            @Parameter(description = "意愿ID") @PathVariable Long wishId,
            @RequestBody WishDTO wishDTO) {
        try {
            Wish wish = wishService.updateWish(wishId, wishDTO);
            return Result.success("更新成功", wish);
        } catch (RuntimeException e) {
            return Result.badRequest(e.getMessage());
        }
    }

    @Operation(summary = "删除意愿", description = "从意愿列表中移除课程")
    @DeleteMapping("/{wishId}")
    public Result<Void> deleteWish(
            @Parameter(description = "意愿ID") @PathVariable Long wishId) {
        wishService.deleteWish(wishId);
        return Result.success("删除成功", null);
    }

    @Operation(summary = "提交意愿", description = "提交当前学期的所有意愿")
    @PostMapping("/submit")
    public Result<Void> submitWishes(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        wishService.submitWishes(studentId, semester);
        return Result.success("提交成功", null);
    }

    @Operation(summary = "检测意愿冲突", description = "检测选课意愿中的各类冲突")
    @GetMapping("/conflicts")
    public Result<List<String>> detectConflicts(
            @Parameter(description = "学生ID") @RequestParam Long studentId,
            @Parameter(description = "学期") @RequestParam(defaultValue = "2024-2025-2") String semester) {
        List<String> conflicts = wishService.detectConflicts(studentId, semester);
        return Result.success(conflicts);
    }
}
