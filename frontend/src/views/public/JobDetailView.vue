<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { fetchJobDetail } from '@/api/modules/job'
import type { JobDetail } from '@/types/job'

type ApplicationStatus =
  | '未投递'
  | '已投递'
  | '已查看'
  | '初筛通过'
  | '面试中'
  | '已录用'
  | '已拒绝'

interface TimelineEntry {
  status: ApplicationStatus
  time: string
  note: string
}

interface JobProfile {
  city: string
  experience: string
  education: string
  workplace: string
  workMode: string
  description: string
  responsibilities: string[]
  requirements: string[]
  perks: string[]
  tags: string[]
  company: {
    companyId: number
    companyName: string
    industry: string
    size: string
    stage: string
    city: string
    intro: string
    values: string[]
    highlights: string[]
  }
  similarJobs: Array<{
    title: string
    salary: string
    companyName: string
    city: string
    tags: string[]
  }>
}

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const jobDetail = ref<JobDetail | null>(null)
const applicationStatus = ref<ApplicationStatus>('未投递')
const isFavorited = ref(false)
const applicationHistory = ref<TimelineEntry[]>([
  {
    status: '未投递',
    time: '当前状态',
    note: '还没有向这个岗位发起投递'
  }
])

const applicationFlow: ApplicationStatus[] = ['未投递', '已投递', '已查看', '初筛通过', '面试中', '已录用']

const profileMap: Record<number, JobProfile> = {
  30001: {
    city: '上海',
    experience: '3-5年',
    education: '本科及以上',
    workplace: '浦东新区',
    workMode: '全职',
    description:
      '负责招聘平台后端核心链路开发，围绕职位、投递、候选人流转和权限体系持续优化系统稳定性与招聘效率。',
    responsibilities: [
      '负责职位发布、投递流转和候选人管理相关接口设计与实现',
      '参与招聘工作台、消息通知和权限体系的建设',
      '推动接口性能优化、数据查询优化和异常排查'
    ],
    requirements: [
      '熟悉 Java 和 Spring Boot，理解常见后端开发模式',
      '了解 MyBatis、Redis、MySQL 或类似技术栈',
      '有招聘、B 端 SaaS、CRM 或 OA 业务经验者优先'
    ],
    perks: ['五险一金', '弹性打卡', '项目奖金', '技术分享', '成长空间'],
    tags: ['Spring Boot', 'MyBatis', 'Redis', '招聘系统', '高并发'],
    company: {
      companyId: 20001,
      companyName: '星河科技有限公司',
      industry: '企业服务',
      size: '100-499人',
      stage: 'B轮',
      city: '上海',
      intro: '专注招聘效率和组织协同的企业服务公司，持续打磨招聘平台和内部协作产品。',
      values: ['结果导向', '快速迭代', '开放协作'],
      highlights: ['招聘平台', 'B 端产品', '技术团队稳定']
    },
    similarJobs: [
      {
        title: 'Java 后端开发工程师',
        salary: '22K-38K',
        companyName: '云图人才科技',
        city: '杭州',
        tags: ['Spring Boot', '微服务', 'MySQL']
      },
      {
        title: '招聘平台架构工程师',
        salary: '30K-45K',
        companyName: '星河科技有限公司',
        city: '上海',
        tags: ['高并发', 'Redis', '系统设计']
      }
    ]
  },
  30002: {
    city: '深圳',
    experience: '2-4年',
    education: '本科及以上',
    workplace: '南山区',
    workMode: '全职',
    description:
      '负责招聘系统产品能力建设，推动职位、公司主页、搜索筛选和投递体验的持续优化。',
    responsibilities: [
      '梳理招聘产品流程，输出页面和信息结构方案',
      '配合前后端推进职位和候选人模块迭代',
      '跟进关键数据指标，优化转化路径与用户体验'
    ],
    requirements: [
      '理解招聘业务和 B 端信息组织逻辑',
      '具备较强的需求抽象和跨团队协作能力',
      '有 SaaS、互联网平台或增长经验更佳'
    ],
    perks: ['远程协作', '双休', '团队年轻', '项目复盘', '学习预算'],
    tags: ['招聘产品', '增长', '数据分析', 'SaaS', '用户研究'],
    company: {
      companyId: 20002,
      companyName: '云图人才科技有限公司',
      industry: 'HR SaaS',
      size: '20-99人',
      stage: 'A轮',
      city: '深圳',
      intro: '专注数字化招聘产品与人才管理工具，帮助企业快速搭建高效招聘流程。',
      values: ['用户第一', '数据决策', '持续打磨'],
      highlights: ['HR SaaS', '招聘数字化', '增长快']
    },
    similarJobs: [
      {
        title: '招聘产品经理',
        salary: '18K-28K',
        companyName: '云图人才科技有限公司',
        city: '深圳',
        tags: ['B端产品', '增长', '数据化']
      },
      {
        title: '前端工程师',
        salary: '20K-35K',
        companyName: '蓝湾科技',
        city: '广州',
        tags: ['Vue3', 'TypeScript', '招聘系统']
      }
    ]
  }
}

