package com.company.recruit.job.service;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.job.dto.AuditJobRequest;
import com.company.recruit.job.dto.JobCreateRequest;
import com.company.recruit.job.dto.JobUpdateRequest;
import com.company.recruit.job.dto.UpdateJobStatusRequest;
import com.company.recruit.job.query.CandidateRecommendJobQuery;
import com.company.recruit.job.query.JobAuditPageQuery;
import com.company.recruit.job.query.JobSearchQuery;
import com.company.recruit.job.query.RecruiterJobPageQuery;
import com.company.recruit.job.vo.JobAuditItemVO;
import com.company.recruit.job.vo.JobCreateResultVO;
import com.company.recruit.job.vo.JobDetailVO;
import com.company.recruit.job.vo.PublicJobItemVO;
import com.company.recruit.job.vo.RecommendedJobItemVO;
import com.company.recruit.job.vo.RecruiterJobItemVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JobFacadeService {

    private final JobRecommendationService jobRecommendationService;

    public JobFacadeService(JobRecommendationService jobRecommendationService) {
        this.jobRecommendationService = jobRecommendationService;
    }

    public JobCreateResultVO createJob(JobCreateRequest request) {
        return JobCreateResultVO.builder().jobId(30001L).status("DRAFT").build();
    }

    public boolean updateJob(Long jobId, JobUpdateRequest request) {
        return true;
    }

    public boolean updateJobStatus(Long jobId, UpdateJobStatusRequest request) {
        return true;
    }

    public PageResponse<RecruiterJobItemVO> pageRecruiterJobs(RecruiterJobPageQuery query) {
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            1L,
            List.of(
                RecruiterJobItemVO.builder()
                    .jobId(30001L)
                    .title("Java Backend Engineer")
                    .status("ONLINE")
                    .applyCount(18)
                    .viewCount(120)
                    .publishedAt("2026-04-08 10:30:00")
                    .build()
            )
        );
    }

    public PageResponse<PublicJobItemVO> searchJobs(JobSearchQuery query) {
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            1L,
            List.of(
                PublicJobItemVO.builder()
                    .jobId(30001L)
                    .title("Java Backend Engineer")
                    .companyId(20001L)
                    .companyName("Galaxy Talent Tech")
                    .workCityCode("310100")
                    .salaryMin(25)
                    .salaryMax(40)
                    .experienceMin(3)
                    .experienceMax(5)
                    .educationRequirement("Bachelor")
                    .status("ONLINE")
                    .publishedAt("2026-04-08 10:30:00")
                    .build()
            )
        );
    }

    public JobDetailVO getJobDetail(Long jobId) {
        jobRecommendationService.recordJobViewBehavior(jobId);
        return JobDetailVO.builder()
            .jobId(jobId)
            .title("Java Backend Engineer")
            .company(JobDetailVO.JobCompanyVO.builder().companyId(20001L).companyName("Galaxy Talent Tech").build())
            .salaryMin(25)
            .salaryMax(40)
            .descriptionText("Build job, application, and permission modules for the recruitment platform.")
            .status("ONLINE")
            .build();
    }

    public PageResponse<RecommendedJobItemVO> pageCandidateRecommendations(CandidateRecommendJobQuery query) {
        return jobRecommendationService.pageCandidateRecommendations(query);
    }

    public PageResponse<JobAuditItemVO> pageJobAudits(JobAuditPageQuery query) {
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            1L,
            List.of(
                JobAuditItemVO.builder()
                    .jobId(30001L)
                    .title("Java Backend Engineer")
                    .companyName("Galaxy Talent Tech")
                    .auditStatus("PENDING")
                    .build()
            )
        );
    }

    public boolean auditJob(Long jobId, AuditJobRequest request) {
        return true;
    }
}