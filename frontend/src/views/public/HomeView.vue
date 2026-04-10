<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'
import JobCard from '@/components/business/JobCard.vue'
import { searchJobs } from '@/api/modules/job'
import type { JobCardItem, JobListItem } from '@/types/job'

const router = useRouter()

const loading = ref(false)
const recommendedJobs = ref<JobCardItem[]>([])
const searchKeyword = ref('')

const categories = [
  { label: '技术', keyword: 'Java', note: '后端 / 前端 / 测试 / 数据' },
  { label: '产品', keyword: '产品经理', note: '增长 / 平台 / 商业化' },
  { label: '设计', keyword: '设计', note: '交互 / UI / 品牌' },
  { label: '运营', keyword: '运营', note: '内容 / 用户 / 活动' },
  { label: '市场', keyword: '市场', note: '投放 / 品牌 / 增长' },
  { label: '销售', keyword: '销售', note: '企业客户 / 顾问式销售' }
]

const hotCities = [
  { city: '上海', code: '310100', tags: '平台 / 企业服务', count: '1.2w+' },
  { city: '北京', code: '110100', tags: 'AI / 中后台', count: '9.4k+' },
  { city: '杭州', code: '330100', tags: '电商 / SaaS', count: '7.8k+' },
  { city: '深圳', code: '440300', tags: '硬件 / 云计算', count: '8.6k+' }
]

const fallbackJobs: JobCardItem[] = [
  {
    id: 1,
    title: 'Java 后端开发工程师',
    companyName: '星河科技',
    city: '上海',
    salary: '25K-40K',
    experience: '3-5年',
    tags: ['Spring Boot', '招聘中台', '高并发']
  },
  {
    id: 2,
    title: 'Vue3 前端工程师',
    companyName: '远帆智能',
    city: '杭州',
    salary: '22K-35K',
    experience: '3年+',
    tags: ['Vue3', 'TypeScript', 'B 端产品']
  },
  {
    id: 3,
    title: '产品经理',
    companyName: '青木数据',
    city: '北京',
    salary: '18K-28K',
    experience: '2-4年',
    tags: ['数据平台', '增长', '用户研究']
  }
]

const topJobs = computed(() => (recommendedJobs.value.length > 0 ? recommendedJobs.value : fallbackJobs))

function mapJobCard(item: JobListItem): JobCardItem {
  return {
    id: item.jobId,
    title: item.title,
    companyName: item.companyName,
    city: item.workCityCode,
    salary: `${item.salaryMin}K-${item.salaryMax}K`,
    experience:
      item.experienceMin != null && item.experienceMax != null
        ? `${item.experienceMin}-${item.experienceMax}年`
        : '经验不限',
    tags: [item.educationRequirement || '学历不限', item.status || '在线岗位', '支持直聊']
  }
}

async function loadHomeJobs() {
  loading.value = true
  try {
    const response = await searchJobs({
      pageNo: 1,
      pageSize: 6,
      sortBy: 'hot'
    })
    recommendedJobs.value = (response.data.data.records ?? []).map(mapJobCard)
  } catch {
    recommendedJobs.value = fallbackJobs
  } finally {
    loading.value = false
  }
}

function goJobList(keyword = '') {
  router.push({
    path: '/jobs',
    query: keyword ? { keyword } : undefined
  })
}

function goCityJobs(cityCode: string) {
  router.push({
    path: '/jobs',
    query: { cityCode }
  })
}

function handleSearch() {
  goJobList(searchKeyword.value.trim())
}

onMounted(() => {
  loadHomeJobs()
})
</script>

<template>
  <div class="home-view">
    <section class="hero-banner home-hero">
      <div class="hero-main">
        <div class="section-chip">找工作</div>
        <h1>搜索岗位，选择方向，直接进入职位流</h1>
        <div class="hero-search panel-card">
          <el-input
            v-model="searchKeyword"
            size="large"
            placeholder="搜索职位、技能或公司"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>
      </div>

      <div class="hero-city panel-card">
        <div class="hero-city__title">热门城市</div>
        <div class="hero-city__list">
          <button
            v-for="item in hotCities"
            :key="item.city"
            class="city-card"
            @click="goCityJobs(item.code)"
          >
            <div>
              <strong>{{ item.city }}</strong>
              <p>{{ item.tags }}</p>
            </div>
            <span>{{ item.count }}</span>
          </button>
        </div>
      </div>
    </section>

    <section class="page-section">
      <AppSectionTitle eyebrow="职类入口" title="从方向开始" />
      <div class="category-grid">
        <button
          v-for="item in categories"
          :key="item.label"
          class="panel-card category-card"
          @click="goJobList(item.keyword)"
        >
          <strong>{{ item.label }}</strong>
          <span>{{ item.note }}</span>
        </button>
      </div>
    </section>

    <section class="page-section">
      <AppSectionTitle eyebrow="推荐职位" title="近期活跃岗位" />
      <div class="board-list">
        <JobCard v-for="job in topJobs" :key="job.id" :job="job" />
      </div>
    </section>
  </div>
</template>

<style scoped>
.home-view {
  display: grid;
  gap: 30px;
}

.home-hero {
  display: grid;
  grid-template-columns: minmax(0, 1.6fr) 360px;
  gap: 22px;
  align-items: stretch;
  padding: 40px;
}

.hero-main {
  display: grid;
  align-content: center;
  gap: 18px;
}

.hero-main h1 {
  max-width: 620px;
  margin: 0;
}

.hero-search {
  padding: 14px;
  max-width: 640px;
  background: rgba(255, 255, 255, 0.96);
}

.hero-search :deep(.el-input__wrapper) {
  min-height: 54px;
  border-radius: 18px;
}

.hero-search :deep(.el-input-group__append) {
  background: transparent;
  border: none;
  padding-left: 12px;
}

.hero-city {
  padding: 22px;
  background: rgba(8, 28, 46, 0.2);
  border-color: rgba(255, 255, 255, 0.16);
}

.hero-city__title {
  color: rgba(255, 255, 255, 0.86);
  font-weight: 800;
  font-size: 18px;
}

.hero-city__list {
  display: grid;
  gap: 14px;
  margin-top: 16px;
}

.city-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  width: 100%;
  padding: 16px;
  border: none;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.12);
  color: #fff;
  cursor: pointer;
  text-align: left;
}

.city-card strong {
  display: block;
  font-size: 18px;
}

.city-card p {
  margin: 6px 0 0;
  color: rgba(255, 255, 255, 0.72);
  font-size: 13px;
}

.city-card span {
  font-size: 18px;
  font-weight: 800;
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.category-card {
  display: grid;
  gap: 8px;
  padding: 22px;
  border: none;
  text-align: left;
  cursor: pointer;
  transition: transform 0.2s ease, border-color 0.2s ease;
}

.category-card:hover {
  transform: translateY(-2px);
  border-color: rgba(0, 166, 166, 0.22);
}

.category-card strong {
  color: var(--brand-title);
  font-size: 20px;
}

.category-card span {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

@media (max-width: 1100px) {
  .home-hero,
  .category-grid {
    grid-template-columns: 1fr;
  }
}
</style>
