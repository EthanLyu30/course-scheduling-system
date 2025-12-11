<template>
  <div class="course-card" :class="{ selected }">
    <div class="course-name">{{ course.name }}</div>
    <div class="course-info">
      <div>课程编号：{{ course.id }}</div>
      <div>授课教师：{{ course.teacherName || '待定' }}</div>
      <div>学分：{{ course.credits || '--' }} | 周学时：{{ course.weeklyHours || '--' }}</div>
      <div>容量：{{ course.capacity || '--' }}</div>
    </div>
    <div style="margin-top: 10px; display: flex; gap: 10px;">
      <el-button type="primary" size="small" @click="$emit('select', course)" :disabled="selected">
        {{ selected ? '已加入意愿' : '加入意愿' }}
      </el-button>
      <slot name="extra"></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  course: {
    type: Object,
    required: true
  },
  selected: {
    type: Boolean,
    default: false
  }
})
</script>
