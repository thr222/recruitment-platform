<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>面试管理</span>
          <el-tag type="success">企业端</el-tag>
        </div>
      </template>
      <p>这里用于统一管理一面、二面、HR 面和面试反馈。</p>
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
