package com.company.recruit.job.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecommendedJobItemVO {
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
    String recommendReason;
    Integer recommendScore;
    List<String> matchedFeatures;
    String publishedAt;
}