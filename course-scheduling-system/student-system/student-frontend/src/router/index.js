import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/HomePage.vue'),
    meta: { title: '首页' }
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginPage.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/preference',
    name: 'Preference',
    component: () => import('@/views/PreferencePage.vue'),
    meta: { title: '时间偏好设置' }
  },
  {
    path: '/courses',
    name: 'Courses',
    component: () => import('@/views/CoursePage.vue'),
    meta: { title: '课程选择' }
  },
  {
    path: '/wishes',
    name: 'Wishes',
    component: () => import('@/views/WishPage.vue'),
    meta: { title: '意愿表达' }
  },
  {
    path: '/schedule',
    name: 'Schedule',
    component: () => import('@/views/SchedulePage.vue'),
    meta: { title: '排课结果' }
  },
  {
    path: '/feedback',
    name: 'Feedback',
    component: () => import('@/views/FeedbackPage.vue'),
    meta: { title: '反馈与帮助' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
