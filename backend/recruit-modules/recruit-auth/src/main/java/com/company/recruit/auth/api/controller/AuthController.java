package com.company.recruit.auth.api.controller;

import com.company.recruit.auth.api.dto.LoginRequest;
import com.company.recruit.auth.api.dto.RefreshTokenRequest;
import com.company.recruit.auth.api.dto.RegisterRequest;
import com.company.recruit.auth.api.vo.TokenVO;
import com.company.recruit.auth.application.service.AuthApplicationService;
import com.company.recruit.common.core.api.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/login")
    public Result<TokenVO> login(@Valid @RequestBody LoginRequest request) {
        return Result.success("login success", authApplicationService.login(request));
    }

    @PostMapping("/register")
    public Result<TokenVO> register(@Valid @RequestBody RegisterRequest request) {
        return Result.success("register success", authApplicationService.register(request));
    }

    @PostMapping("/refresh")
    public Result<TokenVO> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return Result.success("refresh success", authApplicationService.refresh(request));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        authApplicationService.logout(authorization);
        return Result.success("logout success", null);
    }
}
