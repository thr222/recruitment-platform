package com.company.recruit.resume.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WorkExperienceCreateRequest {
    @NotBlank private String companyName;
    @NotBlank private String positionName;
    @NotBlank private String startDate;
    private String endDate;
    private Integer currentlyWorking;
    private String workDescription;
    private String achievementText;
}