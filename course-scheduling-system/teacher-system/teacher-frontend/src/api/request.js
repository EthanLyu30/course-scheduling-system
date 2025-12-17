import axios from 'axios'
import { ElMessage } from 'element-plus'

const instance = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API || '/api',
  timeout: 15000
})

instance.interceptors.request.use(config => {
  const token = localStorage.getItem('teacherToken')
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`
  }
  const teacherId = localStorage.getItem('teacherId') || 't-1'
  config.headers['X-Teacher-Id'] = teacherId
  return config
})

instance.interceptors.response.use(
  res => {
    const data = res.data
    if (data.code && data.code !== 200) {
      ElMessage.error(data.message || '请求失败')
      if (data.code === 401) {
        localStorage.removeItem('teacherToken')
        window.location.href = '/login'
      }
      return Promise.reject(data)
    }
    return data
  },
  err => {
    ElMessage.error(err.response?.data?.message || '网络异常')
    return Promise.reject(err)
  }
)

export default instance
