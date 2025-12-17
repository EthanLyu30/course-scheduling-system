import { defineStore } from 'pinia'
import { login } from '@/api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('teacherToken') || '',
    profile: JSON.parse(localStorage.getItem('teacherProfile') || 'null')
  }),
  actions: {
    async doLogin(payload) {
      // 后端登录占位：期望返回 { token, teacherId, name }
      const res = await login(payload)
      const data = res.data || res
      this.token = data.token || 'mock-token'
      this.profile = { id: data.teacherId || payload.teacherId, name: data.name || '教师' }
      localStorage.setItem('teacherToken', this.token)
      localStorage.setItem('teacherId', this.profile.id)
      localStorage.setItem('teacherProfile', JSON.stringify(this.profile))
      return true
    },
    logout() {
      this.token = ''
      this.profile = null
      localStorage.removeItem('teacherToken')
      localStorage.removeItem('teacherId')
      localStorage.removeItem('teacherProfile')
    }
  }
})