const defaultProfile: JobProfile = {
  city: '上海',
  experience: '3-5年',
  education: '本科及以上',
  workplace: '核心商务区',
  workMode: '全职',
  description: '围绕招聘平台核心流程进行开发、优化与维护，支持职位、投递、消息和企业主页等功能。',
  responsibilities: [
    '参与招聘业务模块开发',
    '优化系统性能与可维护性',
    '与产品和设计协作完善体验'
  ],
  requirements: ['熟悉 Java / Vue 技术栈', '具备良好的编码习惯', '有 B 端业务经验更佳'],
  perks: ['弹性办公', '成长空间', '技术分享'],
  tags: ['Java', '招聘系统', 'B端产品'],
  company: {
    companyId: 20001,
    companyName: '招聘科技有限公司',
    industry: '企业服务',
    size: '100-499人',
    stage: '成长期',
    city: '上海',
    intro: '一家聚焦招聘效率和企业协同的互联网团队。',
    values: ['协作', '速度', '透明'],
    highlights: ['招聘平台', '企业服务', '中台能力']
  },
  similarJobs: []
}

const profile = computed(() => profileMap[Number(route.params.jobId)] ?? defaultProfile)

const detail = computed(() => {
  const record = jobDetail.value
  return {
    jobId: record?.jobId ?? Number(route.params.jobId || 30001),
    title: record?.title ?? 'Java 后端开发工程师',
    salaryMin: record?.salaryMin ?? 25,
    salaryMax: record?.salaryMax ?? 40,
    status: record?.status ?? 'ONLINE',
    descriptionText: record?.descriptionText ?? profile.value.description,
    company: record?.company ?? {
      companyId: profile.value.company.companyId,
      companyName: profile.value.company.companyName
    }
  }
})

const activeCompany = computed(() => profile.value.company)
const similarJobs = computed(() => profile.value.similarJobs)
const currentFlowIndex = computed(() => {
  const index = applicationFlow.indexOf(applicationStatus.value)
  return index === -1 ? 0 : index
})

function formatTime() {
  return new Date().toLocaleString('zh-CN', {
    hour12: false
  })
}

function pushHistory(status: ApplicationStatus, note: string) {
  applicationHistory.value.unshift({
    status,
    time: formatTime(),
    note
  })
}

async function loadJobDetail() {
  loading.value = true
  try {
    const response = await fetchJobDetail(String(route.params.jobId))
    jobDetail.value = response.data.data
  } catch {
    jobDetail.value = null
    ElMessage.warning('职位详情接口暂时不可用，当前展示本地演示内容')
  } finally {
    loading.value = false
  }
}

function applyJob() {
  if (applicationStatus.value === '未投递') {
    applicationStatus.value = '已投递'
    pushHistory('已投递', '已成功投递，等待招聘方查看')
    ElMessage.success('已完成投递，状态已更新为“已投递”')
    return
  }

  ElMessage.info(`当前状态：${applicationStatus.value}`)
}

