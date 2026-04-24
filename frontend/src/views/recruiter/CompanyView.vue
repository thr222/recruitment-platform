<script setup lang="ts">
import { reactive } from 'vue'

const company = reactive({
  name: '星河科技',
  slogan: '让招聘和增长协同更高效',
  city: '杭州',
  scale: '100-499 人',
  stage: 'B 轮',
  industry: '企业服务 / SaaS',
  website: 'https://example.com',
  intro:
    '星河科技专注于企业招聘和组织效率产品，团队节奏稳健，技术栈偏现代化前端与 Java 后端。'
})

const highlights = [
  { label: '品牌曝光', value: '18.6w' },
  { label: '本月访问', value: '3,240' },
  { label: '收藏量', value: '1,246' },
  { label: '转化率', value: '8.4%' }
]

const tags = ['AI 招聘', 'SaaS', '企业服务', '扁平管理', '双休']
</script>

<template>
  <section class="page-shell">
    <section class="hero-card panel-card">
      <div class="brand-block">
        <div class="brand-logo">星</div>
        <div>
          <div class="section-chip">公司主页管理</div>
          <h1>{{ company.name }}</h1>
          <p>{{ company.slogan }}</p>
        </div>
      </div>

      <div class="hero-meta">
        <el-tag type="success" effect="light" round>已通过审核</el-tag>
        <el-tag effect="light" round>{{ company.city }}</el-tag>
        <el-tag effect="light" round>{{ company.scale }}</el-tag>
      </div>
    </section>

    <section class="summary-grid">
      <article v-for="item in highlights" :key="item.label" class="summary-card panel-card">
        <div class="summary-value">{{ item.value }}</div>
        <div class="summary-label">{{ item.label }}</div>
      </article>
    </section>

    <section class="content-grid">
      <div class="panel-card content-card">
        <div class="section-title">公司资料</div>
        <div class="section-subtitle">本地 mock 表单，可直接演示编辑态</div>

        <el-form label-position="top" class="company-form">
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item label="公司名称">
                <el-input v-model="company.name" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公司口号">
                <el-input v-model="company.slogan" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="12">
            <el-col :span="8">
              <el-form-item label="所在城市">
                <el-input v-model="company.city" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="公司规模">
                <el-input v-model="company.scale" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="融资阶段">
                <el-input v-model="company.stage" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="所属行业">
            <el-input v-model="company.industry" />
          </el-form-item>

          <el-form-item label="官网地址">
            <el-input v-model="company.website" />
          </el-form-item>

          <el-form-item label="公司简介">
            <el-input v-model="company.intro" type="textarea" :rows="5" />
          </el-form-item>
        </el-form>

        <div class="action-row">
          <el-button type="primary" round>保存修改</el-button>
          <el-button round>预览主页</el-button>
        </div>
      </div>

      <div class="side-column">
        <div class="panel-card content-card">
          <div class="section-title">公司标签</div>
          <div class="tag-list">
            <el-tag v-for="tag in tags" :key="tag" effect="light" round>{{ tag }}</el-tag>
          </div>
        </div>

        <div class="panel-card content-card">
          <div class="section-title">主页完善度</div>
          <div class="section-subtitle">完善资料能提高职位页和公司页转化</div>
          <div class="completion-value">86%</div>
          <el-progress :percentage="86" :stroke-width="10" />
          <ul class="tip-list">
            <li>补充团队照片和办公环境图。</li>
            <li>完善职位亮点和福利标签。</li>
            <li>增加招聘负责人信息，提升信任度。</li>
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

.hero-card {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
  padding: 24px;
}

.brand-block {
  display: flex;
  gap: 16px;
  align-items: center;
}

.brand-logo {
  width: 56px;
  height: 56px;
  display: grid;
  place-items: center;
  border-radius: 18px;
  background: rgba(31, 107, 87, 0.12);
  color: var(--brand-primary);
  font-size: 24px;
  font-weight: 800;
}

.hero-card h1 {
  margin: 10px 0 8px;
  color: var(--brand-title);
  font-size: 28px;
}

.hero-card p {
  margin: 0;
  color: var(--brand-ink-soft);
}

.hero-meta {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.summary-card,
.content-card {
  padding: 22px;
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
  grid-template-columns: minmax(0, 1.4fr) minmax(320px, 0.7fr);
  gap: 16px;
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

.company-form {
  margin-top: 18px;
}

.side-column {
  display: grid;
  gap: 16px;
}

.tag-list,
.action-row {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tag-list {
  margin-top: 16px;
}

.completion-value {
  margin: 18px 0 10px;
  color: var(--brand-title);
  font-size: 34px;
  font-weight: 800;
}

.tip-list {
  margin: 16px 0 0;
  padding-left: 18px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

@media (max-width: 1200px) {
  .hero-card,
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
