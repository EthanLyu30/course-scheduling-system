import { defineStore } from 'pinia'
import { getScheduleResult, getScheduleTable, getScheduleStatus } from '@/api/schedule'

export const useScheduleStore = defineStore('schedule', {
  state: () => ({
    scheduleList: [],
    scheduleTable: {},
    status: null,
    loading: false
  }),
  
  getters: {
    // 课程总数
    totalCourses: (state) => state.scheduleList.length,
    
    // 总学分
    totalCredits: (state) => {
      return state.scheduleList.reduce((sum, item) => sum + (item.credits || 0), 0)
    },
    
    // 按星期组织的课表
    weeklySchedule: (state) => {
      return state.scheduleTable
    },
    
    // 是否已生成排课结果
    hasSchedule: (state) => state.scheduleList.length > 0
  },
  
  actions: {
    async fetchSchedule(studentId) {
      this.loading = true
      try {
        const res = await getScheduleResult(studentId)
        this.scheduleList = res.data || []
      } catch (error) {
        console.error('获取排课结果失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    async fetchScheduleTable(studentId) {
      this.loading = true
      try {
        const res = await getScheduleTable(studentId)
        this.scheduleTable = res.data || {}
      } catch (error) {
        console.error('获取课表视图失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    async fetchStatus(studentId) {
      try {
        const res = await getScheduleStatus(studentId)
        this.status = res.data
      } catch (error) {
        console.error('获取排课状态失败:', error)
      }
    }
  }
})
