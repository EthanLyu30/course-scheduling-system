package com.schedule.teacher.controller;

import com.schedule.teacher.entity.ConstraintRule;
import com.schedule.teacher.service.ConstraintService;
import com.schedule.teacher.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/constraints")
@RequiredArgsConstructor
@Tag(name = "Constraint Rules")
public class ConstraintController {

    private final ConstraintService constraintService;

    @GetMapping("/active")
    @Operation(summary = "查询启用的约束规则")
    public Result<List<ConstraintRule>> listActive() {
        return constraintService.listActive();
    }
}
