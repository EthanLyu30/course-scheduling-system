package com.schedule.student.service;

import com.schedule.student.entity.Schedule;
import com.schedule.student.vo.ScheduleVO;
import java.util.List;

/**
 * 排课结果服务接口
 */
public interface ScheduleService {

    /**
     * 获取学生课表
     */
    List<Schedule> getStudentSchedule(Long studentId, String semester);

    /**
     * 获取时间偏好满足率
     */
    Double getSatisfactionRate(Long studentId, String semester);

    /**
     * 获取课程匹配状态
     */
    List<ScheduleVO> getCourseMatchStatus(Long studentId, String semester);

    /**
     * 导出课表
     */
    byte[] exportSchedule(Long studentId, String semester, String format);
}
