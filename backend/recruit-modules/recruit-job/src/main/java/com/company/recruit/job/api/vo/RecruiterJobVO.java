package com.company.recruit.job.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecruiterJobVO {
    Long jobId;
    String title;
    String salaryRange;
    String status;
    Integer candidateCount;
    String updatedAt;
}
