<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Search, MapLocation, Money, Briefcase, Calendar, ArrowRight } from '@element-plus/icons-vue'
import { searchJobs } from '@/api/modules/job'
import type { JobListItem, JobSearchParams } from '@/types/job'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const jobList = ref<JobListItem[]>([])
const total = ref(0)
const pageNo = ref(1)
const pageSize = ref(10)

const filterForm = reactive({
  keyword: '',
  cityCode: '',
  salaryRange: '',
  experienceMin: undefined as number | undefined,
  experienceMax: undefined as number | undefined,
  sortBy: 'latest'
})

const cityOptions = [
  { label: '全国', value: '' },
  { label: '北京', value: '110100' },
  { label: '上海', value: '310100' },
  { label: '广州', value: '440100' },
  { label: '深圳', value: '440300' },
  { label: '杭州', value: '330100' }
]

const salaryOptions = [
  { label: '不限', value: '' },
  { label: '3K-5K', value: '3-5' },
  { label: '5K-10K', value: '5-10' },
  { label: '10K-20K', value: '10-20' },
  { label: '20K-50K', value: '20-50' },
  { label: '50K以上', value: '50-999' }
]

const experienceOptions = [
  { label: '不限', min: undefined, max: undefined },
  { label: '应届生', min: 0, max: 0 },
  { label: '1年以内', min: 0, max: 1 },
  { label: '1-3年', min: 1, max: 3 },
  { label: '3-5年', min: 3, max: 5 },
  { label: '5-10年', min: 5, max: 10 },
  { label: '10年以上', min: 10, max: 99 }
]

