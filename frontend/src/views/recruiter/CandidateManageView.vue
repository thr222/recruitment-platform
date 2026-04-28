<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'

type CandidateStatus = '已投递' | '已查看' | '初筛通过' | '面试中' | '已录用' | '已拒绝' | '待沟通'

interface StatusHistoryItem {
  status: CandidateStatus
  at: string
  note: string
}

interface CandidateItem {
  id: number
  name: string
  jobTitle: string
  city: string
  experience: string
  education: string
  appliedAt: string
  status: CandidateStatus
  resumeSummary: string
  note: string
  matchRate: number
  tags: string[]
  history: StatusHistoryItem[]
}

interface InterviewFormState {
  time: string
  interviewer: string
  type: string
}

const storageKey = 'recruiter-candidate-applications'

const defaultCandidates: CandidateItem[] = [
  {
    id: 1,
    name: '王同学',
    jobTitle: 'Java 后端开发工程师',
    city: '上海',
    experience: '3-5 年',
    education: '本科',
    appliedAt: '2026-04-23 09:24',
    status: '已投递',
    resumeSummary: '3 年电商平台经验，熟悉 Spring Boot、MyBatis、Redis 和接口优化。',
    note: '简历关键词命中率高，建议优先处理。',
    matchRate: 92,
    tags: ['Spring Boot', 'MyBatis', '电商'],
    history: [
      { status: '已投递', at: '2026-04-23 09:24', note: '候选人完成职位投递' }
    ]
  },
  {
    id: 2,
    name: '陈同学',
    jobTitle: '产品经理',
    city: '深圳',
    experience: '2-4 年',
    education: '本科',
    appliedAt: '2026-04-23 08:40',
    status: '已查看',
    resumeSummary: '做过 SaaS 产品与增长功能，擅长需求拆解和跨团队推进。',
    note: '适合尽快沟通业务理解和推进节奏。',
    matchRate: 86,
    tags: ['SaaS', '增长', '数据分析'],
    history: [
      { status: '已投递', at: '2026-04-23 08:40', note: '候选人完成职位投递' },
      { status: '已查看', at: '2026-04-23 10:10', note: 'HR 已查看简历' }
    ]
  },
  {
    id: 3,
    name: '李同学',
    jobTitle: '测试开发工程师',
    city: '杭州',
    experience: '1-3 年',
    education: '本科',
    appliedAt: '2026-04-22 16:15',
    status: '初筛通过',
    resumeSummary: '熟悉接口自动化、质量保障流程和持续集成测试。',
    note: '技术栈匹配度不错，可以安排进一步沟通。',
    matchRate: 78,
    tags: ['自动化测试', '接口测试', '质量保障'],
    history: [
      { status: '已投递', at: '2026-04-22 16:15', note: '候选人完成职位投递' },
      { status: '已查看', at: '2026-04-22 18:00', note: 'HR 已查看简历' },
      { status: '初筛通过', at: '2026-04-23 09:05', note: '通过初筛，进入下一轮' }
    ]
  },
  {
    id: 4,
    name: '赵同学',
    jobTitle: '前端开发工程师',
    city: '北京',
    experience: '3-5 年',
    education: '本科',
    appliedAt: '2026-04-22 14:50',
    status: '面试中',
    resumeSummary: 'Vue3、工程化和组件库经历完整，做过招聘系统前台。',
    note: '已发面试邀请，等待候选人确认时间。',
    matchRate: 95,
    tags: ['Vue3', 'TypeScript', '组件库'],
    history: [
      { status: '已投递', at: '2026-04-22 14:50', note: '候选人完成职位投递' },
      { status: '已查看', at: '2026-04-22 15:20', note: 'HR 已查看简历' },
      { status: '初筛通过', at: '2026-04-22 16:00', note: '通过初筛，进入下一轮' },
      { status: '面试中', at: '2026-04-23 09:30', note: '已发送面试邀请' }
    ]
  }
]

const keyword = ref('')
const statusFilter = ref<'全部' | CandidateStatus>('全部')
const jobFilter = ref('全部职位')
const selectedCandidateId = ref<number>(1)
const inviteDialogVisible = ref(false)

const interviewForm = reactive<InterviewFormState>({
  time: '2026-04-24 10:00',
  interviewer: '张经理',
  type: '一面'
})

const candidates = ref<CandidateItem[]>(readCandidates())

function readCandidates() {
  try {
    const raw = localStorage.getItem(storageKey)
    if (!raw) return [...defaultCandidates]
    const parsed = JSON.parse(raw) as CandidateItem[]
    return parsed.length ? parsed : [...defaultCandidates]
  } catch {
    return [...defaultCandidates]
  }
}

function persistCandidates(value: CandidateItem[]) {
  candidates.value = value
  try {
    localStorage.setItem(storageKey, JSON.stringify(value))
  } catch {
    // ignore storage errors in demo mode
  }
}

