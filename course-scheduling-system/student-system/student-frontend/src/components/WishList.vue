<template>
  <div class="page-card">
    <div class="card-header">
      <div class="card-title">已选意愿</div>
      <div>
        <el-button type="primary" size="small" :loading="saving" @click="onSaveOrder">保存顺序</el-button>
      </div>
    </div>

    <el-empty v-if="!wishes || wishes.length === 0" description="暂无意愿，先去课程页添加吧" />

    <div v-else>
      <div
        v-for="(wish, index) in wishes"
        :key="wish.id || wish.courseId"
        class="wish-item"
      >
        <div class="wish-rank">{{ index + 1 }}</div>
        <div class="wish-content">
          <div class="wish-course">{{ wish.course?.name || wish.courseName || '课程' }}</div>
          <div class="wish-teacher">教师：{{ wish.course?.teacherName || wish.teacherName || '待定' }} | 优先级：{{ wish.priority }}</div>
        </div>
        <div style="display: flex; gap: 8px;">
          <el-button circle size="small" icon="ArrowUp" @click="moveUp(index)" :disabled="index === 0" />
          <el-button circle size="small" icon="ArrowDown" @click="moveDown(index)" :disabled="index === wishes.length - 1" />
          <el-button type="danger" plain size="small" icon="Delete" @click="$emit('remove', wish)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  wishes: {
    type: Array,
    default: () => []
  },
  onMove: {
    type: Function,
    default: null
  },
  onSave: {
    type: Function,
    default: null
  }
})

const saving = ref(false)

function moveUp(index) {
  props.onMove && props.onMove(index, index - 1)
}

function moveDown(index) {
  props.onMove && props.onMove(index, index + 1)
}

async function onSaveOrder() {
  if (!props.onSave) return
  saving.value = true
  await props.onSave()
  saving.value = false
}
</script>
