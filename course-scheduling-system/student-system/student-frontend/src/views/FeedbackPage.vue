<template>
  <div>
    <feedback-form @submit="submit" />
  </div>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import FeedbackForm from '@/components/FeedbackForm.vue'
import { submitFeedback } from '@/api/schedule'

const studentId = localStorage.getItem('studentId') || '1'

async function submit(payload) {
  const res = await submitFeedback({ ...payload, studentId })
  if (res) {
    ElMessage.success('已提交')
  }
}
</script>
