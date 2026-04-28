<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'

type JobStatus = '招聘中' | '审核中' | '已下架' | '已关闭' | '草稿'

interface JobItem {
  id: number
  title: string
  salary: string
  status: JobStatus
  city: string
  experience: string
  education: string
  workType: string
  updatedAt: string
  applicantCount: number
  viewCount: number
  tags: string[]
  description: string
}

interface JobFormState {
  title: string
  salary: string
  city: string
  experience: string
  education: string
  workType: string
  status: JobStatus
  tags: string
  description: string
}

const storageKey = 'recruiter-job-management'

const defaultJobs: JobItem[] = [
  {
    id: 1,
    title: 'Java 后端开发工程师',
    salary: '25K-40K',
    status: '招聘中',
    city: '上海',
    experience: '3-5 年',
    education: '本科及以上',
    workType: '全职',
    updatedAt: '2026-04-22',
    applicantCount: 46,
    viewCount: 512,
    tags: ['Spring Boot', 'MyBatis', '高并发'],
    description: '负责招聘平台核心业务链路开发与优化，关注稳定性、性能和招聘效率。'
  },
  {
    id: 2,
    title: '前端开发工程师',
    salary: '22K-35K',
    status: '审核中',
    city: '杭州',
    experience: '2-4 年',
    education: '本科及以上',
    workType: '全职',
    updatedAt: '2026-04-21',
    applicantCount: 18,
    viewCount: 288,
    tags: ['Vue3', 'TypeScript', '招聘系统'],
    description: '负责职位卡片、筛选器和投递状态展示等前台体验建设。'
  },
  {
    id: 3,
    title: '产品经理',
    salary: '18K-28K',
    status: '已下架',
    city: '深圳',
    experience: '3-5 年',
    education: '本科及以上',
    workType: '全职',
    updatedAt: '2026-04-19',
    applicantCount: 31,
    viewCount: 210,
    tags: ['增长', '数据分析', 'SaaS'],
    description: '负责招聘产品规划、需求拆解和投递转化优化。'
  }
]

const statusFilter = ref<'全部' | JobStatus>('全部')
const keyword = ref('')
const publishDialogVisible = ref(false)
const analyticsDrawerVisible = ref(false)
const editingId = ref<number | null>(null)
const selectedJob = ref<JobItem | null>(null)

const form = reactive<JobFormState>({
  title: '',
  salary: '20K-30K',
  city: '杭州',
  experience: '3-5 年',
  education: '本科及以上',
  workType: '全职',
  status: '招聘中',
  tags: 'Vue3, TypeScript, 招聘系统',
  description: '负责招聘平台相关功能开发。'
})

const jobs = ref<JobItem[]>(readJobs())

function readJobs() {
  try {
    const raw = localStorage.getItem(storageKey)
    if (!raw) return [...defaultJobs]
    const parsed = JSON.parse(raw) as JobItem[]
    return parsed.length ? parsed : [...defaultJobs]
  } catch {
    return [...defaultJobs]
  }
}

function persistJobs(value: JobItem[]) {
  jobs.value = value
  try {
    localStorage.setItem(storageKey, JSON.stringify(value))
  } catch {
    // ignore storage errors in demo mode
  }
}

const summaryCards = computed(() => [
  { label: '职位总数', value: jobs.value.length, hint: '包含草稿和已上线职位' },
  { label: '招聘中', value: jobs.value.filter((job) => job.status === '招聘中').length, hint: '当前可见岗位' },
  { label: '待审核', value: jobs.value.filter((job) => job.status === '审核中').length, hint: '发布后等待审核' },
  { label: '本周新增投递', value: 84, hint: '较上周增长 13%' }
])

const filteredJobs = computed(() =>
  jobs.value.filter((job) => {
    const matchedKeyword =
      !keyword.value ||
      [job.title, job.city, job.experience, job.salary, job.education, job.tags.join(' ')].some((value) =>
        value.toLowerCase().includes(keyword.value.toLowerCase())
      )
    const matchedStatus = statusFilter.value === '全部' || job.status === statusFilter.value
    return matchedKeyword && matchedStatus
  })
)

