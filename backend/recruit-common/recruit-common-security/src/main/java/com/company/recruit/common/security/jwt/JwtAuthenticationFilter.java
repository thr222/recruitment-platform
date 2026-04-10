package com.company.recruit.common.security.jwt;

import com.company.recruit.common.security.context.LoginUser;
import com.company.recruit.common.security.jwt.JwtTokenProvider.JwtUserClaims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistStore tokenBlacklistStore;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, TokenBlacklistStore tokenBlacklistStore) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklistStore = tokenBlacklistStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            if (!tokenBlacklistStore.contains(token)) {
                JwtUserClaims claims = jwtTokenProvider.parseToken(token);
                if (claims != null && claims.getTokenType() == TokenType.ACCESS) {
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (claims.getRoles() != null) {
                        claims.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
                    }
                    if (claims.getPermissions() != null) {
                        claims.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
                    }
                    LoginUser loginUser = LoginUser.builder()
                        .userId(claims.getUserId())
                        .username(claims.getUsername())
                        .roleCodes(claims.getRoles())
                        .permissionCodes(claims.getPermissions())
                        .companyIds(claims.getCompanyIds())
                        .build();
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser, token, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
