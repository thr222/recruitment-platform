<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

interface CompanyDetailProfile {
  name: string
  city: string
  industry: string
  stage: string
  size: string
  intro: string
  summary: string
  tags: string[]
  values: string[]
  benefits: string[]
  stats: Array<{ label: string; value: string }>
  openJobs: Array<{
    jobId: number
    title: string
    salary: string
    experience: string
    city: string
    tags: string[]
  }>
}

const route = useRoute()
const router = useRouter()

const companyMap: Record<number, CompanyDetailProfile> = {
  20001: {
    name: '星河科技有限公司',
    city: '上海',
    industry: '企业服务',
    stage: 'B轮',
    size: '100-499人',
    intro:
      '专注招聘效率和企业协同的企业服务团队，围绕招聘平台、组织管理和中台能力持续迭代。',
    summary:
      '公司正在建设面向招聘场景的核心平台，包含职位、简历、投递、消息、权限与数据分析等模块。',
    tags: ['招聘平台', 'B端产品', '系统稳定', '成长中'],
    values: ['结果导向', '快速迭代', '开放协作', '持续学习'],
    benefits: ['五险一金', '弹性打卡', '技术分享', '项目奖金', '带薪年假'],
    stats: [
      { label: '在招职位', value: '18' },
      { label: '主要城市', value: '上海' },
      { label: '团队规模', value: '100-499人' },
      { label: '行业标签', value: '企业服务' }
    ],
    openJobs: [
      {
        jobId: 30001,
        title: 'Java 后端开发工程师',
        salary: '25K-40K',
        experience: '3-5年',
        city: '上海',
        tags: ['Spring Boot', 'MySQL', 'Redis']
      },
      {
        jobId: 30011,
        title: '招聘平台产品经理',
        salary: '20K-32K',
        experience: '3-5年',
        city: '上海',
        tags: ['B端产品', '流程设计', '数据分析']
      }
    ]
  },
  20002: {
    name: '云图人才科技有限公司',
    city: '深圳',
    industry: 'HR SaaS',
    stage: 'A轮',
    size: '20-99人',
    intro:
      '围绕招聘数字化、人才管理和企业用工流程，打造面向企业的招聘和组织管理 SaaS 产品。',
    summary:
      '产品覆盖招聘工作台、公司主页、候选人筛选和消息协作，帮助企业更快完成招聘转化。',
    tags: ['HR SaaS', '招聘数字化', '增长快', '远程协作'],
    values: ['用户第一', '用数据决策', '保持透明', '持续打磨'],
    benefits: ['双休', '远程协作', '学习预算', '绩效奖金', '团队扁平'],
    stats: [
      { label: '在招职位', value: '9' },
      { label: '主要城市', value: '深圳' },
      { label: '团队规模', value: '20-99人' },
      { label: '行业标签', value: 'HR SaaS' }
    ],
    openJobs: [
      {
        jobId: 30002,
        title: '招聘产品经理',
        salary: '18K-28K',
        experience: '2-4年',
        city: '深圳',
        tags: ['B端产品', '增长', '数据化']
      },
      {
        jobId: 30012,
        title: '前端工程师',
        salary: '20K-35K',
        experience: '3-5年',
        city: '深圳',
        tags: ['Vue3', 'TypeScript', '招聘系统']
      }
    ]
  }
}

const fallbackCompany: CompanyDetailProfile = {
  name: '招聘科技有限公司',
  city: '上海',
  industry: '企业服务',
  stage: '成长中',
  size: '100-499人',
  intro: '一家聚焦招聘效率和企业协同的互联网团队。',
  summary: '围绕招聘流程和企业主页持续优化，提升职位曝光、投递和候选人管理效率。',
  tags: ['招聘系统', '企业服务', '成长型团队'],
  values: ['协作', '速度', '透明'],
  benefits: ['弹性办公', '成长空间', '技术分享'],
  stats: [
    { label: '在招职位', value: '12' },
    { label: '主要城市', value: '上海' },
    { label: '团队规模', value: '100-499人' },
    { label: '行业标签', value: '企业服务' }
  ],
  openJobs: [
    {
      jobId: 30001,
      title: 'Java 后端开发工程师',
      salary: '25K-40K',
      experience: '3-5年',
      city: '上海',
      tags: ['Spring Boot', 'MySQL', 'Redis']
    }
  ]
}

const company = computed(() => {
  return companyMap[Number(route.params.companyId)] ?? fallbackCompany
})

function goJob(jobId: number) {
  router.push(`/jobs/${jobId}`)
}
</script>

