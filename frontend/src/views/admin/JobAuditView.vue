<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="font-size: 24px; font-weight: 700; margin-bottom: 18px;">职位审核</div>
    <el-table :data="[{ job: 'Java 后端开发工程师', company: '星河科技', status: '待审核' }, { job: '商务拓展经理', company: '云图人才', status: '违规下架' }]">
      <el-table-column prop="job" label="职位名称" />
      <el-table-column prop="company" label="所属企业" />
      <el-table-column prop="status" label="审核结果" />
      <el-table-column label="操作" width="180">
        <template #default>
          <el-button link type="primary">查看详情</el-button>
          <el-button link>处理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
