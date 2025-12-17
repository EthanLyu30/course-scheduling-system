import request from './request'

export function listActiveConstraints() {
  return request({
    url: '/teacher/constraints/active',
    method: 'get'
  })
}
