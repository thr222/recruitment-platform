<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'
import { searchJobs } from '@/api/modules/job'
import type { JobListItem, JobSearchParams } from '@/types/job'

interface FilterForm {
  keyword: string
  cityCode: string
  salaryRange: string
}

interface OptionItem {
  label: string
  value: string
}

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const jobs = ref<JobListItem[]>([])
const pageNo = ref(1)
const pageSize = ref(10)
const total = ref(0)

const filterForm = reactive<FilterForm>({
  keyword: '',
  cityCode: '',
  salaryRange: ''
})

const cityOptions: OptionItem[] = [
  { label: '上海', value: '310100' },
  { label: '北京', value: '110100' },
  { label: '杭州', value: '330100' },
  { label: '深圳', value: '440300' }
]

const salaryOptions: OptionItem[] = [
  { label: '15K - 25K', value: '15-25' },
  { label: '25K - 35K', value: '25-35' },
  { label: '35K - 50K', value: '35-50' },
  { label: '50K 以上', value: '50-999' }
]

const quickTags = ['后端', '前端', '产品经理', '测试开发', '运营']

const cityLabelMap = computed<Record<string, string>>(() => {
  return cityOptions.reduce<Record<string, string>>((acc, item) => {
    acc[item.value] = item.label
    return acc
  }, {})
})

const resultSummary = computed(() => {
  if (total.value === 0) {
    return '当前筛选下暂时没有匹配职位'
  }
  return `共找到 ${total.value} 个职位，优先展示最近活跃岗位`
})

function syncFromRoute() {
  filterForm.keyword = String(route.query.keyword ?? '')
  filterForm.cityCode = String(route.query.cityCode ?? '')
  filterForm.salaryRange = String(route.query.salaryRange ?? '')
  pageNo.value = Number(route.query.pageNo ?? 1)
}

function buildParams(): JobSearchParams {
  const params: JobSearchParams = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    keyword: filterForm.keyword || undefined,
    cityCode: filterForm.cityCode || undefined,
    sortBy: 'latest'
  }

  if (filterForm.salaryRange) {
    const [salaryMin, salaryMax] = filterForm.salaryRange.split('-').map(Number)
    params.salaryMin = salaryMin
    params.salaryMax = salaryMax
  }

  return params
}

