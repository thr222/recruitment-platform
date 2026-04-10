<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const demoAccounts = [
  { role: '求职者', username: 'candidate_demo', password: '123456' },
  { role: '招聘者', username: 'recruiter_demo', password: '123456' },
  { role: '管理员', username: 'admin_demo', password: '123456' }
]

async function submit() {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }

  loading.value = true
  try {
    await authStore.loginByPassword({
      username: form.username,
      password: form.password
    })
    const target =
      authStore.role === 'candidate'
        ? '/candidate/dashboard'
        : authStore.role === 'recruiter'
          ? '/recruiter/dashboard'
          : '/admin/dashboard'
    router.push(target)
  } catch {
    ElMessage.error('登录失败，请确认账号、密码和后端服务')
  } finally {
    loading.value = false
  }
}

function useDemo(username: string, password: string) {
  form.username = username
  form.password = password
}
</script>

<template>
  <section class="login-page">
    <div class="login-wrap">
      <div class="login-side">
        <div class="section-chip">职业平台</div>
        <h1>登录</h1>
        <div class="demo-list">
          <button
            v-for="item in demoAccounts"
            :key="item.role"
            class="demo-item"
            @click="useDemo(item.username, item.password)"
          >
            <span>{{ item.role }}</span>
            <strong>{{ item.username }}</strong>
          </button>
        </div>
      </div>

      <div class="panel-card login-panel">
        <el-form label-position="top" @submit.prevent="submit">
          <el-form-item label="用户名">
            <el-input v-model="form.username" size="large" placeholder="输入用户名" />
          </el-form-item>

          <el-form-item label="密码">
            <el-input
              v-model="form.password"
              size="large"
              type="password"
              placeholder="输入密码"
              show-password
              @keyup.enter="submit"
            />
          </el-form-item>

          <el-button type="primary" size="large" class="submit-btn" :loading="loading" @click="submit">
            进入系统
          </el-button>
        </el-form>
      </div>
    </div>
  </section>
</template>

<style scoped>
.login-page {
  min-height: calc(100vh - 140px);
  display: grid;
  place-items: center;
}

.login-wrap {
  display: grid;
  grid-template-columns: 0.9fr 460px;
  gap: 42px;
  align-items: center;
  width: min(1100px, 100%);
}

.login-side {
  display: grid;
  gap: 18px;
  align-content: center;
}

.login-side h1 {
  margin: 0;
  color: var(--brand-title);
  font-size: clamp(44px, 7vw, 76px);
  letter-spacing: -0.04em;
}

.demo-list {
  display: grid;
  gap: 12px;
  margin-top: 10px;
  max-width: 360px;
}

.demo-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
  padding: 16px 18px;
  border: 1px solid var(--brand-line);
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.64);
  cursor: pointer;
}

.demo-item span {
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.demo-item strong {
  color: var(--brand-title);
  font-size: 15px;
}

.login-panel {
  padding: 30px;
  max-width: 460px;
}

.submit-btn {
  width: 100%;
  min-height: 50px;
  margin-top: 8px;
  font-weight: 700;
}

@media (max-width: 960px) {
  .login-wrap {
    grid-template-columns: 1fr;
    gap: 24px;
  }

  .login-side {
    justify-items: start;
  }
}
</style>
