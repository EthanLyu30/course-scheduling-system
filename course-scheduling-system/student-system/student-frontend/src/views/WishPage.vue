<template>
  <div>
    <wish-list
      :wishes="sorted"
      :on-move="move"
      :on-save="saveOrder"
      @remove="remove"
    />
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import WishList from '@/components/WishList.vue'
import { useWishStore } from '@/stores'

const store = useWishStore()
const studentId = localStorage.getItem('studentId') || '1'

const sorted = computed(() => store.sortedWishes)

onMounted(async () => {
  await store.fetchWishes(studentId)
})

function move(from, to) {
  store.reorderLocal(from, to)
}

async function saveOrder() {
  const ok = await store.saveWishOrder(studentId)
  if (ok) ElMessage.success('已保存排序')
}

async function remove(wish) {
  const ok = await store.removeWish(wish.id)
  if (ok) ElMessage.success('已删除')
}
</script>
