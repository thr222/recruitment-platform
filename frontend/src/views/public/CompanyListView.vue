<script setup lang="ts">
import { computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'
import type { CompanyCardItem } from '@/types/company'

interface CompanyCard extends CompanyCardItem {
  size: string
  stage: string
  intro: string
  tags: string[]
  highlightJobs: number
}

const router = useRouter()

const filterForm = reactive({
  keyword: '',
  city: '',
  industry: '',
  stage: ''
})

const companies: CompanyCard[] = [
  {
    id: 20001,
    name: '星河科技有限公司',
    city: '上海',
    stage: 'B轮',
    industry: '企业服务',
    openPositions: 18,
    size: '100-499人',
    intro: '专注招聘效率、企业协同和组织管理，持续建设招聘平台和内部协作能力。',
    tags: ['招聘平台', 'B端产品', '技术稳定', '增长中'],
    highlightJobs: 6
  },
  {
    id: 20002,
    name: '云图人才科技有限公司',
    city: '深圳',
    stage: 'A轮',
    industry: 'HR SaaS',
    openPositions: 9,
    size: '20-99人',
    intro: '围绕招聘数字化、人才管理和企业用工流程，打造面向企业的 SaaS 工具。',
    tags: ['HR SaaS', '增长快', '产品导向', '远程协作'],
    highlightJobs: 4
  },
  {
    id: 20003,
    name: '蓝岸科技',
    city: '杭州',
    stage: 'C轮',
    industry: '互联网',
    openPositions: 14,
    size: '500人以上',
    intro: '业务覆盖招聘、企业内容和人才运营，重视工程效率和数据增长。',
    tags: ['前端', '数据平台', '组织成熟', '研发投入'],
    highlightJobs: 5
  },
  {
    id: 20004,
    name: '远聘网络',
    city: '北京',
    stage: '天使轮',
    industry: '招聘服务',
    openPositions: 7,
    size: '0-20人',
    intro: '聚焦轻量化招聘解决方案，围绕中小企业和灵活用工展开。',
    tags: ['灵活用工', '招聘服务', '小团队', '快速迭代'],
    highlightJobs: 3
  }
]

const cityOptions = ['上海', '北京', '杭州', '深圳']
const industryOptions = ['企业服务', 'HR SaaS', '互联网', '招聘服务']
const stageOptions = ['天使轮', 'A轮', 'B轮', 'C轮']

const filteredCompanies = computed(() => {
  const keyword = filterForm.keyword.trim().toLowerCase()
  return companies.filter(company => {
    const keywordHit =
      !keyword ||
      [company.name, company.industry, company.city, company.stage, company.intro, ...company.tags]
        .join(' ')
        .toLowerCase()
        .includes(keyword)
    const cityHit = !filterForm.city || company.city === filterForm.city
    const industryHit = !filterForm.industry || company.industry === filterForm.industry
    const stageHit = !filterForm.stage || company.stage === filterForm.stage
    return keywordHit && cityHit && industryHit && stageHit
  })
})

const totalOpenPositions = computed(() => companies.reduce((sum, company) => sum + company.openPositions, 0))

function goCompanyDetail(id: number) {
  router.push(`/companies/${id}`)
}

function resetFilters() {
  filterForm.keyword = ''
  filterForm.city = ''
  filterForm.industry = ''
  filterForm.stage = ''
}
</script>

<template>
  <section class="company-list-page">
    <section class="hero-board panel-card">
      <div class="hero-board__copy">
        <div class="section-chip">企业列表</div>
        <h1>从公司视角挑选更匹配的机会</h1>
        <p>
          先看行业、规模、融资阶段和在招岗位，再进入公司主页查看业务信息、招聘需求和岗位明细。
        </p>
      </div>

      <div class="hero-board__stats">
        <div class="hero-stat">
          <span>在招职位</span>
          <strong>{{ totalOpenPositions }}</strong>
        </div>
        <div class="hero-stat">
          <span>公司数量</span>
          <strong>{{ companies.length }}</strong>
        </div>
        <div class="hero-stat">
          <span>筛选结果</span>
          <strong>{{ filteredCompanies.length }}</strong>
        </div>
      </div>
    </section>

    <div class="list-grid">
      <aside class="panel-card filter-sidebar">
        <AppSectionTitle
          eyebrow="筛选公司"
          title="按业务和阶段找机会"
          description="支持按城市、行业、融资阶段和关键词过滤，帮助求职者先看公司再看岗位。"
        />

        <el-form label-position="top" class="filter-form">
          <el-form-item label="关键词">
            <el-input v-model="filterForm.keyword" placeholder="公司名 / 行业 / 关键词" clearable />
          </el-form-item>

          <el-form-item label="城市">
            <el-select v-model="filterForm.city" placeholder="请选择城市" clearable>
              <el-option v-for="item in cityOptions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>

          <el-form-item label="行业">
            <el-select v-model="filterForm.industry" placeholder="请选择行业" clearable>
              <el-option v-for="item in industryOptions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>

          <el-form-item label="融资阶段">
            <el-select v-model="filterForm.stage" placeholder="请选择阶段" clearable>
              <el-option v-for="item in stageOptions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>

          <div class="sidebar-actions">
            <el-button type="primary" @click="resetFilters">重置筛选</el-button>
          </div>
        </el-form>
      </aside>

      <div class="company-column">
        <div class="panel-card result-summary">
          <AppSectionTitle
            eyebrow="公司卡片"
            title="推荐企业"
            :description="`当前展示 ${filteredCompanies.length} 家公司，支持快速了解行业、规模和在招职位。`"
          />
        </div>

        <div class="company-grid">
          <article v-for="company in filteredCompanies" :key="company.id" class="panel-card company-card">
            <div class="company-card__top">
              <div class="company-avatar">
                {{ company.name.slice(0, 2) }}
              </div>
              <div class="company-card__copy">
                <h3>{{ company.name }}</h3>
                <div class="company-card__meta">
                  <span>{{ company.industry }}</span>
                  <span>{{ company.stage }}</span>
                  <span>{{ company.city }}</span>
                  <span>{{ company.size }}</span>
                </div>
              </div>
              <el-tag type="success" round>{{ company.openPositions }} 个在招岗位</el-tag>
            </div>

            <p class="company-card__intro">{{ company.intro }}</p>

            <div class="tag-wrap">
              <span v-for="tag in company.tags" :key="tag" class="tag-item">{{ tag }}</span>
            </div>

            <div class="company-card__bottom">
              <span class="text-muted">重点岗位：{{ company.highlightJobs }} 个</span>
              <el-button type="primary" round @click="goCompanyDetail(company.id)">查看公司主页</el-button>
            </div>
          </article>

          <div v-if="filteredCompanies.length === 0" class="panel-card empty-card">
            <h3>没有找到匹配的公司</h3>
            <p>可以尝试清空筛选条件，或换一个行业 / 阶段 / 城市。</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.company-list-page {
  display: grid;
  gap: 24px;
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
  line-height: 1.15;
  letter-spacing: -0.03em;
}

.hero-board__copy p {
  max-width: 720px;
  margin: 0;
  color: var(--brand-ink-soft);
  line-height: 1.85;
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

.list-grid {
  display: grid;
  grid-template-columns: 300px minmax(0, 1fr);
  gap: 20px;
}

.filter-sidebar {
  align-self: start;
  position: sticky;
  top: 98px;
  padding: 22px;
}

.filter-form {
  margin-top: 10px;
}

.sidebar-actions {
  margin-top: 8px;
}

.company-column {
  display: grid;
  gap: 16px;
}

.result-summary {
  padding: 22px 24px;
}

.company-grid {
  display: grid;
  gap: 16px;
}

.company-card {
  padding: 22px 24px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.company-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 18px 34px rgba(20, 35, 45, 0.1);
}

.company-card__top {
  display: flex;
  gap: 16px;
  align-items: flex-start;
}

.company-avatar {
  display: inline-grid;
  place-items: center;
  flex-shrink: 0;
  width: 52px;
  height: 52px;
  border-radius: 16px;
  background: rgba(10, 102, 194, 0.12);
  color: var(--brand-primary-deep);
  font-size: 18px;
  font-weight: 800;
}

.company-card__copy {
  flex: 1;
  min-width: 0;
}

.company-card__copy h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 24px;
  line-height: 1.3;
}

.company-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 10px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.company-card__intro {
  margin: 18px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.tag-wrap {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(15, 46, 77, 0.06);
  color: var(--brand-ink);
  font-size: 13px;
}

.company-card__bottom {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  margin-top: 18px;
  padding-top: 18px;
  border-top: 1px solid var(--brand-line);
}

.empty-card {
  padding: 24px;
}

.empty-card h3 {
  margin: 0;
  color: var(--brand-title);
}

.empty-card p {
  margin: 10px 0 0;
  color: var(--brand-ink-soft);
}

@media (max-width: 1100px) {
  .hero-board,
  .list-grid {
    grid-template-columns: 1fr;
  }

  .filter-sidebar {
    position: static;
  }
}

@media (max-width: 760px) {
  .company-card__top,
  .company-card__bottom {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
