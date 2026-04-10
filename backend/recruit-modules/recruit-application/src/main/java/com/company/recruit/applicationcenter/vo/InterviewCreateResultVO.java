package com.company.recruit.applicationcenter.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InterviewCreateResultVO {
    Long interviewId;
    String interviewStatus;
}