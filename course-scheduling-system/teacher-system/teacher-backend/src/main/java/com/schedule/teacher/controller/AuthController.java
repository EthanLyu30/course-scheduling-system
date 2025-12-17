package com.schedule.teacher.controller;

import com.schedule.teacher.dto.LoginDTO;
import com.schedule.teacher.service.AuthService;
import com.schedule.teacher.vo.LoginVO;
import com.schedule.teacher.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "教师登录（简单校验，返回 token）")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto) {
        return authService.login(dto);
    }
}
