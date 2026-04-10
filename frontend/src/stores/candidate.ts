import { defineStore } from 'pinia'

export const useCandidateStore = defineStore('candidate', {
  state: () => ({
    resumeCompletion: 88,
    favoriteCount: 6,
    latestApplicationStatus: 'INTERVIEWING'
  }),
  actions: {
    setResumeCompletion(value: number) {
      this.resumeCompletion = value
    }
  }
})
