<script setup lang="ts">
import { computed, ref } from 'vue'

type InterviewStatus = '待确认' | '已确认' | '已完成' | '已取消'

interface InterviewItem {
  id: number
  job: string
  company: string
  time: string
  type: string
  interviewer: string
  status: InterviewStatus
}

const statusFilter = ref<'全部' | InterviewStatus>('全部')
const interviews = ref<InterviewItem[]>([
  { id: 1, job: 'Java 后端开发工程师', company: '星河科技', time: '2026-04-25 14:00', type: '线上视频', interviewer: '技术负责人 王磊', status: '待确认' },
  { id: 2, job: '产品经理', company: '青木数据', time: '2026-04-26 10:30', type: '电话沟通', interviewer: 'HR 林薇', status: '已确认' },
  { id: 3, job: 'Vue3 前端工程师', company: '远帆智能', time: '2026-04-20 16:00', type: '现场面试', interviewer: '前端负责人 陈卓', status: '已完成' }
])

const filteredInterviews = computed(() => {
  return interviews.value.filter((item) => statusFilter.value === '全部' || item.status === statusFilter.value)
})

function updateStatus(item: InterviewItem, status: InterviewStatus) {
  item.status = status
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div>
        <div class="section-chip">面试邀请</div>
        <h1>集中管理所有面试安排</h1>
        <p>确认时间、查看面试人、跟踪面试状态，避免投递流程断在消息里。</p>
      </div>
      <el-select v-model="statusFilter" style="width: 160px;">
        <el-option label="全部" value="全部" />
        <el-option label="待确认" value="待确认" />
        <el-option label="已确认" value="已确认" />
        <el-option label="已完成" value="已完成" />
        <el-option label="已取消" value="已取消" />
      </el-select>
    </section>

    <section class="interview-list">
      <article v-for="item in filteredInterviews" :key="item.id" class="panel-card interview-card">
        <div>
          <div class="interview-top">
            <h2>{{ item.job }}</h2>
            <el-tag round :type="item.status === '待确认' ? 'warning' : item.status === '已确认' ? 'success' : 'info'">
              {{ item.status }}
            </el-tag>
          </div>
          <p>{{ item.company }} · {{ item.type }} · {{ item.interviewer }}</p>
          <div class="time-row">{{ item.time }}</div>
        </div>
        <div class="action-row">
          <el-button v-if="item.status === '待确认'" type="primary" round @click="updateStatus(item, '已确认')">确认面试</el-button>
          <el-button v-if="item.status !== '已取消'" round @click="updateStatus(item, '已取消')">改期/取消</el-button>
          <el-button v-if="item.status === '已确认'" plain round @click="updateStatus(item, '已完成')">标记完成</el-button>
        </div>
      </article>
    </section>
  </section>
</template>

<style scoped>
.page-shell,
.interview-list {
  display: grid;
  gap: 18px;
}

.header-card,
.interview-card {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  padding: 24px;
}

.header-card h1,
.interview-card h2 {
  margin: 10px 0 8px;
  color: var(--brand-title);
}

.header-card p,
.interview-card p {
  margin: 0;
  color: var(--brand-ink-soft);
}

.interview-top,
.action-row {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.time-row {
  margin-top: 16px;
  color: var(--brand-primary-deep);
  font-size: 20px;
  font-weight: 800;
}

@media (max-width: 760px) {
  .header-card,
  .interview-card {
    flex-direction: column;
  }
}
</style>