function advanceApplicationStatus() {
  if (applicationStatus.value === '已拒绝' || applicationStatus.value === '已录用') {
    ElMessage.info(`当前状态已结束：${applicationStatus.value}`)
    return
  }

  if (applicationStatus.value === '未投递') {
    applyJob()
    return
  }

  const currentIndex = applicationFlow.indexOf(applicationStatus.value)
  const nextStatus = applicationFlow[currentIndex + 1]
  if (!nextStatus) {
    ElMessage.info('已经是最后一个状态了')
    return
  }

  applicationStatus.value = nextStatus
  const notes: Record<ApplicationStatus, string> = {
    未投递: '尚未投递',
    已投递: '投递已完成，等待查看',
    已查看: '招聘方已查看简历',
    初筛通过: '已通过初筛',
    面试中: '正在推进面试安排',
    已录用: '恭喜，你已被录用',
    已拒绝: '很遗憾，当前岗位已拒绝'
  }
  pushHistory(nextStatus, notes[nextStatus])
  ElMessage.success(`状态已推进为：${nextStatus}`)
}

function rejectApplication() {
  applicationStatus.value = '已拒绝'
  pushHistory('已拒绝', '招聘方暂未继续推进该岗位')
  ElMessage.warning('当前状态已标记为“已拒绝”')
}

function toggleFavorite() {
  isFavorited.value = !isFavorited.value
  ElMessage.success(isFavorited.value ? '已收藏该职位' : '已取消收藏')
}

function goCompany() {
  router.push(`/companies/${activeCompany.value.companyId}`)
}

function goShare() {
  ElMessage.success('职位链接已复制到剪贴板，稍后可接入分享功能')
}

function statusTagType(status: ApplicationStatus) {
  if (status === '已录用') return 'success'
  if (status === '已拒绝') return 'danger'
  if (status === '面试中' || status === '初筛通过') return 'warning'
  if (status === '已查看') return 'info'
  return 'primary'
}

watch(
  () => route.params.jobId,
  () => {
    loadJobDetail()
  }
)

onMounted(() => {
  loadJobDetail()
})
</script>

