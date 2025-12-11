import request from './request'

/**
 * 获取可选课程列表
 */
export function getCourseList(params) {
  return request({
    url: '/courses',
    method: 'get',
    params
  })
}

/**
 * 获取课程详情
 */
export function getCourseDetail(courseId) {
  return request({
    url: `/courses/${courseId}`,
    method: 'get'
  })
}

/**
 * 获取课程的教师列表
 */
export function getCourseTeachers(courseId) {
  return request({
    url: `/courses/${courseId}/teachers`,
    method: 'get'
  })
}

/**
 * 搜索课程
 */
export function searchCourses(keyword) {
  return request({
    url: '/courses/search',
    method: 'get',
    params: { keyword }
  })
}
