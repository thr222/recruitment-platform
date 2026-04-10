package com.company.recruit.user.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminUserItemVO {
    Long userId;
    String username;
    String mobile;
    String realName;
    String roleType;
    String accountStatus;
    String createdAt;
}