package com.company.recruit.resume.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResumeSummaryVO {
    Long resumeId;
    String resumeName;
    String realName;
    String mobile;
    String email;
    String degree;
    Integer completionScore;
    String currentStatus;
}