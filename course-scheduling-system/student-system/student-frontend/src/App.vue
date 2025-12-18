<template>
  <el-config-provider :locale="zhCn">
    <el-container class="app-container">
      <!-- 侧边栏 -->
      <el-aside width="220px" class="app-aside">
        <div class="logo">
          <el-icon><Calendar /></el-icon>
          <span>学生排课系统</span>
        </div>
        <el-menu
          :default-active="$route.path"
          router
          class="app-menu"
        >
          <el-menu-item index="/">
            <el-icon><House /></el-icon>
            <span>导览首页</span>
          </el-menu-item>
          <el-menu-item index="/preference">
            <el-icon><Clock /></el-icon>
            <span>时间偏好设置</span>
          </el-menu-item>
          <el-menu-item index="/courses">
            <el-icon><Reading /></el-icon>
            <span>课程选择</span>
          </el-menu-item>
          <el-menu-item index="/wishes">
            <el-icon><Star /></el-icon>
            <span>意愿表达</span>
          </el-menu-item>
          <el-menu-item index="/schedule">
            <el-icon><Grid /></el-icon>
            <span>排课结果</span>
          </el-menu-item>
          <el-menu-item index="/feedback">
            <el-icon><ChatDotRound /></el-icon>
            <span>反馈与帮助</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主内容区 -->
      <el-container>
        <!-- 顶部栏 -->
        <el-header class="app-header">
          <div class="header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentPageTitle }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="header-right">
            <el-tag type="info">2024-2025学年 第二学期</el-tag>
            <el-dropdown>
              <span class="user-info">
                <el-avatar :size="32" src="" />
                <span>吕霄阳</span>
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="viewProfile">个人信息</el-dropdown-item>
                  <el-dropdown-item @click="changePassword">修改密码</el-dropdown-item>
                  <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <!-- 内容区 -->
        <el-main class="app-main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>

    <!-- 个人信息弹窗 -->
    <el-dialog v-model="profileDialog" title="个人信息" width="420px">
      <div class="profile-row"><span class="label">学号</span><span>{{ profile.studentId || '未填写' }}</span></div>
      <div class="profile-row"><span class="label">姓名</span><span>{{ profile.studentName || '未填写' }}</span></div>
      <div class="profile-row"><span class="label">学期</span><span>2024-2025-2</span></div>
      <template #footer>
        <el-button @click="profileDialog = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 修改密码弹窗（前端占位，未接入后端） -->
    <el-dialog v-model="pwdDialog" title="修改密码" width="420px">
      <el-form :model="pwdForm" label-width="90px">
        <el-form-item label="旧密码">
          <el-input v-model="pwdForm.old" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="pwdForm.new" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="pwdForm.confirm" type="password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="pwdDialog = false">取消</el-button>
        <el-button type="primary" @click="submitPwd">提交</el-button>
      </template>
    </el-dialog>
  </el-config-provider>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const route = useRoute()
const router = useRouter()

const pageTitles = {
  '/': '导览首页',
  '/preference': '时间偏好设置',
  '/courses': '课程选择',
  '/wishes': '意愿表达',
  '/schedule': '排课结果',
  '/feedback': '反馈与帮助'
}

const currentPageTitle = computed(() => {
  return pageTitles[route.path] || '首页'
})

const profileDialog = ref(false)
const profile = reactive({
  studentId: localStorage.getItem('studentId') || '',
  studentName: localStorage.getItem('studentName') || ''
})

const pwdDialog = ref(false)
const pwdForm = reactive({ old: '', new: '', confirm: '' })

function viewProfile() {
  profile.studentId = localStorage.getItem('studentId') || ''
  profile.studentName = localStorage.getItem('studentName') || ''
  profileDialog.value = true
}

function changePassword() {
  pwdDialog.value = true
}

function logout() {
  ElMessageBox.confirm('确定要退出登录吗？', '退出登录', {
    confirmButtonText: '退出',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('studentId')
    localStorage.removeItem('token')
    router.push('/login')
    ElMessage.success('已退出登录')
  }).catch(() => {})
}

function submitPwd() {
  if (!pwdForm.old || !pwdForm.new || !pwdForm.confirm) {
    ElMessage.warning('请完整填写')
    return
  }
  if (pwdForm.new !== pwdForm.confirm) {
    ElMessage.error('两次输入的新密码不一致')
    return
  }
  // 占位：此处应调用后端接口
  ElMessage.success('已提交修改（示例，占位未接入后端）')
  pwdDialog.value = false
  pwdForm.old = ''
  pwdForm.new = ''
  pwdForm.confirm = ''
}
</script>

<style scoped>
.app-container {
  height: 100vh;
}

.app-aside {
  background: #0f172a;
  color: #e2e8f0;
  box-shadow: 6px 0 24px rgba(15, 23, 42, 0.15);
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}

.app-menu {
  border-right: none;
  background: transparent;
}

.app-menu .el-menu-item {
  color: rgba(255, 255, 255, 0.75);
}

.app-menu .el-menu-item:hover,
.app-menu .el-menu-item.is-active {
  background: rgba(255, 255, 255, 0.12);
  color: #fff;
}

.app-header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.app-main {
  background: linear-gradient(180deg, #f8fafc 0%, #eef2ff 100%);
  padding: 18px;
}

.profile-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 4px;
  font-size: 14px;
  color: #1f2937;
}

.profile-row .label {
  color: #6b7280;
}
</style>