const fetchJobs = async () => {
  loading.value = true
  const params: JobSearchParams = {
    pageNo: pageNo.value,
    pageSize: pageSize.value,
    keyword: filterForm.keyword || undefined,
    cityCode: filterForm.cityCode || undefined,
    sortBy: filterForm.sortBy as any
  }

  if (filterForm.salaryRange) {
    const [min, max] = filterForm.salaryRange.split('-').map(Number)
    params.salaryMin = min * 1000
    params.salaryMax = max * 1000
  }

  params.experienceMin = filterForm.experienceMin
  params.experienceMax = filterForm.experienceMax

  try {
    const res = await searchJobs(params)
    if (res.data) {
      jobList.value = res.data.list || []
      total.value = res.data.total || 0
    }
  } catch (error) {
    console.error('Failed to fetch jobs:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pageNo.value = 1
  fetchJobs()
}

const handleExperienceChange = (item: typeof experienceOptions[0]) => {
  filterForm.experienceMin = item.min
  filterForm.experienceMax = item.max
  handleSearch()
}

const formatSalary = (min: number, max: number) => {
  return `${Math.floor(min / 1000)}k-${Math.floor(max / 1000)}k`
}

const formatTime = (time?: string) => {
  if (!time) return ''
  return time.split(' ')[0]
}

const goToDetail = (jobId: number) => {
  router.push({ name: 'JobDetail', params: { id: jobId } })
}

onMounted(() => {
  if (route.query.keyword) {
    filterForm.keyword = route.query.keyword as string
  }
  fetchJobs()
})
</script>

<template>
  <div class="job-list-container">
    <!-- Search Bar Section -->
    <div class="search-section">
      <div class="content-wrapper">
        <div class="search-bar">
          <el-input
            v-model="filterForm.keyword"
            placeholder="搜索职位、公司或关键词"
            class="search-input"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-select v-model="filterForm.cityCode" placeholder="城市" class="city-select" @change="handleSearch">
            <el-option v-for="item in cityOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-button type="primary" class="search-btn" @click="handleSearch">搜索</el-button>
        </div>
      </div>
    </div>

    <div class="content-wrapper main-content">
      <!-- Filters Row -->
      <div class="filter-row card-panel">
        <div class="filter-group">
          <span class="filter-label">薪资范围：</span>
          <el-radio-group v-model="filterForm.salaryRange" size="small" @change="handleSearch">
            <el-radio-button v-for="opt in salaryOptions" :key="opt.value" :label="opt.value">
              {{ opt.label }}
            </el-radio-button>
          </el-radio-group>
        </div>
        <div class="filter-group">
          <span class="filter-label">工作经验：</span>
          <div class="exp-tags">
            <span
              v-for="opt in experienceOptions"
              :key="opt.label"
              class="filter-tag"
              :class="{ active: filterForm.experienceMin === opt.min && filterForm.experienceMax === opt.max }"
              @click="handleExperienceChange(opt)"
            >
              {{ opt.label }}
            </span>
          </div>
        </div>
      </div>

      <!-- Main Grid -->
      <el-row :gutter="20" class="list-container">
        <!-- List Column -->
        <el-col :md="17" :sm="24">
          <div class="sort-bar">
            <span class="total-count">找到 {{ total }} 个相关职位</span>
            <el-radio-group v-model="filterForm.sortBy" size="small" @change="handleSearch">
              <el-radio-button label="latest">最新</el-radio-button>
              <el-radio-button label="hot">热门</el-radio-button>
            </el-radio-group>
          </div>

          <div v-loading="loading" class="jobs-stack">
            <div v-for="job in jobList" :key="job.jobId" class="job-item-card" @click="goToDetail(job.jobId)">
              <div class="job-main-info">
                <div class="job-header">
                  <h3 class="job-title">{{ job.title }}</h3>
                  <span class="job-salary">{{ formatSalary(job.salaryMin, job.salaryMax) }}</span>
                </div>
                <div class="job-labels">
                  <span class="label-item"><el-icon><MapLocation /></el-icon> {{ job.workCityCode === '310100' ? '上海' : '其他' }}</span>
                  <span class="label-item"><el-icon><Briefcase /></el-icon> {{ job.experienceMin || 0 }}-{{ job.experienceMax || 1 }}年</span>
                  <span class="label-item"><el-icon><Calendar /></el-icon> {{ job.educationRequirement || '本科' }}</span>
                </div>
                <div class="job-tags">
                  <el-tag v-for="tag in ['双休', '五险一金', '带薪年假']" :key="tag" size="small" effect="plain" class="tag-pill">
                    {{ tag }}
                  </el-tag>
                </div>
              </div>
              <div class="job-company-info">
                <div class="company-text">
                  <div class="company-name">{{ job.companyName }}</div>
                  <div class="company-meta">{{ job.companySize || '100-499人' }} · 互联网</div>
                </div>
                <el-avatar :size="48" shape="square" class="company-logo">
                  {{ job.companyName.charAt(0) }}
                </el-avatar>
              </div>
            </div>

            <el-empty v-if="jobList.length === 0 && !loading" description="暂无符合条件的职位" />

            <div class="pagination-wrapper">
              <el-pagination
                v-model:current-page="pageNo"
                v-model:page-size="pageSize"
                :total="total"
                layout="prev, pager, next"
                background
                @current-change="fetchJobs"
              />
            </div>
          </div>
        </el-col>

        <!-- Sidebar Column -->
        <el-col :md="7" class="hidden-sm-and-down">
          <div class="sidebar-card">
            <h4>可能感兴趣的公司</h4>
            <div class="interest-companies">
              <div v-for="i in 3" :key="i" class="mini-company-card">
                <el-avatar :size="32" shape="square">企</el-avatar>
                <div class="mini-info">
                  <div class="name">热门名企 {{ i }}</div>
                  <div class="desc">互联网 · 已上市</div>
                </div>
              </div>
            </div>
            <el-divider />
            <div class="app-ad">
              <p>下载 APP 随时随地看职位</p>
              <div class="qr-placeholder">二维码</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
.job-list-container {
  background-color: #f6f9fc;
  min-height: 100vh;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* Search Section */
.search-section {
  background-color: white;
  padding: 24px 0;
  border-bottom: 1px solid #eef2f6;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.03);
}

.search-bar {
  display: flex;
  gap: 12px;
}

.search-input {
  flex: 1;
}

.search-input :deep(.el-input__wrapper) {
  height: 48px;
  border-radius: 8px;
  background-color: #f8fafc;
  box-shadow: none !important;
  border: 1px solid #e2e8f0;
}

.city-select :deep(.el-input__wrapper) {
  height: 48px;
  width: 120px;
  border-radius: 8px;
}

.search-btn {
  height: 48px;
  padding: 0 32px;
  border-radius: 8px;
  font-weight: 600;
  background-color: var(--brand-primary);
  border: none;
}

/* Main Content */
.main-content {
  padding-top: 24px;
  padding-bottom: 60px;
}

.card-panel {
  background: white;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 20px;
  border: 1px solid #eef2f6;
}

.filter-group {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}

.filter-group:last-child {
  margin-bottom: 0;
}

.filter-label {
  font-size: 14px;
  color: #64748b;
  width: 80px;
  flex-shrink: 0;
}

.exp-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.filter-tag {
  font-size: 14px;
  color: #475569;
  cursor: pointer;
  padding: 4px 12px;
  border-radius: 6px;
  transition: all 0.2s;
}

.filter-tag:hover {
  color: var(--brand-primary);
  background-color: rgba(31, 107, 87, 0.05);
}

.filter-tag.active {
  color: white;
  background-color: var(--brand-primary);
}

/* Sort Bar */
.sort-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.total-count {
  font-size: 14px;
  color: #64748b;
}

/* Job Cards */
.jobs-stack {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.job-item-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid #eef2f6;
}

.job-item-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  border-color: var(--brand-primary);
  transform: translateY(-2px);
}

.job-main-info {
  flex: 1;
}

.job-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 8px;
}

.job-title {
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
}

.job-salary {
  font-size: 18px;
  font-weight: 700;
  color: #f59e0b;
}

.job-labels {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
}

.label-item {
  font-size: 14px;
  color: #64748b;
  display: flex;
  align-items: center;
  gap: 4px;
}

.job-tags {
  display: flex;
  gap: 8px;
}

.tag-pill {
  border: none;
  background-color: #f1f5f9;
  color: #64748b;
  font-weight: 500;
}

.job-company-info {
  display: flex;
  align-items: center;
  gap: 16px;
  padding-left: 24px;
  border-left: 1px solid #f1f5f9;
  width: 240px;
}

.company-text {
  flex: 1;
  text-align: right;
}

.company-name {
  font-size: 16px;
  font-weight: 600;
  color: #334155;
  margin-bottom: 4px;
}

.company-meta {
  font-size: 13px;
  color: #94a3b8;
}

.company-logo {
  background-color: var(--brand-primary);
  color: white;
  font-weight: 700;
}

/* Pagination */
.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

/* Sidebar */
.sidebar-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  border: 1px solid #eef2f6;
}

.sidebar-card h4 {
  margin: 0 0 16px 0;
  color: #1e293b;
  font-size: 16px;
}

.interest-companies {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mini-company-card {
  display: flex;
  align-items: center;
  gap: 12px;
}

.mini-info .name {
  font-size: 14px;
  font-weight: 600;
  color: #334155;
}

.mini-info .desc {
  font-size: 12px;
  color: #94a3b8;
}

.qr-placeholder {
  width: 120px;
  height: 120px;
  background-color: #f1f5f9;
  margin: 16px auto 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #94a3b8;
  font-size: 12px;
  border-radius: 8px;
}
</style>
