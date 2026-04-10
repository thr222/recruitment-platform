package com.company.recruit.operation.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.operation.api.vo.CompanyAuditVO;
import com.company.recruit.operation.api.vo.JobAuditVO;
import com.company.recruit.operation.application.service.OperationApplicationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminAuditController {

    private final OperationApplicationService operationApplicationService;

    public AdminAuditController(OperationApplicationService operationApplicationService) {
        this.operationApplicationService = operationApplicationService;
    }

    @GetMapping("/company-audits")
    public Result<List<CompanyAuditVO>> listCompanyAudits() {
        return Result.success(operationApplicationService.listCompanyAudits());
    }

    @GetMapping("/job-audits")
    public Result<List<JobAuditVO>> listJobAudits() {
        return Result.success(operationApplicationService.listJobAudits());
    }
}
