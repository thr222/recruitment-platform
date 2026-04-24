<script setup lang="ts">
const favoriteJobs = [
  {
    company: '星河科技',
    title: 'Java 后端开发工程师',
    salary: '25K-40K',
    city: '上海',
    tags: ['Spring Boot', '高并发', '中台'],
    note: '与你的后端经历匹配度较高'
  },
  {
    company: '远帆智能',
    title: 'Vue3 前端工程师',
    salary: '22K-35K',
    city: '杭州',
    tags: ['Vue3', 'TypeScript', 'B 端产品'],
    note: '适合做中后台和数据平台'
  },
  {
    company: '青木数据',
    title: '产品经理',
    salary: '18K-28K',
    city: '北京',
    tags: ['增长', '数据分析', '用户研究'],
    note: '更偏业务协同和策略型岗位'
  }
]

const savedCompanies = [
  { name: '星河科技', desc: '招聘平台中台与企业服务业务。' },
  { name: '远帆智能', desc: '专注于企业数字化与协同效率。' },
  { name: '青木数据', desc: '数据平台与增长分析方向团队。' }
]
</script>

<template>
  <section class="candidate-page">
    <header class="page-hero panel-card">
      <div>
        <div class="section-chip">收藏职位</div>
        <h1>把值得关注的岗位先收起来，之后再慢慢投</h1>
        <p>这里用于保存你感兴趣的职位和公司，方便对比薪资、城市、技术栈和投递优先级。</p>
      </div>

      <el-button type="primary" round>去职位列表</el-button>
    </header>

    <section class="grid-main">
      <article class="panel-card section-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">收藏职位</div>
            <h2>最近收藏的岗位</h2>
          </div>
          <el-tag type="success" round>3 条</el-tag>
        </div>

        <div class="favorite-grid">
          <article v-for="job in favoriteJobs" :key="job.company + job.title" class="favorite-card">
            <div class="favorite-card__top">
              <div>
                <div class="favorite-card__company">{{ job.company }}</div>
                <h3>{{ job.title }}</h3>
              </div>
              <div class="favorite-card__salary">{{ job.salary }}</div>
            </div>

            <div class="favorite-card__meta">
              <span>{{ job.city }}</span>
              <span>{{ job.note }}</span>
            </div>

            <div class="favorite-card__tags">
              <el-tag v-for="tag in job.tags" :key="tag" round>{{ tag }}</el-tag>
            </div>

            <div class="favorite-card__actions">
              <el-button type="primary" round>查看详情</el-button>
              <el-button round>取消收藏</el-button>
            </div>
          </article>
        </div>
      </article>

      <aside class="side-column">
        <article class="panel-card section-card">
          <div class="section-title-row">
            <div>
              <div class="section-chip">收藏公司</div>
              <h2>你关注的公司</h2>
            </div>
          </div>

          <div class="company-stack">
            <div v-for="company in savedCompanies" :key="company.name" class="company-card">
              <strong>{{ company.name }}</strong>
              <p>{{ company.desc }}</p>
            </div>
          </div>
        </article>

        <article class="panel-card section-card">
          <div class="section-title-row">
            <div>
              <div class="section-chip">稍后投递</div>
              <h2>建议动作</h2>
            </div>
          </div>

          <div class="tip-stack">
            <div class="tip-item">优先投递收藏时间较短、与你技能匹配度最高的岗位。</div>
            <div class="tip-item">将高薪但要求偏高的岗位留作对比参考，避免盲投。</div>
          </div>
        </article>
      </aside>
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
  max-width: 720px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.grid-main {
  display: grid;
  grid-template-columns: minmax(0, 1.45fr) 360px;
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
  font-size: 22px;
  color: var(--brand-title);
}

.favorite-grid {
  display: grid;
  gap: 14px;
}

.favorite-card {
  padding: 18px;
  border-radius: 18px;
  border: 1px solid var(--brand-line);
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(247, 243, 235, 0.74));
  box-shadow: 0 10px 24px rgba(17, 24, 39, 0.05);
}

.favorite-card__top {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.favorite-card__company {
  color: var(--brand-ink-soft);
  font-size: 13px;
  font-weight: 700;
}

.favorite-card h3 {
  margin: 8px 0 0;
  color: var(--brand-title);
  font-size: 20px;
}

.favorite-card__salary {
  color: var(--brand-primary);
  font-size: 20px;
  font-weight: 800;
  white-space: nowrap;
}

.favorite-card__meta {
  display: grid;
  gap: 4px;
  margin-top: 12px;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.favorite-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

.favorite-card__actions {
  display: flex;
  gap: 10px;
  margin-top: 16px;
  flex-wrap: wrap;
}

.side-column {
  display: grid;
  gap: 16px;
}

.company-stack {
  display: grid;
  gap: 12px;
}

.company-card,
.tip-item {
  padding: 16px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.76);
  border: 1px solid var(--brand-line);
}

.company-card strong {
  color: var(--brand-title);
}

.company-card p,
.tip-item {
  margin: 8px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.tip-stack {
  display: grid;
  gap: 12px;
}

@media (max-width: 1080px) {
  .grid-main {
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
