<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>举报处理</span>
          <el-tag type="danger">内容审核</el-tag>
        </div>
      </template>
      <p>这里用于处理职位举报、企业举报和违规记录。</p>
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
