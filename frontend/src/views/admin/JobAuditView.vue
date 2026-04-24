<script setup lang="ts">
import { computed, ref } from 'vue'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

type JobStatus = '待审核' | '已通过' | '违规下架'
type JobScope = '热门' | '急招' | '常规'

interface JobItem {
  id: number
  title: string
  company: string
  city: string
  salary: string
  experience: string
  status: JobStatus
  scope: JobScope
  tags: string[]
  reason: string
}

const activeTab = ref('all')
const detailVisible = ref(false)
const selectedJob = ref<JobItem | null>(null)

const jobs = ref<JobItem[]>([
  {
    id: 1,
    title: 'Java 后端开发工程师',
    company: '星河科技',
    city: '上海',
    salary: '25K-40K',
    experience: '3-5 年',
    status: '待审核',
    scope: '急招',
    tags: ['Spring Boot', '分布式', '高并发'],
    reason: '岗位描述完整，但薪资与职责需二次核验。',
  },
  {
    id: 2,
    title: '产品经理',
    company: '青木数据',
    city: '北京',
    salary: '18K-28K',
    experience: '2-4 年',
    status: '已通过',
    scope: '常规',
    tags: ['数据平台', '用户研究', '增长'],
    reason: '历史发布记录正常，岗位信息合规。',
  },
  {
    id: 3,
    title: '商务拓展经理',
    company: '云图人才',
    city: '广州',
    salary: '面议',
    experience: '1-3 年',
    status: '违规下架',
    scope: '热门',
    tags: ['ToB', '销售', '渠道'],
    reason: '涉嫌重复发布与薪资描述不清，已下架。',
  },
])

const statusMap: Record<JobStatus, 'warning' | 'success' | 'danger'> = {
  待审核: 'warning',
  已通过: 'success',
  违规下架: 'danger',
}

const getStatusType = (status: JobStatus) => statusMap[status]

const filteredJobs = computed(() => {
  if (activeTab.value === 'all') return jobs.value
  return jobs.value.filter((item) => item.status === activeTab.value)
})

const stats = computed(() => [
  { label: '待审核', value: jobs.value.filter((item) => item.status === '待审核').length, type: 'warning' as const },
  { label: '已通过', value: jobs.value.filter((item) => item.status === '已通过').length, type: 'success' as const },
  { label: '违规下架', value: jobs.value.filter((item) => item.status === '违规下架').length, type: 'danger' as const },
  { label: '岗位总数', value: jobs.value.length, type: 'primary' as const },
])

const openDetail = (item: JobItem) => {
  selectedJob.value = item
  detailVisible.value = true
}
</script>

<template>
  <section class="admin-page">
    <AppSectionTitle
      eyebrow="职位审核"
      title="职位内容与合规审核"
      description="管理员可以快速查看职位的审核状态、薪资范围、技能标签和违规处理原因。"
    />

    <div class="stat-grid">
      <el-card v-for="item in stats" :key="item.label" shadow="never" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
        <el-tag :type="item.type" effect="light">{{ item.label }}</el-tag>
      </el-card>
    </div>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <div>
            <div class="panel-title">审核队列</div>
            <div class="panel-desc">支持按审核状态快速切换，统一查看岗位处理进展。</div>
          </div>
          <el-tag effect="light" type="info">本地 mock 数据</el-tag>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="待审核" name="待审核" />
        <el-tab-pane label="已通过" name="已通过" />
        <el-tab-pane label="违规下架" name="违规下架" />
      </el-tabs>

      <div class="job-list">
        <div v-for="item in filteredJobs" :key="item.id" class="job-item">
          <div class="job-main">
            <div class="job-title-row">
              <div class="job-title">{{ item.title }}</div>
              <el-tag :type="getStatusType(item.status)" effect="light">{{ item.status }}</el-tag>
            </div>
            <div class="job-meta">{{ item.company }} · {{ item.city }} · {{ item.experience }}</div>
            <div class="tag-row">
              <el-tag effect="light" type="success">{{ item.salary }}</el-tag>
              <el-tag v-for="tag in item.tags" :key="tag" effect="plain">{{ tag }}</el-tag>
            </div>
            <div class="job-reason">{{ item.reason }}</div>
          </div>
          <div class="job-actions">
            <el-tag effect="light" type="info">{{ item.scope }}</el-tag>
            <el-button text type="primary" @click="openDetail(item)">查看详情</el-button>
            <el-button text type="success">通过</el-button>
            <el-button text type="danger">下架</el-button>
          </div>
        </div>
      </div>
    </el-card>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <span>职位审核说明</span>
          <el-tag effect="light" type="warning">审核规范</el-tag>
        </div>
      </template>

      <el-descriptions :column="3" border>
        <el-descriptions-item label="薪资真实性">检查区间是否与岗位职责匹配。</el-descriptions-item>
        <el-descriptions-item label="岗位重复度">识别重复岗位发布与批量灌水。</el-descriptions-item>
        <el-descriptions-item label="内容合规">过滤违规词、夸大宣传和主体不明确内容。</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-drawer v-model="detailVisible" title="职位审核详情" size="480px">
      <div v-if="selectedJob" class="drawer-body">
        <div class="drawer-title">{{ selectedJob.title }}</div>
        <div class="drawer-meta">{{ selectedJob.company }} · {{ selectedJob.city }} · {{ selectedJob.experience }}</div>
        <div class="drawer-block">
          <div class="drawer-label">薪资</div>
          <div class="drawer-value">{{ selectedJob.salary }}</div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">技能标签</div>
          <div class="tag-row">
            <el-tag v-for="tag in selectedJob.tags" :key="tag" effect="plain">{{ tag }}</el-tag>
          </div>
        </div>
        <div class="drawer-block">
          <div class="drawer-label">处理意见</div>
          <div class="drawer-value">{{ selectedJob.reason }}</div>
        </div>
      </div>
    </el-drawer>
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
.job-title,
.drawer-title,
.drawer-label {
  color: var(--brand-title);
  font-weight: 700;
}

.stat-value {
  margin: 12px 0 16px;
  color: var(--brand-primary-deep);
  font-size: 30px;
  font-weight: 800;
}

.panel-header,
.job-title-row,
.job-actions {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.panel-desc,
.job-meta,
.job-reason,
.drawer-meta,
.drawer-value {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.job-list {
  display: grid;
  gap: 16px;
}

.job-item {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  padding: 18px 0;
  border-bottom: 1px solid rgba(148, 163, 184, 0.12);
}

.job-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.job-main {
  display: grid;
  gap: 10px;
  flex: 1;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.job-actions {
  flex-wrap: wrap;
  justify-content: flex-end;
  align-content: flex-start;
}

.drawer-body {
  display: grid;
  gap: 18px;
}

.drawer-block {
  display: grid;
  gap: 8px;
  padding: 16px;
  border-radius: 18px;
  background: rgba(244, 247, 252, 0.9);
}

@media (max-width: 1200px) {
  .stat-grid {
    grid-template-columns: 1fr 1fr;
  }

  .job-item {
    flex-direction: column;
  }
}

@media (max-width: 560px) {
  .stat-grid {
    grid-template-columns: 1fr;
  }
}
</style>
