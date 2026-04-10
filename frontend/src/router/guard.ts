import type { RouteLocationNormalized } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

export function authGuard(to: RouteLocationNormalized): true | string {
  const authStore = useAuthStore()
  const requiredRole = to.meta.requiredRole as string | undefined

  if (!requiredRole) {
    return true
  }

  if (!authStore.isLoggedIn) {
    return '/login'
  }

  if (authStore.role !== requiredRole) {
    return '/'
  }

  return true
}

export function afterRouteChange(to: RouteLocationNormalized) {
  if (to.meta.title) {
    document.title = `${String(to.meta.title)} - 企业招聘与求职平台`
  }
}
