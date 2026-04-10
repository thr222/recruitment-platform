package com.company.recruit.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.core.exception.BusinessException;
import com.company.recruit.common.security.context.SecurityUtils;
import com.company.recruit.user.dto.UpdateCurrentUserRequest;
import com.company.recruit.user.dto.UpdateUserStatusRequest;
import com.company.recruit.user.entity.RoleEntity;
import com.company.recruit.user.entity.UserAccountEntity;
import com.company.recruit.user.entity.UserRoleEntity;
import com.company.recruit.user.mapper.RoleMapper;
import com.company.recruit.user.mapper.UserAccountMapper;
import com.company.recruit.user.mapper.UserRoleMapper;
import com.company.recruit.user.query.AdminUserPageQuery;
import com.company.recruit.user.service.UserService;
import com.company.recruit.user.vo.AdminUserItemVO;
import com.company.recruit.user.vo.CurrentUserVO;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * User service implementation based on MyBatis-Plus.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final UserAccountMapper userAccountMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;

    public UserServiceImpl(UserAccountMapper userAccountMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper) {
        this.userAccountMapper = userAccountMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public CurrentUserVO getCurrentUser() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BusinessException("UNAUTHORIZED", "current user not found");
        }
        UserAccountEntity user = userAccountMapper.selectById(currentUserId);
        if (user == null) {
            throw new BusinessException("USER_NOT_FOUND", "user does not exist");
        }
        return CurrentUserVO.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .realName(user.getRealName())
            .mobile(user.getMobile())
            .email(user.getEmail())
            .avatarUrl(user.getAvatarUrl())
            .roleList(listRoleCodesByUserId(user.getId()))
            .build();
    }

    @Override
    public boolean updateCurrentUser(UpdateCurrentUserRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId == null) {
            throw new BusinessException("UNAUTHORIZED", "current user not found");
        }
        LambdaUpdateWrapper<UserAccountEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserAccountEntity::getId, currentUserId)
            .set(StringUtils.hasText(request.getRealName()), UserAccountEntity::getRealName, request.getRealName())
            .set(StringUtils.hasText(request.getEmail()), UserAccountEntity::getEmail, request.getEmail())
            .set(StringUtils.hasText(request.getAvatarUrl()), UserAccountEntity::getAvatarUrl, request.getAvatarUrl());
        return userAccountMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public PageResponse<AdminUserItemVO> pageUsers(AdminUserPageQuery query) {
        Page<UserAccountEntity> page = new Page<>(query.getPageNo(), query.getPageSize());
        LambdaQueryWrapper<UserAccountEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAccountEntity::getDeleted, 0)
            .and(StringUtils.hasText(query.getKeyword()), q -> q
                .like(UserAccountEntity::getUsername, query.getKeyword())
                .or()
                .like(UserAccountEntity::getMobile, query.getKeyword()))
            .eq(StringUtils.hasText(query.getAccountStatus()), UserAccountEntity::getAccountStatus, query.getAccountStatus())
            .orderByDesc(UserAccountEntity::getCreatedAt);

        if (StringUtils.hasText(query.getRoleType())) {
            RoleEntity role = roleMapper.selectOne(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getRoleCode, query.getRoleType())
                .last("limit 1"));
            if (role == null) {
                return PageResponse.of(query.getPageNo(), query.getPageSize(), 0L, List.of());
            }
            List<Long> userIds = userRoleMapper.selectList(new LambdaQueryWrapper<UserRoleEntity>()
                    .eq(UserRoleEntity::getRoleId, role.getId()))
                .stream()
                .map(UserRoleEntity::getUserId)
                .toList();
            if (userIds.isEmpty()) {
                return PageResponse.of(query.getPageNo(), query.getPageSize(), 0L, List.of());
            }
            wrapper.in(UserAccountEntity::getId, userIds);
        }

        Page<UserAccountEntity> resultPage = userAccountMapper.selectPage(page, wrapper);
        List<UserAccountEntity> users = resultPage.getRecords();
        Map<Long, List<String>> roleCodeMap = buildRoleCodeMap(users.stream().map(UserAccountEntity::getId).toList());

        List<AdminUserItemVO> records = users.stream()
            .map(user -> AdminUserItemVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .mobile(user.getMobile())
                .realName(user.getRealName())
                .roleType(roleCodeMap.getOrDefault(user.getId(), List.of()).stream().findFirst().orElse(""))
                .accountStatus(user.getAccountStatus())
                .createdAt(user.getCreatedAt() == null ? null : user.getCreatedAt().format(DATE_TIME_FORMATTER))
                .build())
            .toList();

        return PageResponse.of(query.getPageNo(), query.getPageSize(), resultPage.getTotal(), records);
    }

    @Override
    public boolean updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        LambdaUpdateWrapper<UserAccountEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserAccountEntity::getId, userId)
            .eq(UserAccountEntity::getDeleted, 0)
            .set(UserAccountEntity::getAccountStatus, request.getAccountStatus());
        return userAccountMapper.update(null, updateWrapper) > 0;
    }

    private List<String> listRoleCodesByUserId(Long userId) {
        return buildRoleCodeMap(List.of(userId)).getOrDefault(userId, List.of());
    }

    private Map<Long, List<String>> buildRoleCodeMap(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<UserRoleEntity> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRoleEntity>()
            .in(UserRoleEntity::getUserId, userIds));
        if (userRoles.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> roleIds = userRoles.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toSet());
        Map<Long, RoleEntity> roleMap = roleMapper.selectBatchIds(roleIds).stream()
            .collect(Collectors.toMap(RoleEntity::getId, Function.identity()));
        return userRoles.stream()
            .filter(item -> roleMap.containsKey(item.getRoleId()))
            .collect(Collectors.groupingBy(
                UserRoleEntity::getUserId,
                Collectors.mapping(item -> roleMap.get(item.getRoleId()).getRoleCode(), Collectors.toList())
            ));
    }
}
