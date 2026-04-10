package com.company.recruit.applicationcenter.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateApplicationItemVO {
    Long applicationId;
    Long jobId;
    String jobTitle;
    String companyName;
    String status;
    String processStage;
    String appliedAt;
}