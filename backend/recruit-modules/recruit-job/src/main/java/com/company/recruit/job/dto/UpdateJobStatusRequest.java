package com.company.recruit.job.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateJobStatusRequest {
    @NotBlank
    private String status;
}