<template>
  <div class="card">
    <div class="card-header">
      <div>
        <div class="card-title">时间偏好</div>
        <div class="card-sub">点击单元格循环 1~5，数值越大越偏好</div>
      </div>
      <div class="legend">
        <span v-for="l in legends" :key="l.value" :style="chipStyle(l.value)">{{ l.label }}</span>
      </div>
    </div>

    <div class="table-card">
      <table class="pref-table">
        <thead>
          <tr>
            <th style="width:120px;">时间</th>
            <th v-for="d in days" :key="d.value">{{ d.label }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="s in slots" :key="s">
            <td>
              <div class="slot-label">第 {{ s }} 节</div>
              <div class="slot-time">{{ slotLabel[s] }}</div>
            </td>
            <td v-for="d in days" :key="d.value" @click="toggle(d.value, s)" class="cell">
              <div class="chip" :style="chipStyle(score(d.value, s))">
                <span class="chip-score">{{ score(d.value, s) }}</span>
                <span class="chip-text">{{ chipText(score(d.value, s)) }}</span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="actions">
      <el-button @click="reset">重置为一般(3)</el-button>
      <el-button type="primary" :loading="loading" @click="save">保存偏好</el-button>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive } from 'vue'
import { usePreferenceStore } from '@/stores'
import { ElMessage } from 'element-plus'

const store = usePreferenceStore()
const teacherId = localStorage.getItem('teacherId') || 't-1'
const loading = computed(() => store.loading)

const days = [
  { value: 1, label: '周一' }, { value: 2, label: '周二' }, { value: 3, label: '周三' },
  { value: 4, label: '周四' }, { value: 5, label: '周五' }, { value: 6, label: '周六' }, { value: 7, label: '周日' }
]
const slots = [1,2,3,4,5,6]
const slotLabel = {
  1: '08:00 - 09:30',
  2: '09:50 - 11:20',
  3: '13:30 - 15:00',
  4: '15:20 - 16:50',
  5: '18:30 - 20:00',
  6: '20:10 - 21:40'
}
const prefMap = reactive({})

const legends = [
  { value: 1, label: '避开' },
  { value: 2, label: '不喜欢' },
  { value: 3, label: '一般' },
  { value: 4, label: '喜欢' },
  { value: 5, label: '最优先' }
]

onMounted(async () => {
  await store.fetch(teacherId)
  (store.list || []).forEach(p => {
    prefMap[`${p.timeSlotId}`] = p.score
  })
})

function key(day, slot) { return `${day}-${slot}` }
function score(day, slot) { return prefMap[key(day, slot)] || 3 }
function nextScore(val) { return val >=5 ? 1 : val + 1 }

function toggle(day, slot) {
  const cur = score(day, slot)
  prefMap[key(day, slot)] = nextScore(cur)
}

async function save() {
  const payloads = []
  Object.entries(prefMap).forEach(([k,v]) => {
    const [day, slot] = k.split('-')
    payloads.push({ teacherId, courseId: 0, timeSlotId: Number(`${day}${slot}`), score: v })
  })
  for (const p of payloads) {
    await store.save(p)
  }
  ElMessage.success('已保存')
}

function reset() {
  Object.keys(prefMap).forEach(k => { prefMap[k] = 3 })
}

function chipStyle(val) {
  const colors = {
    1: '#f87171', // red-400
    2: '#fb923c', // orange-400
    3: '#38bdf8', // sky-400
    4: '#34d399', // green-400
    5: '#a78bfa'  // violet-400
  }
  return {
    background: `linear-gradient(120deg, ${colors[val]}33, ${colors[val]}99)`,
    border: `1px solid ${colors[val]}`
  }
}

function chipText(val) {
  const map = {1:'尽量避免',2:'不推荐',3:'可接受',4:'偏好',5:'最优先'}
  return map[val] || '偏好'
}
</script>

<style scoped>
.card { background: radial-gradient(circle at 20% 20%, #0f172a, #0b1220); border: 1px solid rgba(255,255,255,0.06); border-radius: 14px; padding: 18px; color: #e2e8f0; box-shadow: 0 20px 60px rgba(0,0,0,0.35); }
.card-header { display: flex; align-items: center; justify-content: space-between; gap: 16px; flex-wrap: wrap; }
.card-title { font-size: 18px; font-weight: 700; color: #38bdf8; }
.card-sub { font-size: 13px; color: #cbd5e1; }
.legend { display: flex; gap: 8px; flex-wrap: wrap; }
.legend span { padding: 6px 10px; border-radius: 10px; font-size: 12px; color: #0b1220; font-weight: 600; border: 1px solid rgba(255,255,255,0.12); }
.table-card { margin-top: 16px; overflow: hidden; border: 1px solid rgba(255,255,255,0.06); border-radius: 12px; background: rgba(255,255,255,0.02); }
.pref-table { width: 100%; border-collapse: separate; border-spacing: 0; }
.pref-table th, .pref-table td { padding: 10px; text-align: center; }
.pref-table thead { background: rgba(255,255,255,0.04); }
.pref-table th { color: #cbd5e1; font-weight: 600; }
.slot-label { font-weight: 700; }
.slot-time { font-size: 12px; color: #94a3b8; }
.cell { cursor: pointer; padding: 10px; }
.chip { border-radius: 12px; padding: 10px; display: flex; flex-direction: column; align-items: center; gap: 4px; transition: transform 0.1s ease; }
.chip:hover { transform: translateY(-2px); }
.chip-score { font-size: 16px; font-weight: 700; color: #0b1220; }
.chip-text { font-size: 12px; font-weight: 600; color: #0b1220; opacity: 0.85; }
.actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 14px; }
</style>
