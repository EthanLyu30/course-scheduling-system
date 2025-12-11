import request from './request'

/**
 * 获取学生意愿列表
 */
export function getWishList(studentId) {
  return request({
    url: `/wishes/${studentId}`,
    method: 'get'
  })
}

/**
 * 添加意愿
 */
export function addWish(data) {
  return request({
    url: '/wishes',
    method: 'post',
    data
  })
}

/**
 * 批量保存意愿（含排序）
 */
export function batchSaveWishes(studentId, wishes) {
  return request({
    url: `/wishes/${studentId}/batch`,
    method: 'post',
    data: wishes
  })
}

/**
 * 更新意愿优先级
 */
export function updateWishPriority(wishId, priority) {
  return request({
    url: `/wishes/${wishId}/priority`,
    method: 'put',
    data: { priority }
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
 * 调整意愿顺序
 */
export function reorderWishes(studentId, wishIds) {
  return request({
    url: `/wishes/${studentId}/reorder`,
    method: 'post',
    data: { wishIds }
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
