package com.schedule.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.student.entity.Course;
import com.schedule.student.mapper.CourseMapper;
import com.schedule.student.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 课程服务实现类
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(String semester) {
        return courseMapper.selectCoursesWithTeacher(semester);
    }

    @Override
    public Course getCourseDetail(Long id) {
        return courseMapper.selectCourseDetail(id);
    }

    @Override
    public List<Course> getCoursesByType(String semester, Integer courseType) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getSemester, semester)
               .eq(Course::getCourseType, courseType)
               .eq(Course::getStatus, 1)
               .orderByDesc(Course::getPopularityIndex);
        return courseMapper.selectList(wrapper);
    }

    @Override
    public List<Course> searchCourses(String semester, String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getSemester, semester)
               .eq(Course::getStatus, 1)
               .and(w -> w.like(Course::getName, keyword)
                         .or()
                         .like(Course::getCourseCode, keyword))
               .orderByDesc(Course::getPopularityIndex);
        return courseMapper.selectList(wrapper);
    }
}
