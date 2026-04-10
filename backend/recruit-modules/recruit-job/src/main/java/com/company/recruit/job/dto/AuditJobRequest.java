package com.company.recruit.job.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuditJobRequest {
    @NotBlank
    private String auditStatus;
    private String auditRemark;
}