import { defineStore } from 'pinia'

export const useMessageStore = defineStore('message', {
  state: () => ({
    unreadCount: 3,
    notifications: [
      { id: 1, title: '面试状态更新', content: '星河科技将你推进到面试中', read: false },
      { id: 2, title: '职位推荐', content: '有 12 个新职位符合你的求职意向', read: true }
    ]
  })
})
