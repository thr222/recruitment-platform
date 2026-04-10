package com.company.recruit.company.api.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompanyProfileVO {
    Long companyId;
    String companyName;
    String stage;
    String city;
    String auditStatus;
    List<String> tags;
}
