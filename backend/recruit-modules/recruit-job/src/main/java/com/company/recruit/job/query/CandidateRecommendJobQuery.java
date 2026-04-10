package com.company.recruit.job.query;

import com.company.recruit.common.core.api.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CandidateRecommendJobQuery extends PageQuery {
    private String scene = "HOME_FEED";
}