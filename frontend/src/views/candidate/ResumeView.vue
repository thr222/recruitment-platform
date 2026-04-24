<script setup lang="ts">
const profileSummary = [
  { label: '当前求职状态', value: '积极求职中' },
  { label: '目标岗位', value: 'Java 后端 / 前端 / 产品' },
  { label: '目标城市', value: '上海 / 杭州 / 北京' },
  { label: '可到岗时间', value: '两周内' }
]

const experiences = [
  {
    title: '星河科技 · Java 后端工程师',
    time: '2022.07 - 至今',
    desc: '负责招聘平台中台能力建设，参与职位检索、简历流转、权限体系和消息中心。'
  },
  {
    title: '远帆智能 · 前端开发实习生',
    time: '2021.10 - 2022.06',
    desc: '完成招聘列表、详情页和管理后台组件的实现，优化了首屏和表单交互。'
  }
]

const projects = [
  {
    name: '招聘平台重构',
    desc: '搭建统一首页、职位检索和投递流程，形成求职者与招聘方双端体验。'
  },
  {
    name: '简历匹配助手',
    desc: '根据岗位关键词生成简历优化建议，提高推荐命中率和面试反馈率。'
  }
]

const skills = ['Java', 'Spring Boot', 'Vue 3', 'TypeScript', 'Element Plus', 'MySQL', 'Redis', '招聘业务']
</script>

<template>
  <section class="candidate-page">
    <header class="page-hero panel-card">
      <div>
        <div class="section-chip">我的简历</div>
        <h1>完善一份更适合投递的在线简历</h1>
        <p>把基础信息、工作经历、项目经历和技能标签统一整理，便于后续投递和自动匹配。</p>
      </div>

      <div class="hero-actions">
        <el-button type="primary" round>保存简历</el-button>
        <el-button round>导出 PDF</el-button>
      </div>
    </header>

    <section class="resume-layout">
      <aside class="panel-card sidebar-card">
        <div class="avatar-block">
          <div class="avatar">张</div>
          <div>
            <h2>张同学</h2>
            <p>3 年 Java / 前端经验</p>
          </div>
        </div>

        <div class="meta-list">
          <div v-for="item in profileSummary" :key="item.label" class="meta-item">
            <span>{{ item.label }}</span>
            <strong>{{ item.value }}</strong>
          </div>
        </div>

        <div class="complete-card">
          <div class="complete-card__title">简历完整度</div>
          <div class="complete-card__value">85%</div>
          <el-progress :percentage="85" :stroke-width="10" />
        </div>

        <div class="tip-card">
          建议补充项目成果和量化数据，能显著提升推荐准确度。
        </div>
      </aside>

      <div class="form-column">
        <article class="panel-card form-card">
          <div class="card-title">
            <h2>基本信息</h2>
            <span>保持信息准确，便于招聘方快速联系</span>
          </div>

          <el-form label-position="top">
            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="姓名">
                  <el-input model-value="张同学" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="手机号">
                  <el-input model-value="13800138000" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="邮箱">
                  <el-input model-value="zhang@example.com" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="最高学历">
                  <el-select model-value="本科" placeholder="请选择学历" style="width: 100%">
                    <el-option label="本科" value="本科" />
                    <el-option label="硕士" value="硕士" />
                    <el-option label="大专" value="大专" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="目标城市">
                  <el-select model-value="上海" placeholder="请选择城市" style="width: 100%">
                    <el-option label="上海" value="上海" />
                    <el-option label="杭州" value="杭州" />
                    <el-option label="北京" value="北京" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="到岗时间">
                  <el-select model-value="两周内" placeholder="请选择时间" style="width: 100%">
                    <el-option label="随时到岗" value="随时到岗" />
                    <el-option label="两周内" value="两周内" />
                    <el-option label="一个月内" value="一个月内" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </article>

        <article class="panel-card form-card">
          <div class="card-title">
            <h2>求职意向</h2>
            <span>用于职位推荐和搜索偏好</span>
          </div>

          <el-form label-position="top">
            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="目标岗位">
                  <el-input model-value="Java 后端 / 前端 / 产品" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="期望薪资">
                  <el-input model-value="20K-35K" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="个人优势">
              <el-input
                :rows="4"
                type="textarea"
                model-value="3 年 Java 后端经验，熟悉 Spring Boot、MySQL、Redis 和招聘业务流程。"
              />
            </el-form-item>
          </el-form>
        </article>

        <article class="panel-card form-card">
          <div class="card-title">
            <h2>工作经历</h2>
            <span>保留最重要的经历和量化结果</span>
          </div>

          <div class="list-stack">
            <div v-for="experience in experiences" :key="experience.title" class="list-card">
              <div class="list-card__head">
                <strong>{{ experience.title }}</strong>
                <span>{{ experience.time }}</span>
              </div>
              <p>{{ experience.desc }}</p>
            </div>
          </div>
        </article>

        <article class="panel-card form-card">
          <div class="card-title">
            <h2>项目经历</h2>
            <span>突出与招聘系统相关的项目经验</span>
          </div>

          <div class="list-stack">
            <div v-for="project in projects" :key="project.name" class="list-card">
              <div class="list-card__head">
                <strong>{{ project.name }}</strong>
              </div>
              <p>{{ project.desc }}</p>
            </div>
          </div>
        </article>

        <article class="panel-card form-card">
          <div class="card-title">
            <h2>技能标签</h2>
            <span>让招聘方一眼看到匹配方向</span>
          </div>

          <div class="skill-tags">
            <el-tag v-for="skill in skills" :key="skill" round>{{ skill }}</el-tag>
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

