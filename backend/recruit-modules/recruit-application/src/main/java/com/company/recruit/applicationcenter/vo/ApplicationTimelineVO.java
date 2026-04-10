package com.company.recruit.applicationcenter.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApplicationTimelineVO {
    String fromStatus;
    String toStatus;
    String operatorRole;
    String createdAt;
}