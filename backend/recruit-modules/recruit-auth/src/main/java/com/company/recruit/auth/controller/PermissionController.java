package com.company.recruit.auth.controller;

import com.company.recruit.auth.dto.AssignUserRolesRequest;
import com.company.recruit.auth.service.PermissionService;
import com.company.recruit.auth.vo.CurrentPermissionVO;
import com.company.recruit.auth.vo.RoleItemVO;
import com.company.recruit.common.core.api.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/api/auth/permissions/me")
    public Result<CurrentPermissionVO> getCurrentPermissions() {
        return Result.success(permissionService.getCurrentPermissions());
    }

    @PreAuthorize("hasAuthority('user:role:assign')")
    @PutMapping("/api/admin/users/{userId}/roles")
    public Result<Boolean> assignUserRoles(@PathVariable Long userId, @Valid @RequestBody AssignUserRolesRequest request) {
        return Result.success("roles assigned", permissionService.assignUserRoles(userId, request));
    }

    @PreAuthorize("hasAuthority('user:role:assign')")
    @GetMapping("/api/admin/roles")
    public Result<List<RoleItemVO>> listRoles() {
        return Result.success(permissionService.listRoles());
    }
}
