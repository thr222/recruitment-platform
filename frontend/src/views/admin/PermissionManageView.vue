<script setup lang="ts">
import { ref } from 'vue'

interface RoleItem {
  role: string
  desc: string
  permissions: string[]
}

const roles = ref<RoleItem[]>([
  { role: '求职者', desc: '浏览职位、管理简历、投递与沟通', permissions: ['职位浏览', '简历管理', '投递管理', '消息通知'] },
  { role: '招聘方', desc: '企业招聘运营与候选人推进', permissions: ['企业主页', '职位发布', '候选人管理', '面试邀请'] },
  { role: '管理员', desc: '平台审核、治理和运营配置', permissions: ['用户管理', '企业审核', '职位审核', '举报处理', '运营配置'] }
])
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div class="section-chip">权限管理</div>
      <h1>按真实平台角色组织权限</h1>
      <p>求职者、招聘方、管理员三端权限边界清晰，后续可接后端 RBAC。</p>
    </section>

    <section class="role-grid">
      <article v-for="item in roles" :key="item.role" class="panel-card role-card">
        <h2>{{ item.role }}</h2>
        <p>{{ item.desc }}</p>
        <div class="tag-row">
          <el-tag v-for="permission in item.permissions" :key="permission" round>{{ permission }}</el-tag>
        </div>
      </article>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card,
.role-card {
  padding: 26px;
}

.header-card h1,
.role-card h2 {
  margin: 12px 0 8px;
  color: var(--brand-title);
}

.header-card p,
.role-card p {
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.role-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.tag-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
}

@media (max-width: 1000px) {
  .role-grid {
    grid-template-columns: 1fr;
  }
}
</style>
