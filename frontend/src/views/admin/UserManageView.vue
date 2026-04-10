<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>用户管理</span>
          <el-tag type="danger">平台治理</el-tag>
        </div>
      </template>
      <p>这里用于管理求职者、招聘者和管理员账号状态。</p>
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
