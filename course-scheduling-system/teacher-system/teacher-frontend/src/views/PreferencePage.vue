<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">时间偏好</div>
      <div class="badge">点击单元格在 1~5 之间循环</div>
    </div>
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
            <td v-for="d in days" :key="d.value" @click="toggle(d.value, s)" style="text-align:center; cursor:pointer; padding:10px;">
              <el-tag size="small" :type="tagType(score(d.value, s))">{{ score(d.value, s) }}</el-tag>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div style="text-align:right; margin-top:12px;">
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
const prefMap = reactive({})

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

function tagType(val) {
  if (val >=4) return 'success'
  if (val ===3) return 'info'
  return 'warning'
}
</script>
