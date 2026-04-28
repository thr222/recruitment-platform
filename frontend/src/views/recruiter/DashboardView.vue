<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

interface MetricItem {
  label: string
  value: string
  hint: string
}

interface PipelineItem {
  label: string
  count: number
  color: string
}

interface ActivityItem {
  candidate: string
  job: string
  stage: string
  time: string
  note: string
}

interface StoredJobItem {
  status: string
  applicantCount: number
  viewCount: number
}

interface StoredCandidateItem {
  name: string
  jobTitle: string
  status: string
  history?: Array<{ status: string; at: string; note: string }>
  appliedAt: string
  note: string
}

const router = useRouter()

const jobStorageKey = 'recruiter-job-management'
const candidateStorageKey = 'recruiter-candidate-applications'

const loadList = <T,>(key: string, fallback: T[]) => {
  try {
    const raw = localStorage.getItem(key)
    if (!raw) return fallback
    const parsed = JSON.parse(raw) as T[]
    return parsed.length ? parsed : fallback
  } catch {
    return fallback
  }
}

const jobs = ref<StoredJobItem[]>(loadList<StoredJobItem>(jobStorageKey, []))
const candidates = ref<StoredCandidateItem[]>(loadList<StoredCandidateItem>(candidateStorageKey, []))

const jobsInHiring = computed(() => jobs.value.filter((item) => item.status === '招聘中').length || 18)
const resumesReceived = computed(() => candidates.value.length || 53)
const pendingCandidates = computed(
  () => candidates.value.filter((item) => item.status === '已投递' || item.status === '已查看' || item.status === '待沟通').length || 31
)
const todayViews = computed(() => jobs.value.reduce((sum, item) => sum + (item.viewCount ?? 0), 0) || 426)

const metrics = computed<MetricItem[]>(() => [
  { label: '在招职位数', value: String(jobsInHiring.value), hint: '当前仍在开放投递' },
  { label: '收到简历数', value: String(resumesReceived.value), hint: '累计投递进入系统' },
  { label: '待处理候选人数', value: String(pendingCandidates.value), hint: '需要优先跟进' },
  { label: '今日浏览量', value: String(todayViews.value), hint: '职位与公司页访问量' }
])

const pipeline = computed<PipelineItem[]>(() => {
  const all = candidates.value.length ? candidates.value : []
  const picked = (status: string) => all.filter((item) => item.status === status).length
  return [
    { label: '已投递', count: picked('已投递') || 126, color: '#0A66C2' },
    { label: '已查看', count: picked('已查看') || 63, color: '#3B82F6' },
    { label: '初筛通过', count: picked('初筛通过') || 28, color: '#3b6cc9' },
    { label: '面试中', count: picked('面试中') || 12, color: '#e28b22' }
  ]
})

const recentActivities = computed<ActivityItem[]>(() => {
  const source = [...candidates.value]
    .sort((a, b) => b.appliedAt.localeCompare(a.appliedAt))
    .slice(0, 4)
  if (!source.length) {
    return [
      {
        candidate: '王同学',
        job: 'Java 后端开发工程师',
        stage: '已投递',
        time: '10 分钟前',
        note: '3 年电商平台经验，简历关键词命中率高。'
      },
      {
        candidate: '陈同学',
        job: '产品经理',
        stage: '已查看',
        time: '28 分钟前',
        note: '做过 SaaS 产品，适合继续推进。'
      },
      {
        candidate: '李同学',
        job: '测试开发工程师',
        stage: '初筛通过',
        time: '1 小时前',
        note: '自动化测试和质量保障经历完整。'
      },
      {
        candidate: '赵同学',
        job: '前端开发工程师',
        stage: '面试中',
        time: '2 小时前',
        note: '已发送面试邀请，等待确认时间。'
      }
    ]
  }

  return source.map((item) => {
    const lastHistory = item.history?.[item.history.length - 1]
    return {
      candidate: item.name,
      job: item.jobTitle,
      stage: item.status,
      time: lastHistory?.at ?? item.appliedAt,
      note: lastHistory?.note ?? item.note
    }
  })
})

const quickActions = computed(() => [
  { label: '发布职位', type: 'primary', action: () => router.push('/recruiter/jobs') },
  { label: '查看候选人', type: 'default', action: () => router.push('/recruiter/candidates') }
])

const statTrend = computed(() => {
  const resumeRate = resumesReceived.value ? Math.min(100, Math.round((pipeline.value[2].count / resumesReceived.value) * 100)) : 24
  return [
    { label: '简历转面率', value: `${resumeRate}%`, hint: '从投递到初筛的核心效率' },
    { label: '今日新投递', value: String(recentActivities.value.filter((item) => item.stage === '已投递').length || 14), hint: '其中一部分可快速推进' },
    { label: '待约面试', value: String(pipeline.value[3].count), hint: '建议今天优先处理' }
  ]
})
</script>

