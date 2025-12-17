import { defineStore } from 'pinia'
import { listPreferences, savePreference } from '@/api'

export const usePreferenceStore = defineStore('t-preference', {
  state: () => ({
    list: [],
    loading: false
  }),
  actions: {
    async fetch(teacherId) {
      this.loading = true
      try {
        const res = await listPreferences(teacherId)
        this.list = res.data || res
      } finally {
        this.loading = false
      }
    },
    async save(payload) {
      this.loading = true
      try {
        await savePreference(payload)
        return true
      } finally {
        this.loading = false
      }
    }
  }
})
