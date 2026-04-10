package com.company.recruit.company.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyAuditItemVO {
    Long companyId;
    String companyName;
    String auditStatus;
    String createdAt;
}