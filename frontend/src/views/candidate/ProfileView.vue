<template>
  <section class="page-shell">
    <el-card shadow="never">
      <template #header>
        <div class="page-header">
          <span>个人设置</span>
          <el-tag>账号安全</el-tag>
        </div>
      </template>
      <p>这里用于维护头像、昵称、手机号、邮箱和隐私配置。</p>
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
