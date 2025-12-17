<template>
  <div class="login-wrap">
    <div class="login-card">
      <div class="title">教师排课登录</div>
      <el-form :model="form" label-position="top">
        <el-form-item label="教师工号">
          <el-input v-model="form.teacherNo" placeholder="如 T1001" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="占位密码" />
        </el-form-item>
        <el-button type="primary" :loading="loading" @click="doLogin" style="width:100%;">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores'

const form = reactive({ teacherNo: 'T1001', password: '123456' })
const loading = ref(false)
const router = useRouter()
const auth = useAuthStore()

async function doLogin() {
  loading.value = true
  await auth.doLogin(form)
  loading.value = false
  router.push('/dashboard')
}
</script>

<style scoped>
.login-wrap {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: radial-gradient(circle at 20% 20%, #1e293b, #0b1220 60%);
}
.login-card {
  width: 340px;
  padding: 24px;
  background: #0f172a;
  border: 1px solid rgba(255,255,255,0.08);
  border-radius: 12px;
  color: #e2e8f0;
  box-shadow: 0 20px 60px rgba(0,0,0,0.35);
}
.title { font-size: 18px; font-weight: 700; margin-bottom: 18px; color: #38bdf8; }
</style>
