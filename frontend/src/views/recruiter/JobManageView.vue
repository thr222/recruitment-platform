<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 18px;">
      <div style="font-size: 24px; font-weight: 700;">职位管理</div>
      <el-button type="primary">发布职位</el-button>
    </div>
    <el-table :data="[{ title: 'Java 后端开发工程师', salary: '25K-40K', status: '已上线', updatedAt: '2026-04-08' }, { title: '测试开发工程师', salary: '20K-30K', status: '待审核', updatedAt: '2026-04-07' }]">
      <el-table-column prop="title" label="职位名称" />
      <el-table-column prop="salary" label="薪资范围" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="updatedAt" label="更新时间" width="160" />
      <el-table-column label="操作" width="180">
        <template #default>
          <el-button link type="primary">编辑</el-button>
          <el-button link>上下架</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