.page-hero {
  padding: 28px 30px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: flex-start;
}

.page-hero h1 {
  margin: 16px 0 10px;
  font-size: 34px;
  line-height: 1.25;
  color: var(--brand-title);
}

.page-hero p {
  margin: 0;
  max-width: 700px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.hero-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.resume-layout {
  display: grid;
  grid-template-columns: 300px minmax(0, 1fr);
  gap: 16px;
}

.sidebar-card,
.form-card {
  padding: 24px;
}

.avatar-block {
  display: flex;
  align-items: center;
  gap: 14px;
  padding-bottom: 18px;
  border-bottom: 1px solid var(--brand-line);
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 18px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, rgba(31, 107, 87, 0.14), rgba(31, 107, 87, 0.05));
  color: var(--brand-primary-deep);
  font-size: 22px;
  font-weight: 800;
}

.avatar-block h2 {
  margin: 0;
  font-size: 20px;
  color: var(--brand-title);
}

.avatar-block p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
}

.meta-list {
  display: grid;
  gap: 14px;
  margin-top: 18px;
}

.meta-item {
  display: grid;
  gap: 4px;
  padding: 14px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid var(--brand-line);
}

.meta-item span {
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.meta-item strong {
  color: var(--brand-title);
  font-size: 15px;
}

.complete-card {
  margin-top: 18px;
  padding: 16px;
  border-radius: 18px;
  background: linear-gradient(180deg, rgba(31, 107, 87, 0.08), rgba(255, 255, 255, 0.96));
  border: 1px solid rgba(31, 107, 87, 0.12);
}

.complete-card__title {
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.complete-card__value {
  margin: 8px 0 12px;
  font-size: 32px;
  font-weight: 800;
  color: var(--brand-primary-deep);
}

.tip-card {
  margin-top: 18px;
  padding: 16px;
  border-radius: 18px;
  background: rgba(17, 24, 39, 0.04);
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.form-card + .form-card {
  margin-top: 16px;
}

.card-title {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: baseline;
  margin-bottom: 16px;
}

.card-title h2 {
  margin: 0;
  font-size: 22px;
  color: var(--brand-title);
}

.card-title span {
  color: var(--brand-ink-soft);
  font-size: 14px;
}

.list-stack {
  display: grid;
  gap: 12px;
}

.list-card {
  border-radius: 16px;
  padding: 16px;
  background: rgba(255, 255, 255, 0.78);
  border: 1px solid var(--brand-line);
}

.list-card__head {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.list-card__head strong {
  color: var(--brand-title);
}

.list-card__head span,
.list-card p {
  color: var(--brand-ink-soft);
  margin: 0;
}

.list-card p {
  margin-top: 10px;
  line-height: 1.7;
}

.skill-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

@media (max-width: 1080px) {
  .resume-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .page-hero {
    flex-direction: column;
  }

  .page-hero h1 {
    font-size: 28px;
  }
}
</style>
