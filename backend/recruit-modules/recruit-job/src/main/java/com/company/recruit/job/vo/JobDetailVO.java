package com.company.recruit.job.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobDetailVO {
    Long jobId;
    String title;
    JobCompanyVO company;
    Integer salaryMin;
    Integer salaryMax;
    String descriptionText;
    String status;

    @Value
    @Builder
    public static class JobCompanyVO {
        Long companyId;
        String companyName;
    }
}