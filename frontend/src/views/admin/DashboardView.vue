<script setup lang="ts">
import AppSectionTitle from '@/components/common/AppSectionTitle.vue'

const overviewMetrics = [
  { label: '总用户数', value: '1,280', trend: '+8.4%', note: '较上周增长 96 人' },
  { label: '企业入驻', value: '86', trend: '+12%', note: '近 7 天新增 9 家' },
  { label: '在线职位', value: '415', trend: '+5.6%', note: '待审核 28 个' },
  { label: '今日投递', value: '132', trend: '+18%', note: '活跃投递持续升温' },
]

const healthCards = [
  { title: '审核健康度', value: '91%', desc: '企业、职位与举报处理保持稳定', percent: 91, color: '#0f766e' },
  { title: '用户活跃度', value: '68%', desc: '求职者登录与投递行为较活跃', percent: 68, color: '#135b4b' },
  { title: '风险待办', value: '14', desc: '待处理举报、违规职位与企业资质', percent: 42, color: '#f59e0b' },
]

const reviewQueue = [
  { title: '云图人才', tag: '企业审核', status: '待审核', meta: '营业执照已上传 · 资料完整度 92%' },
  { title: 'Java 后端开发工程师', tag: '职位审核', status: '待复核', meta: '薪资描述清晰度偏低 · 需要人工确认' },
  { title: '“薪资虚标” 举报', tag: '举报处理', status: '高优先级', meta: '涉及 1 个职位 · 2 条用户反馈' },
]

const todaySignals = [
  { label: '新注册用户', value: '48', detail: '其中招聘者 11 人，求职者 37 人' },
  { label: '通过审核企业', value: '7', detail: '审核通过率 77%' },
  { label: '下架职位', value: '3', detail: '涉及薪资不实与岗位重复发布' },
]

const activityLog = [
  { time: '09:20', text: '通过企业审核：北辰科技', type: 'success' },
  { time: '10:05', text: '驳回职位：商务拓展经理', type: 'warning' },
  { time: '11:15', text: '处理举报：薪资区间与实际不符', type: 'danger' },
  { time: '14:30', text: '新增用户：招聘者账号 5 个', type: 'primary' },
]
</script>

<template>
  <section class="admin-page">
    <AppSectionTitle
      eyebrow="数据统计面板"
      title="平台治理总览"
      description="集中查看用户规模、企业审核、职位治理和举报处理进度，帮助管理员快速判断平台健康度与风险点。"
    />

    <div class="hero-panel">
      <div class="hero-copy">
        <div class="eyebrow">Admin Overview</div>
        <h3>把治理动作收敛到一个统一控制台</h3>
        <p>
          这里保留平台最重要的运营信号：增长、审核、风险和待办。所有卡片采用轻量信息块的形式，方便快速扫读。
        </p>
        <div class="hero-actions">
          <el-button type="primary">查看待审核</el-button>
          <el-button>导出日报</el-button>
        </div>
      </div>
      <div class="hero-side">
        <div v-for="item in overviewMetrics.slice(0, 2)" :key="item.label" class="mini-hero-card">
          <div class="mini-hero-value">{{ item.value }}</div>
          <div class="mini-hero-label">{{ item.label }}</div>
          <div class="mini-hero-note">{{ item.note }}</div>
        </div>
      </div>
    </div>

    <div class="metric-grid">
      <el-card v-for="item in overviewMetrics" :key="item.label" shadow="never" class="metric-card">
        <div class="metric-top">
          <span class="metric-label">{{ item.label }}</span>
          <el-tag size="small" type="success" effect="light">{{ item.trend }}</el-tag>
        </div>
        <div class="metric-value">{{ item.value }}</div>
        <p class="metric-note">{{ item.note }}</p>
      </el-card>
    </div>

    <div class="content-grid">
      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>治理健康度</span>
            <el-tag type="success" effect="light">实时更新</el-tag>
          </div>
        </template>
        <div class="health-list">
          <div v-for="item in healthCards" :key="item.title" class="health-item">
            <div class="health-row">
              <div>
                <div class="health-title">{{ item.title }}</div>
                <div class="health-desc">{{ item.desc }}</div>
              </div>
              <div class="health-value">{{ item.value }}</div>
            </div>
            <el-progress :percentage="item.percent" :stroke-width="10" :color="item.color" />
          </div>
        </div>
      </el-card>

      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>今日信号</span>
            <el-tag type="info" effect="light">平台脉搏</el-tag>
          </div>
        </template>
        <div class="signal-list">
          <div v-for="item in todaySignals" :key="item.label" class="signal-item">
            <div>
              <div class="signal-label">{{ item.label }}</div>
              <div class="signal-detail">{{ item.detail }}</div>
            </div>
            <div class="signal-value">{{ item.value }}</div>
          </div>
        </div>
      </el-card>
    </div>

    <div class="bottom-grid">
      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>待处理队列</span>
            <el-tag type="warning" effect="light">优先级排序</el-tag>
          </div>
        </template>
        <el-timeline>
          <el-timeline-item
            v-for="item in reviewQueue"
            :key="item.title"
            :type="item.status === '高优先级' ? 'danger' : item.status === '待审核' ? 'warning' : 'primary'"
            :timestamp="item.tag"
          >
            <div class="queue-title">{{ item.title }}</div>
            <div class="queue-meta">{{ item.meta }}</div>
            <el-tag size="small" effect="light">{{ item.status }}</el-tag>
          </el-timeline-item>
        </el-timeline>
      </el-card>

      <el-card shadow="never" class="panel-card">
        <template #header>
          <div class="panel-header">
            <span>操作记录</span>
            <el-tag type="primary" effect="light">最近 4 条</el-tag>
          </div>
        </template>
        <div class="activity-list">
          <div v-for="item in activityLog" :key="item.time" class="activity-item">
            <span class="activity-time">{{ item.time }}</span>
            <div class="activity-text">
              <el-tag size="small" :type="item.type" effect="light">{{ item.type }}</el-tag>
              <span>{{ item.text }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </section>
</template>

<style scoped>
.admin-page {
  display: grid;
  gap: 24px;
}

.hero-panel {
  display: grid;
  grid-template-columns: 1.6fr 1fr;
  gap: 20px;
  padding: 28px;
  border-radius: var(--brand-radius-xl);
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.95), rgba(244, 247, 252, 0.92));
  border: 1px solid rgba(148, 163, 184, 0.14);
  box-shadow: var(--brand-shadow-soft);
}

