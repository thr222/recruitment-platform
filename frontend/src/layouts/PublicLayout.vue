<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const navItems = [
  { label: 'Jobs', path: '/jobs' },
  { label: 'Companies', path: '/companies' }
]

const isLoginPage = computed(() => route.path === '/login')

function goHome() {
  router.push('/')
}

function goLogin() {
  router.push('/login')
}

function goRegister() {
  router.push('/login')
}

function goCTA() {
  router.push('/jobs')
}

function isActive(path: string) {
  return route.path === path
}
</script>

<template>
  <div class="page-shell public-shell">
    <header class="public-header">
      <div class="page-container header-inner">
        <button class="brand" type="button" @click="goHome">
          <span class="brand-mark">H</span>
          <span class="brand-copy">
            <strong>HireHub</strong>
            <small>Smart hiring platform</small>
          </span>
        </button>

        <nav class="header-nav" aria-label="Primary">
          <button
            v-for="item in navItems"
            :key="item.path"
            class="nav-item"
            :class="{ 'nav-item--active': isActive(item.path) }"
            type="button"
            @click="$router.push(item.path)"
          >
            {{ item.label }}
          </button>
        </nav>

        <div class="header-actions">
          <button v-if="!isLoginPage" class="auth-link" type="button" @click="goLogin">登录</button>
          <button v-if="!isLoginPage" class="auth-secondary" type="button" @click="goRegister">注册</button>
          <button class="auth-primary" type="button" @click="goCTA">立即体验</button>
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
  background: rgba(251, 248, 242, 0.84);
  border-bottom: 1px solid rgba(17, 24, 39, 0.06);
}

.header-inner {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 24px;
  min-height: 82px;
}

.brand {
  display: inline-flex;
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
  background: var(--brand-title);
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
  justify-content: center;
  gap: 28px;
}

.nav-item {
  position: relative;
  border: none;
  background: transparent;
  color: var(--brand-ink-soft);
  font-size: 15px;
  font-weight: 600;
  padding: 28px 0 24px;
  cursor: pointer;
  transition: color 0.2s ease;
}

.nav-item::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  bottom: 10px;
  height: 3px;
  border-radius: 999px;
  background: var(--brand-primary);
  opacity: 0;
  transform: scaleX(0.35);
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.nav-item:hover,
.nav-item--active {
  color: var(--brand-primary-deep);
}

.nav-item--active::after {
  opacity: 1;
  transform: scaleX(1);
}

.header-actions {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 12px;
}

.auth-link,
.auth-secondary,
.auth-primary {
  border: none;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, background 0.2s ease, color 0.2s ease;
}

.auth-link {
  padding: 10px 8px;
  background: transparent;
  color: var(--brand-title);
}

.auth-link:hover {
  color: var(--brand-primary-deep);
}

.auth-secondary {
  padding: 11px 18px;
  border: 1px solid rgba(17, 24, 39, 0.08);
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.74);
  color: var(--brand-title);
}

.auth-secondary:hover {
  transform: translateY(-1px);
  box-shadow: 0 10px 22px rgba(17, 24, 39, 0.06);
}

.auth-primary {
  padding: 12px 18px;
  border-radius: 999px;
  background: var(--brand-primary);
  color: #fff;
  box-shadow: 0 12px 24px rgba(31, 107, 87, 0.18);
}

.auth-primary:hover {
  transform: translateY(-1px);
  background: var(--brand-primary-deep);
}

.public-main {
  padding-top: 28px;
  padding-bottom: 48px;
}

@media (max-width: 960px) {
  .header-inner {
    grid-template-columns: 1fr;
    justify-items: start;
    min-height: auto;
    padding: 14px 0;
  }

  .header-nav {
    width: 100%;
    justify-content: flex-start;
    gap: 20px;
    overflow-x: auto;
  }

  .header-actions {
    width: 100%;
    justify-content: flex-start;
    flex-wrap: wrap;
  }

  .nav-item {
    padding: 14px 0 18px;
  }
}

@media (max-width: 640px) {
  .brand-copy small {
    display: none;
  }

  .header-actions {
    gap: 10px;
  }

  .auth-secondary,
  .auth-primary {
    padding-inline: 16px;
  }
}
</style>
