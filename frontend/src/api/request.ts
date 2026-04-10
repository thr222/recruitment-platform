import axios, { type AxiosError, type InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'
import { createIdempotencyKey } from '@/utils/idempotency'
import type { LoginResponse } from '@/types/auth'
import type { ApiResponse } from '@/types/common'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || ''

type RetryableRequestConfig = InternalAxiosRequestConfig & { _retry?: boolean }

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 15000
})

let isRefreshing = false
let pendingQueue: Array<(token: string) => void> = []

function resolvePendingQueue(token: string) {
  pendingQueue.forEach((callback) => callback(token))
  pendingQueue = []
}

request.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const authStore = useAuthStore()
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  const method = config.method?.toUpperCase()
  if (method && ['POST', 'PUT', 'PATCH', 'DELETE'].includes(method) && !config.headers['X-Idempotency-Key']) {
    config.headers['X-Idempotency-Key'] = createIdempotencyKey(method.toLowerCase())
  }
  return config
})

request.interceptors.response.use(
  (response) => {
    const body = response.data as ApiResponse<unknown> | undefined
    if (body && body.success === false) {
      ElMessage.error(body.message || 'Request failed')
      return Promise.reject(body)
    }
    return response
  },
  async (error: AxiosError<ApiResponse<never>>) => {
    const authStore = useAuthStore()
    const originalRequest = (error.config || {}) as RetryableRequestConfig
    const status = error.response?.status
    const message = error.response?.data?.message
    const isRefreshRequest = originalRequest.url?.includes('/api/auth/refresh')

    if (status === 401 && !originalRequest._retry && !isRefreshRequest && authStore.refreshToken) {
      if (isRefreshing) {
        return new Promise((resolve) => {
          pendingQueue.push((nextToken: string) => {
            if (!originalRequest.headers) {
              originalRequest.headers = {} as InternalAxiosRequestConfig['headers']
            }
            originalRequest.headers.Authorization = `Bearer ${nextToken}`
            resolve(request(originalRequest))
          })
        })
      }

      originalRequest._retry = true
      isRefreshing = true

      try {
        const refreshResponse = await axios.post<ApiResponse<LoginResponse>>(
          `${API_BASE_URL}/api/auth/refresh`,
          { refreshToken: authStore.refreshToken },
          { timeout: 15000 }
        )
        const tokenPayload = refreshResponse.data.data
        authStore.applyTokenPayload(tokenPayload)
        resolvePendingQueue(tokenPayload.accessToken)
        if (!originalRequest.headers) {
          originalRequest.headers = {} as InternalAxiosRequestConfig['headers']
        }
        originalRequest.headers.Authorization = `Bearer ${tokenPayload.accessToken}`
        return request(originalRequest)
      } catch (refreshError) {
        authStore.clearSession()
        ElMessage.error('Login session expired, please sign in again')
        if (window.location.pathname !== '/login') {
          window.location.href = '/login'
        }
        return Promise.reject(refreshError)
      } finally {
        isRefreshing = false
      }
    }

    if (message) {
      ElMessage.error(message)
    } else if (status && status >= 500) {
      ElMessage.error('Server error, please try again later')
    } else if (error.message) {
      ElMessage.error(error.message)
    }

    return Promise.reject(error)
  }
)

export default request