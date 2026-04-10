package com.company.recruit.job.domain.entity;

import com.company.recruit.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JobPost extends BaseEntity {

    private Long companyId;
    private String title;
    private String city;
    private String salaryRange;
    private Integer experienceMin;
    private Integer experienceMax;
    private String educationRequirement;
    private String status;
}
