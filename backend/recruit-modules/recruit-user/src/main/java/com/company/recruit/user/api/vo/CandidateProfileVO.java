package com.company.recruit.user.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateProfileVO {
    Long userId;
    String nickname;
    String avatar;
    String currentCity;
    Integer resumeCompletion;
}
