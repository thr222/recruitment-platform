<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const metrics = [
  { label: '曝光量', value: '12,840', trend: '+18%' },
  { label: '浏览量', value: '2,436', trend: '+9%' },
  { label: '投递数', value: '186', trend: '+24%' },
  { label: '转化率', value: '7.6%', trend: '+1.4%' }
]

const sources = [
  { name: '搜索结果', value: 42 },
  { name: '首页推荐', value: 28 },
  { name: '公司主页', value: 18 },
  { name: '收藏回访', value: 12 }
]

const title = computed(() => `职位数据 #${route.params.jobId}`)
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div>
        <div class="section-chip">职位数据</div>
        <h1>{{ title }}</h1>
        <p>用数据判断职位是否需要改标题、调薪资、加标签或扩大投放。</p>
      </div>
    </section>

    <section class="metric-grid">
      <article v-for="item in metrics" :key="item.label" class="panel-card metric-card">
        <div class="metric-value">{{ item.value }}</div>
        <div class="metric-label">{{ item.label }}</div>
        <div class="metric-trend">{{ item.trend }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card section-card">
        <div class="section-title">流量来源</div>
        <div v-for="item in sources" :key="item.name" class="source-item">
          <div>
            <strong>{{ item.name }}</strong>
            <span>{{ item.value }}%</span>
          </div>
          <el-progress :percentage="item.value" :show-text="false" />
        </div>
      </div>

      <div class="panel-card section-card">
        <div class="section-title">优化建议</div>
        <ul>
          <li>标题可补充 “Spring Boot / 中台” 等关键词，提高搜索相关性。</li>
          <li>薪资区间与同城岗位相比偏中位，可突出福利和成长空间。</li>
          <li>最近投递转化提升明显，建议保持职位在线并优先处理新简历。</li>
        </ul>
      </div>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card,
.section-card,
.metric-card {
  padding: 24px;
}

.header-card h1 {
  margin: 12px 0 8px;
  color: var(--brand-title);
  font-size: 34px;
}

.header-card p,
.metric-label,
li {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.metric-value {
  color: var(--brand-title);
  font-size: 34px;
  font-weight: 800;
}

.metric-trend {
  margin-top: 10px;
  color: var(--brand-primary);
  font-weight: 800;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.section-title {
  margin-bottom: 16px;
  color: var(--brand-title);
  font-size: 22px;
  font-weight: 800;
}

.source-item {
  display: grid;
  gap: 8px;
  margin-bottom: 16px;
}

.source-item div {
  display: flex;
  justify-content: space-between;
}

@media (max-width: 1000px) {
  .metric-grid,
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
