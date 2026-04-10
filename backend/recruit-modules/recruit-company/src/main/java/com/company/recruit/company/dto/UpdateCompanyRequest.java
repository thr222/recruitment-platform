package com.company.recruit.company.dto;

import lombok.Data;

@Data
public class UpdateCompanyRequest {
    private String companyShortName;
    private String companySize;
    private String financingStage;
    private String introduction;
}