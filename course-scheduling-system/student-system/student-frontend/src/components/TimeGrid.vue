<template>
  <div class="page-card">
    <div class="card-header">
      <div>
        <div class="card-title">时间偏好</div>
        <div class="card-subtitle">点击格子在四档偏好间循环切换</div>
      </div>
      <div class="legend">
        <div
          v-for="level in legendLevels"
          :key="level.value"
          class="legend-item"
        >
          <span class="legend-dot" :class="level.class" />
          <span class="legend-text">{{ level.label }}</span>
        </div>
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
          <div class="slot-no">第 {{ slot }} 节</div>
          <div class="time-text">{{ slotTimeText(slot) }}</div>
        </div>
        <div
          v-for="day in days"
          :key="`${day.value}-${slot}`"
          class="time-slot"
          :class="slotClass(day.value, slot)"
          @click="toggle(day.value, slot)"
        >
          <div class="slot-status">{{ slotText(day.value, slot) }}</div>
          <div class="slot-hint">{{ day.label }} · 第 {{ slot }} 节</div>
        </div>
      </template>
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

const levelMeta = {
  4: { text: '优先安排', class: 'level-4' },
  3: { text: '可上', class: 'level-3' },
  2: { text: '不太想上', class: 'level-2' },
  1: { text: '不可上', class: 'level-1' }
}

const legendLevels = [
  { value: 4, label: '优先安排', class: levelMeta[4].class },
  { value: 3, label: '可上', class: levelMeta[3].class },
  { value: 2, label: '不太想上', class: levelMeta[2].class },
  { value: 1, label: '不可上', class: levelMeta[1].class }
]

function slotKey(day, slot) {
  return `${day}-${slot}`
}

function slotText(day, slot) {
  const level = slotMap.value[slotKey(day, slot)] ?? 3
  return levelMeta[level]?.text || '可上'
}

function slotClass(day, slot) {
  const level = slotMap.value[slotKey(day, slot)] ?? 3
  return levelMeta[level]?.class || 'level-3'
}

function nextLevel(current) {
  const order = [3, 4, 2, 1]
  const idx = order.indexOf(current)
  const nextIdx = idx === -1 ? 0 : (idx + 1) % order.length
  return order[nextIdx]
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
.page-card {
  background: linear-gradient(135deg, #f7f9fc 0%, #eef2ff 100%);
  border-radius: 16px;
  padding: 16px;
  box-shadow: 0 10px 30px rgba(64, 80, 169, 0.08);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  gap: 12px;
}

.card-title {
  font-size: 18px;
  font-weight: 700;
  color: #1f2d3d;
}

.card-subtitle {
  color: #6b7280;
  font-size: 13px;
  margin-top: 4px;
}

.legend {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.legend-item {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #4b5563;
}

.legend-dot {
  width: 14px;
  height: 14px;
  border-radius: 6px;
  display: inline-block;
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.legend-text {
  white-space: nowrap;
}

.time-grid {
  display: grid;
  grid-template-columns: 120px repeat(7, 1fr);
  gap: 8px;
}

.grid-header {
  font-weight: 700;
  color: #374151;
  text-align: center;
  background: #ffffff;
  padding: 10px 6px;
  border-radius: 12px;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.03);
}

.time-label {
  background: #ffffff;
  border-radius: 12px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 4px;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.04);
}

.slot-no {
  font-weight: 700;
  color: #111827;
}

.time-text {
  color: #6b7280;
  font-size: 12px;
}

.time-slot {
  min-height: 76px;
  border-radius: 14px;
  padding: 10px;
  cursor: pointer;
  transition: transform 0.12s ease, box-shadow 0.12s ease, opacity 0.12s ease;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.04);
}

.time-slot:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 18px rgba(55, 65, 81, 0.12);
}

.slot-status {
  font-weight: 700;
  font-size: 15px;
}

.slot-hint {
  margin-top: 6px;
  color: #6b7280;
  font-size: 12px;
}

.level-4 {
  background: linear-gradient(135deg, #34d399 0%, #10b981 100%);
  color: #0b3b2c;
}

.level-3 {
  background: linear-gradient(135deg, #bfdbfe 0%, #93c5fd 100%);
  color: #0f172a;
}

.level-2 {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
}

.level-1 {
  background: linear-gradient(135deg, #fee2e2 0%, #fecdd3 100%);
  color: #991b1b;
}

.level-4 .slot-hint,
.level-3 .slot-hint,
.level-2 .slot-hint,
.level-1 .slot-hint {
  color: rgba(17, 24, 39, 0.7);
}

.level-4 .legend-dot,
.level-3 .legend-dot,
.level-2 .legend-dot,
.level-1 .legend-dot {
  border: none;
}
</style>
