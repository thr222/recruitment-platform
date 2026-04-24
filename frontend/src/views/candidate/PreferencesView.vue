<script setup lang="ts">
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'

const form = reactive({
  keywords: ['Java', 'Vue3', '招聘系统'],
  cities: ['上海', '杭州'],
  salary: '25K-40K',
  workTypes: ['全职', '远程'],
  industries: ['企业服务', 'HR SaaS'],
  notice: true
})

function savePreferences() {
  ElMessage.success('求职偏好已保存，后续推荐会优先匹配这些条件')
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div class="section-chip">求职偏好</div>
      <h1>让推荐职位更像 LinkedIn / Indeed</h1>
      <p>平台会基于职位关键词、城市、薪资、工作性质和行业偏好生成更精准的职位推荐。</p>
    </section>

    <section class="panel-card form-card">
      <el-form label-position="top">
        <el-form-item label="目标关键词">
          <el-select v-model="form.keywords" multiple filterable allow-create default-first-option>
            <el-option label="Java" value="Java" />
            <el-option label="Vue3" value="Vue3" />
            <el-option label="产品经理" value="产品经理" />
            <el-option label="实习" value="实习" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望城市">
          <el-select v-model="form.cities" multiple>
            <el-option label="上海" value="上海" />
            <el-option label="北京" value="北京" />
            <el-option label="杭州" value="杭州" />
            <el-option label="深圳" value="深圳" />
          </el-select>
        </el-form-item>
        <el-form-item label="期望薪资">
          <el-input v-model="form.salary" />
        </el-form-item>
        <el-form-item label="工作性质">
          <el-checkbox-group v-model="form.workTypes">
            <el-checkbox label="全职" />
            <el-checkbox label="实习" />
            <el-checkbox label="远程" />
            <el-checkbox label="混合办公" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="关注行业">
          <el-select v-model="form.industries" multiple filterable allow-create>
            <el-option label="企业服务" value="企业服务" />
            <el-option label="HR SaaS" value="HR SaaS" />
            <el-option label="电商" value="电商" />
            <el-option label="人工智能" value="人工智能" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-switch v-model="form.notice" active-text="开启匹配职位通知" />
        </el-form-item>
        <el-button type="primary" size="large" round @click="savePreferences">保存偏好</el-button>
      </el-form>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
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
  line-height: 1.7;
}

.form-card {
  max-width: 760px;
}
</style>
