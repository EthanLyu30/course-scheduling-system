<template>
  <div class="layout">
    <header class="topbar">
      <div class="brand">教师排课 · Control Desk</div>
      <div class="top-actions">
        <el-tag type="info">2024-2025 下</el-tag>
        <el-dropdown>
          <span class="user-entry">
            <el-icon><User /></el-icon>
            <span>{{ teacherName || '未登录' }}</span>
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="goProfile">个人信息</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </header>

    <aside class="sidebar">
      <div class="sidebar-title">导航</div>
      <el-menu :default-active="$route.path" router class="nav-menu" background-color="#0f172a" text-color="#cbd5e1" active-text-color="#38bdf8">
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/preferences">
          <el-icon><Timer /></el-icon>
          <span>时间偏好</span>
        </el-menu-item>
        <el-menu-item index="/schedule">
          <el-icon><Calendar /></el-icon>
          <span>我的课表</span>
        </el-menu-item>
        <el-menu-item index="/conflicts">
          <el-icon><WarningFilled /></el-icon>
          <span>冲突反馈</span>
        </el-menu-item>
        <el-menu-item index="/constraints">
          <el-icon><Collection /></el-icon>
          <span>约束规则</span>
        </el-menu-item>
      </el-menu>
    </aside>

    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores'

const router = useRouter()
const auth = useAuthStore()
const teacherName = computed(() => auth.profile?.name)

function goProfile() {
  // 占位：跳个人信息页
}

function logout() {
  auth.logout()
  router.push('/login')
}
</script>
