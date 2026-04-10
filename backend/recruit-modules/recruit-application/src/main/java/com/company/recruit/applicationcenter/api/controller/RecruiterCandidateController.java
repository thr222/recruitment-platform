package com.company.recruit.applicationcenter.api.controller;

import com.company.recruit.applicationcenter.api.vo.RecruiterCandidateVO;
import com.company.recruit.applicationcenter.application.service.ApplicationCenterService;
import com.company.recruit.common.core.api.Result;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter/candidates")
public class RecruiterCandidateController {

    private final ApplicationCenterService applicationCenterService;

    public RecruiterCandidateController(ApplicationCenterService applicationCenterService) {
        this.applicationCenterService = applicationCenterService;
    }

    @GetMapping
    public Result<List<RecruiterCandidateVO>> listCandidates() {
        return Result.success(applicationCenterService.listRecruiterCandidates());
    }
}
