export type UserRole = 'guest' | 'candidate' | 'recruiter' | 'admin'

export interface AuthUser {
  id: number
  name: string
  role: UserRole
  companyName?: string
  permissions?: string[]
}

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  expiresIn: number
  roleList: string[]
}

export interface RefreshTokenRequest {
  refreshToken: string
}
