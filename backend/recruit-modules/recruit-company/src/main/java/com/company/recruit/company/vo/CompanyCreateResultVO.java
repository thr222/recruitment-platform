package com.company.recruit.company.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyCreateResultVO {
    Long companyId;
    String auditStatus;
}