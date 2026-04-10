<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const menu = [
  { label: '工作台', path: '/candidate/dashboard' },
  { label: '在线简历', path: '/candidate/resume' },
  { label: '我的投递', path: '/candidate/applications' },
  { label: '消息中心', path: '/candidate/messages' }
]

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const activeMenu = computed(() => route.path)

function logout() {
  authStore.logout()
  router.push('/')
}
</script>

<template>
  <div class="page-shell" style="display: grid; grid-template-columns: 280px 1fr; min-height: 100vh;">
    <aside style="background: linear-gradient(180deg, #173551 0%, #24527a 100%); color: #fff; padding: 28px 20px; position: relative;">
      <div style="font-size: 24px; font-weight: 800;">候选人中心</div>
      <div style="margin-top: 8px; color: rgba(255,255,255,0.75);">面向求职者的简历与投递工作台</div>
      <el-menu :default-active="activeMenu" router background-color="transparent" text-color="rgba(255,255,255,0.82)" active-text-color="#fff" style="margin-top: 32px; border-right: none;">
        <el-menu-item v-for="item in menu" :key="item.path" :index="item.path">{{ item.label }}</el-menu-item>
      </el-menu>
      <div style="position: absolute; bottom: 24px; left: 20px; right: 20px;">
        <div style="margin-bottom: 12px; color: rgba(255,255,255,0.75);">当前用户：{{ authStore.user?.name }}</div>
        <el-button @click="logout">退出体验</el-button>
      </div>
    </aside>
    <section style="padding: 28px;">
      <router-view />
    </section>
  </div>
</template>
