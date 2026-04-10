package com.company.recruit.user.query;

import com.company.recruit.common.core.api.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserPageQuery extends PageQuery {
    private String keyword;
    private String roleType;
    private String accountStatus;
}