const jobOptions = computed(() => ['全部职位', ...new Set(candidates.value.map((item) => item.jobTitle))])

const stageSummary = computed(() => [
  { label: '已投递', value: candidates.value.filter((item) => item.status === '已投递').length },
  { label: '已查看', value: candidates.value.filter((item) => item.status === '已查看').length },
  { label: '初筛通过', value: candidates.value.filter((item) => item.status === '初筛通过').length },
  { label: '面试中', value: candidates.value.filter((item) => item.status === '面试中').length }
])

const filteredCandidates = computed(() =>
  candidates.value.filter((item) => {
    const matchedKeyword =
      !keyword.value ||
      [item.name, item.jobTitle, item.city, item.experience, item.education, item.resumeSummary, item.note].some((value) =>
        value.toLowerCase().includes(keyword.value.toLowerCase())
      )
    const matchedStatus = statusFilter.value === '全部' || item.status === statusFilter.value
    const matchedJob = jobFilter.value === '全部职位' || item.jobTitle === jobFilter.value
    return matchedKeyword && matchedStatus && matchedJob
  })
)

const selectedCandidate = computed(() => {
  return candidates.value.find((item) => item.id === selectedCandidateId.value) ?? candidates.value[0]
})

function updateCandidate(candidateId: number, nextStatus: CandidateStatus, note: string) {
  const now = '2026-04-23 10:30'
  persistCandidates(
    candidates.value.map((item) =>
      item.id === candidateId
        ? {
            ...item,
            status: nextStatus,
            history: [...item.history, { status: nextStatus, at: now, note }]
          }
        : item
    )
  )
  selectedCandidateId.value = candidateId
}

function markViewed(candidateId: number) {
  updateCandidate(candidateId, '已查看', 'HR 已查看简历')
  ElMessage.success('已标记为已查看')
}

function markUnfit(candidateId: number) {
  updateCandidate(candidateId, '已拒绝', '不符合当前岗位要求')
  ElMessage.success('已标记为不合适')
}

function markCommunicate(candidateId: number) {
  updateCandidate(candidateId, '待沟通', '等待继续沟通')
  ElMessage.success('已标记为待沟通')
}

function openInviteDialog(candidateId: number) {
  selectedCandidateId.value = candidateId
  inviteDialogVisible.value = true
}

function confirmInvite() {
  if (!selectedCandidate.value) return
  updateCandidate(selectedCandidate.value.id, '面试中', `已发出 ${interviewForm.type} 邀请，面试时间 ${interviewForm.time}`)
  inviteDialogVisible.value = false
  ElMessage.success('面试邀请已发送')
}

function selectCandidate(candidateId: number) {
  selectedCandidateId.value = candidateId
}

