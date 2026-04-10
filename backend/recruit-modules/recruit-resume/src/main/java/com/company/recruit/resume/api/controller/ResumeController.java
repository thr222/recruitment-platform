package com.company.recruit.resume.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.resume.api.vo.ResumeVO;
import com.company.recruit.resume.application.service.ResumeApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate/resume")
public class ResumeController {

    private final ResumeApplicationService resumeApplicationService;

    public ResumeController(ResumeApplicationService resumeApplicationService) {
        this.resumeApplicationService = resumeApplicationService;
    }

    @GetMapping("/mine")
    public Result<ResumeVO> getMyResume() {
        return Result.success(resumeApplicationService.getMyResume());
    }
}
