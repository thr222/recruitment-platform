<script setup lang="ts">
import { computed, ref } from 'vue'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

interface CompanyItem {
  id: number
  name: string
  industry: string
  city: string
  employeeScale: string
  contact: string
  status: '待审核' | '已通过' | '已驳回'
  material: string
  note: string
}

const companyList = ref<CompanyItem[]>([
  {
    id: 1,
    name: '云图人才',
    industry: '人力资源服务',
    city: '上海',
    employeeScale: '50-150 人',
    contact: 'hr@cloudtalent.cn',
    status: '待审核',
    material: '营业执照、法人信息、招聘授权函已上传',
    note: '资料完整，但需要人工确认招聘主体与授权范围。',
  },
  {
    id: 2,
    name: '北辰科技',
    industry: '企业服务',
    city: '北京',
    employeeScale: '150-500 人',
    contact: 'jobs@beichen.tech',
    status: '已通过',
    material: '统一社会信用代码、公司介绍、岗位样例已上传',
    note: '历史审核记录正常，可继续开放职位发布。',
  },
  {
    id: 3,
    name: '青木数据',
    industry: '数据分析',
    city: '深圳',
    employeeScale: '20-50 人',
    contact: 'talent@qingmu.ai',
    status: '已驳回',
    material: '营业执照不清晰、主体说明不足',
    note: '需补充主体材料后再次提交审核。',
  },
])

const selectedCompany = ref<CompanyItem | null>(companyList.value[0])
const detailVisible = ref(false)

const summaryCards = computed(() => [
  { label: '待审核企业', value: companyList.value.filter((item) => item.status === '待审核').length, type: 'warning' as const },
  { label: '已通过企业', value: companyList.value.filter((item) => item.status === '已通过').length, type: 'success' as const },
  { label: '已驳回企业', value: companyList.value.filter((item) => item.status === '已驳回').length, type: 'danger' as const },
  { label: '累计提交', value: companyList.value.length, type: 'primary' as const },
])

const openDetail = (item: CompanyItem) => {
  selectedCompany.value = item
  detailVisible.value = true
}

const statusTypeMap = {
  待审核: 'warning',
  已通过: 'success',
  已驳回: 'danger',
} as const

const getStatusType = (status: CompanyItem['status']) => statusTypeMap[status]
</script>

<template>
  <section class="admin-page">
    <AppSectionTitle
      eyebrow="企业审核"
      title="企业主体与资质审核"
      description="围绕企业主体、营业执照、招聘授权等信息进行审核，保障平台招聘内容的真实性与合规性。"
    />

    <div class="stat-grid">
      <el-card v-for="item in summaryCards" :key="item.label" shadow="never" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
        <el-tag :type="item.type" effect="light">{{ item.label }}</el-tag>
      </el-card>
    </div>

    <div class="company-grid">
      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>待处理企业</span>
            <el-tag effect="light" type="warning">人工审核队列</el-tag>
          </div>
        </template>

        <div class="company-list">
          <div v-for="item in companyList" :key="item.id" class="company-item">
            <div class="company-main">
              <div class="company-name-row">
                <div class="company-name">{{ item.name }}</div>
                <el-tag :type="getStatusType(item.status)" effect="light">{{ item.status }}</el-tag>
              </div>
              <div class="company-meta">{{ item.industry }} · {{ item.city }} · {{ item.employeeScale }}</div>
              <div class="company-note">{{ item.note }}</div>
            </div>
            <div class="company-actions">
              <el-button type="primary" text @click="openDetail(item)">查看资料</el-button>
              <el-button type="success" text>通过</el-button>
              <el-button type="danger" text>驳回</el-button>
            </div>
          </div>
        </div>
      </el-card>

      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>审核标准</span>
            <el-tag effect="light" type="info">统一口径</el-tag>
          </div>
        </template>

        <el-timeline>
          <el-timeline-item type="success" timestamp="主体核验">
            检查公司名称、统一社会信用代码与营业执照是否一致。
          </el-timeline-item>
          <el-timeline-item type="warning" timestamp="招聘授权">
            确认企业具有岗位发布与招聘行为的授权范围。
          </el-timeline-item>
          <el-timeline-item type="info" timestamp="风险提示">
            对异常行业、高频注册和重复主体进行二次人工确认。
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </div>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <span>审核台账</span>
          <el-tag effect="light" type="success">本地 mock 数据</el-tag>
        </div>
      </template>

      <el-table :data="companyList" stripe>
        <el-table-column prop="name" label="企业名称" min-width="160" />
        <el-table-column prop="industry" label="行业" width="140" />
        <el-table-column prop="city" label="城市" width="120" />
        <el-table-column prop="employeeScale" label="规模" width="130" />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" effect="light">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="contact" label="联系人" min-width="180" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDetail(row)">详情</el-button>
            <el-button link type="success">通过</el-button>
            <el-button link type="danger">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="detailVisible" title="企业审核详情" width="640px">
      <div v-if="selectedCompany" class="detail-grid">
        <div>
          <div class="detail-label">企业名称</div>
          <div class="detail-value">{{ selectedCompany.name }}</div>
        </div>
        <div>
          <div class="detail-label">行业 / 规模</div>
          <div class="detail-value">{{ selectedCompany.industry }} · {{ selectedCompany.employeeScale }}</div>
        </div>
        <div>
          <div class="detail-label">联系邮箱</div>
          <div class="detail-value">{{ selectedCompany.contact }}</div>
        </div>
        <div>
          <div class="detail-label">材料情况</div>
          <div class="detail-value">{{ selectedCompany.material }}</div>
        </div>
        <div>
          <div class="detail-label">审核备注</div>
          <div class="detail-value">{{ selectedCompany.note }}</div>
        </div>
      </div>
      <template #footer>
        <el-button @click="detailVisible = false">关闭</el-button>
        <el-button type="success">通过</el-button>
        <el-button type="danger">驳回</el-button>
      </template>
    </el-dialog>
  </section>
</template>

<style scoped>
.admin-page {
  display: grid;
  gap: 24px;
}

.stat-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.company-grid {
  display: grid;
  grid-template-columns: 1.4fr 0.9fr;
  gap: 18px;
}

.stat-card,
.panel-card {
  border: none;
  border-radius: 22px;
  box-shadow: var(--brand-shadow-soft);
  background: var(--brand-card);
}

.stat-label,
.company-name,
.detail-label {
  color: var(--brand-ink);
  font-weight: 700;
}

.stat-value {
  margin: 12px 0 16px;
  color: var(--brand-title);
  font-size: 30px;
  font-weight: 800;
}

.panel-header,
.company-name-row,
.company-item {
  display: flex;
  gap: 12px;
}

.panel-header,
.company-name-row {
  align-items: center;
  justify-content: space-between;
}

.company-list {
  display: grid;
  gap: 16px;
}

.company-item {
  align-items: flex-start;
  justify-content: space-between;
  padding: 16px 0;
  border-bottom: 1px solid rgba(148, 163, 184, 0.12);
}

.company-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.company-main {
  display: grid;
  gap: 8px;
}

.company-meta,
.company-note,
.detail-value {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.company-actions {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 8px;
}

.detail-grid {
  display: grid;
  gap: 16px;
}

@media (max-width: 1200px) {
  .stat-grid,
  .company-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 720px) {
  .stat-grid {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 560px) {
  .stat-grid {
    grid-template-columns: 1fr;
  }
}
</style>
