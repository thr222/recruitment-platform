package com.company.recruit.user.service;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.user.dto.UpdateCurrentUserRequest;
import com.company.recruit.user.dto.UpdateUserStatusRequest;
import com.company.recruit.user.query.AdminUserPageQuery;
import com.company.recruit.user.vo.AdminUserItemVO;
import com.company.recruit.user.vo.CurrentUserVO;

/**
 * User query and profile maintenance service.
 */
public interface UserService {

    /**
     * Get the current logged-in user profile.
     */
    CurrentUserVO getCurrentUser();

    /**
     * Update the current user profile.
     */
    boolean updateCurrentUser(UpdateCurrentUserRequest request);

    /**
     * Page query users for admin console.
     */
    PageResponse<AdminUserItemVO> pageUsers(AdminUserPageQuery query);

    /**
     * Update user account status.
     */
    boolean updateUserStatus(Long userId, UpdateUserStatusRequest request);
}
