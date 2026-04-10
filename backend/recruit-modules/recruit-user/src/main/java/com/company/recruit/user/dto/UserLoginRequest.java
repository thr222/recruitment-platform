package com.company.recruit.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request for user login.
 */
@Data
public class UserLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
