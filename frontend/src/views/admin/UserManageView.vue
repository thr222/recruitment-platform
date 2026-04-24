<script setup lang="ts">
import { computed, ref } from 'vue'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

type UserStatus = 'active' | 'pending' | 'disabled'
type UserRole = '求职者' | '招聘者' | '管理员'

interface UserItem {
  id: number
  name: string
  role: UserRole
  status: UserStatus
  city: string
  company: string
  lastLogin: string
  source: string
}

const keyword = ref('')
const roleFilter = ref('')
const statusFilter = ref('')

const users = ref<UserItem[]>([
  { id: 1, name: '林浩', role: '求职者', status: 'active', city: '上海', company: '个人用户', lastLogin: '10 分钟前', source: 'PC Web' },
  { id: 2, name: '周婷', role: '招聘者', status: 'active', city: '杭州', company: '星河科技', lastLogin: '2 小时前', source: 'H5' },
  { id: 3, name: '李晨', role: '求职者', status: 'pending', city: '北京', company: '个人用户', lastLogin: '昨天', source: 'APP' },
  { id: 4, name: '王妍', role: '管理员', status: 'active', city: '深圳', company: '平台运营', lastLogin: '5 分钟前', source: '后台' },
  { id: 5, name: '赵航', role: '招聘者', status: 'disabled', city: '广州', company: '云图人才', lastLogin: '3 天前', source: 'PC Web' },
  { id: 6, name: '陈雪', role: '求职者', status: 'active', city: '成都', company: '个人用户', lastLogin: '1 小时前', source: '小程序' },
])

const statusMeta: Record<UserStatus, { text: string; type: 'success' | 'warning' | 'danger' | 'info' }> = {
  active: { text: '正常', type: 'success' },
  pending: { text: '待激活', type: 'warning' },
  disabled: { text: '已停用', type: 'danger' },
}

const roleOptions = ['求职者', '招聘者', '管理员']
const statusOptions = [
  { label: '正常', value: 'active' },
  { label: '待激活', value: 'pending' },
  { label: '停用', value: 'disabled' },
]

const getStatusMeta = (status: UserStatus) => statusMeta[status]

const filteredUsers = computed(() => {
  const q = keyword.value.trim().toLowerCase()
  return users.value.filter((user) => {
    const matchedKeyword =
      !q ||
      [user.name, user.role, user.city, user.company, user.source].some((field) =>
        field.toLowerCase().includes(q),
      )
    const matchedRole = !roleFilter.value || user.role === roleFilter.value
    const matchedStatus = !statusFilter.value || user.status === statusFilter.value
    return matchedKeyword && matchedRole && matchedStatus
  })
})

const stats = computed(() => [
  { label: '活跃账号', value: users.value.filter((item) => item.status === 'active').length, hint: '近 7 日登录正常' },
  { label: '待处理账号', value: users.value.filter((item) => item.status === 'pending').length, hint: '需补全资料或激活' },
  { label: '招聘者', value: users.value.filter((item) => item.role === '招聘者').length, hint: '企业账号与岗位发布者' },
  { label: '管理员', value: users.value.filter((item) => item.role === '管理员').length, hint: '平台治理与审查角色' },
])

const resetFilters = () => {
  keyword.value = ''
  roleFilter.value = ''
  statusFilter.value = ''
}
</script>

<template>
  <section class="admin-page">
    <AppSectionTitle
      eyebrow="用户管理"
      title="平台账号治理"
      description="统一管理求职者、招聘者和管理员账号，支持关键词检索、角色筛选和状态治理。"
    />

    <div class="stat-grid">
      <el-card v-for="item in stats" :key="item.label" shadow="never" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
        <div class="stat-hint">{{ item.hint }}</div>
      </el-card>
    </div>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <div>
            <div class="panel-title">筛选与搜索</div>
            <div class="panel-desc">可快速定位异常账号或特定角色用户。</div>
          </div>
          <el-button text type="primary" @click="resetFilters">清空条件</el-button>
        </div>
      </template>

      <div class="filter-grid">
        <el-input v-model="keyword" clearable placeholder="搜索姓名 / 公司 / 城市 / 来源" />
        <el-select v-model="roleFilter" clearable placeholder="角色筛选">
          <el-option v-for="item in roleOptions" :key="item" :label="item" :value="item" />
        </el-select>
        <el-select v-model="statusFilter" clearable placeholder="状态筛选">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </div>
    </el-card>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <div>
            <div class="panel-title">用户列表</div>
            <div class="panel-desc">共 {{ filteredUsers.length }} 条账号记录。</div>
          </div>
          <el-tag effect="light" type="success">本地 mock 数据</el-tag>
        </div>
      </template>

      <el-table :data="filteredUsers" stripe>
        <el-table-column label="用户" min-width="220">
          <template #default="{ row }">
            <div class="user-cell">
              <el-avatar :size="36" class="user-avatar">{{ row.name.slice(0, 1) }}</el-avatar>
              <div>
                <div class="user-name">{{ row.name }}</div>
                <div class="user-meta">{{ row.city }} · {{ row.source }}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="角色" width="120" />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusMeta(row.status).type" effect="light">{{ getStatusMeta(row.status).text }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="company" label="所属企业 / 归属" min-width="170" />
        <el-table-column prop="lastLogin" label="最近登录" width="130" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default>
            <el-button link type="primary">查看</el-button>
            <el-button link type="warning">停用</el-button>
            <el-button link type="danger">重置密码</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </section>
</template>

<style scoped>
.admin-page {
  display: grid;
  gap: 24px;
}

.stat-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.stat-card,
.panel-card {
  border: none;
  border-radius: 22px;
  box-shadow: var(--brand-shadow-soft);
  background: var(--brand-card);
}

.stat-label,
.panel-title,
.user-name {
  color: var(--brand-title);
  font-weight: 700;
}

.stat-value {
  margin-top: 10px;
  font-size: 32px;
  font-weight: 800;
  color: var(--brand-primary-deep);
}

.stat-hint,
.panel-desc,
.user-meta {
  margin-top: 8px;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.filter-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  background: var(--brand-primary-deep);
  color: #fff;
  font-weight: 700;
}

@media (max-width: 1200px) {
  .stat-grid,
  .filter-grid {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 720px) {
  .stat-grid,
  .filter-grid {
    grid-template-columns: 1fr;
  }
}
</style>
