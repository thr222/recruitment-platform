import { defineStore } from 'pinia'

export const useRecruiterStore = defineStore('recruiter', {
  state: () => ({
    currentCompanyId: 20001,
    pendingApplications: 18,
    onlineJobCount: 9
  }),
  actions: {
    setCurrentCompany(companyId: number) {
      this.currentCompanyId = companyId
    }
  }
})
