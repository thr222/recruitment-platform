package com.company.recruit.resume.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectExperienceCreateRequest {
    @NotBlank private String projectName;
    private String roleName;
    @NotBlank private String startDate;
    private String endDate;
    private String projectDescription;
    private String responsibilityText;
    private String projectResult;
}