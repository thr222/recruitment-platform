package com.company.recruit.applicationcenter.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecruiterCandidateVO {
    Long applicationId;
    String candidateName;
    String jobTitle;
    String stage;
    String lastActionTime;
}
