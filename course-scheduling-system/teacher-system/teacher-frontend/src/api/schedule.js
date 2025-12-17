import request from './request'

export function getSchedule(teacherId) {
  return request({
    url: `/teacher/schedules/${teacherId}`,
    method: 'get'
  })
}

export function confirmPlan(planId, comment) {
  return request({
    url: `/teacher/schedules/${planId}/confirm`,
    method: 'post',
    data: { comment }
  })
}