watch(
  candidates,
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
        <div class="section-chip">候选人管理</div>
        <h1>按职位维度查看投递、筛选状态和后续动作，推进招聘闭环。</h1>
        <p>这里适合 HR 快速处理投递、标记状态、安排面试，并保持每个候选人的流转状态清晰可见。</p>
      </div>
      <div class="header-meta">
        <el-tag type="success" effect="light" round>今日新增 14</el-tag>
        <el-tag type="warning" effect="light" round>待处理 31</el-tag>
      </div>
    </section>

    <section class="summary-grid">
      <article v-for="item in stageSummary" :key="item.label" class="summary-card panel-card">
        <div class="summary-value">{{ item.value }}</div>
        <div class="summary-label">{{ item.label }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card content-card">
        <div class="content-header">
          <div>
            <div class="section-title">候选人列表</div>
            <div class="section-subtitle">按职位、状态和关键词快速筛选投递人。</div>
          </div>
          <div class="filters">
            <el-input v-model="keyword" clearable placeholder="搜索候选人 / 职位 / 城市 / 简历摘要" />
            <el-select v-model="jobFilter" style="width: 180px;">
              <el-option v-for="item in jobOptions" :key="item" :label="item" :value="item" />
            </el-select>
            <el-select v-model="statusFilter" style="width: 140px;">
              <el-option label="全部" value="全部" />
              <el-option label="已投递" value="已投递" />
              <el-option label="已查看" value="已查看" />
              <el-option label="初筛通过" value="初筛通过" />
              <el-option label="待沟通" value="待沟通" />
              <el-option label="面试中" value="面试中" />
              <el-option label="已录用" value="已录用" />
              <el-option label="已拒绝" value="已拒绝" />
            </el-select>
          </div>
        </div>

        <div class="candidate-grid">
          <article
            v-for="item in filteredCandidates"
            :key="item.id"
            class="candidate-card"
            :class="{ 'candidate-card--active': item.id === selectedCandidateId }"
            @click="selectCandidate(item.id)"
          >
            <div class="candidate-card__head">
              <div>
                <h3>{{ item.name }}</h3>
                <p>{{ item.jobTitle }}</p>
              </div>
              <el-tag
                :type="item.status === '面试中' || item.status === '初筛通过' ? 'success' : item.status === '已拒绝' ? 'danger' : 'info'"
                effect="light"
                round
              >
                {{ item.status }}
              </el-tag>
            </div>

            <div class="candidate-card__meta">
              <span>{{ item.city }}</span>
              <span>{{ item.experience }}</span>
              <span>{{ item.education }}</span>
              <span>{{ item.appliedAt }}</span>
            </div>

            <p class="candidate-card__summary">{{ item.resumeSummary }}</p>

            <div class="candidate-card__footer">
              <div class="candidate-card__tags">
                <el-tag v-for="tag in item.tags" :key="tag" effect="light" round>{{ tag }}</el-tag>
              </div>
              <strong>{{ item.matchRate }}%</strong>
            </div>

            <div class="candidate-card__actions">
              <el-button link type="primary" @click.stop="markViewed(item.id)">标记已查看</el-button>
              <el-button link @click.stop="markUnfit(item.id)">标记不合适</el-button>
              <el-button link @click.stop="markCommunicate(item.id)">标记待沟通</el-button>
              <el-button link @click.stop="openInviteDialog(item.id)">邀请面试</el-button>
            </div>
          </article>
        </div>
      </div>

      <div class="side-column">
        <div class="panel-card detail-card">
          <div class="section-title">简历预览</div>
          <div class="candidate-name">{{ selectedCandidate.name }}</div>
          <div class="candidate-job">{{ selectedCandidate.jobTitle }}</div>

          <div class="detail-tags">
            <el-tag effect="light" round>{{ selectedCandidate.city }}</el-tag>
            <el-tag effect="light" round>{{ selectedCandidate.experience }}</el-tag>
            <el-tag effect="light" round>{{ selectedCandidate.education }}</el-tag>
            <el-tag type="success" effect="light" round>匹配度 {{ selectedCandidate.matchRate }}%</el-tag>
          </div>

          <p class="candidate-note">{{ selectedCandidate.resumeSummary }}</p>
          <p class="candidate-note candidate-note--soft">{{ selectedCandidate.note }}</p>

          <div class="action-row">
            <el-button type="primary" round @click="markViewed(selectedCandidate.id)">已查看</el-button>
            <el-button round @click="markCommunicate(selectedCandidate.id)">待沟通</el-button>
            <el-button round @click="openInviteDialog(selectedCandidate.id)">邀请面试</el-button>
          </div>
        </div>

        <div class="panel-card detail-card">
          <div class="section-title">状态时间线</div>
          <el-timeline class="timeline-reset">
            <el-timeline-item
              v-for="item in selectedCandidate.history"
              :key="item.at + item.status"
              :timestamp="item.at"
              placement="top"
            >
              <strong>{{ item.status }}</strong>
              <p>{{ item.note }}</p>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </section>
  </section>

  <el-dialog v-model="inviteDialogVisible" title="邀请面试" width="520px">
    <el-form label-position="top">
      <el-form-item label="面试类型">
        <el-input v-model="interviewForm.type" />
      </el-form-item>
      <el-form-item label="面试时间">
        <el-input v-model="interviewForm.time" />
      </el-form-item>
      <el-form-item label="面试官">
        <el-input v-model="interviewForm.interviewer" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="inviteDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmInvite">发送邀请</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  padding: 24px;
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

.header-meta {
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
  color: var(--brand-ink-soft);
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.5fr) minmax(320px, 0.7fr);
  gap: 16px;
}

.content-card,
.detail-card {
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

.candidate-grid {
  display: grid;
  gap: 14px;
}

.candidate-card {
  padding: 18px 20px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.74);
  border: 1px solid rgba(17, 24, 39, 0.06);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

.candidate-card:hover,
.candidate-card--active {
  transform: translateY(-2px);
  border-color: rgba(10, 102, 194, 0.16);
  box-shadow: 0 18px 34px rgba(20, 35, 45, 0.08);
}

.candidate-card__head {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: flex-start;
}

.candidate-card__head h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 20px;
}

.candidate-card__head p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
}

.candidate-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.candidate-card__summary {
  margin: 14px 0 0;
  color: var(--brand-ink);
  line-height: 1.7;
}

.candidate-card__footer {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
  margin-top: 16px;
}

.candidate-card__tags,
.detail-tags,
.action-row {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.candidate-card__actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 12px;
}

.candidate-name {
  margin-top: 12px;
  color: var(--brand-title);
  font-size: 24px;
  font-weight: 800;
}

.candidate-job {
  margin-top: 6px;
  color: var(--brand-ink-soft);
}

.candidate-note {
  margin: 16px 0 0;
  color: var(--brand-ink);
  line-height: 1.7;
}

.candidate-note--soft {
  color: var(--brand-ink-soft);
}

.action-row {
  margin-top: 18px;
}

.timeline-reset {
  margin-top: 16px;
}

.timeline-reset strong {
  color: var(--brand-title);
}

.timeline-reset p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.6;
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
}
</style>
