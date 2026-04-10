package com.company.recruit.operation.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyAuditVO {
    Long companyId;
    String companyName;
    String auditStatus;
    String submittedAt;
}
