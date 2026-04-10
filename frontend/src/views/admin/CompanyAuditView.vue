<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="font-size: 24px; font-weight: 700; margin-bottom: 18px;">企业审核</div>
    <el-table :data="[{ name: '云图人才', license: '统一社会信用代码已上传', status: '待审核' }, { name: '北辰科技', license: '资料完整', status: '已通过' }]">
      <el-table-column prop="name" label="企业名称" />
      <el-table-column prop="license" label="材料情况" />
      <el-table-column prop="status" label="审核状态" />
      <el-table-column label="操作" width="180">
        <template #default>
          <el-button link type="primary">通过</el-button>
          <el-button link>驳回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
