package com.company.recruit.applicationcenter.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationCreateResultVO {
    Long applicationId;
    String status;
}