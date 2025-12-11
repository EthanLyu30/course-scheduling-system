package com.schedule.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.student.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 排课结果Mapper接口
 */
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {

    /**
     * 查询学生的课表
     */
    @Select("""
        SELECT s.*, c.name as course_name, c.course_code, c.credits, c.course_type,
               cr.room_name as classroom_name, cr.building
        FROM student_schedule ss
        JOIN schedule s ON ss.schedule_id = s.id
        JOIN course c ON s.course_id = c.id
        LEFT JOIN classroom cr ON s.classroom_id = cr.id
        WHERE ss.student_id = #{studentId} AND ss.semester = #{semester}
        ORDER BY s.day_of_week, s.start_time
    """)
    List<Schedule> selectStudentSchedule(@Param("studentId") Long studentId, @Param("semester") String semester);
}
