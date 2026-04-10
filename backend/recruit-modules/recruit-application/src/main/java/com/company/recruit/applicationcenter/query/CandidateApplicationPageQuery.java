package com.company.recruit.applicationcenter.query;

import com.company.recruit.common.core.api.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CandidateApplicationPageQuery extends PageQuery {
    private String status;
    private String keyword;
}