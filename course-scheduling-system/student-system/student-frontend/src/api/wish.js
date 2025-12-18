import request from './request'

/**
 * 获取学生意愿列表
 */
export function getWishList(studentId) {
  return request({
    url: '/wishes',
    method: 'get',
    params: { studentId }
  })
}

/**
 * 添加意愿
 */
export function addWish(studentId, data) {
  return request({
    url: '/wishes',
    method: 'post',
    params: { studentId },
    data
  })
}

/**
 * 批量保存意愿（含排序）
 */
export function updateWish(wishId, data) {
  return request({
    url: `/wishes/${wishId}`,
    method: 'put',
    data
  })
}

/**
 * 删除意愿
 */
export function deleteWish(wishId) {
  return request({
    url: `/wishes/${wishId}`,
    method: 'delete'
  })
}

/**
 * 获取推荐课程
 */
export function getRecommendedCourses(studentId) {
  return request({
    url: `/wishes/${studentId}/recommendations`,
    method: 'get'
  })
}
