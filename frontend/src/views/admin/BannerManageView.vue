<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>Banner 管理</span>
          <el-tag type="warning">运营配置</el-tag>
        </div>
      </template>
      <p>这里用于配置首页 Banner、推荐位和活动入口。</p>
    </el-card>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 16px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
