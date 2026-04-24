<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

type ApplicationStage = '已投递' | '已查看' | '初筛通过' | '待沟通' | '面试中' | '已录用' | '已拒绝'

interface TimelineItem {
  stage: ApplicationStage
  time: string
  description: string
}

const route = useRoute()
const router = useRouter()
const activeStage = ref<ApplicationStage>('面试中')

const stages: ApplicationStage[] = ['已投递', '已查看', '初筛通过', '待沟通', '面试中', '已录用']

const timeline = ref<TimelineItem[]>([
  { stage: '已投递', time: '2026-04-18 10:24', description: '你已使用在线简历投递该职位' },
  { stage: '已查看', time: '2026-04-18 15:40', description: '招聘方已查看你的简历' },
  { stage: '初筛通过', time: '2026-04-19 11:12', description: '你的经历与岗位要求匹配，进入下一轮沟通' },
  { stage: '待沟通', time: '2026-04-20 09:30', description: 'HR 已发起沟通，请留意消息中心' },
  { stage: '面试中', time: '2026-04-22 14:00', description: '一面已安排，等待面试反馈' }
])

const progress = computed(() => {
  const index = stages.indexOf(activeStage.value)
  return Math.max(12, ((index + 1) / stages.length) * 100)
})

function withdrawApplication() {
  activeStage.value = '已拒绝'
  timeline.value.push({
    stage: '已拒绝',
    time: '2026-04-23 18:00',
    description: '你已撤回本次投递，流程结束'
  })
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card hero-card">
      <div>
        <div class="section-chip">投递详情 #{{ route.params.applicationId }}</div>
        <h1>Java 后端开发工程师</h1>
        <p>星河科技 · 上海 · 25K-40K · 3-5年 · 本科</p>
        <div class="tag-row">
          <el-tag round>Spring Boot</el-tag>
          <el-tag round>MySQL</el-tag>
          <el-tag round>招聘系统</el-tag>
          <el-tag round>五险一金</el-tag>
        </div>
      </div>
      <div class="status-box">
        <span>当前状态</span>
        <strong>{{ activeStage }}</strong>
        <el-progress :percentage="progress" :show-text="false" />
      </div>
    </section>

    <section class="content-grid">
      <div class="panel-card section-card">
        <div class="section-title">投递进度</div>
        <el-steps :active="stages.indexOf(activeStage)" finish-status="success" align-center>
          <el-step v-for="stage in stages" :key="stage" :title="stage" />
        </el-steps>

        <el-timeline class="timeline">
          <el-timeline-item v-for="item in timeline" :key="item.time" :timestamp="item.time" placement="top">
            <h3>{{ item.stage }}</h3>
            <p>{{ item.description }}</p>
          </el-timeline-item>
        </el-timeline>
      </div>

      <aside class="side-column">
        <div class="panel-card section-card">
          <div class="section-title">下一步建议</div>
          <p class="muted">准备 2 个项目亮点、1 段自我介绍，并提前确认面试时间。</p>
          <div class="action-row">
            <el-button type="primary" round @click="router.push('/candidate/messages')">查看沟通</el-button>
            <el-button round @click="router.push('/candidate/resume')">更新简历</el-button>
          </div>
        </div>

        <div class="panel-card section-card danger-card">
          <div class="section-title">流程操作</div>
          <p class="muted">如果已经不考虑该机会，可以撤回投递，撤回后招聘方将不再推进。</p>
          <el-button type="danger" plain round @click="withdrawApplication">撤回投递</el-button>
        </div>
      </aside>
    </section>
  </section>
</template>

<style scoped>
.page-shell,
.side-column {
  display: grid;
  gap: 20px;
}

.hero-card {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  padding: 26px;
}

.hero-card h1 {
  margin: 12px 0 8px;
  color: var(--brand-title);
  font-size: 34px;
}

.hero-card p,
.muted,
.timeline p {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.tag-row,
.action-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
}

.status-box {
  min-width: 260px;
  padding: 20px;
  border-radius: 20px;
  background: rgba(31, 107, 87, 0.08);
}

.status-box span {
  color: var(--brand-ink-soft);
}

.status-box strong {
  display: block;
  margin: 8px 0 14px;
  color: var(--brand-title);
  font-size: 30px;
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) 340px;
  gap: 20px;
}

.section-card {
  padding: 24px;
}

.section-title {
  margin-bottom: 16px;
  color: var(--brand-title);
  font-size: 22px;
  font-weight: 800;
}

.timeline {
  margin-top: 28px;
}

.timeline h3 {
  margin: 0 0 6px;
  color: var(--brand-title);
}

.danger-card {
  border-color: rgba(220, 38, 38, 0.16);
}

@media (max-width: 1000px) {
  .hero-card,
  .content-grid {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
