<template>
  <div class="home">
    <div class="hero">
      <div>
        <div class="eyebrow">智能排课 · 学生端</div>
        <div class="hero-title">把你的时间和意愿说清楚，排课交给系统</div>
        <div class="hero-subtitle">四档时间偏好、课程意愿收集、结果查看与反馈，一站式完成。</div>
        <div class="hero-actions">
          <el-button type="primary" size="large" @click="go('/preference')">先设置时间偏好</el-button>
          <el-button size="large" @click="go('/courses')">去挑课</el-button>
        </div>
      </div>
      <div class="hero-stat">
        <div class="stat-card">
          <div class="stat-number">4</div>
          <div class="stat-label">偏好档位</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">8</div>
          <div class="stat-label">本学期课程</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">3</div>
          <div class="stat-label">反馈渠道</div>
        </div>
      </div>
    </div>

    <div class="grid">
      <div
        v-for="item in modules"
        :key="item.path"
        class="module-card"
        @click="go(item.path)"
      >
        <div class="module-icon" :style="{ background: item.bg }">
          <component :is="item.icon" />
        </div>
        <div class="module-title">{{ item.title }}</div>
        <div class="module-desc">{{ item.desc }}</div>
        <div class="module-link">进入 {{ item.cta }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { Clock, Reading, Star, Grid, ChatDotRound } from '@element-plus/icons-vue'

const router = useRouter()

const modules = [
  { title: '时间偏好', desc: '标记优先、可上、不太想上、不可上，助力智能排课。', path: '/preference', icon: Clock, cta: '偏好', bg: 'linear-gradient(135deg,#e0f7ff,#cde6ff)' },
  { title: '课程选择', desc: '浏览课程、查看教师信息，添加到意愿列表。', path: '/courses', icon: Reading, cta: '课程', bg: 'linear-gradient(135deg,#e8e7ff,#dcd5ff)' },
  { title: '意愿表达', desc: '调整意愿优先级，提交你的必选/想选理由。', path: '/wishes', icon: Star, cta: '意愿', bg: 'linear-gradient(135deg,#ffe9f0,#ffd7e3)' },
  { title: '排课结果', desc: '查看系统排课结果，核对与偏好的匹配度。', path: '/schedule', icon: Grid, cta: '结果', bg: 'linear-gradient(135deg,#e3f8ef,#ccf2e2)' },
  { title: '反馈与帮助', desc: '提交问题或申诉，获取常见问答与联系人。', path: '/feedback', icon: ChatDotRound, cta: '反馈', bg: 'linear-gradient(135deg,#fff4e5,#ffe8cc)' }
]

function go(path) {
  router.push(path)
}
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.hero {
  background: linear-gradient(135deg, #f0f4ff 0%, #e9fbff 100%);
  border-radius: 18px;
  padding: 22px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  border: 1px solid #e5e7eb;
  box-shadow: 0 12px 32px rgba(59, 130, 246, 0.08);
}

.eyebrow {
  font-size: 12px;
  color: #2563eb;
  letter-spacing: 0.4px;
  font-weight: 700;
}

.hero-title {
  font-size: 24px;
  font-weight: 800;
  margin-top: 6px;
  color: #0f172a;
}

.hero-subtitle {
  margin-top: 8px;
  color: #475569;
  font-size: 14px;
  max-width: 520px;
}

.hero-actions {
  margin-top: 14px;
  display: flex;
  gap: 10px;
}

.hero-stat {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  min-width: 240px;
}

.stat-card {
  background: #fff;
  border-radius: 14px;
  padding: 12px;
  text-align: center;
  box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.04);
}

.stat-number {
  font-size: 28px;
  font-weight: 800;
  color: #111827;
}

.stat-label {
  color: #6b7280;
  font-size: 12px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 14px;
}

.module-card {
  background: #fff;
  border-radius: 16px;
  padding: 16px;
  border: 1px solid #e5e7eb;
  box-shadow: 0 10px 22px rgba(15, 23, 42, 0.08);
  cursor: pointer;
  transition: transform 0.15s ease, box-shadow 0.15s ease;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.module-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 14px 28px rgba(15, 23, 42, 0.12);
}

.module-icon {
  width: 46px;
  height: 46px;
  border-radius: 12px;
  display: grid;
  place-items: center;
  color: #0f172a;
  font-size: 20px;
}

.module-title {
  font-size: 16px;
  font-weight: 700;
  color: #0f172a;
}

.module-desc {
  font-size: 13px;
  color: #475569;
  line-height: 1.5;
}

.module-link {
  font-size: 12px;
  color: #2563eb;
  font-weight: 700;
  margin-top: auto;
}
</style>
