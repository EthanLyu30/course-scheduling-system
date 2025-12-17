import request from './request'

export function listPreferences(teacherId) {
  return request({
    url: `/teacher/preferences/${teacherId}`,
    method: 'get'
  })
}

export function savePreference(data) {
  return request({
    url: '/teacher/preferences',
    method: 'post',
    data
  })
}
