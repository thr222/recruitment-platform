package com.company.recruit.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCompanyRequest {
    @NotBlank
    private String companyName;
    @NotBlank
    private String companyShortName;
    @NotBlank
    private String unifiedSocialCreditCode;
    @NotBlank
    private String industryCode;
    private String financingStage;
    private String companySize;
    @NotBlank
    private String cityCode;
    private String addressDetail;
    private String officialWebsite;
    private String introduction;
    @NotBlank
    private String licenseImageUrl;
}