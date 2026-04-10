package com.company.recruit.operation.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobAuditVO {
    Long jobId;
    String title;
    String companyName;
    String auditStatus;
    String submittedAt;
}
