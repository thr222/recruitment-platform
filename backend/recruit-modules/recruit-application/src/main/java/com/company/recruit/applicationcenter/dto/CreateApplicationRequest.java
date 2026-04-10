package com.company.recruit.applicationcenter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    @NotNull private Long jobId;
    @NotNull private Long resumeId;
    private String candidateNote;
}