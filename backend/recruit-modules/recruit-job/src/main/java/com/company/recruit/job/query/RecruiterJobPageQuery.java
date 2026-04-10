package com.company.recruit.job.query;

import com.company.recruit.common.core.api.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecruiterJobPageQuery extends PageQuery {
    private String status;
    private String keyword;
}