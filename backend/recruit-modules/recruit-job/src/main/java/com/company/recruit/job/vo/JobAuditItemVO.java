package com.company.recruit.job.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobAuditItemVO {
    Long jobId;
    String title;
    String companyName;
    String auditStatus;
}