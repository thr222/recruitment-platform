package com.company.recruit.job.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PublicJobItemVO {
    Long jobId;
    String title;
    Long companyId;
    String companyName;
    String workCityCode;
    Integer salaryMin;
    Integer salaryMax;
    Integer experienceMin;
    Integer experienceMax;
    String educationRequirement;
    String status;
    String publishedAt;
}