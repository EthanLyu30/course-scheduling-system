import request from './request'

/**
 * 获取时间偏好配置
 */
export function getPreference(studentId) {
  return request({
    url: `/preferences/${studentId}`,
    method: 'get'
  })
}

/**
 * 获取时间段列表
 */
export function getTimeSlots(studentId) {
  return request({
    url: `/preferences/${studentId}/slots`,
    method: 'get'
  })
}

/**
 * 保存时间偏好
 */
export function savePreference(data) {
  return request({
    url: '/preferences',
    method: 'post',
    data
  })
}

/**
 * 批量保存时间段
 */
export function saveTimeSlots(studentId, slots) {
  return request({
    url: `/preferences/${studentId}/slots/batch`,
    method: 'post',
    data: slots
  })
}

/**
 * 获取偏好模板列表
 */
export function getPreferenceTemplates() {
  return request({
    url: '/preferences/templates',
    method: 'get'
  })
}

/**
 * 应用偏好模板
 */
export function applyTemplate(studentId, templateId) {
  return request({
    url: `/preferences/${studentId}/apply-template`,
    method: 'post',
    data: { templateId }
  })
}
