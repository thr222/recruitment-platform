package com.company.recruit.auth.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleItemVO {
    String roleCode;
    String roleName;
}