import request from '@/api/request'

export function fetchAdminDashboard() {
  return request.get('/api/admin/dashboard')
}
