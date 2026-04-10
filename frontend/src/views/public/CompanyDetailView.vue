<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>企业详情</span>
          <el-tag>公共门户</el-tag>
        </div>
      </template>
      <p>这里用于展示企业介绍、企业标签和在招职位。</p>
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
