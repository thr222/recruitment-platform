import request from '@/api/request'
import type { ApiResponse, PageResponse } from '@/types/common'
import type { JobDetail, JobListItem, JobSearchParams } from '@/types/job'

export function searchJobs(params: JobSearchParams) {
  return request.get<ApiResponse<PageResponse<JobListItem>>>('/api/jobs', {
    params
  })
}

export function fetchJobDetail(jobId: string | number) {
  return request.get<ApiResponse<JobDetail>>(`/api/jobs/${jobId}`)
}
