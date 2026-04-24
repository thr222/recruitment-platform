<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const menu = [
  { label: '招聘工作台', path: '/recruiter/dashboard' },
  { label: '企业资料', path: '/recruiter/company' },
  { label: '企业认证', path: '/recruiter/certification' },
  { label: '职位管理', path: '/recruiter/jobs' },
  { label: '候选人管理', path: '/recruiter/candidates' },
  { label: '面试邀请', path: '/recruiter/interviews' },
  { label: '沟通消息', path: '/recruiter/messages' }
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
    <aside style="background: linear-gradient(180deg, #39210d 0%, #7f4f24 100%); color: #fff; padding: 28px 20px; position: relative;">
      <div style="font-size: 24px; font-weight: 800;">招聘者中心</div>
      <div style="margin-top: 8px; color: rgba(255,255,255,0.75);">面向 HR 与企业管理员的招聘运营后台</div>
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
