<template>
  <div class="page-card">
    <div class="card-header">
      <div class="card-title">课程列表</div>
      <div style="display: flex; gap: 10px;">
        <el-input v-model="keyword" placeholder="按课程名/教师搜索" clearable @keyup.enter="search" />
        <el-button type="primary" @click="search" :loading="loading">搜索</el-button>
      </div>
    </div>

    <el-skeleton v-if="loading" rows="6" animated />

    <el-row :gutter="16" v-else>
      <el-col :span="8" v-for="course in courseList" :key="course.id">
        <course-card
          :course="course"
          :selected="selectedIds.includes(course.id)"
          @select="addWish(course)"
        />
      </el-col>
    </el-row>

    <el-empty v-if="!loading && courseList.length === 0" description="暂无课程" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import CourseCard from '@/components/CourseCard.vue'
import { getCourseList, searchCourses } from '@/api/course'
import { useWishStore } from '@/stores'

const keyword = ref('')
const courseList = ref([])
const loading = ref(false)
const wishStore = useWishStore()
const studentId = localStorage.getItem('studentId') || '1'

const selectedIds = computed(() => wishStore.selectedCourseIds)

onMounted(async () => {
  await fetchCourses()
  await wishStore.fetchWishes(studentId)
})

async function fetchCourses() {
  loading.value = true
  try {
    const res = await getCourseList()
    courseList.value = res.data || []
  } finally {
    loading.value = false
  }
}

async function search() {
  loading.value = true
  try {
    if (!keyword.value) {
      await fetchCourses()
      return
    }
    const res = await searchCourses(keyword.value)
    courseList.value = res.data || []
  } finally {
    loading.value = false
  }
}

async function addWish(course) {
  const payload = {
    studentId,
    courseId: course.id,
    courseName: course.name,
    teacherId: course.teacherId,
    teacherName: course.teacherName,
    priority: wishStore.wishCount + 1
  }
  const ok = await wishStore.addNewWish(payload)
  if (ok) ElMessage.success('已加入意愿')
}
</script>
