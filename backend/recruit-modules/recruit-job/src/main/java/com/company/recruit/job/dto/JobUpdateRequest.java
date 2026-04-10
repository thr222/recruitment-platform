package com.company.recruit.job.dto;

import java.util.List;
import lombok.Data;

@Data
public class JobUpdateRequest {
    private String title;
    private String employmentType;
    private Integer headcount;
    private String workCityCode;
    private String workAddress;
    private Integer salaryMin;
    private Integer salaryMax;
    private Integer salaryMonths;
    private Integer experienceMin;
    private Integer experienceMax;
    private String educationRequirement;
    private List<String> jobHighlights;
    private List<Long> tagIds;
    private String descriptionText;
}