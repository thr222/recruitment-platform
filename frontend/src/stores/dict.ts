import { defineStore } from 'pinia'

export const useDictStore = defineStore('dict', {
  state: () => ({
    educationOptions: ['大专', '本科', '硕士', '博士'],
    experienceOptions: ['应届', '1-3年', '3-5年', '5-10年'],
    cityOptions: ['北京', '上海', '杭州', '深圳']
  })
})
