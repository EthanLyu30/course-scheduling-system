<template>
  <div>
    <time-grid
      :slots="timeSlots"
      :on-change="onChange"
    />
    <div style="text-align: right; margin-top: 10px;">
      <el-button type="primary" :loading="loading" @click="save">保存偏好</el-button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import TimeGrid from '@/components/TimeGrid.vue'
import { usePreferenceStore } from '@/stores'

const store = usePreferenceStore()
const studentId = localStorage.getItem('studentId') || '1'

const loading = computed(() => store.loading)
const timeSlots = computed(() => store.timeSlots)

onMounted(async () => {
  await Promise.all([
    store.fetchPreference(studentId),
    store.fetchTimeSlots(studentId)
  ])
})

function onChange(day, slot, level) {
  store.updateSlotLevel(day, slot, level)
}

async function save() {
  const ok = await store.saveAll(studentId)
  if (ok) ElMessage.success('已保存')
}
</script>