<template>
  <section class="job-detail-page">
    <div class="detail-grid">
      <div class="main-column">
        <section class="panel-card detail-hero" v-loading="loading">
          <div class="detail-hero__top">
            <div class="detail-hero__copy">
              <div class="section-chip">职位详情</div>
              <h1>{{ detail.title }}</h1>
              <div class="detail-hero__meta">
                <span>{{ activeCompany.companyName }}</span>
                <span>{{ profile.city }}</span>
                <span>{{ profile.experience }}</span>
                <span>{{ profile.education }}</span>
                <span>{{ profile.workMode }}</span>
              </div>
            </div>

            <div class="detail-hero__salary">
              <strong>{{ detail.salaryMin }}K-{{ detail.salaryMax }}K</strong>
              <span>{{ detail.status || 'ONLINE' }}</span>
            </div>
          </div>

          <div class="detail-hero__tags">
            <span v-for="tag in profile.tags" :key="tag" class="tag-item">{{ tag }}</span>
          </div>

          <div class="detail-hero__actions">
            <el-button type="primary" size="large" round @click="applyJob">
              {{ applicationStatus === '未投递' ? '立即投递' : '继续跟进' }}
            </el-button>
            <el-button size="large" round :type="isFavorited ? 'success' : 'default'" @click="toggleFavorite">
              {{ isFavorited ? '已收藏' : '收藏职位' }}
            </el-button>
            <el-button size="large" round @click="advanceApplicationStatus">推进状态</el-button>
            <el-button size="large" round @click="goShare">分享职位</el-button>
          </div>
        </section>

        <section class="panel-card detail-section">
          <div class="section-heading">
            <div class="section-chip">岗位信息</div>
            <h2>你将负责什么</h2>
            <p>{{ profile.description }}</p>
          </div>
          <ul class="detail-list">
            <li v-for="item in profile.responsibilities" :key="item">{{ item }}</li>
          </ul>
        </section>

        <section class="panel-card detail-section">
          <div class="section-heading">
            <div class="section-chip">任职要求</div>
            <h2>我们希望你具备什么</h2>
          </div>
          <ul class="detail-list">
            <li v-for="item in profile.requirements" :key="item">{{ item }}</li>
          </ul>
        </section>

        <section class="panel-card detail-section">
          <div class="section-heading">
            <div class="section-chip">福利标签</div>
            <h2>这份工作为什么值得关注</h2>
          </div>
          <div class="tag-wrap">
            <span v-for="tag in profile.perks" :key="tag" class="tag-item tag-item--soft">{{ tag }}</span>
          </div>
        </section>

        <section class="panel-card detail-section">
          <div class="section-heading">
            <div class="section-chip">投递状态</div>
            <h2>状态流转一目了然</h2>
            <p>这里不会“投完就没了”，你可以看到投递后的完整状态变化。</p>
          </div>

          <div class="status-banner">
            <span>当前状态</span>
            <el-tag :type="statusTagType(applicationStatus)" effect="light" round size="large">
              {{ applicationStatus }}
            </el-tag>
          </div>

          <el-alert
            v-if="applicationStatus === '已拒绝'"
            title="当前状态已进入拒绝分支"
            type="error"
            show-icon
            :closable="false"
            class="status-alert"
          />

          <el-steps
            :active="applicationFlow.indexOf(applicationStatus === '已拒绝' ? '已投递' : applicationStatus)"
            finish-status="success"
            align-center
            class="status-steps"
          >
            <el-step v-for="step in applicationFlow.slice(0, 6)" :key="step" :title="step" />
          </el-steps>

          <el-timeline class="status-timeline">
            <el-timeline-item
              v-for="item in applicationHistory"
              :key="item.status + item.time"
              :timestamp="item.time"
              :type="item.status === '已拒绝' ? 'danger' : item.status === applicationStatus ? 'primary' : 'success'"
            >
              <div class="timeline-card">
                <strong>{{ item.status }}</strong>
                <p>{{ item.note }}</p>
              </div>
            </el-timeline-item>
          </el-timeline>

          <div class="status-actions">
            <el-button type="primary" round @click="applyJob">投递职位</el-button>
            <el-button round @click="advanceApplicationStatus">推进下一阶段</el-button>
            <el-button type="danger" plain round @click="rejectApplication">标记拒绝</el-button>
          </div>
        </section>

        <section class="panel-card detail-section">
          <div class="section-heading">
            <div class="section-chip">相似职位</div>
            <h2>你可能还会感兴趣</h2>
          </div>
          <div class="similar-list">
            <article v-for="item in similarJobs" :key="item.title + item.companyName" class="similar-card">
              <div class="similar-card__top">
                <div>
                  <h3>{{ item.title }}</h3>
                  <p>{{ item.companyName }} · {{ item.city }}</p>
                </div>
                <strong>{{ item.salary }}</strong>
              </div>
              <div class="tag-wrap">
                <span v-for="tag in item.tags" :key="tag" class="tag-item">{{ tag }}</span>
              </div>
            </article>
          </div>
        </section>
      </div>

      <aside class="side-column">
        <section class="panel-card sticky-card">
          <div class="section-heading">
            <div class="section-chip">公司信息</div>
            <h2>{{ activeCompany.companyName }}</h2>
          </div>
          <div class="company-card__meta">
            <div class="company-card__line">
              <span>行业</span>
              <strong>{{ activeCompany.industry }}</strong>
            </div>
            <div class="company-card__line">
              <span>规模</span>
              <strong>{{ activeCompany.size }}</strong>
            </div>
            <div class="company-card__line">
              <span>阶段</span>
              <strong>{{ activeCompany.stage }}</strong>
            </div>
            <div class="company-card__line">
              <span>城市</span>
              <strong>{{ activeCompany.city }}</strong>
            </div>
            <div class="company-card__line">
              <span>工作地点</span>
              <strong>{{ profile.workplace }}</strong>
            </div>
          </div>
          <div class="soft-divider" />
          <p class="company-card__intro">{{ activeCompany.intro }}</p>
          <div class="tag-wrap">
            <span v-for="tag in activeCompany.highlights" :key="tag" class="tag-item tag-item--soft">{{ tag }}</span>
          </div>
          <el-button type="primary" plain round class="full-button" @click="goCompany">查看公司主页</el-button>
        </section>

        <section class="panel-card sticky-card">
          <div class="section-heading">
            <div class="section-chip">投递建议</div>
            <h2>投递前先检查</h2>
          </div>
          <div class="check-list">
            <div class="check-item">简历里是否突出对应岗位的技术栈和业务经验</div>
            <div class="check-item">是否准备了项目亮点和可量化结果</div>
            <div class="check-item">是否了解该公司业务、行业和招聘节奏</div>
          </div>
        </section>

        <section class="panel-card sticky-card">
          <div class="section-heading">
            <div class="section-chip">岗位摘要</div>
            <h2>简要说明</h2>
          </div>
          <p class="job-brief">{{ detail.descriptionText }}</p>
        </section>
      </aside>
    </div>
  </section>