.hero-copy {
  display: grid;
  gap: 14px;
}

.eyebrow {
  display: inline-flex;
  width: fit-content;
  border-radius: 999px;
  padding: 6px 12px;
  background: rgba(19, 91, 75, 0.08);
  color: var(--brand-primary-deep);
  font-size: 12px;
  font-weight: 700;
}

.hero-copy h3 {
  margin: 0;
  color: var(--brand-title);
  font-size: clamp(24px, 3vw, 36px);
  line-height: 1.25;
}

.hero-copy p {
  margin: 0;
  max-width: 780px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 8px;
}

.hero-side {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
  align-self: stretch;
}

.mini-hero-card {
  padding: 18px;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.86);
  border: 1px solid rgba(148, 163, 184, 0.14);
}

.mini-hero-value {
  color: var(--brand-title);
  font-size: 28px;
  font-weight: 800;
}

.mini-hero-label {
  margin-top: 6px;
  color: var(--brand-ink);
  font-weight: 600;
}

.mini-hero-note {
  margin-top: 6px;
  color: var(--brand-ink-soft);
  font-size: 13px;
  line-height: 1.7;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.metric-card {
  border: none;
  border-radius: 20px;
  box-shadow: var(--brand-shadow-soft);
  background: var(--brand-card);
}

.metric-top,
.panel-header,
.health-row,
.signal-item,
.activity-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.metric-label,
.health-title,
.signal-label {
  color: var(--brand-ink);
  font-weight: 700;
}

.metric-value {
  margin-top: 16px;
  color: var(--brand-title);
  font-size: 32px;
  font-weight: 800;
}

.metric-note,
.health-desc,
.signal-detail {
  margin: 10px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.7;
  font-size: 14px;
}

.content-grid,
.bottom-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}

.panel-card {
  border: none;
  border-radius: 22px;
  background: var(--brand-card);
  box-shadow: var(--brand-shadow-soft);
}

.health-list,
.signal-list,
.activity-list {
  display: grid;
  gap: 16px;
}

.health-item,
.signal-item {
  padding: 16px 0;
  border-bottom: 1px solid rgba(148, 163, 184, 0.12);
}

.health-item:last-child,
.signal-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.health-value,
.signal-value {
  color: var(--brand-primary-deep);
  font-size: 24px;
  font-weight: 800;
}

.queue-title {
  color: var(--brand-title);
  font-weight: 700;
}

.queue-meta {
  margin: 6px 0 10px;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.activity-item {
  align-items: flex-start;
  padding: 14px 0;
  border-bottom: 1px dashed rgba(148, 163, 184, 0.18);
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-time {
  flex: 0 0 56px;
  color: var(--brand-ink-soft);
  font-weight: 600;
}

.activity-text {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;
  color: var(--brand-ink);
  line-height: 1.7;
}

@media (max-width: 1200px) {
  .metric-grid,
  .content-grid,
  .bottom-grid,
  .hero-panel {
    grid-template-columns: 1fr;
  }
}
</style>