function resetForm() {
  form.title = ''
  form.salary = '20K-30K'
  form.city = '杭州'
  form.experience = '3-5 年'
  form.education = '本科及以上'
  form.workType = '全职'
  form.status = '招聘中'
  form.tags = 'Vue3, TypeScript, 招聘系统'
  form.description = '负责招聘平台相关功能开发。'
}

function openCreateDialog() {
  editingId.value = null
  resetForm()
  publishDialogVisible.value = true
}

function openEditDialog(job: JobItem) {
  editingId.value = job.id
  selectedJob.value = job
  form.title = job.title
  form.salary = job.salary
  form.city = job.city
  form.experience = job.experience
  form.education = job.education
  form.workType = job.workType
  form.status = job.status
  form.tags = job.tags.join(', ')
  form.description = job.description
  publishDialogVisible.value = true
}

function saveJob() {
  if (!form.title.trim()) {
    ElMessage.warning('请先填写职位名称')
    return
  }

  const nextJob: JobItem = {
    id: editingId.value ?? Date.now(),
    title: form.title.trim(),
    salary: form.salary.trim(),
    status: form.status,
    city: form.city.trim(),
    experience: form.experience.trim(),
    education: form.education.trim(),
    workType: form.workType.trim(),
    updatedAt: '2026-04-23',
    applicantCount: selectedJob.value?.applicantCount ?? 0,
    viewCount: selectedJob.value?.viewCount ?? 0,
    tags: form.tags
      .split(',')
      .map((item) => item.trim())
      .filter(Boolean),
    description: form.description.trim()
  }

  const nextJobs = editingId.value
    ? jobs.value.map((job) => (job.id === editingId.value ? nextJob : job))
    : [nextJob, ...jobs.value]

  persistJobs(nextJobs)
  publishDialogVisible.value = false
  ElMessage.success(editingId.value ? '职位已更新' : '职位已发布到列表')
}

function changeStatus(job: JobItem, status: JobStatus) {
  persistJobs(jobs.value.map((item) => (item.id === job.id ? { ...item, status, updatedAt: '2026-04-23' } : item)))
  ElMessage.success(`已将 ${job.title} 更新为 ${status}`)
}

function openAnalytics(job: JobItem) {
  selectedJob.value = job
  analyticsDrawerVisible.value = true
}

const analytics = computed(() => {
  const job = selectedJob.value
  if (!job) {
    return {
      jobTitle: '请选择一个职位',
      visitorTrend: 0,
      applicantTrend: 0,
      conversion: '0%',
      tags: []
    }
  }

  return {
    jobTitle: job.title,
    visitorTrend: Math.round(job.viewCount * 0.18),
    applicantTrend: job.applicantCount,
    conversion: `${Math.max(8, Math.round((job.applicantCount / Math.max(job.viewCount, 1)) * 100))}%`,
    tags: job.tags
  }
})

watch(
  jobs,
  (value) => {
    try {
      localStorage.setItem(storageKey, JSON.stringify(value))
    } catch {
      // ignore
    }
  },
  { deep: true }
)
</script>

