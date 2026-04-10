package com.company.recruit.user.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.common.web.annotation.PreventDuplicateSubmit;
import com.company.recruit.user.dto.UserLoginRequest;
import com.company.recruit.user.dto.UserRegisterRequest;
import com.company.recruit.user.service.UserAuthService;
import com.company.recruit.user.vo.UserLoginVO;
import com.company.recruit.user.vo.UserRegisterVO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User auth controller.
 */
@Validated
@RestController
@RequestMapping("/api/users/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    /**
     * Register a new platform user.
     */
    @PreventDuplicateSubmit(scene = "user-register", intervalSeconds = 5, requireIdempotencyKey = true)
    @PostMapping("/register")
    public Result<UserRegisterVO> register(@Valid @RequestBody UserRegisterRequest request) {
        return Result.success("user register success", userAuthService.register(request));
    }

    /**
     * Login with username and password.
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserLoginRequest request) {
        return Result.success("user login success", userAuthService.login(request));
    }
}