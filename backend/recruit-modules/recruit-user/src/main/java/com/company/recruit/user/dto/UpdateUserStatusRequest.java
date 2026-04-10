package com.company.recruit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    @NotBlank
    private String accountStatus;
    @Size(max = 255)
    private String remark;
}