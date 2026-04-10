import { defineStore } from 'pinia'

export const useAdminStore = defineStore('admin', {
  state: () => ({
    pendingCompanyAudits: 12,
    pendingJobAudits: 21,
    reportCount: 4
  })
})
