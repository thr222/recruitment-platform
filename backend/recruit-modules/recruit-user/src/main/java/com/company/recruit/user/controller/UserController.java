package com.company.recruit.user.controller;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.core.api.Result;
import com.company.recruit.user.dto.UpdateCurrentUserRequest;
import com.company.recruit.user.dto.UpdateUserStatusRequest;
import com.company.recruit.user.query.AdminUserPageQuery;
import com.company.recruit.user.service.UserService;
import com.company.recruit.user.vo.AdminUserItemVO;
import com.company.recruit.user.vo.CurrentUserVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/me")
    public Result<CurrentUserVO> getCurrentUser() {
        return Result.success(userService.getCurrentUser());
    }

    @PreAuthorize("hasAuthority('user:profile:update')")
    @PutMapping("/api/users/me")
    public Result<Boolean> updateCurrentUser(@Valid @RequestBody UpdateCurrentUserRequest request) {
        return Result.success("profile updated", userService.updateCurrentUser(request));
    }

    @PreAuthorize("hasAuthority('user:view')")
    @GetMapping("/api/admin/users")
    public Result<PageResponse<AdminUserItemVO>> pageUsers(@Valid AdminUserPageQuery query) {
        return Result.success(userService.pageUsers(query));
    }

    @PreAuthorize("hasAuthority('user:status:update')")
    @PatchMapping("/api/admin/users/{userId}/status")
    public Result<Boolean> updateUserStatus(@PathVariable Long userId, @Valid @RequestBody UpdateUserStatusRequest request) {
        return Result.success("user status updated", userService.updateUserStatus(userId, request));
    }
}
