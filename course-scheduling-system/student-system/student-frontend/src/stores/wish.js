import { defineStore } from 'pinia'
import { getWishList, addWish, deleteWish, updateWish } from '@/api/wish'

export const useWishStore = defineStore('wish', {
  state: () => ({
    wishes: [],
    loading: false
  }),
  
  getters: {
    // 按优先级排序的意愿列表
    sortedWishes: (state) => {
      return [...state.wishes].sort((a, b) => a.priority - b.priority)
    },
    
    // 已添加的课程ID列表
    selectedCourseIds: (state) => {
      return state.wishes.map(w => w.courseId)
    },
    
    // 意愿数量
    wishCount: (state) => state.wishes.length
  },
  
  actions: {
    async fetchWishes(studentId) {
      this.loading = true
      try {
        const res = await getWishList(studentId)
        this.wishes = res.data || []
      } catch (error) {
        console.error('获取意愿列表失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    async addNewWish(wishData) {
      this.loading = true
      try {
        const { studentId, ...dto } = wishData
        const res = await addWish(studentId, dto)
        const saved = res.data || {}
        this.wishes.push({ ...dto, ...saved })
        return true
      } catch (error) {
        console.error('添加意愿失败:', error)
        return false
      } finally {
        this.loading = false
      }
    },
    
    async removeWish(wishId) {
      this.loading = true
      try {
        await deleteWish(wishId)
        this.wishes = this.wishes.filter(w => w.id !== wishId)
        return true
      } catch (error) {
        console.error('删除意愿失败:', error)
        return false
      } finally {
        this.loading = false
      }
    },
    
    // 调整顺序（本地）
    reorderLocal(fromIndex, toIndex) {
      const wish = this.wishes.splice(fromIndex, 1)[0]
      this.wishes.splice(toIndex, 0, wish)
      // 重新计算优先级
      this.wishes.forEach((w, index) => {
        w.priority = index + 1
      })
    },
    
    async saveWishOrder(studentId) {
      this.loading = true
      try {
        const tasks = this.wishes.map(w => updateWish(w.id, {
          courseId: w.courseId,
          semester: w.semester || '2024-2025-2',
          priority: w.priority,
          reason: w.reason
        }))
        await Promise.all(tasks)
        return true
      } catch (error) {
        console.error('保存排序失败:', error)
        return false
      } finally {
        this.loading = false
      }
    }
  }
})
