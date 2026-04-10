package com.company.recruit.company.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.company.api.vo.CompanyProfileVO;
import com.company.recruit.company.application.service.CompanyApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter/company")
public class CompanyController {

    private final CompanyApplicationService companyApplicationService;

    public CompanyController(CompanyApplicationService companyApplicationService) {
        this.companyApplicationService = companyApplicationService;
    }

    @GetMapping("/mine")
    public Result<CompanyProfileVO> getMyCompany() {
        return Result.success(companyApplicationService.getMyCompany());
    }
}
