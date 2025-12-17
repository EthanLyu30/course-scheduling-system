package com.schedule.teacher.service;

import com.schedule.teacher.vo.Result;
import com.schedule.teacher.vo.ScheduleVO;

import java.util.List;

public interface ScheduleService {
    Result<List<ScheduleVO>> getSchedule(String teacherId);
    Result<Void> confirm(String planId);
}
