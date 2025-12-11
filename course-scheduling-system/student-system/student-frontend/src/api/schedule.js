import request from './request'

/**
 * 获取排课结果
 */
export function getScheduleResult(studentId) {
  return request({
    url: `/schedules/${studentId}`,
    method: 'get'
  })
}

/**
 * 获取课表视图数据
 */
export function getScheduleTable(studentId) {
  return request({
    url: `/schedules/${studentId}/table`,
    method: 'get'
  })
}

/**
 * 获取排课状态
 */
export function getScheduleStatus(studentId) {
  return request({
    url: `/schedules/${studentId}/status`,
    method: 'get'
  })
}

/**
 * 导出课表（PDF）
 */
export function exportSchedulePdf(studentId) {
  return request({
    url: `/schedules/${studentId}/export/pdf`,
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导出课表（Excel）
 */
export function exportScheduleExcel(studentId) {
  return request({
    url: `/schedules/${studentId}/export/excel`,
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 提交反馈/申诉
 */
export function submitFeedback(data) {
  return request({
    url: '/schedules/feedback',
    method: 'post',
    data
  })
}

/**
 * 获取反馈历史
 */
export function getFeedbackHistory(studentId) {
  return request({
    url: `/schedules/${studentId}/feedback/history`,
    method: 'get'
  })
}
