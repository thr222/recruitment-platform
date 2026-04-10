<template>
  <div class="panel-card" style="padding: 24px;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 18px;">
      <div style="font-size: 24px; font-weight: 700;">字典配置</div>
      <el-button type="primary">新增字典项</el-button>
    </div>
    <el-table :data="[{ type: 'education', label: '学历', items: '大专/本科/硕士/博士' }, { type: 'city', label: '城市', items: '北京/上海/深圳/杭州' }]">
      <el-table-column prop="type" label="字典编码" />
      <el-table-column prop="label" label="字典名称" />
      <el-table-column prop="items" label="字典项" />
    </el-table>
  </div>
</template>
