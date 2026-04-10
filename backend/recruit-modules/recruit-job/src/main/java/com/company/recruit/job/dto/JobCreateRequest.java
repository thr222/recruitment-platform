package com.company.recruit.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class JobCreateRequest {
    @NotNull private Long companyId;
    @NotNull private Long categoryId;
    @NotBlank private String title;
    @NotBlank private String employmentType;
    @NotNull private Integer headcount;
    @NotBlank private String workCityCode;
    private String workAddress;
    @NotNull private Integer salaryMin;
    @NotNull private Integer salaryMax;
    @NotNull private Integer salaryMonths;
    @NotNull private Integer experienceMin;
    @NotNull private Integer experienceMax;
    private String educationRequirement;
    private List<String> jobHighlights;
    @NotEmpty private List<Long> tagIds;
    @NotBlank private String descriptionText;
}