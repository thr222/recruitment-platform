package com.company.recruit.auth.application.service;

import com.company.recruit.auth.api.dto.LoginRequest;
import com.company.recruit.auth.api.dto.RefreshTokenRequest;
import com.company.recruit.auth.api.dto.RegisterRequest;
import com.company.recruit.auth.api.vo.TokenVO;
import com.company.recruit.common.core.exception.BusinessException;
import com.company.recruit.common.security.jwt.JwtTokenProvider;
import com.company.recruit.common.security.jwt.JwtTokenProvider.JwtUserClaims;
import com.company.recruit.common.security.jwt.RefreshTokenStore;
import com.company.recruit.common.security.jwt.TokenBlacklistStore;
import com.company.recruit.common.security.jwt.TokenType;
import java.time.Duration;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthApplicationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;
    private final TokenBlacklistStore tokenBlacklistStore;

    public AuthApplicationService(
        JwtTokenProvider jwtTokenProvider,
        RefreshTokenStore refreshTokenStore,
        TokenBlacklistStore tokenBlacklistStore
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenStore = refreshTokenStore;
        this.tokenBlacklistStore = tokenBlacklistStore;
    }

    public TokenVO login(LoginRequest request) {
        AuthSubject subject = mockSubject(request.getUsername());
        return buildToken(subject);
    }

    public TokenVO register(RegisterRequest request) {
        String roleCode = request.getRoleType().name();
        AuthSubject subject = new AuthSubject(
            20001L,
            request.getUsername(),
            List.of(roleCode),
            rolePermissions(roleCode),
            List.of()
        );
        return buildToken(subject);
    }

    public TokenVO refresh(RefreshTokenRequest request) {
        JwtUserClaims claims = jwtTokenProvider.parseToken(request.getRefreshToken());
        if (claims == null || claims.getTokenType() != TokenType.REFRESH) {
            throw new BusinessException("INVALID_REFRESH_TOKEN", "refresh token is invalid or expired");
        }
        if (!refreshTokenStore.matches(claims.getUserId(), request.getRefreshToken())) {
            throw new BusinessException("INVALID_REFRESH_TOKEN", "refresh token has been revoked");
        }
        AuthSubject subject = new AuthSubject(
            claims.getUserId(),
            claims.getUsername(),
            claims.getRoles(),
            claims.getPermissions(),
            claims.getCompanyIds()
        );
        return buildToken(subject);
    }

    public void logout(String authorization) {
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            return;
        }
        String accessToken = authorization.substring(7);
        JwtUserClaims claims = jwtTokenProvider.parseToken(accessToken);
        if (claims == null || claims.getTokenType() != TokenType.ACCESS) {
            return;
        }
        tokenBlacklistStore.save(accessToken, Duration.ofSeconds(jwtTokenProvider.getAccessTokenExpireSeconds()));
        refreshTokenStore.remove(claims.getUserId());
    }

    private TokenVO buildToken(AuthSubject subject) {
        String accessToken = jwtTokenProvider.createAccessToken(
            subject.userId(),
            subject.username(),
            subject.roles(),
            subject.permissions(),
            subject.companyIds()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(
            subject.userId(),
            subject.username(),
            subject.roles(),
            subject.permissions(),
            subject.companyIds()
        );
        refreshTokenStore.save(subject.userId(), refreshToken, Duration.ofSeconds(jwtTokenProvider.getRefreshTokenExpireSeconds()));
        return TokenVO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(jwtTokenProvider.getAccessTokenExpireSeconds())
            .roleList(subject.roles())
            .build();
    }

    private AuthSubject mockSubject(String username) {
        if (username.startsWith("admin")) {
            return new AuthSubject(3L, username, List.of("ADMIN"), rolePermissions("ADMIN"), List.of());
        }
        if (username.startsWith("recruiter")) {
            return new AuthSubject(2L, username, List.of("RECRUITER"), rolePermissions("RECRUITER"), List.of(1L));
        }
        return new AuthSubject(1L, username, List.of("CANDIDATE"), rolePermissions("CANDIDATE"), List.of());
    }

    private List<String> rolePermissions(String role) {
        return switch (role) {
            case "ADMIN" -> List.of(
                "user:view",
                "user:status:update",
                "user:role:assign",
                "company:audit",
                "job:audit"
            );
            case "RECRUITER" -> List.of(
                "company:view",
                "company:edit",
                "job:create",
                "job:update",
                "job:status:update",
                "application:view",
                "application:process",
                "interview:create",
                "interview:feedback",
                "resume:view"
            );
            default -> List.of(
                "user:profile:view",
                "user:profile:update",
                "resume:view",
                "resume:create",
                "resume:update",
                "application:create",
                "application:view",
                "application:withdraw"
            );
        };
    }

    private record AuthSubject(Long userId, String username, List<String> roles, List<String> permissions, List<Long> companyIds) {
    }
}
