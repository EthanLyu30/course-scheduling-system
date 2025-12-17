package com.schedule.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.teacher.dto.LoginDTO;
import com.schedule.teacher.entity.Teacher;
import com.schedule.teacher.mapper.TeacherMapper;
import com.schedule.teacher.service.AuthService;
import com.schedule.teacher.vo.LoginVO;
import com.schedule.teacher.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TeacherMapper teacherMapper;

    @Override
    public Result<LoginVO> login(LoginDTO dto) {
        Teacher teacher = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getTeacherNo, dto.getTeacherNo())
                .last("limit 1"));
        if (teacher == null) {
            return new Result<>(401, "教师不存在", null);
        }
        if (teacher.getStatus() != null && teacher.getStatus() == 0) {
            return new Result<>(401, "教师已被禁用", null);
        }
        // 简单密码校验（生产中需加密）
        if (teacher.getPassword() != null && !teacher.getPassword().equals(dto.getPassword())) {
            return new Result<>(401, "密码错误", null);
        }
        LoginVO vo = new LoginVO();
        vo.setTeacherId(String.valueOf(teacher.getId()));
        vo.setTeacherNo(teacher.getTeacherNo());
        vo.setName(teacher.getName());
        vo.setToken(UUID.randomUUID().toString().replace("-", ""));
        return Result.ok(vo);
    }
}
