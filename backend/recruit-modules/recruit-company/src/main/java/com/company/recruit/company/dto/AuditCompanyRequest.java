package com.company.recruit.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuditCompanyRequest {
    @NotBlank
    private String auditStatus;
    private String auditRemark;
}