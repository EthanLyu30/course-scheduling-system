import { defineStore } from 'pinia'
import { listActiveConstraints } from '@/api'

export const useConstraintStore = defineStore('t-constraint', {
  state: () => ({
    rules: [],
    loading: false
  }),
  actions: {
    async fetch() {
      this.loading = true
      try {
        const res = await listActiveConstraints()
        this.rules = res.data || res
      } finally {
        this.loading = false
      }
    }
  }
})
