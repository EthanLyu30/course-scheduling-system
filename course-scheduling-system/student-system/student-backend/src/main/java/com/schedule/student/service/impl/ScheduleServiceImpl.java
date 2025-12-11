package com.schedule.student.service.impl;

import com.schedule.student.entity.Schedule;
import com.schedule.student.mapper.ScheduleMapper;
import com.schedule.student.service.ScheduleService;
import com.schedule.student.vo.ScheduleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 排课结果服务实现类
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> getStudentSchedule(Long studentId, String semester) {
        return scheduleMapper.selectStudentSchedule(studentId, semester);
    }

    @Override
    public Double getSatisfactionRate(Long studentId, String semester) {
        // TODO: 根据学生时间偏好和实际课表计算满足率
        // 暂时返回模拟数据
        return 85.5;
    }

    @Override
    public List<ScheduleVO> getCourseMatchStatus(Long studentId, String semester) {
        // TODO: 获取每门课的匹配状态
        return null;
    }

    @Override
    public byte[] exportSchedule(Long studentId, String semester, String format) {
        // TODO: 根据format生成PDF/图片/iCal格式
        return new byte[0];
    }
}
