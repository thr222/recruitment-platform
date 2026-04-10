package com.company.recruit.job.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobCreateResultVO {
    Long jobId;
    String status;
}