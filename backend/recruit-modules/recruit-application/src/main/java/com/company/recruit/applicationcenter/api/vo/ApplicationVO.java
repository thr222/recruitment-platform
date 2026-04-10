package com.company.recruit.applicationcenter.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationVO {
    Long applicationId;
    Long jobId;
    String jobTitle;
    String companyName;
    String status;
    String lastActionTime;
}
