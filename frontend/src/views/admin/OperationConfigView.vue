<script setup lang="ts">
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'

const config = reactive({
  enableCompanyAudit: true,
  enableJobAudit: true,
  enableReportAutoHide: true,
  homeRecommendCount: 12,
  hotKeyword: 'Java,前端,实习,产品经理,远程'
})

function saveConfig() {
  ElMessage.success('运营配置已保存')
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div class="section-chip">运营配置</div>
      <h1>把平台规则配置化，而不是写死在页面里</h1>
      <p>控制企业审核、职位审核、举报风控、首页推荐数量和热门搜索词。</p>
    </section>

    <section class="panel-card form-card">
      <el-form label-position="top">
        <el-form-item label="审核规则">
          <div class="switch-list">
            <el-switch v-model="config.enableCompanyAudit" active-text="企业入驻需要审核" />
            <el-switch v-model="config.enableJobAudit" active-text="职位发布需要审核" />
            <el-switch v-model="config.enableReportAutoHide" active-text="高风险举报自动隐藏内容" />
          </div>
        </el-form-item>
        <el-form-item label="首页推荐职位数量">
          <el-input-number v-model="config.homeRecommendCount" :min="4" :max="30" />
        </el-form-item>
        <el-form-item label="热门搜索词">
          <el-input v-model="config.hotKeyword" type="textarea" :rows="3" />
        </el-form-item>
        <el-button type="primary" round size="large" @click="saveConfig">保存配置</el-button>
      </el-form>
    </section>
  </section>
</template>

<style scoped>
.page-shell,
.switch-list {
  display: grid;
  gap: 20px;
}

.header-card,
.form-card {
  padding: 26px;
}

.header-card h1 {
  margin: 12px 0 8px;
  color: var(--brand-title);
  font-size: 34px;
}

.header-card p {
  margin: 0;
  color: var(--brand-ink-soft);
}

.form-card {
  max-width: 760px;
}
</style>