<template>
  <section class="page-shell">
    <section class="header-card panel-card">
      <div>
        <div class="section-chip">职位管理</div>
        <h1>新建、编辑、下架和关闭职位，都放在同一块管理面板里。</h1>
        <p>这里保留轻量发布入口，同时把浏览量、投递数和状态控制放在可见的位置，方便直接推进招聘闭环。</p>
      </div>
      <div class="header-actions">
        <el-button type="primary" size="large" round @click="openCreateDialog">发布职位</el-button>
        <el-button size="large" round @click="statusFilter = '全部'">查看全部</el-button>
      </div>
    </section>

    <section class="summary-grid">
      <article v-for="item in summaryCards" :key="item.label" class="summary-card panel-card">
        <div class="summary-value">{{ item.value }}</div>
        <div class="summary-label">{{ item.label }}</div>
        <div class="summary-hint">{{ item.hint }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card content-card">
        <div class="content-header">
          <div>
            <div class="section-title">职位列表</div>
            <div class="section-subtitle">每张卡片都带上编辑、下架、关闭和浏览数据入口。</div>
          </div>
          <div class="filters">
            <el-input v-model="keyword" clearable placeholder="搜索职位 / 城市 / 技能 / 学历" />
            <el-select v-model="statusFilter" style="width: 140px;">
              <el-option label="全部" value="全部" />
              <el-option label="招聘中" value="招聘中" />
              <el-option label="审核中" value="审核中" />
              <el-option label="已下架" value="已下架" />
              <el-option label="已关闭" value="已关闭" />
            </el-select>
          </div>
        </div>

        <div class="job-grid">
          <article v-for="job in filteredJobs" :key="job.id" class="job-card">
            <div class="job-card__head">
              <div class="job-card__title">
                <h3>{{ job.title }}</h3>
                <div class="job-card__company">{{ job.city }} · {{ job.workType }} · {{ job.education }}</div>
              </div>
              <div class="job-card__salary">{{ job.salary }}</div>
            </div>

            <div class="job-card__meta">
              <span>{{ job.experience }}</span>
              <span>{{ job.updatedAt }}</span>
              <span>{{ job.applicantCount }} 份投递</span>
              <span>{{ job.viewCount }} 次浏览</span>
            </div>

            <div class="job-card__tags">
              <el-tag v-for="tag in job.tags" :key="tag" effect="light" round>{{ tag }}</el-tag>
              <el-tag :type="job.status === '招聘中' ? 'success' : job.status === '审核中' ? 'warning' : 'info'" effect="light" round>
                {{ job.status }}
              </el-tag>
            </div>

            <p class="job-card__desc">{{ job.description }}</p>

            <div class="job-card__actions">
              <el-button link type="primary" @click="openEditDialog(job)">编辑职位</el-button>
              <el-button link @click="changeStatus(job, '已下架')">下架职位</el-button>
              <el-button link @click="changeStatus(job, '已关闭')">关闭职位</el-button>
              <el-button link @click="openAnalytics(job)">查看浏览数据</el-button>
            </div>
          </article>
        </div>
      </div>

      <div class="side-column">
        <div class="panel-card side-card">
          <div class="section-title">快速发布</div>
          <div class="section-subtitle">先留一个轻量表单，发布时直接补齐核心信息。</div>

          <div class="publish-form">
            <el-button type="primary" round @click="openCreateDialog">新建职位</el-button>
            <el-button round @click="openCreateDialog">复制现有模板</el-button>
          </div>
        </div>

        <div class="panel-card side-card">
          <div class="section-title">状态提示</div>
          <div class="tips-list">
            <div class="tip-item">
              <strong>招聘中</strong>
              <p>对外可见，候选人可以正常投递。</p>
            </div>
            <div class="tip-item">
              <strong>审核中</strong>
              <p>发布后等待审核，适合核对岗位描述。</p>
            </div>
            <div class="tip-item">
              <strong>关闭 / 下架</strong>
              <p>不再接受新投递，但保留历史数据。</p>
            </div>
          </div>
        </div>
      </div>
    </section>
  </section>

  <el-dialog v-model="publishDialogVisible" :title="editingId ? '编辑职位' : '发布职位'" width="620px">
    <el-form label-position="top">
      <el-form-item label="职位名称">
        <el-input v-model="form.title" placeholder="例如：Vue 前端开发工程师" />
      </el-form-item>
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="薪资范围">
            <el-input v-model="form.salary" placeholder="例如：20K-35K" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="城市">
            <el-input v-model="form.city" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="工作经验">
            <el-input v-model="form.experience" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学历要求">
            <el-input v-model="form.education" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="工作性质">
            <el-input v-model="form.workType" placeholder="全职 / 实习" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="职位状态">
            <el-select v-model="form.status">
              <el-option label="招聘中" value="招聘中" />
              <el-option label="审核中" value="审核中" />
              <el-option label="草稿" value="草稿" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="技能标签">
        <el-input v-model="form.tags" placeholder="用英文逗号分隔，例如：Vue3, TypeScript, 招聘系统" />
      </el-form-item>
      <el-form-item label="职位描述">
        <el-input v-model="form.description" type="textarea" :rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="publishDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="saveJob">保存</el-button>
    </template>
  </el-dialog>

  <el-drawer v-model="analyticsDrawerVisible" title="职位浏览数据" size="420px">
    <div class="drawer-card">
      <div class="drawer-title">{{ analytics.jobTitle }}</div>
      <div class="drawer-metric">
        <span>浏览量</span>
        <strong>{{ selectedJob?.viewCount ?? 0 }}</strong>
      </div>
      <div class="drawer-metric">
        <span>今日新增浏览</span>
        <strong>{{ analytics.visitorTrend }}</strong>
      </div>
      <div class="drawer-metric">
        <span>投递量</span>
        <strong>{{ analytics.applicantTrend }}</strong>
      </div>
      <div class="drawer-metric">
        <span>浏览转投递</span>
        <strong>{{ analytics.conversion }}</strong>
      </div>

      <div class="drawer-tags">
        <el-tag v-for="tag in analytics.tags" :key="tag" effect="light" round>{{ tag }}</el-tag>
      </div>
    </div>
  </el-drawer>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card,
.section-card {
  padding: 26px;
}

.header-card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
}

