<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{ status: string }>()

const tagType = computed(() => {
  if (props.status.includes('通过') || props.status === '已上线') return 'success'
  if (props.status.includes('审核') || props.status.includes('待')) return 'warning'
  if (props.status.includes('不合适') || props.status.includes('驳回')) return 'danger'
  return 'info'
})
</script>

<template>
  <el-tag :type="tagType" round>{{ status }}</el-tag>
</template>
