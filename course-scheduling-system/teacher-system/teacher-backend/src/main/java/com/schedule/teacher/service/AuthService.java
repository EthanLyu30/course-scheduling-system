package com.schedule.teacher.service;

import com.schedule.teacher.dto.LoginDTO;
import com.schedule.teacher.vo.LoginVO;
import com.schedule.teacher.vo.Result;

public interface AuthService {
    Result<LoginVO> login(LoginDTO dto);
}
