<script setup lang="ts">
import { useMessageStore } from '@/stores/message'
const messageStore = useMessageStore()
</script>

<template>
  <div class="board-list">
    <div v-for="message in messageStore.notifications" :key="message.id" class="panel-card" style="padding: 24px;">
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <div style="font-size: 20px; font-weight: 700;">{{ message.title }}</div>
        <el-tag :type="message.read ? 'info' : 'warning'" round>{{ message.read ? '已读' : '未读' }}</el-tag>
      </div>
      <div style="margin-top: 10px; color: rgba(22, 50, 79, 0.76);">{{ message.content }}</div>
    </div>
  </div>
</template>
