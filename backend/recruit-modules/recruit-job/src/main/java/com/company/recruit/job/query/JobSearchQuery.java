package com.company.recruit.job.query;

import com.company.recruit.common.core.api.PageQuery;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JobSearchQuery extends PageQuery {
    private String keyword;
    private String cityCode;
    private Long categoryId;
    private Integer salaryMin;
    private Integer salaryMax;
    private Integer experienceMin;
    private Integer experienceMax;
    private String educationRequirement;
    private Long companyId;
    private List<Long> tagIds;
    private String sortBy;
}