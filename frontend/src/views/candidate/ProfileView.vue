<script setup lang="ts">
const preferenceItems = [
  { label: '求职状态', value: '积极求职中' },
  { label: '期望岗位', value: 'Java 后端 / 前端 / 产品' },
  { label: '期望城市', value: '上海 / 杭州 / 北京' },
  { label: '隐私设置', value: '仅对招聘方可见' }
]

const accountSettings = [
  { label: '消息提醒', desc: '投递反馈、面试邀请和系统通知都推送到当前账号。' },
  { label: '简历公开', desc: '允许招聘方在搜索和推荐结果里查看你的简历摘要。' },
  { label: '求职状态展示', desc: '展示你是否正在积极找工作，方便招聘方快速判断。' }
]
</script>

<template>
  <section class="candidate-page">
    <header class="page-hero panel-card">
      <div>
        <div class="section-chip">个人中心</div>
        <h1>管理你的账号、偏好和隐私信息</h1>
        <p>这里集中维护头像、昵称、联系方式、求职偏好和账号安全设置，保持求职信息一致。</p>
      </div>

      <el-button type="primary" round>保存设置</el-button>
    </header>

    <section class="profile-layout">
      <aside class="panel-card sidebar-card">
        <div class="profile-head">
          <div class="avatar">张</div>
          <div>
            <h2>张同学</h2>
            <p>Java 后端 / 前端 求职者</p>
          </div>
        </div>

        <div class="profile-rows">
          <div v-for="item in preferenceItems" :key="item.label" class="profile-row">
            <span>{{ item.label }}</span>
            <strong>{{ item.value }}</strong>
          </div>
        </div>

        <div class="security-card">
          <div class="security-card__title">账号安全</div>
          <div class="security-card__desc">建议开启双重验证，并定期更新密码。</div>
        </div>
      </aside>

      <div class="content-column">
        <article class="panel-card section-card">
          <div class="section-title-row">
            <div>
              <div class="section-chip">基础资料</div>
              <h2>联系方式与个人简介</h2>
            </div>
          </div>

          <el-form label-position="top">
            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="昵称">
                  <el-input model-value="张同学" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="手机号">
                  <el-input model-value="13800138000" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="16">
              <el-col :xs="24" :md="12">
                <el-form-item label="邮箱">
                  <el-input model-value="zhang@example.com" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :md="12">
                <el-form-item label="当前城市">
                  <el-select model-value="上海" style="width: 100%">
                    <el-option label="上海" value="上海" />
                    <el-option label="杭州" value="杭州" />
                    <el-option label="北京" value="北京" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="个人简介">
              <el-input
                :rows="4"
                type="textarea"
                model-value="3 年 Java 后端经验，熟悉招聘平台中台、前端页面和权限体系。"
              />
            </el-form-item>
          </el-form>
        </article>

        <section class="grid-two">
          <article class="panel-card section-card">
            <div class="section-title-row">
              <div>
                <div class="section-chip">求职偏好</div>
                <h2>推荐参数</h2>
              </div>
            </div>

            <div class="preference-stack">
              <div class="preference-card">
                <span>目标岗位</span>
                <strong>Java 后端 / 前端 / 产品</strong>
              </div>
              <div class="preference-card">
                <span>薪资范围</span>
                <strong>20K-35K</strong>
              </div>
              <div class="preference-card">
                <span>到岗时间</span>
                <strong>两周内</strong>
              </div>
            </div>
          </article>

          <article class="panel-card section-card">
            <div class="section-title-row">
              <div>
                <div class="section-chip">消息设置</div>
                <h2>通知和提醒</h2>
              </div>
            </div>

            <div class="setting-stack">
              <div v-for="item in accountSettings" :key="item.label" class="setting-card">
                <div class="setting-card__head">
                  <strong>{{ item.label }}</strong>
                  <el-switch :model-value="true" />
                </div>
                <p>{{ item.desc }}</p>
              </div>
            </div>
          </article>
        </section>
      </div>
    </section>
  </section>
</template>

<style scoped>
.candidate-page {
  display: grid;
  gap: 20px;
}

.page-hero {
  padding: 28px 30px;
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
}

.page-hero h1 {
  margin: 16px 0 10px;
  font-size: 32px;
  line-height: 1.25;
  color: var(--brand-title);
}

.page-hero p {
  margin: 0;
  max-width: 720px;
  color: var(--brand-ink-soft);
  line-height: 1.8;
}

.profile-layout {
  display: grid;
  grid-template-columns: 300px minmax(0, 1fr);
  gap: 16px;
}

.sidebar-card,
.section-card {
  padding: 24px;
}

.profile-head {
  display: flex;
  align-items: center;
  gap: 14px;
  padding-bottom: 18px;
  border-bottom: 1px solid var(--brand-line);
}

.avatar {
  width: 58px;
  height: 58px;
  border-radius: 18px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, rgba(31, 107, 87, 0.14), rgba(31, 107, 87, 0.04));
  color: var(--brand-primary-deep);
  font-size: 22px;
  font-weight: 800;
}

.profile-head h2 {
  margin: 0;
  color: var(--brand-title);
}

.profile-head p {
  margin: 6px 0 0;
  color: var(--brand-ink-soft);
}

.profile-rows,
.setting-stack,
.preference-stack {
  display: grid;
  gap: 12px;
  margin-top: 18px;
}

.profile-row,
.preference-card,
.setting-card {
  padding: 16px;
  border-radius: 16px;
  border: 1px solid var(--brand-line);
  background: rgba(255, 255, 255, 0.78);
}

.profile-row {
  display: grid;
  gap: 4px;
}

.profile-row span,
.preference-card span {
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.profile-row strong,
.preference-card strong {
  color: var(--brand-title);
}

.security-card {
  margin-top: 18px;
  padding: 16px;
  border-radius: 18px;
  background: linear-gradient(180deg, rgba(31, 107, 87, 0.08), rgba(255, 255, 255, 0.96));
  border: 1px solid rgba(31, 107, 87, 0.12);
}

.security-card__title {
  color: var(--brand-title);
  font-weight: 700;
}

.security-card__desc {
  margin-top: 8px;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.content-column {
  display: grid;
  gap: 16px;
}

.grid-two {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.section-title-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-start;
  margin-bottom: 18px;
}

.section-title-row h2 {
  margin: 10px 0 0;
  font-size: 22px;
  color: var(--brand-title);
}

.setting-card__head {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.setting-card p {
  margin: 10px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

@media (max-width: 1080px) {
  .profile-layout,
  .grid-two {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 760px) {
  .page-hero {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
