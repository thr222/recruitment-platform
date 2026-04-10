package com.company.recruit.job.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.job.api.vo.JobPostVO;
import com.company.recruit.job.application.service.JobApplicationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common/jobs")
public class PublicJobController {

    private final JobApplicationService jobApplicationService;

    public PublicJobController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public Result<List<JobPostVO>> listJobs() {
        return Result.success(jobApplicationService.listPublicJobs());
    }
}
