<template>
  <div>
    <div class="page-card">
      <div class="card-header">
        <div class="card-title">时间偏好</div>
        <div>
          <el-tag type="success">点击切换：可上 / 中立 / 不可上</el-tag>
        </div>
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
            :class="slotClass(day.value, slot)"
            @click="toggle(day.value, slot)"
          >
            <div class="slot-status">{{ slotText(day.value, slot) }}</div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  slots: {
    type: Array,
    default: () => []
  },
  onChange: {
    type: Function,
    default: null
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

// 建立索引方便查询
const slotMap = computed(() => {
  const map = {}
  props.slots.forEach(s => {
    const key = `${s.dayOfWeek}-${s.slotNumber}`
    map[key] = s.preferenceLevel ?? 0
  })
  return map
})

const statusText = {
  1: '可上',
  0: '中立',
  [-1]: '不可上'
}

const statusClass = {
  1: 'preferred',
  0: 'neutral',
  [-1]: 'unavailable'
}

function slotKey(day, slot) {
  return `${day}-${slot}`
}

function slotText(day, slot) {
  const level = slotMap.value[slotKey(day, slot)] ?? 0
  return statusText[level] || '中立'
}

function slotClass(day, slot) {
  const level = slotMap.value[slotKey(day, slot)] ?? 0
  return statusClass[level] || 'neutral'
}

function nextLevel(current) {
  // 顺序：中立(0) -> 可上(1) -> 不可上(-1) -> 中立
  if (current === 0) return 1
  if (current === 1) return -1
  return 0
}

function toggle(day, slot) {
  if (!props.onChange) {
    ElMessage.warning('未配置事件处理')
    return
  }
  const current = slotMap.value[slotKey(day, slot)] ?? 0
  const next = nextLevel(current)
  props.onChange(day, slot, next)
}

function slotTimeText(slot) {
  // 可根据学校作息调整
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

<style scoped>
.time-text {
  color: #909399;
  font-size: 12px;
}
.slot-status {
  font-weight: 600;
  text-align: center;
}
</style>
