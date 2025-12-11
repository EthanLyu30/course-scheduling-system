<template>
  <div class="page-card">
    <div class="card-header">
      <div class="card-title">反馈/申诉</div>
    </div>
    <el-form label-width="90px" :model="form">
      <el-form-item label="主题">
        <el-input v-model="form.title" placeholder="例如：课程冲突、意愿错误" />
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model="form.content" type="textarea" :rows="5" placeholder="描述问题详情" />
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.contact" placeholder="邮箱或手机号" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="submitting" @click="submit">提交</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const emit = defineEmits(['submit'])

const form = reactive({
  title: '',
  content: '',
  contact: ''
})

const submitting = ref(false)

function reset() {
  form.title = ''
  form.content = ''
  form.contact = ''
}

async function submit() {
  submitting.value = true
  await emit('submit', { ...form })
  submitting.value = false
  reset()
}
</script>