</template>

<style scoped>
.job-detail-page {
  display: grid;
}

.detail-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.55fr) 360px;
  gap: 20px;
}

.main-column,
.side-column {
  display: grid;
  gap: 18px;
}

.detail-hero,
.detail-section,
.sticky-card {
  padding: 24px;
}

.detail-hero__top {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: flex-start;
}

.detail-hero__copy {
  min-width: 0;
}

.detail-hero h1 {
  margin: 14px 0 0;
  color: var(--brand-title);
  font-size: clamp(30px, 4vw, 38px);
  line-height: 1.15;
  letter-spacing: -0.03em;
}

.detail-hero__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 14px;
  color: var(--brand-ink-soft);
}

.detail-hero__salary {
  display: grid;
  gap: 8px;
  flex-shrink: 0;
  text-align: right;
}

.detail-hero__salary strong {
  color: var(--brand-accent);
  font-size: 30px;
  font-weight: 800;
}

.detail-hero__salary span {
  color: var(--brand-primary-deep);
  font-size: 13px;
  font-weight: 700;
}

.detail-hero__tags,
.tag-wrap {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.detail-hero__tags {
  margin-top: 18px;
}

.detail-hero__actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 22px;
}

.section-heading h2 {
  margin: 10px 0 0;
  color: var(--brand-title);
  font-size: 22px;
  line-height: 1.25;
}

.section-heading p {
  margin: 10px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.detail-list {
  display: grid;
  gap: 12px;
  margin: 18px 0 0;
  padding-left: 18px;
  color: var(--brand-ink-soft);
  line-height: 1.9;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(0, 166, 166, 0.08);
  color: var(--brand-primary-deep);
  font-size: 13px;
  font-weight: 700;
}

.tag-item--soft {
  background: rgba(15, 46, 77, 0.06);
  color: var(--brand-ink);
}

.status-banner {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 18px;
}

.status-banner span {
  color: var(--brand-ink-soft);
}

.status-alert {
  margin-top: 16px;
}

.status-steps {
  margin-top: 18px;
}

.status-timeline {
  margin-top: 24px;
}

.timeline-card {
  padding: 12px 14px;
  border-radius: 16px;
  background: rgba(10, 102, 194, 0.06);
}

.timeline-card strong {
  color: var(--brand-title);
}

.timeline-card p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.status-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 18px;
}

.similar-list {
  display: grid;
  gap: 14px;
  margin-top: 18px;
}

.similar-card {
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.68);
  border: 1px solid rgba(17, 24, 39, 0.06);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.similar-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 14px 28px rgba(20, 35, 45, 0.08);
}

.similar-card__top {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-start;
  margin-bottom: 10px;
}

.similar-card h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 18px;
}

.similar-card p {
  margin: 8px 0 0;
  color: var(--brand-ink-soft);
}

.similar-card strong {
  color: var(--brand-primary);
  font-size: 20px;
}

.company-card__meta {
  display: grid;
  gap: 12px;
  margin-top: 18px;
}

.company-card__line {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.company-card__line span,
.job-brief,
.company-card__intro {
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.company-card__line strong {
  color: var(--brand-title);
}

.soft-divider {
  height: 1px;
  margin: 18px 0;
  background: var(--brand-line);
}

.check-list {
  display: grid;
  gap: 10px;
  margin-top: 18px;
}

.check-item {
  padding: 12px 14px;
  border-radius: 14px;
  background: rgba(10, 102, 194, 0.06);
  color: var(--brand-title);
  line-height: 1.7;
}

.job-brief {
  margin: 18px 0 0;
}

.full-button {
  width: 100%;
  margin-top: 18px;
}

@media (max-width: 1100px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .detail-hero__top,
  .detail-hero__actions,
  .similar-card__top,
  .status-banner,
  .status-actions {
    flex-direction: column;
    align-items: flex-start;
  }

  .detail-hero__salary {
    text-align: left;
  }
}
</style>
