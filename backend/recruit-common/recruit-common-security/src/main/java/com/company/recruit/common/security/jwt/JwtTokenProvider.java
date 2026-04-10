package com.company.recruit.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final String issuer;
    private final long accessTokenExpireSeconds;
    private final long refreshTokenExpireSeconds;

    public JwtTokenProvider(
        @Value("${security.jwt.secret}") String secret,
        @Value("${security.jwt.issuer}") String issuer,
        @Value("${security.jwt.access-token-expire-seconds}") long accessTokenExpireSeconds,
        @Value("${security.jwt.refresh-token-expire-seconds}") long refreshTokenExpireSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.accessTokenExpireSeconds = accessTokenExpireSeconds;
        this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
    }

    public String createAccessToken(Long userId, String username, List<String> roles, List<String> permissions, List<Long> companyIds) {
        return createToken(userId, username, roles, permissions, companyIds, TokenType.ACCESS, accessTokenExpireSeconds);
    }

    public String createRefreshToken(
        Long userId,
        String username,
        List<String> roles,
        List<String> permissions,
        List<Long> companyIds
    ) {
        return createToken(userId, username, roles, permissions, companyIds, TokenType.REFRESH, refreshTokenExpireSeconds);
    }

    private String createToken(Long userId, String username, List<String> roles, List<String> permissions, List<Long> companyIds, TokenType tokenType, long expireSeconds) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuer(issuer)
            .claim("username", username)
            .claim("roles", roles)
            .claim("permissions", permissions)
            .claim("companyIds", companyIds)
            .claim("tokenType", tokenType.name())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(expireSeconds)))
            .signWith(secretKey)
            .compact();
    }

    public JwtUserClaims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            return JwtUserClaims.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .username(String.valueOf(claims.get("username")))
                .roles(claims.get("roles", List.class))
                .permissions(claims.get("permissions", List.class))
                .companyIds(claims.get("companyIds", List.class))
                .tokenType(TokenType.valueOf(String.valueOf(claims.get("tokenType"))))
                .build();
        } catch (JwtException | IllegalArgumentException exception) {
            return null;
        }
    }

    public long getAccessTokenExpireSeconds() {
        return accessTokenExpireSeconds;
    }

    public long getRefreshTokenExpireSeconds() {
        return refreshTokenExpireSeconds;
    }

    public static class JwtUserClaims {
        private final Long userId;
        private final String username;
        private final List<String> roles;
        private final List<String> permissions;
        private final List<Long> companyIds;
        private final TokenType tokenType;

        private JwtUserClaims(Builder builder) {
            this.userId = builder.userId;
            this.username = builder.username;
            this.roles = builder.roles;
            this.permissions = builder.permissions;
            this.companyIds = builder.companyIds;
            this.tokenType = builder.tokenType;
        }

        public static Builder builder() {
            return new Builder();
        }

        public Long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public List<String> getRoles() {
            return roles;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public List<Long> getCompanyIds() {
            return companyIds;
        }

        public TokenType getTokenType() {
            return tokenType;
        }

        public static class Builder {
            private Long userId;
            private String username;
            private List<String> roles;
            private List<String> permissions;
            private List<Long> companyIds;
            private TokenType tokenType;

            public Builder userId(Long userId) {
                this.userId = userId;
                return this;
            }

            public Builder username(String username) {
                this.username = username;
                return this;
            }

            public Builder roles(List<String> roles) {
                this.roles = roles;
                return this;
            }

            public Builder permissions(List<String> permissions) {
                this.permissions = permissions;
                return this;
            }

            public Builder companyIds(List<Long> companyIds) {
                this.companyIds = companyIds;
                return this;
            }

            public Builder tokenType(TokenType tokenType) {
                this.tokenType = tokenType;
                return this;
            }

            public JwtUserClaims build() {
                return new JwtUserClaims(this);
            }
        }
    }
}