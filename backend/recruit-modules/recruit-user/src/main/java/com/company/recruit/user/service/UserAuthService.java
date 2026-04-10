package com.company.recruit.user.service;

import com.company.recruit.user.dto.UserLoginRequest;
import com.company.recruit.user.dto.UserRegisterRequest;
import com.company.recruit.user.vo.UserLoginVO;
import com.company.recruit.user.vo.UserRegisterVO;

/**
 * User authentication service.
 */
public interface UserAuthService {

    /**
     * Register a new user account.
     */
    UserRegisterVO register(UserRegisterRequest request);

    /**
     * Login and issue JWT token pair.
     */
    UserLoginVO login(UserLoginRequest request);
}
