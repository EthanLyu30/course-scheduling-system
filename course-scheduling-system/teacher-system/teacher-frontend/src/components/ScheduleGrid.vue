<template>
  <div class="table-card">
    <table style="width:100%; color:#e2e8f0;">
      <thead>
        <tr>
          <th style="width:80px;">时间</th>
          <th v-for="d in days" :key="d.value">{{ d.label }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="s in slots" :key="s">
          <td>第 {{ s }} 节</td>
          <td v-for="d in days" :key="d.value" style="padding:10px;">
            <div class="cell" v-if="cell(d.value, s)">
              <div class="course">{{ cell(d.value, s).courseName || '课程' }}</div>
              <div class="meta">{{ cell(d.value, s).roomName || '教室' }}</div>
            </div>
            <div class="cell empty" v-else>—</div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  items: { type: Array, default: () => [] }
})

const days = [
  { value: 1, label: '周一' }, { value: 2, label: '周二' }, { value: 3, label: '周三' },
  { value: 4, label: '周四' }, { value: 5, label: '周五' }, { value: 6, label: '周六' }, { value: 7, label: '周日' }
]
const slots = [1,2,3,4,5,6]

const map = computed(() => {
  const m = {}
  props.items.forEach(i => {
    const key = `${i.dayOfWeek}-${i.slotNumber}`
    m[key] = i
  })
  return m
})

function cell(day, slot) {
  return map.value[`${day}-${slot}`]
}
</script>

<style scoped>
.cell {
  background: rgba(56,189,248,0.1);
  border: 1px solid rgba(56,189,248,0.2);
  border-radius: 8px;
  padding: 8px;
}
.cell.empty {
  background: rgba(255,255,255,0.03);
  border: 1px dashed rgba(255,255,255,0.08);
  text-align: center;
}
.course { font-weight: 600; }
.meta { font-size: 12px; color: #cbd5e1; }
</style>