<template>
  <section class="page-shell">
    <section class="hero-card panel-card">
      <div class="hero-copy">
        <div class="section-chip">招聘方工作台</div>
        <h1>把职位发布、投递处理和面试推进，放在同一个视图里看清楚。</h1>
        <p>
          这里汇总招聘闭环最常用的入口和核心指标，方便 HR 快速判断岗位热度、处理新投递并推进候选人状态流转。
        </p>
        <div class="hero-actions">
          <el-button
            v-for="item in quickActions"
            :key="item.label"
            size="large"
            :type="item.type as 'primary' | 'default'"
            round
            @click="item.action"
          >
            {{ item.label }}
          </el-button>
        </div>
      </div>

      <div class="hero-panel">
        <div class="hero-panel__title">今日效率</div>
        <div class="hero-panel__value">82%</div>
        <div class="hero-panel__desc">招聘漏斗跑起来之后，简历筛选和状态推进会明显更顺。</div>

        <div class="hero-bars">
          <div v-for="item in pipeline" :key="item.label" class="hero-bar">
            <span>{{ item.label }}</span>
            <strong>{{ item.count }}</strong>
          </div>
        </div>
      </div>
    </section>

    <section class="metric-grid">
      <article v-for="item in metrics" :key="item.label" class="metric-card panel-card">
        <div class="metric-value">{{ item.value }}</div>
        <div class="metric-label">{{ item.label }}</div>
        <div class="metric-hint">{{ item.hint }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card section-card">
        <div class="section-header">
          <div>
            <div class="section-title">最近投递动态</div>
            <div class="section-subtitle">先看新鲜投递，再决定要不要快速推进到面试。</div>
          </div>
          <el-tag type="success" effect="light" round>待处理 {{ pendingCandidates }} 人</el-tag>
        </div>

        <div class="activity-list">
          <article v-for="item in recentActivities" :key="item.candidate + item.job" class="activity-card">
            <div class="activity-card__head">
              <div>
                <h3>{{ item.candidate }}</h3>
                <p>{{ item.job }}</p>
              </div>
              <el-tag effect="light" round>{{ item.stage }}</el-tag>
            </div>
            <div class="activity-card__foot">
              <span>{{ item.time }}</span>
              <span>{{ item.note }}</span>
            </div>
          </article>
        </div>
      </div>

      <div class="side-column">
        <div class="panel-card section-card">
          <div class="section-header">
            <div>
              <div class="section-title">招聘漏斗</div>
              <div class="section-subtitle">用最短路径看出每个阶段的压力点。</div>
            </div>
          </div>

          <div class="pipeline-list">
            <div v-for="item in pipeline" :key="item.label" class="pipeline-item">
              <div class="pipeline-item__top">
                <span>{{ item.label }}</span>
                <strong>{{ item.count }}</strong>
              </div>
              <el-progress :percentage="Math.min(item.count, 100)" :color="item.color" :show-text="false" />
            </div>
          </div>
        </div>

        <div class="panel-card section-card">
          <div class="section-header">
            <div>
              <div class="section-title">关键提醒</div>
              <div class="section-subtitle">今天可以优先处理的几个动作。</div>
            </div>
          </div>

          <div class="tip-list">
            <article v-for="item in statTrend" :key="item.label" class="tip-card">
              <strong>{{ item.label }}</strong>
              <div>{{ item.value }}</div>
              <p>{{ item.hint }}</p>
            </article>
          </div>
        </div>
      </div>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.hero-card,
.section-card {
  padding: 26px;
}

.hero-card {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(300px, 0.9fr);
  gap: 22px;
  align-items: stretch;
}

.hero-copy h1 {
  margin: 14px 0 12px;
  color: var(--brand-title);
  font-size: 36px;
  line-height: 1.2;
  letter-spacing: -0.03em;
}

.hero-copy p {
  margin: 0;
  max-width: 720px;
  color: var(--brand-ink-soft);
  font-size: 15px;
  line-height: 1.7;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 22px;
}

.hero-panel {
  border-radius: 22px;
  padding: 24px;
  background: linear-gradient(180deg, rgba(10, 102, 194, 0.12), rgba(10, 102, 194, 0.04));
  border: 1px solid rgba(10, 102, 194, 0.12);
}

.hero-panel__title {
  color: var(--brand-ink-soft);
  font-size: 13px;
  font-weight: 700;
}

.hero-panel__value {
  margin-top: 10px;
  color: var(--brand-title);
  font-size: 44px;
  font-weight: 800;
}

.hero-panel__desc {
  margin-top: 6px;
  color: var(--brand-ink-soft);
}

.hero-bars {
  display: grid;
  gap: 12px;
  margin-top: 22px;
}

.hero-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 14px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.72);
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.metric-card {
  padding: 20px;
}

.metric-value {
  color: var(--brand-title);
  font-size: 34px;
  font-weight: 800;
}

.metric-label {
  margin-top: 8px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.metric-hint {
  margin-top: 10px;
  color: var(--brand-primary);
  font-size: 13px;
  font-weight: 700;
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(320px, 0.8fr);
  gap: 16px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: flex-start;
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

.side-column {
  display: grid;
  gap: 16px;
}

.activity-list {
  display: grid;
  gap: 14px;
}

.activity-card {
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(17, 24, 39, 0.06);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.activity-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 14px 28px rgba(20, 35, 45, 0.08);
}

.activity-card__head {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: flex-start;
}

.activity-card h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 18px;
}

.activity-card p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
}

.activity-card__foot {
  display: grid;
  gap: 6px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  font-size: 13px;
  line-height: 1.6;
}

.pipeline-list {
  display: grid;
  gap: 14px;
}

.pipeline-item {
  display: grid;
  gap: 8px;
}

.pipeline-item__top {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  color: var(--brand-ink);
}

.tip-list {
  display: grid;
  gap: 12px;
}

.tip-card {
  padding: 14px 16px;
  border-radius: 16px;
  background: rgba(10, 102, 194, 0.06);
}

.tip-card strong {
  display: block;
  color: var(--brand-title);
}

.tip-card div {
  margin-top: 6px;
  color: var(--brand-primary);
  font-size: 22px;
  font-weight: 800;
}

.tip-card p {
  margin: 8px 0 0;
  color: var(--brand-ink-soft);
  font-size: 13px;
  line-height: 1.6;
}

@media (max-width: 1200px) {
  .hero-card,
  .content-grid {
    grid-template-columns: 1fr;
  }

  .metric-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .metric-grid {
    grid-template-columns: 1fr;
  }

  .hero-copy h1 {
    font-size: 30px;
  }
}
</style>
