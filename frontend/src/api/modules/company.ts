import request from '@/api/request'

export function fetchMyCompany() {
  return request.get('/api/recruiter/company/mine')
}
