package com.company.recruit.applicationcenter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInterviewRequest {
    @NotNull private Integer roundNo;
    @NotBlank private String interviewType;
    @NotBlank private String scheduledStartAt;
    @NotBlank private String scheduledEndAt;
    @NotNull private Long interviewerUserId;
    private String meetingLink;
}