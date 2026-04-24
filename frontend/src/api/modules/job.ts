import request, { type AppRequestConfig } from '@/api/request'
import type { ApiResponse, PageResponse } from '@/types/common'
import type { JobDetail, JobListItem, JobSearchParams } from '@/types/job'

export function searchJobs(params: JobSearchParams, config?: AppRequestConfig) {
  return request.get<ApiResponse<PageResponse<JobListItem>>>('/api/jobs', {
    ...config,
    params
  })
}

export function fetchJobDetail(jobId: string | number) {
  return request.get<ApiResponse<JobDetail>>(`/api/jobs/${jobId}`)
}
