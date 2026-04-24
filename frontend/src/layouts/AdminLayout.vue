<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const menu = [
  { label: '运营看板', path: '/admin/dashboard' },
  { label: '用户管理', path: '/admin/users' },
  { label: '企业审核', path: '/admin/company-audit' },
  { label: '职位审核', path: '/admin/job-audit' },
  { label: '举报处理', path: '/admin/reports' },
  { label: '运营配置', path: '/admin/operation-config' },
  { label: '字典配置', path: '/admin/dict' },
  { label: '权限管理', path: '/admin/permissions' }
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
    <aside style="background: linear-gradient(180deg, #202939 0%, #38475e 100%); color: #fff; padding: 28px 20px; position: relative;">
      <div style="font-size: 24px; font-weight: 800;">平台管理后台</div>
      <div style="margin-top: 8px; color: rgba(255,255,255,0.75);">面向运营与审核人员的治理控制台</div>
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
