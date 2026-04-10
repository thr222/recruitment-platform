import type { UserRole } from '@/types/auth'

export interface AppRouteMeta {
  title: string
  section?: string
  requiredRole?: Exclude<UserRole, 'guest'>
  permission?: string
  hiddenInMenu?: boolean
}
