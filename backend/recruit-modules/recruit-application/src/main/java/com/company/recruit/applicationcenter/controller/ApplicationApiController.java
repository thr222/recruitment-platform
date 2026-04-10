package com.company.recruit.applicationcenter.controller;

import com.company.recruit.applicationcenter.dto.CreateApplicationRequest;
import com.company.recruit.applicationcenter.dto.CreateInterviewRequest;
import com.company.recruit.applicationcenter.dto.UpdateApplicationStatusRequest;
import com.company.recruit.applicationcenter.dto.UpdateInterviewResultRequest;
import com.company.recruit.applicationcenter.dto.WithdrawApplicationRequest;
import com.company.recruit.applicationcenter.query.CandidateApplicationPageQuery;
import com.company.recruit.applicationcenter.query.RecruiterApplicationPageQuery;
import com.company.recruit.applicationcenter.service.ApplicationApiService;
import com.company.recruit.applicationcenter.vo.ApplicationCreateResultVO;
import com.company.recruit.applicationcenter.vo.CandidateApplicationDetailVO;
import com.company.recruit.applicationcenter.vo.CandidateApplicationItemVO;
import com.company.recruit.applicationcenter.vo.InterviewCreateResultVO;
import com.company.recruit.applicationcenter.vo.RecruiterApplicationItemVO;
import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.core.api.Result;
import com.company.recruit.common.web.annotation.PreventDuplicateSubmit;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ApplicationApiController {

    private final ApplicationApiService applicationApiService;

    public ApplicationApiController(ApplicationApiService applicationApiService) {
        this.applicationApiService = applicationApiService;
    }

    @PreventDuplicateSubmit(scene = "candidate-apply-job", intervalSeconds = 5, requireIdempotencyKey = true)
    @PreAuthorize("hasAuthority('application:create')")
    @PostMapping("/api/candidate/applications")
    public Result<ApplicationCreateResultVO> createApplication(@Valid @RequestBody CreateApplicationRequest request) {
        return Result.success("application created", applicationApiService.createApplication(request));
    }

    @PreAuthorize("hasAuthority('application:view')")
    @GetMapping("/api/candidate/applications")
    public Result<PageResponse<CandidateApplicationItemVO>> pageCandidateApplications(@Valid CandidateApplicationPageQuery query) {
        return Result.success(applicationApiService.pageCandidateApplications(query));
    }

    @PreAuthorize("hasAuthority('application:view')")
    @GetMapping("/api/candidate/applications/{applicationId}")
    public Result<CandidateApplicationDetailVO> getCandidateApplicationDetail(@PathVariable Long applicationId) {
        return Result.success(applicationApiService.getCandidateApplicationDetail(applicationId));
    }

    @PreAuthorize("hasAuthority('application:withdraw')")
    @PatchMapping("/api/candidate/applications/{applicationId}/withdraw")
    public Result<Boolean> withdrawApplication(@PathVariable Long applicationId, @Valid @RequestBody WithdrawApplicationRequest request) {
        return Result.success("application withdrawn", applicationApiService.withdrawApplication(applicationId, request));
    }

    @PreAuthorize("hasAuthority('application:view')")
    @GetMapping("/api/recruiter/applications")
    public Result<PageResponse<RecruiterApplicationItemVO>> pageRecruiterApplications(@Valid RecruiterApplicationPageQuery query) {
        return Result.success(applicationApiService.pageRecruiterApplications(query));
    }

    @PreAuthorize("hasAuthority('application:process')")
    @PatchMapping("/api/recruiter/applications/{applicationId}/status")
    public Result<Boolean> updateApplicationStatus(@PathVariable Long applicationId, @Valid @RequestBody UpdateApplicationStatusRequest request) {
        return Result.success("application status updated", applicationApiService.updateApplicationStatus(applicationId, request));
    }

    @PreventDuplicateSubmit(scene = "recruiter-create-interview", intervalSeconds = 5, requireIdempotencyKey = true)
    @PreAuthorize("hasAuthority('interview:create')")
    @PostMapping("/api/recruiter/applications/{applicationId}/interviews")
    public Result<InterviewCreateResultVO> createInterview(@PathVariable Long applicationId, @Valid @RequestBody CreateInterviewRequest request) {
        return Result.success("interview created", applicationApiService.createInterview(applicationId, request));
    }

    @PreAuthorize("hasAuthority('interview:feedback')")
    @PatchMapping("/api/recruiter/interviews/{interviewId}/result")
    public Result<Boolean> updateInterviewResult(@PathVariable Long interviewId, @Valid @RequestBody UpdateInterviewResultRequest request) {
        return Result.success("interview result updated", applicationApiService.updateInterviewResult(interviewId, request));
    }
}