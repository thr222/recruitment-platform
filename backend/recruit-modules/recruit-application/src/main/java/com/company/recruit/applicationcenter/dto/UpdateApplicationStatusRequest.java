package com.company.recruit.applicationcenter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {
    @NotBlank private String status;
    @NotBlank private String processStage;
    private String remark;
}