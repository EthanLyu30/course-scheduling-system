import { defineStore } from 'pinia'
import { getPreference, getTimeSlots, savePreference, saveTimeSlots } from '@/api/preference'

export const usePreferenceStore = defineStore('preference', {
  state: () => ({
    preference: null,
    timeSlots: [],
    loading: false
  }),
  
  getters: {
    // 按星期分组的时间段
    slotsByDay: (state) => {
      const days = {
        1: [], 2: [], 3: [], 4: [], 5: [], 6: [], 7: []
      }
      state.timeSlots.forEach(slot => {
        if (days[slot.dayOfWeek]) {
          days[slot.dayOfWeek].push(slot)
        }
      })
      return days
    },
    
    // 是否有未保存的修改
    hasChanges: (state) => {
      return state.timeSlots.some(slot => slot._modified)
    }
  },
  
  actions: {
    async fetchPreference(studentId) {
      this.loading = true
      try {
        const res = await getPreference(studentId)
        this.preference = res.data
      } catch (error) {
        console.error('获取偏好失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    async fetchTimeSlots(studentId) {
      this.loading = true
      try {
        const res = await getTimeSlots(studentId)
        this.timeSlots = res.data || []
      } catch (error) {
        console.error('获取时间段失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    updateSlotLevel(dayOfWeek, slotNumber, level) {
      const slot = this.timeSlots.find(
        s => s.dayOfWeek === dayOfWeek && s.slotNumber === slotNumber
      )
      if (slot) {
        slot.preferenceLevel = level
        slot._modified = true
      } else {
        // 新增
        this.timeSlots.push({
          dayOfWeek,
          slotNumber,
          preferenceLevel: level,
          _modified: true
        })
      }
    },
    
    async saveAll(studentId) {
      this.loading = true
      try {
        const modifiedSlots = this.timeSlots.filter(s => s._modified)
        if (modifiedSlots.length > 0) {
          await saveTimeSlots(studentId, modifiedSlots)
          // 清除修改标记
          modifiedSlots.forEach(s => delete s._modified)
        }
        return true
      } catch (error) {
        console.error('保存失败:', error)
        return false
      } finally {
        this.loading = false
      }
    }
  }
})
