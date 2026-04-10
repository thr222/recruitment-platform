import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', () => {
  const pageTitle = ref('企业招聘与求职平台')
  const breadcrumb = ref<string[]>([])

  const pageSubtitle = computed(() => breadcrumb.value.join(' / '))

  function updatePageMeta(title: string, trail: string[]) {
    pageTitle.value = title
    breadcrumb.value = trail
  }

  return {
    pageTitle,
    breadcrumb,
    pageSubtitle,
    updatePageMeta
  }
})
