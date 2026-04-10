import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { login as loginApi, logout as logoutApi } from '@/api/modules/auth'
import { fetchCurrentUser } from '@/api/modules/user'
import type { AuthUser, LoginRequest, LoginResponse, UserRole } from '@/types/auth'
import { clearAuthCache, loadAuthCache, saveAuthCache } from '@/utils/auth'

const roleNameMap: Record<string, UserRole> = {
  CANDIDATE: 'candidate',
  RECRUITER: 'recruiter',
  ADMIN: 'admin'
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref('')
  const refreshToken = ref('')
  const role = ref<UserRole>('guest')
  const user = ref<AuthUser | null>(null)

  const isLoggedIn = computed(() => token.value.length > 0)
  const permissionList = computed(() => user.value?.permissions ?? [])

  function applyTokenPayload(payload: LoginResponse) {
    token.value = payload.accessToken
    refreshToken.value = payload.refreshToken
    const backendRole = payload.roleList[0] ?? 'GUEST'
    role.value = roleNameMap[backendRole] ?? 'guest'
    saveAuthCache(token.value, refreshToken.value, role.value)
  }

  function setAccessToken(nextToken: string) {
    token.value = nextToken
    saveAuthCache(token.value, refreshToken.value, role.value)
  }

  async function loginByPassword(payload: LoginRequest) {
    const response = await loginApi(payload)
    applyTokenPayload(response.data.data)
    await loadCurrentUser()
  }

  async function loadCurrentUser() {
    const response = await fetchCurrentUser()
    const profile = response.data.data
    user.value = {
      id: profile.userId,
      name: profile.realName || profile.username,
      role: role.value,
      permissions: user.value?.permissions ?? [],
      companyName: user.value?.companyName
    }
  }

  function setPermissions(permissions: string[]) {
    user.value = user.value
      ? { ...user.value, permissions }
      : { id: 0, name: '未命名用户', role: role.value, permissions }
  }

  async function logout() {
    try {
      if (token.value) {
        await logoutApi()
      }
    } catch {
      // Ignore logout request failure and clear local session anyway.
    } finally {
      clearSession()
    }
  }

  function clearSession() {
    token.value = ''
    refreshToken.value = ''
    role.value = 'guest'
    user.value = null
    clearAuthCache()
  }

  function restoreSession() {
    const cached = loadAuthCache()
    token.value = cached.accessToken
    refreshToken.value = cached.refreshToken
    role.value = (cached.role as UserRole) || 'guest'
  }

  function hasPermission(permission: string) {
    return permissionList.value.includes(permission)
  }

  return {
    token,
    refreshToken,
    role,
    user,
    isLoggedIn,
    permissionList,
    applyTokenPayload,
    setAccessToken,
    setPermissions,
    loginByPassword,
    loadCurrentUser,
    logout,
    clearSession,
    restoreSession,
    hasPermission
  }
})
