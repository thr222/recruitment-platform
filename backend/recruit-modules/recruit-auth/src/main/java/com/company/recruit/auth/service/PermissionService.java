package com.company.recruit.auth.service;

import com.company.recruit.auth.dto.AssignUserRolesRequest;
import com.company.recruit.auth.vo.CurrentPermissionVO;
import com.company.recruit.auth.vo.RoleItemVO;
import com.company.recruit.common.security.context.LoginUser;
import com.company.recruit.common.security.context.SecurityUtils;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    public CurrentPermissionVO getCurrentPermissions() {
        LoginUser loginUser = SecurityUtils.getCurrentLoginUser();
        if (loginUser == null) {
            return CurrentPermissionVO.builder()
                .userId(null)
                .roleList(List.of())
                .permissionList(List.of())
                .dataScope(CurrentPermissionVO.DataScopeVO.builder().companyIds(List.of()).build())
                .build();
        }
        return CurrentPermissionVO.builder()
            .userId(loginUser.getUserId())
            .roleList(loginUser.getRoleCodes())
            .permissionList(loginUser.getPermissionCodes())
            .dataScope(CurrentPermissionVO.DataScopeVO.builder().companyIds(loginUser.getCompanyIds()).build())
            .build();
    }

    public boolean assignUserRoles(Long userId, AssignUserRolesRequest request) {
        return true;
    }

    public List<RoleItemVO> listRoles() {
        return List.of(
            RoleItemVO.builder().roleCode("ADMIN").roleName("Administrator").build(),
            RoleItemVO.builder().roleCode("RECRUITER").roleName("Recruiter").build(),
            RoleItemVO.builder().roleCode("CANDIDATE").roleName("Candidate").build()
        );
    }
}
