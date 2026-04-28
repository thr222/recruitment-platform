<script setup lang="ts">
const summaryCards = [
  { value: '85%', label: '简历完整度', hint: '补齐项目经历和期望薪资后，推荐精度会更高。' },
  { value: '12', label: '累计投递', hint: '近 7 天新增 3 次投递，保持节奏更容易拿到反馈。' },
  { value: '3', label: '进行中流程', hint: '1 个面试中，2 个待处理，建议优先跟进。' },
  { value: '18', label: '今日推荐', hint: '基于你的 Java / 前端 / 产品偏好生成。' }
]

const timelineItems = [
  { time: '2026-04-08 14:30', title: '星河科技', content: 'Java 后端岗位进入面试中，请留意 HR 消息。', type: 'warning' },
  { time: '2026-04-07 19:10', title: '远帆智能', content: '简历已被查看，职位为 Vue3 前端工程师。', type: 'primary' },
  { time: '2026-04-07 09:20', title: '云图人才', content: '投递成功，等待初筛结果。', type: 'success' }
]

const recommendedJobs = [
  {
    title: 'Java 后端开发工程师',
    company: '星河科技',
    city: '上海',
    salary: '25K-40K',
    tags: ['Spring Boot', '高并发', '中台']
  },
  {
    title: 'Vue3 前端工程师',
    company: '远帆智能',
    city: '杭州',
    salary: '22K-35K',
    tags: ['Vue3', 'TypeScript', 'B 端产品']
  },
  {
    title: '产品经理',
    company: '青木数据',
    city: '北京',
    salary: '18K-28K',
    tags: ['增长', '数据平台', '用户研究']
  }
]

const insights = [
  '你的近期搜索偏向后端、产品和前端岗位。',
  '上海岗位命中率高于其他城市，建议优先关注。',
  '补齐项目经历后，推荐职位会更贴合你的背景。'
]
</script>

<template>
  <section class="candidate-page">
    <header class="hero-banner panel-card">
      <div class="hero-copy">
        <div class="section-chip">求职者工作台</div>
        <h1>让投递、简历、收藏和消息都在一个节奏里推进</h1>
        <p>
          这是求职者端首页的静态展示版，用于快速查看进度、掌握反馈，并持续优化你的求职动作。
        </p>

        <div class="hero-actions">
          <el-button type="primary" round>继续投递</el-button>
          <el-button round>完善简历</el-button>
        </div>
      </div>

      <div class="hero-side">
        <div class="hero-side__badge">今日状态</div>
        <div class="hero-side__title">积极求职中</div>
        <div class="hero-side__text">推荐岗位持续更新，建议每天查看一次筛选结果并及时回复消息。</div>
      </div>
    </header>

    <section class="summary-grid">
      <article v-for="card in summaryCards" :key="card.label" class="panel-card summary-card">
        <div class="summary-card__value">{{ card.value }}</div>
        <div class="summary-card__label">{{ card.label }}</div>
        <div class="summary-card__hint">{{ card.hint }}</div>
      </article>
    </section>

    <section class="content-grid">
      <article class="panel-card section-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">最新动态</div>
            <h2>最近的投递进展</h2>
          </div>
          <el-button text type="primary">查看全部</el-button>
        </div>

        <el-timeline>
          <el-timeline-item
            v-for="item in timelineItems"
            :key="item.time + item.title"
            :timestamp="item.time"
            :type="item.type as any"
          >
            <strong>{{ item.title }}</strong>
            <span class="timeline-text">{{ item.content }}</span>
          </el-timeline-item>
        </el-timeline>
      </article>

      <article class="panel-card section-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">求职提醒</div>
            <h2>下一步建议</h2>
          </div>
          <el-tag type="warning" round>待优化</el-tag>
        </div>

        <div class="insight-stack">
          <el-alert
            v-for="insight in insights"
            :key="insight"
            :title="insight"
            type="info"
            :closable="false"
            show-icon
          />
        </div>

        <div class="mini-metrics">
          <div>
            <div class="mini-metrics__value">78%</div>
            <div class="mini-metrics__label">简历匹配度</div>
          </div>
          <div>
            <div class="mini-metrics__value">3</div>
            <div class="mini-metrics__label">未读消息</div>
          </div>
          <div>
            <div class="mini-metrics__value">2</div>
            <div class="mini-metrics__label">待回复面试</div>
          </div>
        </div>
      </article>
    </section>

    <section class="panel-card section-card">
      <div class="section-title-row">
        <div>
          <div class="section-chip">今日推荐</div>
          <h2>值得优先查看的职位</h2>
        </div>
        <el-button type="primary" round>去职位列表</el-button>
      </div>

      <div class="job-grid">
        <article v-for="job in recommendedJobs" :key="job.title" class="job-card">
          <div class="job-card__top">
            <div>
              <div class="job-card__company">{{ job.company }}</div>
              <h3>{{ job.title }}</h3>
            </div>
            <div class="job-card__salary">{{ job.salary }}</div>
          </div>

          <div class="job-card__meta">
            <span>{{ job.city }}</span>
            <span>On-site</span>
          </div>

          <div class="job-card__tags">
            <el-tag v-for="tag in job.tags" :key="tag" round>{{ tag }}</el-tag>
          </div>
        </article>
      </div>
    </section>
  </section>
