<script setup lang="ts">
import { computed, ref } from 'vue'
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

type ReportType = '职位举报' | '企业举报' | '用户举报'
type ReportStatus = '待处理' | '处理中' | '已关闭'

interface ReportItem {
  id: number
  type: ReportType
  target: string
  reporter: string
  reason: string
  status: ReportStatus
  priority: '高' | '中' | '低'
  time: string
}

const activeTab = ref('all')
const detailVisible = ref(false)
const selectedReport = ref<ReportItem | null>(null)

const reports = ref<ReportItem[]>([
  {
    id: 1,
    type: '职位举报',
    target: 'Java 后端开发工程师',
    reporter: '用户：张同学',
    reason: '薪资范围与实际面试口径不一致，存在夸大宣传嫌疑。',
    status: '待处理',
    priority: '高',
    time: '10 分钟前',
  },
  {
    id: 2,
    type: '企业举报',
    target: '云图人才',
    reporter: '用户：陈先生',
    reason: '企业主体描述与招聘授权材料不匹配。',
    status: '处理中',
    priority: '中',
    time: '2 小时前',
  },
  {
    id: 3,
    type: '用户举报',
    target: '账号：hr_rose',
    reporter: '平台巡检',
    reason: '疑似批量发布重复职位，已触发风控。',
    status: '已关闭',
    priority: '低',
    time: '昨天',
  },
])

const statusMap: Record<ReportStatus, 'warning' | 'primary' | 'info'> = {
  待处理: 'warning',
  处理中: 'primary',
  已关闭: 'info',
}

const filteredReports = computed(() => {
  if (activeTab.value === 'all') return reports.value
  return reports.value.filter((item) => item.type === activeTab.value)
})

const summaryCards = computed(() => [
  { label: '待处理举报', value: reports.value.filter((item) => item.status === '待处理').length, type: 'warning' as const },
  { label: '处理中', value: reports.value.filter((item) => item.status === '处理中').length, type: 'primary' as const },
  { label: '已关闭', value: reports.value.filter((item) => item.status === '已关闭').length, type: 'info' as const },
  { label: '总举报数', value: reports.value.length, type: 'success' as const },
])

const openDetail = (item: ReportItem) => {
  selectedReport.value = item
  detailVisible.value = true
}

const getStatusType = (status: ReportStatus) => statusMap[status]
</script>

<template>
  <section class="admin-page">
    <AppSectionTitle
      eyebrow="举报处理"
      title="内容风险与违规处理中心"
      description="对职位、企业和用户举报进行统一分流、记录和处置，提升平台治理效率。"
    />

    <div class="stat-grid">
      <el-card v-for="item in summaryCards" :key="item.label" shadow="never" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ item.value }}</div>
        <el-tag :type="item.type" effect="light">{{ item.label }}</el-tag>
      </el-card>
    </div>

    <el-card shadow="never" class="panel-card">
      <template #header>
        <div class="panel-header">
          <div>
            <div class="panel-title">举报分类</div>
            <div class="panel-desc">可以按举报类型快速分流，减少治理等待时间。</div>
          </div>
          <el-tag effect="light" type="danger">风险处理中</el-tag>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="职位举报" name="职位举报" />
        <el-tab-pane label="企业举报" name="企业举报" />
        <el-tab-pane label="用户举报" name="用户举报" />
      </el-tabs>

      <el-table :data="filteredReports" stripe>
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="target" label="举报对象" min-width="200" />
        <el-table-column prop="reporter" label="举报来源" min-width="160" />
        <el-table-column prop="reason" label="举报描述" min-width="260" show-overflow-tooltip />
        <el-table-column label="优先级" width="100">
          <template #default="{ row }">
            <el-tag :type="row.priority === '高' ? 'danger' : row.priority === '中' ? 'warning' : 'info'" effect="light">
              {{ row.priority }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" effect="light">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="提交时间" width="120" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDetail(row)">详情</el-button>
            <el-button link type="success">受理</el-button>
            <el-button link type="danger">关闭</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <div class="bottom-grid">
      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>处理原则</span>
            <el-tag effect="light" type="warning">统一标准</el-tag>
          </div>
        </template>

        <el-timeline>
          <el-timeline-item type="danger" timestamp="优先级">
            高优先级举报优先进入人工核查。
          </el-timeline-item>
          <el-timeline-item type="warning" timestamp="证据">
            优先查看举报截图、聊天记录与发布页内容。
          </el-timeline-item>
          <el-timeline-item type="success" timestamp="闭环">
            处理结果需同步记录并可追溯。
          </el-timeline-item>
        </el-timeline>
      </el-card>

      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>当前队列摘要</span>
            <el-tag effect="light" type="info">最新状态</el-tag>
          </div>
        </template>

        <div class="queue-list">
          <div v-for="item in filteredReports" :key="item.id" class="queue-item">
            <div>
              <div class="queue-title">{{ item.target }}</div>
              <div class="queue-meta">{{ item.reason }}</div>
            </div>
            <el-tag :type="getStatusType(item.status)" effect="light">{{ item.time }}</el-tag>
          </div>
        </div>
      </el-card>
    </div>

    <el-dialog v-model="detailVisible" title="举报详情" width="640px">
      <div v-if="selectedReport" class="detail-grid">
        <div>
          <div class="detail-label">举报类型</div>
          <div class="detail-value">{{ selectedReport.type }}</div>
        </div>
        <div>
          <div class="detail-label">举报对象</div>
          <div class="detail-value">{{ selectedReport.target }}</div>
        </div>
        <div>
          <div class="detail-label">举报来源</div>
          <div class="detail-value">{{ selectedReport.reporter }}</div>
        </div>
        <div>
          <div class="detail-label">举报描述</div>
          <div class="detail-value">{{ selectedReport.reason }}</div>
        </div>
        <div>
          <div class="detail-label">处理状态</div>
          <div class="detail-value">{{ selectedReport.status }}</div>
        </div>
      </div>
      <template #footer>
        <el-button @click="detailVisible = false">关闭</el-button>
        <el-button type="primary">受理</el-button>
        <el-button type="success">标记已处理</el-button>
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

.bottom-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
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
.panel-title,
.queue-title,
.detail-label {
  color: var(--brand-title);
  font-weight: 700;
}

.stat-value {
  margin: 12px 0 16px;
  color: var(--brand-primary-deep);
  font-size: 30px;
  font-weight: 800;
}

.panel-header,
.queue-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.panel-desc,
.queue-meta,
.detail-value {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.queue-list,
.detail-grid {
  display: grid;
  gap: 16px;
}

.queue-item {
  padding: 14px 0;
  border-bottom: 1px solid rgba(148, 163, 184, 0.12);
}

.queue-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.detail-grid {
  grid-template-columns: 1fr;
}

@media (max-width: 1200px) {
  .stat-grid,
  .bottom-grid {
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
