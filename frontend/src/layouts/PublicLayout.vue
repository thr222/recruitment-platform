<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const navItems = [
  { label: '首页', path: '/' },
  { label: '找工作', path: '/jobs' },
  { label: '找公司', path: '/companies' }
]

const isLoginPage = computed(() => route.path === '/login')

function goLogin() {
  router.push('/login')
}

function isActive(path: string) {
  return route.path === path
}
</script>

<template>
  <div class="page-shell public-shell">
    <header class="public-header">
      <div class="page-container header-inner">
        <button class="brand" @click="$router.push('/')">
          <span class="brand-mark">H</span>
          <span class="brand-copy">
            <strong>HireHub</strong>
            <small>直聊式招聘平台</small>
          </span>
        </button>

        <nav class="header-nav">
          <button
            v-for="item in navItems"
            :key="item.path"
            class="nav-item"
            :class="{ 'nav-item--active': isActive(item.path) }"
            @click="$router.push(item.path)"
          >
            {{ item.label }}
          </button>
        </nav>

        <div class="header-actions">
          <div class="hotline">
            <span class="section-chip">面试项目演示版</span>
          </div>
          <el-button v-if="!isLoginPage" type="primary" round @click="goLogin">登录 / 体验</el-button>
        </div>
      </div>
    </header>

    <main class="page-container public-main">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.public-shell {
  position: relative;
  overflow-x: hidden;
}

.public-header {
  position: sticky;
  top: 0;
  z-index: 20;
  backdrop-filter: blur(18px);
  background: rgba(247, 251, 253, 0.78);
  border-bottom: 1px solid rgba(15, 46, 77, 0.08);
}

.header-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  min-height: 78px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  border: none;
  background: transparent;
  cursor: pointer;
}

.brand-mark {
  display: inline-grid;
  place-items: center;
  width: 44px;
  height: 44px;
  border-radius: 14px;
  background: linear-gradient(135deg, var(--brand-primary), var(--brand-primary-deep));
  color: #fff;
  font-size: 22px;
  font-weight: 800;
}

.brand-copy {
  display: grid;
  text-align: left;
}

.brand-copy strong {
  color: var(--brand-title);
  font-size: 20px;
  line-height: 1.1;
}

.brand-copy small {
  color: var(--brand-ink-soft);
  font-size: 12px;
  margin-top: 4px;
}

.header-nav {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-item {
  border: none;
  background: transparent;
  color: var(--brand-ink-soft);
  font-size: 15px;
  font-weight: 700;
  padding: 10px 14px;
  border-radius: 999px;
  cursor: pointer;
  transition: background 0.2s ease, color 0.2s ease;
}

.nav-item:hover,
.nav-item--active {
  background: rgba(0, 166, 166, 0.1);
  color: var(--brand-primary-deep);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.public-main {
  padding-top: 28px;
  padding-bottom: 48px;
}

@media (max-width: 960px) {
  .header-inner {
    min-height: auto;
    padding: 14px 0;
    flex-wrap: wrap;
  }

  .header-nav {
    order: 3;
    width: 100%;
    overflow-x: auto;
  }

  .header-actions {
    margin-left: auto;
  }
}
</style>
