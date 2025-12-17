<template>
  <div>
    <div class="stat-grid">
      <div class="stat">
        <div class="label">待确认方案</div>
        <div class="value">{{ pendingPlans }}</div>
      </div>
      <div class="stat">
        <div class="label">已确认课程</div>
        <div class="value">{{ confirmed }}</div>
      </div>
      <div class="stat">
        <div class="label">冲突提醒</div>
        <div class="value">{{ conflicts }}</div>
      </div>
    </div>

    <div class="card">
      <div class="card-header">
        <div class="card-title">近期通知</div>
      </div>
      <el-empty description="暂无通知" />
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useScheduleStore } from '@/stores'

const scheduleStore = useScheduleStore()
const confirmed = computed(() => scheduleStore.confirmedCount)
const pendingPlans = computed(() => Math.max(scheduleStore.items.length - confirmed.value, 0))
const conflicts = computed(() => 0)

onMounted(async () => {
  const teacherId = localStorage.getItem('teacherId') || 't-1'
  await scheduleStore.fetch(teacherId)
})
</script>
