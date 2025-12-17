import { defineStore } from 'pinia'
import { getSchedule, confirmPlan } from '@/api'

export const useScheduleStore = defineStore('t-schedule', {
  state: () => ({
    items: [],
    loading: false
  }),
  getters: {
    confirmedCount: state => state.items.filter(i => i.status === 'CONFIRMED').length
  },
  actions: {
    async fetch(teacherId) {
      this.loading = true
      try {
        const res = await getSchedule(teacherId)
        this.items = res.data || res
      } finally {
        this.loading = false
      }
    },
    async confirm(planId, comment) {
      await confirmPlan(planId, comment)
    }
  }
})
