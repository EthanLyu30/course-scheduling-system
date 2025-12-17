<template>
  <div class="card">
    <div class="card-header">
      <div class="card-title">启用的约束规则</div>
    </div>
    <el-table :data="rules" size="small" stripe>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="ruleType" label="类型" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="parameters" label="参数(JSON)" />
    </el-table>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue'
import { useConstraintStore } from '@/stores'

const store = useConstraintStore()
const rules = computed(() => store.rules)

onMounted(async () => {
  await store.fetch()
})
</script>
