package com.company.recruit.applicationcenter.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecruiterApplicationItemVO {
    Long applicationId;
    Long candidateUserId;
    String candidateName;
    String jobTitle;
    String status;
    String processStage;
    String appliedAt;
}