<template>
  <div>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="stat-icon blue"><el-icon><Collection /></el-icon></div>
        <div class="stat-value">{{ totalCourses }}</div>
        <div class="stat-label">课程数</div>
      </div>
      <div class="stat-card">
        <div class="stat-icon green"><el-icon><Coin /></el-icon></div>
        <div class="stat-value">{{ totalCredits }}</div>
        <div class="stat-label">总学分</div>
      </div>
      <div class="stat-card">
        <div class="stat-icon orange"><el-icon><Loading /></el-icon></div>
        <div class="stat-value">{{ statusText }}</div>
        <div class="stat-label">排课状态</div>
      </div>
      <div class="stat-card">
        <div class="stat-icon purple"><el-icon><Bell /></el-icon></div>
        <div class="stat-value">{{ scheduleList.length }}</div>
        <div class="stat-label">已确认门数</div>
      </div>
    </div>

    <schedule-table :weekly="weekly" />
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import ScheduleTable from '@/components/ScheduleTable.vue'
import { useScheduleStore } from '@/stores'

const store = useScheduleStore()
const studentId = localStorage.getItem('studentId') || '1'

const totalCourses = computed(() => store.totalCourses)
const totalCredits = computed(() => store.totalCredits)
const weekly = computed(() => store.weeklySchedule)
const scheduleList = computed(() => store.scheduleList)
const statusText = computed(() => (store.status ? store.status.statusText || '待排课' : '待排课'))

onMounted(async () => {
  await Promise.all([
    store.fetchSchedule(studentId),
    store.fetchScheduleTable(studentId),
    store.fetchStatus(studentId)
  ])
})
</script>
