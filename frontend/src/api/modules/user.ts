import request from '@/api/request'
import type { ApiResponse } from '@/types/common'

export interface CurrentUserResponse {
  userId: number
  username: string
  realName?: string
  mobile?: string
  email?: string
  avatarUrl?: string
  roleList: string[]
}

export function fetchCurrentUser() {
  return request.get<ApiResponse<CurrentUserResponse>>('/api/users/me')
}
