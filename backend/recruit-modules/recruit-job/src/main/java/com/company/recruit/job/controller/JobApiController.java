package com.company.recruit.job.controller;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.core.api.Result;
import com.company.recruit.job.dto.AuditJobRequest;
import com.company.recruit.job.dto.JobCreateRequest;
import com.company.recruit.job.dto.JobUpdateRequest;
import com.company.recruit.job.dto.UpdateJobStatusRequest;
import com.company.recruit.job.query.CandidateRecommendJobQuery;
import com.company.recruit.job.query.JobAuditPageQuery;
import com.company.recruit.job.query.JobSearchQuery;
import com.company.recruit.job.query.RecruiterJobPageQuery;
import com.company.recruit.job.service.JobFacadeService;
import com.company.recruit.job.vo.JobAuditItemVO;
import com.company.recruit.job.vo.JobCreateResultVO;
import com.company.recruit.job.vo.JobDetailVO;
import com.company.recruit.job.vo.PublicJobItemVO;
import com.company.recruit.job.vo.RecommendedJobItemVO;
import com.company.recruit.job.vo.RecruiterJobItemVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class JobApiController {

    private final JobFacadeService jobFacadeService;

    public JobApiController(JobFacadeService jobFacadeService) {
        this.jobFacadeService = jobFacadeService;
    }

    @PreAuthorize("hasAuthority('job:create')")
    @PostMapping("/api/recruiter/jobs")
    public Result<JobCreateResultVO> createJob(@Valid @RequestBody JobCreateRequest request) {
        return Result.success("job created", jobFacadeService.createJob(request));
    }

    @PreAuthorize("hasAuthority('job:update')")
    @PutMapping("/api/recruiter/jobs/{jobId}")
    public Result<Boolean> updateJob(@PathVariable Long jobId, @Valid @RequestBody JobUpdateRequest request) {
        return Result.success("job updated", jobFacadeService.updateJob(jobId, request));
    }

    @PreAuthorize("hasAuthority('job:status:update')")
    @PatchMapping("/api/recruiter/jobs/{jobId}/status")
    public Result<Boolean> updateJobStatus(@PathVariable Long jobId, @Valid @RequestBody UpdateJobStatusRequest request) {
        return Result.success("job status updated", jobFacadeService.updateJobStatus(jobId, request));
    }

    @PreAuthorize("hasAuthority('job:update')")
    @GetMapping("/api/recruiter/jobs")
    public Result<PageResponse<RecruiterJobItemVO>> pageRecruiterJobs(@Valid RecruiterJobPageQuery query) {
        return Result.success(jobFacadeService.pageRecruiterJobs(query));
    }

    @GetMapping("/api/jobs")
    public Result<PageResponse<PublicJobItemVO>> searchJobs(@Valid JobSearchQuery query) {
        return Result.success(jobFacadeService.searchJobs(query));
    }

    @GetMapping("/api/jobs/{jobId}")
    public Result<JobDetailVO> getJobDetail(@PathVariable Long jobId) {
        return Result.success(jobFacadeService.getJobDetail(jobId));
    }

    @GetMapping("/api/candidate/jobs/recommendations")
    public Result<PageResponse<RecommendedJobItemVO>> pageCandidateRecommendations(@Valid CandidateRecommendJobQuery query) {
        return Result.success(jobFacadeService.pageCandidateRecommendations(query));
    }

    @PreAuthorize("hasAuthority('job:audit')")
    @GetMapping("/api/admin/jobs/audits")
    public Result<PageResponse<JobAuditItemVO>> pageJobAudits(@Valid JobAuditPageQuery query) {
        return Result.success(jobFacadeService.pageJobAudits(query));
    }

    @PreAuthorize("hasAuthority('job:audit')")
    @PatchMapping("/api/admin/jobs/{jobId}/audit")
    public Result<Boolean> auditJob(@PathVariable Long jobId, @Valid @RequestBody AuditJobRequest request) {
        return Result.success("job audited", jobFacadeService.auditJob(jobId, request));
    }
}