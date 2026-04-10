package com.company.recruit.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.recruit.common.core.enums.UserRoleType;
import com.company.recruit.common.core.exception.BusinessException;
import com.company.recruit.common.security.jwt.JwtTokenProvider;
import com.company.recruit.user.dto.UserLoginRequest;
import com.company.recruit.user.dto.UserRegisterRequest;
import com.company.recruit.user.entity.RoleEntity;
import com.company.recruit.user.entity.UserAccountEntity;
import com.company.recruit.user.entity.UserRoleEntity;
import com.company.recruit.user.mapper.RoleMapper;
import com.company.recruit.user.mapper.UserAccountMapper;
import com.company.recruit.user.mapper.UserRoleMapper;
import com.company.recruit.user.service.UserAuthService;
import com.company.recruit.user.vo.UserLoginVO;
import com.company.recruit.user.vo.UserRegisterVO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User authentication service implementation based on MyBatis-Plus.
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private static final String ENABLED = "ENABLED";
    private static final String WEB = "WEB";

    private final UserAccountMapper userAccountMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserAuthServiceImpl(
        UserAccountMapper userAccountMapper,
        RoleMapper roleMapper,
        UserRoleMapper userRoleMapper,
        JwtTokenProvider jwtTokenProvider
    ) {
        this.userAccountMapper = userAccountMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserRegisterVO register(UserRegisterRequest request) {
        validateRegisterRequest(request);
        RoleEntity role = loadRole(request.getRoleType());

        UserAccountEntity entity = UserAccountEntity.builder()
            .username(request.getUsername())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .mobile(request.getMobile())
            .gender(0)
            .deleted(0)
            .registerSource(WEB)
            .accountStatus(ENABLED)
            .build();
        userAccountMapper.insert(entity);

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
            .userId(entity.getId())
            .roleId(role.getId())
            .createdAt(LocalDateTime.now())
            .build();
        userRoleMapper.insert(userRoleEntity);

        return UserRegisterVO.builder()
            .userId(entity.getId())
            .username(entity.getUsername())
            .roleList(List.of(role.getRoleCode()))
            .build();
    }

    @Override
    public UserLoginVO login(UserLoginRequest request) {
        UserAccountEntity user = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccountEntity>()
            .eq(UserAccountEntity::getUsername, request.getUsername())
            .eq(UserAccountEntity::getDeleted, 0)
            .last("limit 1"));
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException("LOGIN_FAILED", "username or password is incorrect");
        }
        if (!ENABLED.equals(user.getAccountStatus())) {
            throw new BusinessException("USER_DISABLED", "user account is not available");
        }

        List<String> roleCodes = loadRoleCodes(user.getId());
        List<String> permissionCodes = roleCodes.stream()
            .flatMap(roleCode -> rolePermissions(roleCode).stream())
            .distinct()
            .toList();

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), roleCodes, permissionCodes, List.of());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername(), roleCodes, permissionCodes, List.of());

        userAccountMapper.update(null, new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<UserAccountEntity>()
            .eq(UserAccountEntity::getId, user.getId())
            .set(UserAccountEntity::getLastLoginAt, LocalDateTime.now()));

        return UserLoginVO.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(jwtTokenProvider.getAccessTokenExpireSeconds())
            .roleList(roleCodes)
            .build();
    }

    private void validateRegisterRequest(UserRegisterRequest request) {
        if (request.getRoleType() == UserRoleType.ADMIN || request.getRoleType() == UserRoleType.GUEST) {
            throw new BusinessException("ROLE_NOT_ALLOWED", "the selected role cannot be registered directly");
        }
        boolean usernameExists = userAccountMapper.selectCount(new LambdaQueryWrapper<UserAccountEntity>()
            .eq(UserAccountEntity::getUsername, request.getUsername())
            .eq(UserAccountEntity::getDeleted, 0)) > 0;
        if (usernameExists) {
            throw new BusinessException("USERNAME_EXISTS", "username already exists");
        }
        boolean mobileExists = userAccountMapper.selectCount(new LambdaQueryWrapper<UserAccountEntity>()
            .eq(UserAccountEntity::getMobile, request.getMobile())
            .eq(UserAccountEntity::getDeleted, 0)) > 0;
        if (mobileExists) {
            throw new BusinessException("MOBILE_EXISTS", "mobile already exists");
        }
    }

    private RoleEntity loadRole(UserRoleType roleType) {
        RoleEntity role = roleMapper.selectOne(new LambdaQueryWrapper<RoleEntity>()
            .eq(RoleEntity::getRoleCode, roleType.name())
            .eq(RoleEntity::getRoleStatus, ENABLED)
            .last("limit 1"));
        if (role == null) {
            throw new BusinessException("ROLE_NOT_FOUND", "role is not configured");
        }
        return role;
    }

    private List<String> loadRoleCodes(Long userId) {
        List<Long> roleIds = userRoleMapper.selectList(new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getUserId, userId))
            .stream()
            .map(UserRoleEntity::getRoleId)
            .toList();
        if (roleIds.isEmpty()) {
            throw new BusinessException("ROLE_NOT_FOUND", "user role is not configured");
        }
        return roleMapper.selectBatchIds(roleIds).stream()
            .map(RoleEntity::getRoleCode)
            .toList();
    }

    private List<String> rolePermissions(String roleCode) {
        return switch (roleCode) {
            case "ADMIN" -> List.of("user:view", "user:status:update", "user:role:assign", "company:audit", "job:audit");
            case "RECRUITER" -> List.of("company:view", "company:edit", "job:create", "job:update", "job:status:update",
                "application:view", "application:process", "interview:create", "interview:feedback", "resume:view");
            default -> List.of("user:profile:view", "user:profile:update", "resume:view", "resume:create",
                "resume:update", "application:create", "application:view", "application:withdraw");
        };
    }
}
