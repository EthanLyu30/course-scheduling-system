import request from './request'

export function resolveConflict(payload) {
  return request({
    url: '/teacher/conflicts/resolve',
    method: 'post',
    data: payload
  })
}
