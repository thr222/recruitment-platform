package com.company.recruit.resume.dto;

import lombok.Data;

@Data
public class ResumeUpdateRequest {
    private String resumeName;
    private String realName;
    private String mobile;
    private String email;
    private String degree;
    private String expectationCity;
    private String expectationPosition;
    private Integer expectationSalaryMin;
    private Integer expectationSalaryMax;
    private String personalSummary;
}