</template>

<style scoped>
.candidate-page {
  display: grid;
  gap: 20px;
}

.hero-banner {
  padding: 28px 30px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: stretch;
  background:
    radial-gradient(circle at top left, rgba(10, 102, 194, 0.14), transparent 42%),
    linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(255, 255, 255, 0.92));
}

.hero-copy {
  max-width: 680px;
}

.hero-copy h1 {
  margin: 16px 0 12px;
  font-size: 38px;
  line-height: 1.18;
  color: var(--brand-title);
}

.hero-copy p {
  margin: 0;
  color: var(--brand-ink-soft);
  font-size: 16px;
  line-height: 1.8;
}

.hero-actions {
  display: flex;
  gap: 12px;
  margin-top: 22px;
  flex-wrap: wrap;
}

.hero-side {
  min-width: 280px;
  border-radius: 22px;
  background: linear-gradient(160deg, rgba(10, 102, 194, 0.08), rgba(255, 255, 255, 0.92));
  border: 1px solid rgba(10, 102, 194, 0.12);
  padding: 22px;
  display: grid;
  align-content: space-between;
}

.hero-side__badge {
  display: inline-flex;
  width: fit-content;
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(10, 102, 194, 0.1);
  color: var(--brand-primary-deep);
  font-size: 13px;
  font-weight: 700;
}

.hero-side__title {
  font-size: 24px;
  font-weight: 800;
  color: var(--brand-title);
}

.hero-side__text {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.summary-card {
  padding: 20px;
}

.summary-card__value {
  font-size: 30px;
  font-weight: 800;
  color: var(--brand-primary-deep);
}

.summary-card__label {
  margin-top: 8px;
  font-weight: 700;
  color: var(--brand-title);
}

.summary-card__hint {
  margin-top: 8px;
  color: var(--brand-ink-soft);
  font-size: 14px;
  line-height: 1.6;
}

.content-grid {
  display: grid;
  grid-template-columns: 1.25fr 0.95fr;
  gap: 16px;
}

.section-card {
  padding: 24px;
}

.section-title-row {
  display: flex;
  justify-content: space-between;
  align-items: start;
  gap: 16px;
  margin-bottom: 18px;
}

.section-title-row h2 {
  margin: 10px 0 0;
  font-size: 24px;
  color: var(--brand-title);
}

.timeline-text {
  display: block;
  margin-top: 4px;
  color: var(--brand-ink-soft);
}

.insight-stack {
  display: grid;
  gap: 12px;
}

.mini-metrics {
  margin-top: 18px;
  padding-top: 18px;
  border-top: 1px solid var(--brand-line);
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}

.mini-metrics__value {
  font-size: 24px;
  font-weight: 800;
  color: var(--brand-title);
}

.mini-metrics__label {
  margin-top: 6px;
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.job-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
}

.job-card {
  border-radius: 18px;
  padding: 18px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(247, 243, 235, 0.72));
  border: 1px solid var(--brand-line);
  box-shadow: 0 14px 30px rgba(17, 24, 39, 0.06);
}

.job-card__top {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.job-card__company {
  color: var(--brand-ink-soft);
  font-size: 13px;
  font-weight: 700;
}

.job-card h3 {
  margin: 8px 0 0;
  color: var(--brand-title);
  font-size: 20px;
  line-height: 1.35;
}

.job-card__salary {
  color: var(--brand-primary);
  font-size: 20px;
  font-weight: 800;
  white-space: nowrap;
}

.job-card__meta {
  display: flex;
  gap: 10px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.job-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

@media (max-width: 1180px) {
  .summary-grid,
  .job-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .content-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .hero-banner {
    flex-direction: column;
  }

  .summary-grid,
  .job-grid,
  .mini-metrics {
    grid-template-columns: 1fr;
  }

  .hero-copy h1 {
    font-size: 30px;
  }
}
</style>
