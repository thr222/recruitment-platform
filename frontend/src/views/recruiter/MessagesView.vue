<script setup lang="ts">
import { computed, ref } from 'vue'

interface ConversationItem {
  id: number
  name: string
  role: string
  lastMessage: string
  time: string
  unread: number
}

interface MessageItem {
  id: number
  sender: '招聘方' | '候选人'
  content: string
  time: string
}

const activeConversationId = ref(1)
const draft = ref('')

const conversations = ref<ConversationItem[]>([
  {
    id: 1,
    name: '王同学',
    role: 'Java 后端开发工程师',
    lastMessage: '我可以下周一下午面试。',
    time: '09:32',
    unread: 2
  },
  {
    id: 2,
    name: '陈同学',
    role: '产品经理',
    lastMessage: '想了解一下团队规模和职责范围。',
    time: '昨天',
    unread: 0
  },
  {
    id: 3,
    name: '李同学',
    role: '测试开发工程师',
    lastMessage: '简历已更新，麻烦看一下最新版本。',
    time: '昨天',
    unread: 1
  }
])

const messages = ref<Record<number, MessageItem[]>>({
  1: [
    { id: 1, sender: '招聘方', content: '你好，想和你确认一下后端岗位的面试时间。', time: '09:10' },
    { id: 2, sender: '候选人', content: '可以，周一到周三都方便。', time: '09:18' },
    { id: 3, sender: '招聘方', content: '那我们先定周一下午 3 点，稍后发会议链接。', time: '09:22' },
    { id: 4, sender: '候选人', content: '我可以下周一下午面试。', time: '09:32' }
  ],
  2: [
    { id: 1, sender: '招聘方', content: '欢迎投递产品经理岗位，方便聊一下你的项目经历吗？', time: '昨天 16:18' },
    { id: 2, sender: '候选人', content: '可以，我最近做的是企业服务类产品。', time: '昨天 16:24' }
  ],
  3: [
    { id: 1, sender: '候选人', content: '简历我更新了，主要补充了自动化测试项目。', time: '昨天 11:02' },
    { id: 2, sender: '招聘方', content: '收到，我晚点再看一下。', time: '昨天 11:06' }
  ]
})

const currentConversation = computed(() => {
  return conversations.value.find((item) => item.id === activeConversationId.value) ?? conversations.value[0]
})

const currentMessages = computed(() => messages.value[activeConversationId.value] ?? [])

function sendMessage() {
  const text = draft.value.trim()
  if (!text) return

  messages.value[activeConversationId.value] = [
    ...currentMessages.value,
    {
      id: Date.now(),
      sender: '招聘方',
      content: text,
      time: '刚刚'
    }
  ]
  draft.value = ''
}
</script>

<template>
  <section class="page-shell">
    <section class="header-card panel-card">
      <div>
        <div class="section-chip">消息沟通</div>
        <h1>把候选人沟通、投递提醒和系统消息放到一个沟通中心里。</h1>
        <p>左侧看会话列表，右侧看消息线程，方便招聘方快速回复和推进流程。</p>
      </div>
      <div class="header-actions">
        <el-badge :value="conversations.reduce((sum, item) => sum + item.unread, 0)">
          <el-button type="primary" round>全部标记已读</el-button>
        </el-badge>
      </div>
    </section>

    <section class="chat-layout">
      <aside class="panel-card conversation-card">
        <div class="section-title">会话列表</div>
        <div class="section-subtitle">按候选人维度查看最新沟通</div>

        <div class="conversation-list">
          <button
            v-for="item in conversations"
            :key="item.id"
            class="conversation-item"
            :class="{ active: item.id === activeConversationId }"
            @click="activeConversationId = item.id"
          >
            <div class="conversation-avatar">{{ item.name.slice(0, 1) }}</div>
            <div class="conversation-main">
              <div class="conversation-top">
                <strong>{{ item.name }}</strong>
                <span>{{ item.time }}</span>
              </div>
              <div class="conversation-role">{{ item.role }}</div>
              <div class="conversation-message">{{ item.lastMessage }}</div>
            </div>
            <el-badge :value="item.unread" :hidden="item.unread === 0" />
          </button>
        </div>
      </aside>

      <main class="panel-card thread-card">
        <div class="thread-header">
          <div>
            <div class="section-title">{{ currentConversation.name }}</div>
            <div class="section-subtitle">{{ currentConversation.role }}</div>
          </div>
          <div class="thread-actions">
            <el-button round>发面试邀请</el-button>
            <el-button type="primary" round>快捷回复</el-button>
          </div>
        </div>

        <div class="message-list">
          <article
            v-for="message in currentMessages"
            :key="message.id"
            class="message-item"
            :class="{ me: message.sender === '招聘方' }"
          >
            <div class="message-bubble">
              <div class="message-meta">
                <strong>{{ message.sender }}</strong>
                <span>{{ message.time }}</span>
              </div>
              <p>{{ message.content }}</p>
            </div>
          </article>
        </div>

        <div class="reply-box">
          <el-input
            v-model="draft"
            type="textarea"
            :rows="4"
            placeholder="输入回复内容，支持快速推进面试、确认时间或补充职位信息"
          />
          <div class="reply-actions">
            <el-tag effect="light" round>常用回复</el-tag>
            <el-tag effect="light" round>发送链接</el-tag>
            <el-button type="primary" round @click="sendMessage">发送消息</el-button>
          </div>
        </div>
      </main>
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
  gap: 16px;
  align-items: center;
  padding: 24px;
}

