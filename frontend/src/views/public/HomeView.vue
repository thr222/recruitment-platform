<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search, ArrowRight } from '@element-plus/icons-vue'
import { searchJobs } from '@/api/modules/job'
import type { JobListItem } from '@/types/job'
import JobCard from '@/components/business/JobCard.vue'

const router = useRouter()
const searchQuery = ref('')
const recommendedJobs = ref<JobListItem[]>([])
const loading = ref(false)

// 模拟热门公司数据（后端暂无公共列表接口）
const hotCompanies = ref([
  { id: 20001, name: '星河科技有限公司', logo: 'https://p6-passport.byteacctimg.com/img/user-avatar/6180373e3a4799003362a9b3~120x120.awebp', tags: ['互联网', 'B轮', '100-499人'], jobCount: 12 },
  { id: 20002, name: '字节跳动', logo: 'https://p3-passport.byteacctimg.com/img/user-avatar/4c4c8182b8813f83733a1e944410a568~120x120.awebp', tags: ['社交', '已上市', '10000人以上'], jobCount: 156 },
  { id: 20003, name: '阿里巴巴', logo: 'https://p3-passport.byteacctimg.com/img/user-avatar/28f32545d65609a63381a1a9e34e5675~120x120.awebp', tags: ['电商', '已上市', '10000人以上'], jobCount: 89 },
  { id: 20004, name: '腾讯', logo: 'https://p3-passport.byteacctimg.com/img/user-avatar/7200787e67f79401826f04c6488a0329~120x120.awebp', tags: ['游戏', '已上市', '10000人以上'], jobCount: 102 }
])

// 统计数据
const stats = ref([
  { label: '在招职位', value: '12,540+', color: '#409EFF' },
  { label: '活跃企业', value: '3,200+', color: '#67C23A' },
  { label: '入驻人才', value: '85,000+', color: '#E6A23C' }
])

const fetchJobs = async () => {
  loading.value = true
  try {
    const res = await searchJobs({ pageNo: 1, pageSize: 8 })
    if (res.data?.list) {
      // 适配 JobListItem 到 JobCardItem 的显示需求
      recommendedJobs.value = res.data.list.map((item: any) => ({
        id: item.jobId,
        title: item.title,
        companyName: item.companyName,
        city: '上海', // 暂无映射，默认上海
        salary: `${Math.floor(item.salaryMin / 1000)}k-${Math.floor(item.salaryMax / 1000)}k`,
        experience: item.experienceMin === 0 ? '不限' : `${item.experienceMin}年以上`,
        tags: ['互联网', '双休']
      }))
    }
  } catch (error) {
    console.error('Failed to fetch jobs:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  router.push({
    name: 'JobList',
    query: { keyword: searchQuery.value }
  })
}

const quickSearch = (tag: string) => {
  searchQuery.value = tag
  handleSearch()
}

onMounted(() => {
  fetchJobs()
})
</script>

