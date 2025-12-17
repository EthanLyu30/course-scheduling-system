<template>
  <div>
    <div class="card">
      <div class="card-header">
        <div class="card-title">我的课表</div>
        <el-button type="primary" size="small" @click="confirm">确认当前方案</el-button>
      </div>
      <el-table :data="items" size="small" stripe>
        <el-table-column prop="courseName" label="课程" />
        <el-table-column prop="dayOfWeek" label="星期" />
        <el-table-column prop="slotNumber" label="节次" />
        <el-table-column prop="roomName" label="教室" />
        <el-table-column prop="status" label="状态" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue'
import { useScheduleStore } from '@/stores'
import { ElMessage } from 'element-plus'

const store = useScheduleStore()
const teacherId = localStorage.getItem('teacherId') || 't-1'
const items = computed(() => store.items)

onMounted(async () => {
  await store.fetch(teacherId)
})

async function confirm() {
  if (items.value.length === 0) {
    ElMessage.warning('暂无可确认的方案')
    return
  }
  const planId = items.value[0].planId
  await store.confirm(planId, '确认通过')
  ElMessage.success('已提交确认')
}
</script>
