package com.company.recruit.job.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecruiterJobItemVO {
    Long jobId;
    String title;
    String status;
    Integer applyCount;
    Integer viewCount;
    String publishedAt;
}