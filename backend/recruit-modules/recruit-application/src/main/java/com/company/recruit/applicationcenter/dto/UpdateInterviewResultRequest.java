package com.company.recruit.applicationcenter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInterviewResultRequest {
    @NotNull private Integer feedbackScore;
    @NotBlank private String feedbackResult;
    private String feedbackText;
}