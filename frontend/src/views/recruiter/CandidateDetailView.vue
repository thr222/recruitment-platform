<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

type CandidateStage = '新投递' | '已查看' | '待沟通' | '面试邀请' | '面试中' | '已录用' | '不合适'

const route = useRoute()
const stage = ref<CandidateStage>('待沟通')
const notes = ref('候选人有完整的 Java / Spring Boot 项目经验，建议安排技术一面。')

function updateStage(nextStage: CandidateStage) {
  stage.value = nextStage
  ElMessage.success(`候选人状态已更新为：${nextStage}`)
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card hero-card">
      <div>
        <div class="section-chip">候选人详情 #{{ route.params.candidateId }}</div>
        <h1>王同学</h1>
        <p>应聘 Java 后端开发工程师 · 上海 · 3-5年 · 匹配度 92%</p>
        <div class="tag-row">
          <el-tag round>Spring Boot</el-tag>
          <el-tag round>MySQL</el-tag>
          <el-tag round>支付系统</el-tag>
        </div>
      </div>
      <div class="stage-box">
        <span>当前状态</span>
        <strong>{{ stage }}</strong>
      </div>
    </section>

    <section class="content-grid">
      <div class="panel-card section-card">
        <div class="section-title">简历摘要</div>
        <p>曾参与电商订单、支付链路、招聘中台等项目，熟悉 Spring Boot、MyBatis、Redis、MySQL，有接口性能优化和线上问题排查经验。</p>
        <div class="resume-block">
          <h3>项目经历</h3>
          <ul>
            <li>招聘平台投递链路：负责职位投递、状态流转和消息通知。</li>
            <li>订单系统重构：优化慢 SQL 和缓存策略，接口响应降低 35%。</li>
          </ul>
        </div>
      </div>

      <aside class="side-column">
        <div class="panel-card section-card">
          <div class="section-title">流程动作</div>
          <div class="action-grid">
            <el-button round @click="updateStage('已查看')">标记已查看</el-button>
            <el-button round @click="updateStage('待沟通')">标记待沟通</el-button>
            <el-button type="primary" round @click="updateStage('面试邀请')">邀请面试</el-button>
            <el-button type="success" round @click="updateStage('已录用')">标记录用</el-button>
            <el-button type="danger" plain round @click="updateStage('不合适')">不合适</el-button>
          </div>
        </div>

        <div class="panel-card section-card">
          <div class="section-title">招聘备注</div>
          <el-input v-model="notes" type="textarea" :rows="5" />
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
  font-size: 36px;
}

.hero-card p,
.section-card p,
li {
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
}

.stage-box {
  min-width: 220px;
  padding: 20px;
  border-radius: 20px;
  background: rgba(31, 107, 87, 0.08);
}

.stage-box strong {
  display: block;
  margin-top: 8px;
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

.resume-block {
  margin-top: 20px;
}

.action-grid {
  display: grid;
  gap: 10px;
}

@media (max-width: 1000px) {
  .hero-card,
  .content-grid {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
