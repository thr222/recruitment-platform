package com.company.recruit.applicationcenter.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateApplicationDetailVO {
    Long applicationId;
    String jobTitle;
    String companyName;
    String status;
    String processStage;
    List<ApplicationTimelineVO> timeline;
}