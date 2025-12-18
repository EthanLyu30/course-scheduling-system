import request from './request'

/**
 * 获取时间偏好配置
 */
export function getPreference(studentId) {
  return request({
    url: '/preferences/current',
    method: 'get',
    params: { studentId }
  })
}

/**
 * 保存时间偏好
 */
export function savePreference(studentId, data) {
  return request({
    url: '/preferences',
    method: 'post',
    params: { studentId },
    data
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
