<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search, ArrowRight } from '@element-plus/icons-vue'
import { searchJobs } from '@/api/modules/job'
import type { JobListItem } from '@/types/job'
import JobCard from '@/components/business/JobCard.vue'
import Hero from '@/components/wellfound/Hero.vue'
import LogoLoop from '@/components/common/LogoLoop.vue'

const router = useRouter()
const searchQuery = ref('')
const recommendedJobs = ref<JobListItem[]>([])
const loading = ref(false)

const partnerLogos = [
  { node: 'NOTION', title: 'Notion' },
  { node: 'IFTTT', title: 'IFTTT' },
  { node: 'Postmates', title: 'Postmates' },
  { node: 'PLAID', title: 'Plaid' },
  { node: 'Airtable', title: 'Airtable' },
  { node: 'Robinhood', title: 'Robinhood' },
  { node: 'COINBASE', title: 'Coinbase' },
  { node: 'STRIPE', title: 'Stripe' },
  { node: 'FIGMA', title: 'Figma' }
]

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
    <Hero />

    <!-- Exact 1:1 Divider -->
    <div class="w-full h-px bg-gray-200"></div>

    <!-- 1:1 Subtitle & CTA Section (Directly after Hero) -->
    <section class="py-24 bg-white text-center animate-zoom-in">
      <div class="max-w-4xl mx-auto px-6 space-y-12">
        <h2 class="text-3xl md:text-[40px] font-black text-black tracking-tight">
          Where startups and job seekers connect
        </h2>
        
        <div class="flex flex-col sm:flex-row items-center justify-center gap-6 pt-4">
          <router-link 
            to="/login" 
            class="w-full sm:w-auto px-10 py-4 bg-[#0a0a0a] text-white text-lg font-bold rounded-xl hover:bg-black transition-colors"
          >
            Find your next hire
          </router-link>
          <router-link 
            to="/jobs" 
            class="w-full sm:w-auto px-10 py-4 bg-white text-black text-lg font-bold rounded-xl border border-gray-200 hover:border-gray-300 transition-colors"
          >
            Find your next job
          </router-link>
        </div>
      </div>
    </section>

    <!-- Trust & Stats Section -->
    <section class="py-24 bg-[#2D1132] text-white overflow-hidden">
      <div class="max-w-6xl mx-auto px-6">
        <!-- Stats Grid -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-12 text-center mb-24">
          <div class="space-y-2">
            <div class="text-6xl md:text-7xl font-black tracking-tighter">800万+</div>
            <div class="text-lg font-bold opacity-80 uppercase tracking-widest">成功匹配</div>
          </div>
          <div class="space-y-2">
            <div class="text-6xl md:text-7xl font-black tracking-tighter">15万+</div>
            <div class="text-lg font-bold opacity-80 uppercase tracking-widest">技术职位</div>
          </div>
          <div class="space-y-2">
            <div class="text-6xl md:text-7xl font-black tracking-tighter">1000万+</div>
            <div class="text-lg font-bold opacity-80 uppercase tracking-widest">入驻人才</div>
          </div>
        </div>

        <!-- Logo Cloud with LogoLoop -->
        <div class="space-y-12">
          <LogoLoop
            :logos="partnerLogos"
            :speed="40"
            direction="left"
            :logoHeight="32"
            :gap="80"
            scaleOnHover
            class="opacity-70 grayscale invert brightness-0 font-black tracking-tighter"
          />
          <div class="text-center text-sm font-bold opacity-50 tracking-widest uppercase">
            已有数千家顶尖初创企业加入我们
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
  background: linear-gradient(135deg, #0A66C2 0%, #F8FAFC 100%);
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
  text-shadow: 0 0 20px rgba(10, 102, 194, 0.2);
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
