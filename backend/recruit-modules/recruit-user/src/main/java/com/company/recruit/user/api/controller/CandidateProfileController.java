package com.company.recruit.user.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.user.api.vo.CandidateProfileVO;
import com.company.recruit.user.application.service.CandidateProfileApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate/profile")
public class CandidateProfileController {

    private final CandidateProfileApplicationService candidateProfileApplicationService;

    public CandidateProfileController(CandidateProfileApplicationService candidateProfileApplicationService) {
        this.candidateProfileApplicationService = candidateProfileApplicationService;
    }

    @GetMapping("/me")
    public Result<CandidateProfileVO> getProfile() {
        return Result.success(candidateProfileApplicationService.getCurrentProfile());
    }
}
