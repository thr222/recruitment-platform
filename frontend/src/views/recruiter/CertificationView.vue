<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'

type AuditStatus = '未提交' | '审核中' | '已通过' | '已驳回'

const status = ref<AuditStatus>('审核中')
const form = reactive({
  companyName: '星河科技有限公司',
  licenseNo: '91310000MA1HIREHUB',
  contactName: '林薇',
  contactPhone: '13800001234',
  address: '上海市浦东新区张江软件园'
})

function submitAudit() {
  status.value = '审核中'
  ElMessage.success('企业认证资料已提交，管理员审核通过后可发布职位')
}
</script>

<template>
  <section class="page-shell">
    <section class="panel-card header-card">
      <div>
        <div class="section-chip">企业认证</div>
        <h1>先认证企业，再开放招聘权限</h1>
        <p>真实招聘产品里，企业认证是职位质量和平台信任的第一道门槛。</p>
      </div>
      <el-tag size="large" round :type="status === '已通过' ? 'success' : status === '已驳回' ? 'danger' : 'warning'">
        {{ status }}
      </el-tag>
    </section>

    <section class="panel-card form-card">
      <el-form label-position="top">
        <el-form-item label="企业名称"><el-input v-model="form.companyName" /></el-form-item>
        <el-form-item label="统一社会信用代码"><el-input v-model="form.licenseNo" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item>
        <el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item>
        <el-form-item label="办公地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="营业执照">
          <el-upload drag action="#" :auto-upload="false">
            <el-icon><UploadFilled /></el-icon>
            <div class="el-upload__text">拖拽营业执照到这里，或点击上传</div>
          </el-upload>
        </el-form-item>
        <el-button type="primary" size="large" round @click="submitAudit">提交审核</el-button>
      </el-form>
    </section>
  </section>
</template>

<style scoped>
.page-shell {
  display: grid;
  gap: 20px;
}

.header-card {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
  padding: 26px;
}

.header-card h1 {
  margin: 12px 0 8px;
  color: var(--brand-title);
  font-size: 34px;
}

.header-card p {
  margin: 0;
  color: var(--brand-ink-soft);
}

.form-card {
  max-width: 780px;
  padding: 26px;
}
</style>
