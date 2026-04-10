<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="font-size: 24px; font-weight: 700; margin-bottom: 18px;">候选人管理</div>
    <el-table :data="[{ name: '王同学', job: 'Java 后端开发工程师', stage: '待初筛' }, { name: '陈同学', job: '测试开发工程师', stage: '面试中' }]">
      <el-table-column prop="name" label="候选人" />
      <el-table-column prop="job" label="应聘职位" />
      <el-table-column prop="stage" label="流程状态" />
      <el-table-column label="操作" width="220">
        <template #default>
          <el-button link type="primary">查看简历</el-button>
          <el-button link>推进流程</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
