<template>
  <div class="preference-page">
    <div class="hero">
      <div>
        <div class="hero-title">我的时间偏好</div>
        <div class="hero-subtitle">四档细分：优先安排 / 可上 / 不太想上 / 不可上</div>
        <div class="hero-pills">
          <span class="pill level-4">优先安排</span>
          <span class="pill level-3">可上</span>
          <span class="pill level-2">不太想上</span>
          <span class="pill level-1">不可上</span>
        </div>
      </div>
      <div class="hero-actions">
        <el-button :loading="loading" @click="reset">重置为“可上”</el-button>
        <el-button type="primary" :loading="loading" @click="save">保存偏好</el-button>
      </div>
    </div>

    <time-grid
      :slots="timeSlots"
      :on-change="onChange"
    />
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
  await store.fetchPreference(studentId)
})

function onChange(day, slot, level) {
  store.updateSlotLevel(day, slot, level)
}

function reset() {
  store.timeSlots = store.timeSlots.map(s => ({
    ...s,
    preferenceLevel: 3,
    _modified: true
  }))
}

async function save() {
  const ok = await store.saveAll(studentId)
  if (ok) ElMessage.success('已保存')
}
</script>

<style scoped>
.preference-page {
  padding: 12px;
  background: #f8fafc;
  min-height: calc(100vh - 120px);
}

.hero {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #f0f4ff 0%, #e9fbff 100%);
  border: 1px solid #e5e7eb;
  border-radius: 16px;
  padding: 16px 18px;
  margin-bottom: 14px;
  box-shadow: 0 10px 28px rgba(59, 130, 246, 0.08);
  gap: 14px;
}

.hero-title {
  font-size: 20px;
  font-weight: 800;
  color: #0f172a;
}

.hero-subtitle {
  margin-top: 6px;
  color: #475569;
  font-size: 13px;
}

.hero-pills {
  margin-top: 10px;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.pill {
  padding: 6px 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.04);
}

.pill.level-4 {
  background: linear-gradient(135deg, #34d399 0%, #10b981 100%);
  color: #0b3b2c;
}

.pill.level-3 {
  background: linear-gradient(135deg, #bfdbfe 0%, #93c5fd 100%);
  color: #0f172a;
}

.pill.level-2 {
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  color: #92400e;
}

.pill.level-1 {
  background: linear-gradient(135deg, #fee2e2 0%, #fecdd3 100%);
  color: #991b1b;
}

.hero-actions {
  display: flex;
  gap: 10px;
}
</style>