.header-card h1 {
  margin: 10px 0 8px;
  color: var(--brand-title);
  font-size: 28px;
  line-height: 1.2;
}

.header-card p {
  margin: 0;
  color: var(--brand-ink-soft);
  max-width: 660px;
}

.header-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.summary-card {
  padding: 20px;
}

.summary-value {
  color: var(--brand-title);
  font-size: 32px;
  font-weight: 800;
}

.summary-label {
  margin-top: 6px;
  color: var(--brand-ink);
  font-weight: 700;
}

.summary-hint {
  margin-top: 8px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.5fr) minmax(320px, 0.7fr);
  gap: 16px;
}

.content-card,
.side-card {
  padding: 24px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  margin-bottom: 16px;
}

.section-title {
  color: var(--brand-title);
  font-size: 22px;
  font-weight: 800;
}

.section-subtitle {
  margin-top: 6px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.filters {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  align-items: center;
}

.job-grid {
  display: grid;
  gap: 16px;
}

.job-card {
  padding: 20px 22px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.74);
  border: 1px solid rgba(17, 24, 39, 0.06);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.job-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 18px 34px rgba(20, 35, 45, 0.1);
}

.job-card__head {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: flex-start;
}

.job-card__title h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 24px;
  line-height: 1.3;
}

.job-card__company {
  margin-top: 8px;
  color: var(--brand-ink-soft);
}

.job-card__salary {
  color: var(--brand-primary);
  font-size: 28px;
  font-weight: 800;
  flex-shrink: 0;
}

.job-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 14px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.job-card__tags,
.drawer-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.job-card__tags {
  margin-top: 14px;
}

.job-card__desc {
  margin: 16px 0 0;
  color: var(--brand-ink);
  line-height: 1.7;
}

.job-card__actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 16px;
}

.side-column {
  display: grid;
  gap: 16px;
}

.publish-form,
.tips-list {
  display: grid;
  gap: 12px;
  margin-top: 16px;
}

.tip-item {
  padding: 14px;
  border-radius: 16px;
  background: rgba(10, 102, 194, 0.06);
}

.tip-item strong {
  color: var(--brand-title);
}

.tip-item p {
  margin: 8px 0 0;
  color: var(--brand-ink-soft);
  font-size: 13px;
  line-height: 1.6;
}

.drawer-card {
  display: grid;
  gap: 16px;
}

.drawer-title {
  color: var(--brand-title);
  font-size: 24px;
  font-weight: 800;
}

.drawer-metric {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  padding: 14px 16px;
  border-radius: 16px;
  background: rgba(15, 46, 77, 0.05);
}

.drawer-metric span {
  color: var(--brand-ink-soft);
}

.drawer-metric strong {
  color: var(--brand-title);
}

@media (max-width: 1200px) {
  .header-card,
  .content-grid {
    grid-template-columns: 1fr;
    display: grid;
  }

  .summary-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .summary-grid {
    grid-template-columns: 1fr;
  }

  .job-card__head {
    flex-direction: column;
  }

  .job-card__salary {
    font-size: 24px;
  }
}
</style>
