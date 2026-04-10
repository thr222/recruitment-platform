package com.company.recruit.company.controller;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.core.api.Result;
import com.company.recruit.common.web.annotation.PreventDuplicateSubmit;
import com.company.recruit.company.dto.AuditCompanyRequest;
import com.company.recruit.company.dto.CreateCompanyRequest;
import com.company.recruit.company.dto.UpdateCompanyRequest;
import com.company.recruit.company.query.CompanyAuditPageQuery;
import com.company.recruit.company.service.CompanyService;
import com.company.recruit.company.vo.CompanyAuditItemVO;
import com.company.recruit.company.vo.CompanyCreateResultVO;
import com.company.recruit.company.vo.CompanyDetailVO;
import com.company.recruit.company.vo.CurrentCompanyVO;
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
public class CompanyApiController {

    private final CompanyService companyService;

    public CompanyApiController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PreventDuplicateSubmit(scene = "recruiter-company-settle", intervalSeconds = 10, requireIdempotencyKey = true)
    @PreAuthorize("hasAuthority('company:edit')")
    @PostMapping("/api/recruiter/companies")
    public Result<CompanyCreateResultVO> createCompany(@Valid @RequestBody CreateCompanyRequest request) {
        return Result.success("company submitted", companyService.createCompany(request));
    }

    @PreAuthorize("hasAuthority('company:view')")
    @GetMapping("/api/recruiter/companies/current")
    public Result<CurrentCompanyVO> getCurrentCompany() {
        return Result.success(companyService.getCurrentCompany());
    }

    @PreAuthorize("hasAuthority('company:edit')")
    @PutMapping("/api/recruiter/companies/{companyId}")
    public Result<Boolean> updateCompany(@PathVariable Long companyId, @Valid @RequestBody UpdateCompanyRequest request) {
        return Result.success("company updated", companyService.updateCompany(companyId, request));
    }

    @GetMapping("/api/companies/{companyId}")
    public Result<CompanyDetailVO> getCompanyDetail(@PathVariable Long companyId) {
        return Result.success(companyService.getCompanyDetail(companyId));
    }

    @PreAuthorize("hasAuthority('company:audit')")
    @GetMapping("/api/admin/companies/audits")
    public Result<PageResponse<CompanyAuditItemVO>> pageCompanyAudits(@Valid CompanyAuditPageQuery query) {
        return Result.success(companyService.pageCompanyAudits(query));
    }

    @PreAuthorize("hasAuthority('company:audit')")
    @PatchMapping("/api/admin/companies/{companyId}/audit")
    public Result<Boolean> auditCompany(@PathVariable Long companyId, @Valid @RequestBody AuditCompanyRequest request) {
        return Result.success("company audited", companyService.auditCompany(companyId, request));
    }
}