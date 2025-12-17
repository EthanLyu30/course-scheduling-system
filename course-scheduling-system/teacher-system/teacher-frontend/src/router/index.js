import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: () => import('@/views/LoginPage.vue'), meta: { public: true } },
  { path: '/dashboard', name: 'Dashboard', component: () => import('@/views/DashboardPage.vue') },
  { path: '/preferences', name: 'Preferences', component: () => import('@/views/PreferencePage.vue') },
  { path: '/schedule', name: 'Schedule', component: () => import('@/views/SchedulePage.vue') },
  { path: '/conflicts', name: 'Conflicts', component: () => import('@/views/ConflictPage.vue') },
  { path: '/constraints', name: 'Constraints', component: () => import('@/views/ConstraintPage.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('teacherToken')
  if (!to.meta.public && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
