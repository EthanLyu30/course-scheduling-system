import request from './request'

export function login(payload) {
  return request({
    url: '/auth/login',
    method: 'post',
    data: payload
  })
}
