import request from '@/api/request'
import type { ApiResponse } from '@/types/common'
import type { LoginRequest, LoginResponse, RefreshTokenRequest } from '@/types/auth'

export function login(payload: LoginRequest) {
  return request.post<ApiResponse<LoginResponse>>('/api/auth/login', payload)
}

export function refreshToken(payload: RefreshTokenRequest) {
  return request.post<ApiResponse<LoginResponse>>('/api/auth/refresh', payload)
}

export function logout() {
  return request.post<ApiResponse<null>>('/api/auth/logout')
}