<template>
  <section class="company-detail-page">
    <section class="panel-card hero-board">
      <div class="hero-board__copy">
        <div class="section-chip">公司详情</div>
        <h1>{{ company.name }}</h1>
        <p>{{ company.summary }}</p>

        <div class="hero-board__meta">
          <span>{{ company.industry }}</span>
          <span>{{ company.stage }}</span>
          <span>{{ company.city }}</span>
          <span>{{ company.size }}</span>
        </div>

        <div class="tag-wrap">
          <span v-for="tag in company.tags" :key="tag" class="tag-item">{{ tag }}</span>
        </div>

        <div class="hero-board__actions">
          <el-button type="primary" size="large" round>关注公司</el-button>
          <el-button size="large" round>查看在招职位</el-button>
        </div>
      </div>

      <div class="hero-board__stats">
        <div v-for="item in company.stats" :key="item.label" class="hero-stat">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </div>
      </div>
    </section>

    <div class="detail-grid">
      <div class="main-column">
        <section class="panel-card detail-section">
          <AppSectionTitle eyebrow="公司简介" title="公司正在做什么" :description="company.intro" />
          <p class="section-copy">
            {{ company.summary }}
          </p>
        </section>

        <section class="panel-card detail-section">
          <AppSectionTitle eyebrow="文化价值观" title="团队协作方式" />
          <div class="value-grid">
            <div v-for="item in company.values" :key="item" class="value-card">
              {{ item }}
            </div>
          </div>
        </section>

        <section class="panel-card detail-section">
          <AppSectionTitle eyebrow="在招职位" title="可以直接投递的岗位" />
          <div class="job-grid">
            <article v-for="job in company.openJobs" :key="job.jobId" class="job-card">
              <div class="job-card__top">
                <div>
                  <h3>{{ job.title }}</h3>
                  <p>{{ job.city }} · {{ job.experience }}</p>
                </div>
                <strong>{{ job.salary }}</strong>
              </div>
              <div class="tag-wrap">
                <span v-for="tag in job.tags" :key="tag" class="tag-item tag-item--soft">{{ tag }}</span>
              </div>
              <div class="job-card__bottom">
                <span class="text-muted">已收录到公司主页</span>
                <el-button type="primary" plain round @click="goJob(job.jobId)">查看职位详情</el-button>
              </div>
            </article>
          </div>
        </section>
      </div>

      <aside class="side-column">
        <section class="panel-card sticky-card">
          <AppSectionTitle eyebrow="公司档案" title="快速了解公司" />
          <div class="company-info">
            <div class="company-info__line">
              <span>行业</span>
              <strong>{{ company.industry }}</strong>
            </div>
            <div class="company-info__line">
              <span>融资阶段</span>
              <strong>{{ company.stage }}</strong>
            </div>
            <div class="company-info__line">
              <span>团队规模</span>
              <strong>{{ company.size }}</strong>
            </div>
            <div class="company-info__line">
              <span>城市</span>
              <strong>{{ company.city }}</strong>
            </div>
          </div>
          <div class="soft-divider" />
          <el-button type="primary" plain round class="full-button">投递前联系招聘方</el-button>
        </section>

        <section class="panel-card sticky-card">
          <AppSectionTitle eyebrow="岗位福利" title="员工能获得什么" />
          <div class="tag-wrap">
            <span v-for="item in company.benefits" :key="item" class="tag-item tag-item--soft">{{ item }}</span>
          </div>
        </section>

        <section class="panel-card sticky-card">
          <AppSectionTitle eyebrow="招聘建议" title="适合什么样的候选人" />
          <ul class="hint-list">
            <li>有招聘系统、企业服务或 B 端产品背景更容易匹配。</li>
            <li>如果你擅长信息架构和流程设计，可以重点看产品和前端岗位。</li>
            <li>如果偏后端，可重点看职位、投递和权限链路相关岗位。</li>
          </ul>
        </section>
      </aside>
    </div>
  </section>
</template>

<style scoped>
.company-detail-page {
  display: grid;
  gap: 20px;
}

.hero-board {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) 280px;
  gap: 24px;
  padding: 30px 32px;
}

.hero-board__copy {
  display: grid;
  gap: 14px;
}

.hero-board__copy h1 {
  margin: 0;
  color: var(--brand-title);
  font-size: clamp(32px, 4vw, 44px);
  line-height: 1.12;
  letter-spacing: -0.03em;
}

.hero-board__copy p {
  max-width: 720px;
  margin: 0;
  color: var(--brand-ink-soft);
  line-height: 1.85;
}

.hero-board__meta,
.tag-wrap {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.hero-board__meta {
  color: var(--brand-ink-soft);
}

.hero-board__actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 4px;
}

.hero-board__stats {
  display: grid;
  gap: 12px;
}

.hero-stat {
  display: grid;
  gap: 8px;
  padding: 18px 18px 16px;
  border-radius: 20px;
  background: rgba(10, 102, 194, 0.06);
  border: 1px solid rgba(10, 102, 194, 0.08);
}

.hero-stat span {
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.hero-stat strong {
  color: var(--brand-title);
  font-size: 20px;
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

.detail-section,
.sticky-card {
  padding: 24px;
}

.section-copy {
  margin: 0;
  color: var(--brand-ink-soft);
  line-height: 1.9;
}

.value-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.value-card {
  padding: 16px 18px;
  border-radius: 16px;
  background: rgba(15, 46, 77, 0.05);
  color: var(--brand-title);
  font-weight: 700;
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

.job-grid {
  display: grid;
  gap: 14px;
}

.job-card {
  padding: 18px 20px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(17, 24, 39, 0.06);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.job-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 14px 28px rgba(20, 35, 45, 0.08);
}

.job-card__top {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-start;
}

.job-card__top h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 20px;
}

.job-card__top p {
  margin: 8px 0 0;
  color: var(--brand-ink-soft);
}

.job-card__top strong {
  color: var(--brand-primary);
  font-size: 22px;
}

.job-card__bottom {
  display: flex;
  justify-content: space-between;
  gap: 14px;
  align-items: center;
  margin-top: 16px;
}

.company-info {
  display: grid;
  gap: 12px;
}

.company-info__line {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.company-info__line span,
.hint-list {
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.company-info__line strong {
  color: var(--brand-title);
}

.soft-divider {
  height: 1px;
  margin: 18px 0;
  background: var(--brand-line);
}

.full-button {
  width: 100%;
}

.hint-list {
  margin: 0;
  padding-left: 18px;
}

@media (max-width: 1100px) {
  .hero-board,
  .detail-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .job-card__top,
  .job-card__bottom {
    flex-direction: column;
    align-items: flex-start;
  }

  .value-grid {
    grid-template-columns: 1fr;
  }
}
</style>
