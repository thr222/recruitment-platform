import request from '@/api/request'

export function fetchMyResume() {
  return request.get('/api/candidate/resume/mine')
}
