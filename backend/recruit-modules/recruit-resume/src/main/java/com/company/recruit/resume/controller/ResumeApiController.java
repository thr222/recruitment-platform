package com.company.recruit.resume.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.resume.dto.ProjectExperienceCreateRequest;
import com.company.recruit.resume.dto.ResumeCreateRequest;
import com.company.recruit.resume.dto.ResumeUpdateRequest;
import com.company.recruit.resume.dto.WorkExperienceCreateRequest;
import com.company.recruit.resume.service.ResumeFacadeService;
import com.company.recruit.resume.vo.ProjectExperienceCreateResultVO;
import com.company.recruit.resume.vo.RecruiterResumeVO;
import com.company.recruit.resume.vo.ResumeCreateResultVO;
import com.company.recruit.resume.vo.ResumeSummaryVO;
import com.company.recruit.resume.vo.WorkExperienceCreateResultVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ResumeApiController {

    private final ResumeFacadeService resumeFacadeService;

    public ResumeApiController(ResumeFacadeService resumeFacadeService) {
        this.resumeFacadeService = resumeFacadeService;
    }

    @PreAuthorize("hasAuthority('resume:view')")
    @GetMapping("/api/candidate/resumes/default")
    public Result<ResumeSummaryVO> getDefaultResume() {
        return Result.success(resumeFacadeService.getDefaultResume());
    }

    @PreAuthorize("hasAuthority('resume:create')")
    @PostMapping("/api/candidate/resumes")
    public Result<ResumeCreateResultVO> createResume(@Valid @RequestBody ResumeCreateRequest request) {
        return Result.success("resume created", resumeFacadeService.createResume(request));
    }

    @PreAuthorize("hasAuthority('resume:update')")
    @PutMapping("/api/candidate/resumes/{resumeId}")
    public Result<Boolean> updateResume(@PathVariable Long resumeId, @Valid @RequestBody ResumeUpdateRequest request) {
        return Result.success("resume updated", resumeFacadeService.updateResume(resumeId, request));
    }

    @PreAuthorize("hasAuthority('resume:update')")
    @PostMapping("/api/candidate/resumes/{resumeId}/work-experiences")
    public Result<WorkExperienceCreateResultVO> createWorkExperience(
        @PathVariable Long resumeId,
        @Valid @RequestBody WorkExperienceCreateRequest request
    ) {
        return Result.success("work experience created", resumeFacadeService.createWorkExperience(resumeId, request));
    }

    @PreAuthorize("hasAuthority('resume:update')")
    @PostMapping("/api/candidate/resumes/{resumeId}/project-experiences")
    public Result<ProjectExperienceCreateResultVO> createProjectExperience(
        @PathVariable Long resumeId,
        @Valid @RequestBody ProjectExperienceCreateRequest request
    ) {
        return Result.success("project experience created", resumeFacadeService.createProjectExperience(resumeId, request));
    }

    @PreAuthorize("hasAuthority('resume:view')")
    @GetMapping("/api/recruiter/resumes/{resumeId}")
    public Result<RecruiterResumeVO> getRecruiterResume(@PathVariable Long resumeId) {
        return Result.success(resumeFacadeService.getRecruiterResume(resumeId));
    }
}