async function fetchJobList() {
  loading.value = true
  try {
    const { data } = await searchJobs(buildParams())
    jobs.value = data.data.records ?? []
    total.value = data.data.total ?? 0
    pageNo.value = data.data.pageNo ?? pageNo.value
    pageSize.value = data.data.pageSize ?? pageSize.value
  } catch {
    ElMessage.error('职位列表加载失败，请确认后端接口是否已启动')
    jobs.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

function updateRouteAndFetch() {
  router.replace({
    path: '/jobs',
    query: {
      ...(filterForm.keyword ? { keyword: filterForm.keyword } : {}),
      ...(filterForm.cityCode ? { cityCode: filterForm.cityCode } : {}),
      ...(filterForm.salaryRange ? { salaryRange: filterForm.salaryRange } : {}),
      ...(pageNo.value > 1 ? { pageNo: String(pageNo.value) } : {})
    }
  })
  fetchJobList()
}

function handleSearch() {
  pageNo.value = 1
  updateRouteAndFetch()
}

function handleReset() {
  filterForm.keyword = ''
  filterForm.cityCode = ''
  filterForm.salaryRange = ''
  pageNo.value = 1
  updateRouteAndFetch()
}

function handleCurrentChange(value: number) {
  pageNo.value = value
  updateRouteAndFetch()
}

function handleQuickKeyword(keyword: string) {
  filterForm.keyword = keyword
  pageNo.value = 1
  updateRouteAndFetch()
}

function handleViewDetail(row: JobListItem) {
  router.push({ name: 'public-job-detail', params: { jobId: row.jobId } })
}

function formatCity(cityCode: string) {
  return cityLabelMap.value[cityCode] ?? cityCode
}

function formatSalary(row: JobListItem) {
  return `${row.salaryMin}K-${row.salaryMax}K`
}

function formatExperience(row: JobListItem) {
  if (row.experienceMin == null && row.experienceMax == null) {
    return '经验不限'
  }
  if (row.experienceMin != null && row.experienceMax != null) {
    return `${row.experienceMin}-${row.experienceMax}年`
  }
  return `${row.experienceMin ?? row.experienceMax}年+`
}

watch(
  () => route.fullPath,
  () => {
    syncFromRoute()
  }
)

onMounted(() => {
  syncFromRoute()
  fetchJobList()
})
</script>

<template>
  <section class="job-list-page">
    <section class="hero-banner list-hero">
      <div>
        <div class="section-chip">职位搜索</div>
        <h1>像真实招聘网站那样，把筛选和职位结果放在同一工作流里</h1>
        <p>这页改成“搜索栏 + 快捷筛选 + 列表卡片”的结构，不再只是中后台表格页。</p>
      </div>
    </section>

    <div class="list-grid">
      <aside class="panel-card filter-sidebar">
        <AppSectionTitle eyebrow="条件筛选" title="缩小范围更快" description="保留高频条件，把找工作这件事做成一条顺手的路径。" />

        <el-form label-position="top" class="filter-form">
          <el-form-item label="关键词">
            <el-input
              v-model="filterForm.keyword"
              placeholder="职位、技能、公司"
              clearable
              size="large"
              @keyup.enter="handleSearch"
            />
          </el-form-item>

          <el-form-item label="城市">
            <el-select v-model="filterForm.cityCode" placeholder="请选择城市" clearable size="large">
              <el-option
                v-for="item in cityOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="薪资范围">
            <el-select v-model="filterForm.salaryRange" placeholder="请选择薪资" clearable size="large">
              <el-option
                v-for="item in salaryOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

          <div class="sidebar-actions">
            <el-button type="primary" size="large" @click="handleSearch">搜索职位</el-button>
            <el-button size="large" @click="handleReset">重置条件</el-button>
          </div>

          <div class="quick-tags">
            <span class="quick-tags__label">热门搜索</span>
            <button
              v-for="tag in quickTags"
              :key="tag"
              class="quick-tag"
              @click="handleQuickKeyword(tag)"
            >
              {{ tag }}
            </button>
          </div>
        </el-form>
      </aside>

      <div class="result-column">
        <div class="panel-card result-summary">
          <div>
            <AppSectionTitle
              eyebrow="搜索结果"
              title="职位列表"
              :description="resultSummary"
            />
          </div>
          <div class="result-summary__meta">
            <span class="text-muted">默认排序：最新发布</span>
            <span class="link-ghost">支持后续扩展为热度 / 薪资排序</span>
          </div>
        </div>

        <div v-loading="loading" class="result-list">
          <article
            v-for="job in jobs"
            :key="job.jobId"
            class="panel-card list-card"
          >
            <div class="list-card__top">
              <div>
                <div class="list-card__title-row">
                  <h3>{{ job.title }}</h3>
                  <span class="status-pill">
                    <i class="status-dot" />
                    {{ job.status || 'ONLINE' }}
                  </span>
                </div>

                <div class="list-card__meta">
                  <span>{{ job.companyName }}</span>
                  <span>{{ formatCity(job.workCityCode) }}</span>
                  <span>{{ formatExperience(job) }}</span>
                  <span>{{ job.educationRequirement || '学历不限' }}</span>
                </div>
              </div>

              <div class="list-card__salary">{{ formatSalary(job) }}</div>
            </div>

            <div class="list-card__bottom">
              <div class="list-card__tags">
                <span class="tag-item">在线沟通</span>
                <span class="tag-item">反馈快</span>
                <span class="tag-item">{{ job.educationRequirement || '经验可谈' }}</span>
              </div>
              <div class="list-card__action">
                <span class="text-muted">{{ job.publishedAt || '刚刚更新' }}</span>
                <el-button type="primary" round @click="handleViewDetail(job)">查看详情</el-button>
              </div>
            </div>
          </article>

          <div v-if="!loading && jobs.length === 0" class="panel-card empty-card">
            <h3>没有找到匹配的职位</h3>
            <p>可以先试试放宽城市或薪资条件，再重新搜索。</p>
          </div>
        </div>

        <div class="panel-card pagination-panel">
          <el-pagination
            v-model:current-page="pageNo"
            v-model:page-size="pageSize"
            background
            layout="total, prev, pager, next, jumper"
            :total="total"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.job-list-page {
  display: grid;
  gap: 24px;
}

.list-hero {
  padding: 34px 36px;
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
  display: grid;
  gap: 10px;
}

.quick-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 18px;
}

.quick-tags__label {
  width: 100%;
  color: var(--brand-ink-soft);
  font-size: 13px;
  font-weight: 700;
}

.quick-tag {
  border: none;
  background: rgba(0, 166, 166, 0.08);
  color: var(--brand-primary-deep);
  padding: 8px 12px;
  border-radius: 999px;
  cursor: pointer;
}

.result-column {
  display: grid;
  gap: 16px;
}

.result-summary {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: end;
  padding: 22px 24px;
}

.result-summary__meta {
  display: grid;
  gap: 8px;
  text-align: right;
}

.result-list {
  display: grid;
  gap: 16px;
}

.list-card {
  padding: 22px 24px;
}

.list-card__top {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: flex-start;
}

.list-card__title-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;
}

.list-card__title-row h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: 24px;
  line-height: 1.3;
}

.list-card__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.list-card__salary {
  flex-shrink: 0;
  color: var(--brand-accent);
  font-size: 28px;
  font-weight: 800;
}

.status-pill {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 7px 12px;
  border-radius: 999px;
  background: rgba(25, 180, 91, 0.1);
  color: #148d48;
  font-size: 12px;
  font-weight: 700;
}

.list-card__bottom {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  margin-top: 18px;
  padding-top: 18px;
  border-top: 1px solid var(--brand-line);
}

.list-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(15, 46, 77, 0.06);
  color: var(--brand-ink);
  font-size: 13px;
}

.list-card__action {
  display: flex;
  align-items: center;
  gap: 14px;
}

.empty-card,
.pagination-panel {
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
  .list-grid {
    grid-template-columns: 1fr;
  }

  .filter-sidebar {
    position: static;
  }
}

@media (max-width: 760px) {
  .result-summary,
  .list-card__top,
  .list-card__bottom,
  .list-card__action {
    flex-direction: column;
    align-items: flex-start;
  }

  .list-card__salary {
    font-size: 24px;
  }
}
</style>
