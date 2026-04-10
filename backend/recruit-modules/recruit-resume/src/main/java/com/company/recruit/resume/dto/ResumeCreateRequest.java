package com.company.recruit.resume.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResumeCreateRequest {
    @NotBlank private String resumeName;
    @NotBlank private String realName;
    @NotBlank private String mobile;
    private String email;
    private String degree;
    private String expectationCity;
    private String expectationPosition;
    private Integer expectationSalaryMin;
    private Integer expectationSalaryMax;
    private String personalSummary;
}