import request from '@/api/request'

export function fetchMyApplications() {
  return request.get('/api/candidate/applications')
}
