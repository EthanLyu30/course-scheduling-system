package com.schedule.student.service;

import com.schedule.student.entity.Course;
import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService {

    /**
     * 获取当前学期课程列表
     */
    List<Course> getCourseList(String semester);

    /**
     * 获取课程详情
     */
    Course getCourseDetail(Long id);

    /**
     * 根据类型筛选课程
     */
    List<Course> getCoursesByType(String semester, Integer courseType);

    /**
     * 搜索课程
     */
    List<Course> searchCourses(String semester, String keyword);
}
