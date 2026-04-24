<script setup lang="ts">
import { computed, ref } from 'vue'

type CategoryKey = 'all' | 'delivery' | 'interview' | 'system'

const categories: Array<{ key: CategoryKey; label: string; count: number }> = [
  { key: 'all', label: '全部消息', count: 6 },
  { key: 'delivery', label: '投递动态', count: 2 },
  { key: 'interview', label: '面试邀请', count: 2 },
  { key: 'system', label: '系统通知', count: 2 }
]

const selectedCategory = ref<CategoryKey>('all')

const messages = [
  {
    id: 1,
    category: 'interview',
    read: false,
    title: '星河科技邀请你参加面试',
    company: '星河科技',
    time: '2026-04-08 14:30',
    content: '请确认你能否在周五下午 3 点参加线上技术面。',
    detail: '岗位为 Java 后端开发工程师，面试官会重点关注 Spring Boot、数据库设计和项目经验。'
  },
  {
    id: 2,
    category: 'delivery',
    read: false,
    title: '远帆智能已查看你的简历',
    company: '远帆智能',
    time: '2026-04-07 19:10',
    content: '你的 Vue3 前端工程师投递已经进入初筛阶段。',
    detail: '招聘方正在评估你的前端经验和中后台项目经历，建议保持电话畅通。'
  },
  {
    id: 3,
    category: 'system',
    read: true,
    title: '简历完整度提升提醒',
    company: '系统消息',
    time: '2026-04-07 09:20',
    content: '补齐项目成果和目标薪资后，推荐准确度会更高。',
    detail: '你可以在我的简历中补充项目结果、技术栈标签和近期工作经历。'
  },
  {
    id: 4,
    category: 'interview',
    read: true,
    title: '云图人才面试邀约待确认',
    company: '云图人才',
    time: '2026-04-06 18:40',
    content: '产品经理岗位面试时间可选周一或周二。',
    detail: '建议提前查看公司主页和岗位要求，准备一份简短的自我介绍。'
  }
]

const filteredMessages = computed(() =>
  selectedCategory.value === 'all'
    ? messages
    : messages.filter((message) => message.category === selectedCategory.value)
)

const activeMessage = computed(() => filteredMessages.value[0] ?? messages[0])
</script>

<template>
  <section class="candidate-page">
    <header class="page-hero panel-card">
      <div>
        <div class="section-chip">消息通知</div>
        <h1>把投递反馈、面试邀请和系统提醒统一收进来</h1>
        <p>这里可以快速确认下一步动作，避免错过任何一条和求职流程相关的重要消息。</p>
      </div>

      <el-button type="primary" round>全部标记已读</el-button>
    </header>

    <section class="message-layout">
      <aside class="panel-card sidebar-card">
        <div class="section-title-row">
          <div>
            <div class="section-chip">消息分类</div>
            <h2>筛选消息类型</h2>
          </div>
        </div>

        <div class="category-stack">
          <button
            v-for="category in categories"
            :key="category.key"
            class="category-chip"
            :class="{ 'is-active': selectedCategory === category.key }"
            @click="selectedCategory = category.key"
          >
            <span>{{ category.label }}</span>
            <strong>{{ category.count }}</strong>
          </button>
        </div>
      </aside>

      <div class="content-column">
        <article class="panel-card section-card">
          <div class="section-title-row">
            <div>
              <div class="section-chip">消息列表</div>
              <h2>最近通知</h2>
            </div>
            <el-tag type="warning" round>未读 2 条</el-tag>
          </div>

          <div class="message-list">
            <div
              v-for="message in filteredMessages"
              :key="message.id"
              class="message-card"
              :class="{ 'is-unread': !message.read }"
            >
              <div class="message-card__head">
                <div>
                  <strong>{{ message.title }}</strong>
                  <div class="message-card__meta">
                    <span>{{ message.company }}</span>
                    <span>{{ message.time }}</span>
                  </div>
                </div>
                <el-tag :type="message.read ? 'info' : 'warning'" round>
                  {{ message.read ? '已读' : '未读' }}
                </el-tag>
              </div>

              <p>{{ message.content }}</p>
            </div>
          </div>
        </article>

        <article class="panel-card section-card">
          <div class="section-title-row">
            <div>
              <div class="section-chip">消息详情</div>
              <h2>选中消息预览</h2>
            </div>
          </div>

          <div class="detail-card">
            <div class="detail-card__title">{{ activeMessage.title }}</div>
            <div class="detail-card__sub">{{ activeMessage.company }} · {{ activeMessage.time }}</div>
            <p>{{ activeMessage.detail }}</p>

            <div class="detail-card__actions">
              <el-button type="primary" round>回复消息</el-button>
              <el-button round>标记已读</el-button>
            </div>
          </div>
        </article>
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

.message-layout {
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  gap: 16px;
}

.sidebar-card,
.section-card {
  padding: 24px;
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

.category-stack {
  display: grid;
  gap: 10px;
}

.category-chip {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  width: 100%;
  padding: 14px 16px;
  border-radius: 16px;
  border: 1px solid var(--brand-line);
  background: rgba(255, 255, 255, 0.78);
  color: var(--brand-title);
  cursor: pointer;
  transition: all 0.2s ease;
}

.category-chip:hover,
.category-chip.is-active {
  transform: translateY(-2px);
  border-color: rgba(31, 107, 87, 0.24);
  box-shadow: 0 12px 24px rgba(17, 24, 39, 0.06);
}

.category-chip strong {
  color: var(--brand-primary-deep);
}

.content-column {
  display: grid;
  gap: 16px;
}

.message-list {
  display: grid;
  gap: 12px;
}

.message-card {
  padding: 18px;
  border-radius: 18px;
  border: 1px solid var(--brand-line);
  background: rgba(255, 255, 255, 0.78);
}

.message-card.is-unread {
  background: linear-gradient(180deg, rgba(31, 107, 87, 0.06), rgba(255, 255, 255, 0.88));
}

.message-card__head {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: flex-start;
}

.message-card__head strong,
.detail-card__title {
  color: var(--brand-title);
}

.message-card__meta {
  display: flex;
  gap: 10px;
  margin-top: 8px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.message-card p,
.detail-card p {
  margin: 12px 0 0;
  color: var(--brand-ink-soft);
  line-height: 1.7;
}

.detail-card {
  padding: 18px;
  border-radius: 18px;
  border: 1px solid var(--brand-line);
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(247, 243, 235, 0.72));
}

.detail-card__title {
  font-size: 20px;
  font-weight: 700;
}

.detail-card__sub {
  margin-top: 8px;
  color: var(--brand-ink-soft);
}

.detail-card__actions {
  display: flex;
  gap: 10px;
  margin-top: 18px;
  flex-wrap: wrap;
}

@media (max-width: 1080px) {
  .message-layout {
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
