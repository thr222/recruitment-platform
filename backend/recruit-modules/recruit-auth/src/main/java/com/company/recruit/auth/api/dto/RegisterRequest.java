package com.company.recruit.auth.api.dto;

import com.company.recruit.common.core.enums.UserRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String mobile;

    @NotNull
    private UserRoleType roleType;
}
