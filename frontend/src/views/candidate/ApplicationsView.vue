<script setup lang="ts">
const rows = [
  { job: 'Java 后端开发工程师', company: '星河科技', status: '面试中', updatedAt: '2026-04-08 14:30' },
  { job: '招聘产品经理', company: '云图人才', status: '待处理', updatedAt: '2026-04-07 09:20' }
]
</script>

<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="font-size: 24px; font-weight: 700; margin-bottom: 18px;">我的投递</div>
    <el-table :data="rows">
      <el-table-column prop="job" label="职位" />
      <el-table-column prop="company" label="公司" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="updatedAt" label="最近更新时间" width="180" />
      <el-table-column label="操作" width="160">
        <template #default>
          <el-button link type="primary">查看进度</el-button>
          <el-button link>撤回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
