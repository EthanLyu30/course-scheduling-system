package com.schedule.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.student.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询课程列表（包含教师姓名）
     */
    @Select("""
        SELECT c.*, t.name as teacher_name 
        FROM course c 
        LEFT JOIN teacher t ON c.teacher_id = t.id 
        WHERE c.semester = #{semester} AND c.status = 1
        ORDER BY c.popularity_index DESC
    """)
    List<Course> selectCoursesWithTeacher(@Param("semester") String semester);

    /**
     * 根据ID查询课程详情（包含教师姓名）
     */
    @Select("""
        SELECT c.*, t.name as teacher_name 
        FROM course c 
        LEFT JOIN teacher t ON c.teacher_id = t.id 
        WHERE c.id = #{id}
    """)
    Course selectCourseDetail(@Param("id") Long id);
}
