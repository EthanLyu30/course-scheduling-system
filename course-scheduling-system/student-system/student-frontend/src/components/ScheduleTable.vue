<template>
  <div class="page-card">
    <div class="card-header">
      <div class="card-title">课表</div>
    </div>

    <div class="time-grid">
      <div class="grid-header">时间/星期</div>
      <div
        v-for="day in days"
        :key="day.value"
        class="grid-header"
      >
        {{ day.label }}
      </div>

      <template v-for="slot in slotNumbers" :key="slot">
        <div class="time-label">
          <span>第 {{ slot }} 节</span>
          <span class="time-text">{{ slotTimeText(slot) }}</span>
        </div>
        <div
          v-for="day in days"
          :key="`${day.value}-${slot}`"
          class="time-slot"
        >
          <div v-if="cell(day.value, slot)" class="course-cell">
            <div class="course-name">{{ cell(day.value, slot).courseName }}</div>
            <div class="course-detail">教师：{{ cell(day.value, slot).teacherName || '待定' }}</div>
            <div class="course-detail">地点：{{ cell(day.value, slot).classroomName || '待定' }}</div>
          </div>
          <div v-else class="course-empty">—</div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  weekly: {
    type: Object,
    default: () => ({})
  },
  slotNumbers: {
    type: Array,
    default: () => [1, 2, 3, 4, 5, 6]
  }
})

const days = [
  { value: 1, label: '周一' },
  { value: 2, label: '周二' },
  { value: 3, label: '周三' },
  { value: 4, label: '周四' },
  { value: 5, label: '周五' },
  { value: 6, label: '周六' },
  { value: 7, label: '周日' }
]

const weeklyMap = computed(() => {
  // weekly 格式假设：{ "1-1": {...}, "1-2": {...} }
  return props.weekly || {}
})

function slotKey(day, slot) {
  return `${day}-${slot}`
}

function cell(day, slot) {
  return weeklyMap.value[slotKey(day, slot)] || null
}

function slotTimeText(slot) {
  const mapping = {
    1: '08:00 - 09:35',
    2: '09:50 - 12:05',
    3: '13:30 - 15:05',
    4: '15:20 - 17:00',
    5: '18:30 - 20:05',
    6: '20:10 - 21:40'
  }
  return mapping[slot] || ''
}
</script>
