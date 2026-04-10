package com.company.recruit.auth.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurrentPermissionVO {
    Long userId;
    List<String> roleList;
    List<String> permissionList;
    DataScopeVO dataScope;

    @Value
    @Builder
    public static class DataScopeVO {
        List<Long> companyIds;
    }
}