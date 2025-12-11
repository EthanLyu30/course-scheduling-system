<template>
  <div class="page-card" style="max-width: 420px; margin: 80px auto;">
    <div class="card-title">模拟登录</div>
    <el-form label-width="80px" :model="form">
      <el-form-item label="学号">
        <el-input v-model="form.studentId" placeholder="输入学号，如 1" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" placeholder="可选" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">进入系统</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = reactive({ studentId: '1', name: '' })

function login() {
  localStorage.setItem('studentId', form.studentId || '1')
  if (form.name) localStorage.setItem('studentName', form.name)
  router.push('/preference')
}
</script>