.header-card h1 {
  margin: 10px 0 8px;
  color: var(--brand-title);
  font-size: 28px;
  line-height: 1.2;
}

.header-card p {
  margin: 0;
  color: var(--brand-ink-soft);
  max-width: 680px;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.chat-layout {
  display: grid;
  grid-template-columns: 320px minmax(0, 1fr);
  gap: 16px;
}

.conversation-card,
.thread-card {
  padding: 22px;
}

.section-title {
  color: var(--brand-title);
  font-size: 22px;
  font-weight: 800;
}

.section-subtitle {
  margin-top: 6px;
  color: var(--brand-ink-soft);
  font-size: 13px;
}

.conversation-list {
  display: grid;
  gap: 10px;
  margin-top: 16px;
}

.conversation-item {
  display: grid;
  grid-template-columns: 46px minmax(0, 1fr) auto;
  gap: 12px;
  align-items: center;
  width: 100%;
  padding: 14px;
  border: 1px solid transparent;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.7);
  text-align: left;
  cursor: pointer;
  transition: all 0.2s ease;
}

.conversation-item:hover,
.conversation-item.active {
  border-color: rgba(10, 102, 194, 0.16);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.06);
  transform: translateY(-2px);
}

.conversation-avatar {
  width: 46px;
  height: 46px;
  display: grid;
  place-items: center;
  border-radius: 16px;
  background: rgba(10, 102, 194, 0.12);
  color: var(--brand-primary);
  font-weight: 800;
}

.conversation-top {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.conversation-top strong {
  color: var(--brand-title);
}

.conversation-top span,
.conversation-role,
.conversation-message {
  color: var(--brand-ink-soft);
  font-size: 12px;
}

.conversation-message {
  margin-top: 4px;
}

.thread-card {
  display: grid;
  gap: 18px;
  min-height: 620px;
}

.thread-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: center;
}

.thread-actions,
.reply-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.message-list {
  display: grid;
  gap: 12px;
  align-content: start;
}

.message-item {
  display: flex;
}

.message-item.me {
  justify-content: flex-end;
}

.message-bubble {
  max-width: min(620px, 92%);
  padding: 14px 16px;
  border-radius: 18px;
  background: rgba(10, 102, 194, 0.07);
  border: 1px solid rgba(10, 102, 194, 0.08);
}

.message-item.me .message-bubble {
  background: rgba(10, 102, 194, 0.12);
}

.message-meta {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  color: var(--brand-ink-soft);
  font-size: 12px;
}

.message-bubble p {
  margin: 10px 0 0;
  color: var(--brand-title);
  line-height: 1.7;
}

.reply-box {
  display: grid;
  gap: 12px;
  margin-top: auto;
}

@media (max-width: 1200px) {
  .header-card,
  .chat-layout {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
