package com.company.recruit.user.dto;

import com.company.recruit.common.core.enums.UserRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request for user registration.
 */
@Data
public class UserRegisterRequest {

    @NotBlank
    @Size(max = 64)
    private String username;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String mobile;

    @NotNull
    private UserRoleType roleType;
}
