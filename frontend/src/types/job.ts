export interface JobCardItem {
  id: number
  title: string
  companyName: string
  city: string
  salary: string
  experience: string
  tags: string[]
}

export interface JobListItem {
  jobId: number
  title: string
  companyId?: number
  companyName: string
  workCityCode: string
  salaryMin: number
  salaryMax: number
  experienceMin?: number
  experienceMax?: number
  educationRequirement?: string
  status?: string
  publishedAt?: string
}

export interface JobSearchParams {
  pageNo: number
  pageSize: number
  keyword?: string
  cityCode?: string
  salaryMin?: number
  salaryMax?: number
  sortBy?: 'latest' | 'salary' | 'hot'
}

export interface JobDetail {
  jobId: number
  title: string
  salaryMin: number
  salaryMax: number
  descriptionText?: string
  status?: string
  company?: {
    companyId: number
    companyName: string
  }
}
