<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'
import { fetchJobDetail } from '@/api/modules/job'
import type { JobDetail } from '@/types/job'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const jobDetail = ref<JobDetail | null>(null)

const fallbackDetail: JobDetail = {
  jobId: Number(route.params.jobId || 30001),
  title: 'Java Backend Engineer',
  salaryMin: 25,
  salaryMax: 40,
  status: 'ONLINE',
  descriptionText:
    '负责招聘平台中的职位、投递、权限相关模块建设，参与系统设计、接口开发、性能优化和线上问题排查。',
  company: {
    companyId: 20001,
    companyName: 'Galaxy Talent Tech'
  }
}

const detail = computed(() => jobDetail.value ?? fallbackDetail)

const requirementTags = ['Spring Boot', 'MySQL', 'Redis', 'JWT', '模块化单体']
const benefitTags = ['五险一金', '双休', '技术氛围', '业务成长', '年度体检']

const sections = computed(() => [
  {
    title: '岗位职责',
    items: [
      '负责招聘系统职位、投递、权限等核心模块开发。',
      '参与接口设计、业务建模和数据库结构落地。',
      '推动系统在可维护性、性能和扩展性上的持续优化。'
    ]
  },
  {
    title: '任职要求',
    items: [
      '具备扎实的 Java 后端开发基础，理解常见 Web 架构。',
      '熟悉 Spring Boot、MyBatis-Plus、Redis、MySQL。',
      '有中后台系统或招聘、CRM、OA 等业务项目经验更佳。'
    ]
  }
])

async function loadJobDetail() {
  loading.value = true
  try {
    const response = await fetchJobDetail(String(route.params.jobId))
    jobDetail.value = response.data.data
  } catch {
    jobDetail.value = fallbackDetail
    ElMessage.warning('职位详情接口未返回完整数据，当前展示示例详情')
  } finally {
    loading.value = false
  }
}

function goCompany() {
  if (!detail.value.company?.companyId) {
    return
  }
  router.push(`/companies/${detail.value.company.companyId}`)
}

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
            <div>
              <div class="section-chip">职位详情</div>
              <h1>{{ detail.title }}</h1>
              <div class="detail-hero__meta">
                <span>{{ detail.company?.companyName || '企业名称待补充' }}</span>
                <span>上海</span>
                <span>3-5年</span>
                <span>本科</span>
              </div>
            </div>

            <div class="detail-hero__salary">
              <strong>{{ detail.salaryMin }}K-{{ detail.salaryMax }}K</strong>
              <span>{{ detail.status || 'ONLINE' }}</span>
            </div>
          </div>

          <div class="detail-hero__actions">
            <el-button type="primary" size="large" round>立即沟通</el-button>
            <el-button size="large" round>收藏职位</el-button>
          </div>
        </section>

        <section
          v-for="section in sections"
          :key="section.title"
          class="panel-card detail-section"
        >
          <AppSectionTitle :title="section.title" />
          <ul class="detail-list">
            <li v-for="item in section.items" :key="item">{{ item }}</li>
          </ul>
        </section>

        <section class="panel-card detail-section">
          <AppSectionTitle title="职位亮点" />
          <div class="tag-wrap">
            <span v-for="tag in requirementTags" :key="tag" class="tag-item">{{ tag }}</span>
          </div>
        </section>
      </div>

      <aside class="side-column">
        <section class="panel-card company-card">
          <AppSectionTitle eyebrow="公司信息" :title="detail.company?.companyName || '企业名称'" />
          <div class="company-card__meta">
            <div class="company-card__line">
              <span>行业</span>
              <strong>企业服务</strong>
            </div>
            <div class="company-card__line">
              <span>规模</span>
              <strong>100-499人</strong>
            </div>
            <div class="company-card__line">
              <span>融资</span>
              <strong>B轮</strong>
            </div>
          </div>
          <div class="soft-divider" />
          <p class="company-card__intro">
            这是一家做招聘效率和组织协作产品的企业，当前在持续扩张研发和产品团队。
          </p>
          <el-button type="primary" plain round @click="goCompany">查看公司主页</el-button>
        </section>

        <section class="panel-card company-card">
          <AppSectionTitle eyebrow="岗位福利" title="你会得到什么" />
          <div class="tag-wrap">
            <span v-for="tag in benefitTags" :key="tag" class="tag-item">{{ tag }}</span>
          </div>
        </section>

        <section class="panel-card company-card">
          <AppSectionTitle eyebrow="岗位描述" title="简要说明" />
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
.company-card {
  padding: 24px;
}

.detail-hero__top {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: flex-start;
}

.detail-hero h1 {
  margin: 14px 0 0;
  color: var(--brand-title);
  font-size: 34px;
  line-height: 1.2;
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

.detail-hero__actions {
  display: flex;
  gap: 12px;
  margin-top: 22px;
}

.detail-list {
  display: grid;
  gap: 12px;
  margin: 0;
  padding-left: 18px;
  color: var(--brand-ink-soft);
  line-height: 1.9;
}

.tag-wrap {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(0, 166, 166, 0.08);
  color: var(--brand-primary-deep);
  font-size: 13px;
  font-weight: 700;
}

.company-card__meta {
  display: grid;
  gap: 12px;
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

@media (max-width: 1100px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .detail-hero__top,
  .detail-hero__actions {
    flex-direction: column;
    align-items: flex-start;
  }

  .detail-hero__salary {
    text-align: left;
  }
}
</style>
