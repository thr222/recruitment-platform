package com.company.recruit.company.query;

import com.company.recruit.common.core.api.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyAuditPageQuery extends PageQuery {
    private String auditStatus;
    private String keyword;
}