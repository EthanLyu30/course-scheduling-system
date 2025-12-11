<template>
  <el-skeleton v-if="loading" rows="5" animated />
  <slot v-else></slot>
</template>

<script setup>
defineProps({
  loading: {
    type: Boolean,
    default: false
  }
})
</script>
