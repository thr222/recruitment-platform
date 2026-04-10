package com.company.recruit.company.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyDetailVO {
    Long companyId;
    String companyName;
    String companyShortName;
    String financingStage;
    String companySize;
    String cityCode;
    String introduction;
    String logoUrl;
}