<template>
  <div class="home-container">
    <!-- Hero Section -->
    <section class="hero-section">
      <div class="content-wrapper">
        <h1 class="hero-title">发现属于你的 <span class="highlight">理想职业</span></h1>
        <p class="hero-subtitle">连接数万家名企，开启职业生涯新篇章</p>
        
        <div class="search-box-container">
          <el-input
            v-model="searchQuery"
            placeholder="搜索职位、公司、关键词..."
            class="main-search-input"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
            <template #append>
              <el-button type="primary" @click="handleSearch">搜索职位</el-button>
            </template>
          </el-input>
          
          <div class="hot-tags">
            <span>热门搜索：</span>
            <el-tag
              v-for="tag in ['Java', '前端', '产品经理', '运营', '实习']"
              :key="tag"
              size="small"
              effect="plain"
              class="hot-tag"
              @click="quickSearch(tag)"
            >
              {{ tag }}
            </el-tag>
          </div>
        </div>
      </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
      <div class="content-wrapper">
        <el-row :gutter="40">
          <el-col v-for="stat in stats" :key="stat.label" :xs="24" :sm="8">
            <div class="stat-card">
              <div class="stat-value" :style="{ color: stat.color }">{{ stat.value }}</div>
              <div class="stat-label">{{ stat.label }}</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </section>

    <!-- Recommended Jobs -->
    <section class="section recommended-jobs">
      <div class="content-wrapper">
        <div class="section-header">
          <h2 class="section-title">推荐职位</h2>
          <el-link type="primary" @click="router.push({ name: 'JobList' })">查看更多 <el-icon><ArrowRight /></el-icon></el-link>
        </div>
        
        <el-skeleton :loading="loading" animated :count="4">
          <template #template>
            <div style="padding: 14px; margin-bottom: 20px; border: 1px solid #eee; border-radius: 8px">
              <el-skeleton-item variant="p" style="width: 30%" />
              <el-skeleton-item variant="text" style="margin-top: 10px" />
              <el-skeleton-item variant="text" style="width: 60%" />
            </div>
          </template>
          <template #default>
            <div class="job-grid">
              <JobCard v-for="job in recommendedJobs" :key="job.id" :job="job" />
            </div>
            <div v-if="recommendedJobs.length === 0 && !loading" class="empty-state">
              <el-empty description="暂无推荐职位" />
            </div>
          </template>
        </el-skeleton>
      </div>
    </section>

    <!-- Hot Companies -->
    <section class="section hot-companies">
      <div class="content-wrapper">
        <div class="section-header">
          <h2 class="section-title">热门名企</h2>
          <el-link type="primary" @click="router.push({ name: 'CompanyList' })">全部公司</el-link>
        </div>
        
        <el-row :gutter="20">
          <el-col v-for="company in hotCompanies" :key="company.id" :xs="24" :sm="12" :md="6">
            <el-card class="company-card" shadow="hover" @click="router.push({ name: 'CompanyDetail', params: { id: company.id }})">
              <div class="company-info">
                <el-avatar :size="64" :src="company.logo" shape="square" class="company-logo" />
                <h3 class="company-name">{{ company.name }}</h3>
                <div class="company-tags">
                  <span v-for="tag in company.tags" :key="tag" class="small-tag">{{ tag }}</span>
                </div>
                <div class="company-footer">
                  <span class="job-count"><b>{{ company.jobCount }}</b> 个在招职位</span>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
      <div class="content-wrapper">
        <div class="cta-card">
          <div class="cta-content">
            <h2>准备好寻找你的下一份工作了吗？</h2>
            <p>完善你的在线简历，让优秀的企业主动找到你。</p>
          </div>
          <div class="cta-actions">
            <el-button type="primary" size="large" @click="router.push({ name: 'candidate-resume' })">完善简历</el-button>
            <el-button size="large" @click="router.push({ name: 'JobList' })">浏览职位</el-button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.home-container {
  background-color: #f5f7fa;
  min-height: 100vh;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* Hero Section */
.hero-section {
  background: linear-gradient(135deg, #1f6b57 0%, #f7f3eb 100%);
  padding: 80px 0 100px;
  color: var(--brand-ink);
  text-align: center;
}

.hero-title {
  font-size: 48px;
  font-weight: 800;
  margin-bottom: 20px;
  letter-spacing: -1px;
  color: var(--brand-title);
}

.hero-title .highlight {
  color: var(--brand-primary);
  text-shadow: 0 0 20px rgba(31, 107, 87, 0.2);
}

.hero-subtitle {
  font-size: 20px;
  opacity: 0.9;
  margin-bottom: 40px;
  color: var(--brand-ink-soft);
}

.search-box-container {
  max-width: 800px;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.4);
  padding: 24px;
  border-radius: 16px;
  backdrop-filter: blur(10px);
  border: 1px solid var(--brand-line);
}

.main-search-input :deep(.el-input__wrapper) {
  height: 56px;
  font-size: 18px;
  border-radius: 8px 0 0 8px;
  background: white;
}

.main-search-input :deep(.el-input-group__append) {
  background-color: var(--brand-primary);
  border-color: var(--brand-primary);
  color: white;
  padding: 0 30px;
  font-size: 18px;
  font-weight: 600;
}

.hot-tags {
  margin-top: 16px;
  text-align: left;
  font-size: 14px;
  color: var(--brand-ink);
}

.hot-tag {
  margin-right: 8px;
  cursor: pointer;
  background: white;
  border: 1px solid var(--brand-line);
  color: var(--brand-ink-soft);
}

.hot-tag:hover {
  background: var(--brand-primary);
  color: white;
}

/* Stats Section */
.stats-section {
  margin-top: -50px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  padding: 30px;
  border-radius: var(--brand-radius);
  box-shadow: var(--brand-shadow);
  text-align: center;
  transition: transform 0.3s ease;
  border: 1px solid var(--brand-line);
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 8px;
}

.stat-label {
  color: var(--brand-ink-soft);
  font-size: 16px;
}

/* Sections */
.section {
  padding: 60px 0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.section-title {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  margin: 0;
}

.job-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

@media (max-width: 768px) {
  .job-grid {
    grid-template-columns: 1fr;
  }
}

/* Company Cards */
.company-card {
  cursor: pointer;
  height: 100%;
  transition: all 0.3s ease;
  border: none;
  border-radius: 12px;
}

.company-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.company-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.company-logo {
  margin-bottom: 16px;
  background-color: #fff;
  border: 1px solid #f0f0f0;
}

.company-name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 10px 0;
}

.company-tags {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 6px;
  margin-bottom: 16px;
}

.small-tag {
  font-size: 12px;
  color: #909399;
  background: #f0f2f5;
  padding: 2px 8px;
  border-radius: 4px;
}

.company-footer {
  width: 100%;
  padding-top: 16px;
  border-top: 1px dashed #ebeef5;
}

.job-count {
  font-size: 14px;
  color: #606266;
}

.job-count b {
  color: #409EFF;
}

/* CTA Section */
.cta-section {
  padding-bottom: 80px;
}

.cta-card {
  background: linear-gradient(90deg, var(--brand-primary) 0%, var(--brand-primary-deep) 100%);
  border-radius: var(--brand-radius);
  padding: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: white;
  box-shadow: var(--brand-shadow);
}

.cta-content h2 {
  font-size: 32px;
  font-weight: 700;
  margin: 0 0 10px 0;
}

.cta-content p {
  font-size: 18px;
  opacity: 0.9;
  margin: 0;
}

.cta-actions {
  display: flex;
  gap: 16px;
}

@media (max-width: 992px) {
  .cta-card {
    flex-direction: column;
    text-align: center;
    gap: 30px;
  }
}
</style>
