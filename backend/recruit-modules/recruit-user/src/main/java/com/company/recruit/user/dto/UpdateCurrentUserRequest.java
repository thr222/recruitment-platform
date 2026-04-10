package com.company.recruit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCurrentUserRequest {
    @Size(max = 64)
    private String realName;
    @Email
    private String email;
    @Size(max = 255)
    private String avatarUrl;
}