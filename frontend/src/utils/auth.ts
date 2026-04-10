const ACCESS_TOKEN_KEY = 'recruit_access_token'
const REFRESH_TOKEN_KEY = 'recruit_refresh_token'
const ROLE_KEY = 'recruit_role'

export function saveAuthCache(accessToken: string, refreshToken: string, role: string) {
  localStorage.setItem(ACCESS_TOKEN_KEY, accessToken)
  localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken)
  localStorage.setItem(ROLE_KEY, role)
}

export function clearAuthCache() {
  localStorage.removeItem(ACCESS_TOKEN_KEY)
  localStorage.removeItem(REFRESH_TOKEN_KEY)
  localStorage.removeItem(ROLE_KEY)
}

export function loadAuthCache() {
  return {
    accessToken: localStorage.getItem(ACCESS_TOKEN_KEY) ?? '',
    refreshToken: localStorage.getItem(REFRESH_TOKEN_KEY) ?? '',
    role: localStorage.getItem(ROLE_KEY) ?? 'guest'
  }
}
