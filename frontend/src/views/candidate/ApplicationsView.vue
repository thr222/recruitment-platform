<script setup lang="ts">
const applicationStats = [
  { label: '已投递', value: '12' },
  { label: '面试中', value: '3' },
  { label: '已拒绝', value: '2' },
  { label: '本周新增', value: '4' }
]

const applications = [
  {
    job: 'Java 后端开发工程师',
    company: '星河科技',
    city: '上海',
    salary: '25K-40K',
    status: '面试中',
    updatedAt: '2026-04-08 14:30',
    stage: '等待面试安排'
  },
  {
    job: 'Vue3 前端工程师',
    company: '远帆智能',
    city: '杭州',
    salary: '22K-35K',
    status: '待处理',
    updatedAt: '2026-04-07 09:20',
    stage: '等待初筛'
  },
  {
    job: '产品经理',
    company: '青木数据',
    city: '北京',
    salary: '18K-28K',
    status: '已拒绝',
    updatedAt: '2026-04-05 18:00',
    stage: '不匹配岗位要求'
  }
]

const statusMap: Record<string, 'success' | 'warning' | 'danger' | 'info'> = {
  面试中: 'warning',
  待处理: 'info',
  已拒绝: 'danger',
  已通过: 'success'
}
</script>

<template>
  <section class="candidate-page">
    <header class="page-hero panel-card">
      <div>
        <div class="section-chip">我的投递</div>
        <h1>查看每一次投递的状态变化</h1>
        <p>这里集中展示投递进度、面试阶段和最近反馈，方便你判断该继续跟进还是优先投递新岗位。</p>
      </div>

      <el-button type="primary" round>去投递新职位</el-button>
    </header>

    <section class="stats-grid">
      <article v-for="stat in applicationStats" :key="stat.label" class="panel-card stat-card">
        <div class="stat-card__value">{{ stat.value }}</div>
        <div class="stat-card__label">{{ stat.label }}</div>
      </article>
    </section>

    <article class="panel-card section-card">
      <div class="section-title-row">
        <div>
          <div class="section-chip">投递列表</div>
          <h2>近期申请记录</h2>
        </div>
        <div class="filter-group">
          <el-tag round>全部</el-tag>
          <el-tag type="warning" round>面试中</el-tag>
          <el-tag type="info" round>待处理</el-tag>
          <el-tag type="danger" round>已拒绝</el-tag>
        </div>
      </div>

      <el-table :data="applications" style="width: 100%">
        <el-table-column prop="job" label="职位" min-width="220">
          <template #default="{ row }">
            <div class="job-cell">
              <strong>{{ row.job }}</strong>
              <span>{{ row.company }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="city" label="城市" width="110" />
        <el-table-column prop="salary" label="薪资" width="120" />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="statusMap[row.status] || 'info'" round>{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="stage" label="当前阶段" min-width="180" />
        <el-table-column prop="updatedAt" label="更新时间" width="180" />
        <el-table-column label="操作" width="180">
          <template #default>
            <el-button link type="primary">查看进度</el-button>
            <el-button link>撤回投递</el-button>
          </template>
        </el-table-column>
      </el-table>
    </article>

    <section class="grid-two">
      <article class="panel-card section-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">跟进建议</div>
            <h2>下一步可以做什么</h2>
          </div>
        </div>

        <div class="tip-stack">
          <div class="tip-item">面试中的职位优先安排回复时间，避免错过面试窗口。</div>
          <div class="tip-item">对已查看但未反馈的公司，可以在 3 天后礼貌跟进一次。</div>
          <div class="tip-item">将投递聚焦在与你技能匹配度高的岗位，效率更高。</div>
        </div>
      </article>

      <article class="panel-card section-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">数据概览</div>
            <h2>投递节奏</h2>
          </div>
        </div>

        <div class="progress-stack">
          <div>
            <div class="progress-label">面试转化率</div>
            <el-progress :percentage="62" />
          </div>
          <div>
            <div class="progress-label">简历查看率</div>
            <el-progress :percentage="78" />
          </div>
          <div>
            <div class="progress-label">收藏转投递率</div>
            <el-progress :percentage="54" />
          </div>
        </div>
      </article>
    </section>
  </section>
</template>

<style scoped>
.candidate-page {
  display: grid;
  gap: 20px;
}

.page-hero {
  padding: 28px 30px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
}

.page-hero h1 {
  margin: 16px 0 10px;
  font-size: 32px;
  line-height: 1.25;
  color: var(--brand-title);
}

.page-hero p {
  margin: 0;
  color: var(--brand-ink-soft);
  line-height: 1.8;
  max-width: 720px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.stat-card {
  padding: 20px;
}

.stat-card__value {
  font-size: 30px;
  font-weight: 800;
  color: var(--brand-primary-deep);
}

.stat-card__label {
  margin-top: 8px;
  color: var(--brand-ink-soft);
}

.section-card {
  padding: 24px;
}

.section-title-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-start;
  margin-bottom: 18px;
}

.section-title-row h2 {
  margin: 10px 0 0;
  font-size: 22px;
  color: var(--brand-title);
}

.filter-group {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.job-cell {
  display: grid;
  gap: 4px;
}

.job-cell strong {
  color: var(--brand-title);
}

.job-cell span,
.tip-item,
.progress-label {
  color: var(--brand-ink-soft);
}

.grid-two {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.tip-stack {
  display: grid;
  gap: 12px;
}

.tip-item {
  padding: 16px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid var(--brand-line);
  line-height: 1.7;
}

.progress-stack {
  display: grid;
  gap: 18px;
}

@media (max-width: 1080px) {
  .stats-grid,
  .grid-two {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .page-hero {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
