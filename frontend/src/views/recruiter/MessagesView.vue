<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>沟通中心</span>
          <el-tag type="info">站内消息</el-tag>
        </div>
      </template>
      <p>这里用于聚合候选人沟通、投递提醒和系统通知。</p>
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
