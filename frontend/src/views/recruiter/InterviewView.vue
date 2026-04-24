<script setup lang="ts">
import { computed, ref } from 'vue'

type InterviewStatus = '待安排' | '已确认' | '已完成' | '已取消'

interface InterviewItem {
  id: number
  candidate: string
  job: string
  time: string
  interviewer: string
  status: InterviewStatus
  type: string
}

const activeTab = ref<'全部' | InterviewStatus>('全部')

const interviews = ref<InterviewItem[]>([
  {
    id: 1,
    candidate: '王同学',
    job: 'Java 后端开发工程师',
    time: '2026-04-23 10:00',
    interviewer: '张经理',
    status: '已确认',
    type: '一面'
  },
  {
    id: 2,
    candidate: '陈同学',
    job: '产品经理',
    time: '2026-04-23 14:30',
    interviewer: '李总监',
    status: '待安排',
    type: '二面'
  },
  {
    id: 3,
    candidate: '李同学',
    job: '测试开发工程师',
    time: '2026-04-22 16:00',
    interviewer: 'HR Lily',
    status: '已完成',
    type: 'HR 面'
  },
  {
    id: 4,
    candidate: '赵同学',
    job: '前端开发工程师',
    time: '2026-04-24 09:30',
    interviewer: '前端负责人',
    status: '已取消',
    type: '一面'
  }
])

const stats = computed(() => [
  { label: '待安排', value: interviews.value.filter((item) => item.status === '待安排').length },
  { label: '已确认', value: interviews.value.filter((item) => item.status === '已确认').length },
  { label: '已完成', value: interviews.value.filter((item) => item.status === '已完成').length },
  { label: '已取消', value: interviews.value.filter((item) => item.status === '已取消').length }
])

const filteredInterviews = computed(() => {
  return interviews.value.filter((item) => activeTab.value === '全部' || item.status === activeTab.value)
})
</script>

<template>
  <section class="page-shell">
    <section class="header-card panel-card">
      <div>
        <div class="section-chip">面试邀请管理</div>
        <h1>统一安排面试、确认时间、查看状态与反馈。</h1>
        <p>适合招聘方管理一面、二面、HR 面和面试结果流转，全部基于本地 mock 数据展示。</p>
      </div>
      <div class="header-actions">
        <el-button type="primary" round>新建面试邀请</el-button>
        <el-button round>导出日程</el-button>
      </div>
    </section>

    <section class="summary-grid">
      <article v-for="item in stats" :key="item.label" class="summary-card panel-card">
        <div class="summary-value">{{ item.value }}</div>
        <div class="summary-label">{{ item.label }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card content-card">
        <div class="content-header">
          <div>
            <div class="section-title">面试列表</div>
            <div class="section-subtitle">按状态切换查看</div>
          </div>
          <el-segmented v-model="activeTab" :options="['全部', '待安排', '已确认', '已完成', '已取消']" />
        </div>

        <el-table :data="filteredInterviews" size="large" class="table-reset">
          <el-table-column prop="candidate" label="候选人" width="120" />
          <el-table-column prop="job" label="职位" min-width="170" />
          <el-table-column prop="type" label="轮次" width="100" />
          <el-table-column prop="time" label="时间" width="170" />
          <el-table-column prop="interviewer" label="面试官" width="120" />
          <el-table-column label="状态" width="120">
            <template #default="{ row }">
              <el-tag
                :type="row.status === '已确认' ? 'success' : row.status === '待安排' ? 'warning' : row.status === '已完成' ? 'info' : 'danger'"
                effect="light"
                round
              >
                {{ row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="220" fixed="right">
            <template #default>
              <el-button link type="primary">改期</el-button>
              <el-button link>发送提醒</el-button>
              <el-button link>反馈记录</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="side-column">
        <div class="panel-card content-card">
          <div class="section-title">流程提醒</div>
          <el-timeline class="timeline-reset">
            <el-timeline-item timestamp="10:00" placement="top">
              王同学一面已确认，建议提前发送职位介绍。
            </el-timeline-item>
            <el-timeline-item timestamp="14:30" placement="top">
              陈同学二面待安排，需要确认面试官空档。
            </el-timeline-item>
            <el-timeline-item timestamp="16:00" placement="top">
              李同学 HR 面已完成，等待下一步反馈。
            </el-timeline-item>
          </el-timeline>
        </div>

        <div class="panel-card content-card">
          <div class="section-title">面试建议</div>
          <ul class="tip-list">
            <li>统一把线上会议链接提前发给候选人。</li>
            <li>对已确认面试，提前 24 小时提醒。</li>
            <li>面试后建议当天填写反馈，避免遗忘。</li>
          </ul>
        </div>
      </div>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  padding: 24px;
}

.header-card h1 {
  margin: 10px 0 8px;
  color: var(--brand-title);
  font-size: 28px;
  line-height: 1.2;
}

.header-card p {
  margin: 0;
  color: var(--brand-ink-soft);
  max-width: 660px;
}

.header-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.summary-card {
  padding: 20px;
}

.summary-value {
  color: var(--brand-title);
  font-size: 32px;
  font-weight: 800;
}

.summary-label {
  margin-top: 6px;
  color: var(--brand-ink-soft);
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.5fr) minmax(320px, 0.7fr);
  gap: 16px;
}

.content-card {
  padding: 24px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-end;
  margin-bottom: 16px;
}

.section-title {
  color: var(--brand-title);
  font-size: 22px;
  font-weight: 800;
}

.section-subtitle {
  margin-top: 6px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.table-reset {
  width: 100%;
}

.side-column {
  display: grid;
  gap: 16px;
}

.timeline-reset {
  margin-top: 16px;
}

.tip-list {
  margin: 16px 0 0;
  padding-left: 18px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

@media (max-width: 1200px) {
  .header-card,
  .content-grid {
    grid-template-columns: 1fr;
    display: grid;
  }

  .summary-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .summary-grid {
    grid-template-columns: 1fr;
  }
}
</style>
