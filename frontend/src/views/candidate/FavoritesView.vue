<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>我的收藏</span>
          <el-tag type="warning">候选人侧</el-tag>
        </div>
      </template>
      <p>这里用于展示收藏职位、浏览历史和稍后投递列表。</p>
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
