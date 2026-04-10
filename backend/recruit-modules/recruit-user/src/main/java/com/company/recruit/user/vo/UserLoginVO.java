package com.company.recruit.user.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Login response containing JWT token pair.
 */
@Value
@Builder
public class UserLoginVO {
    Long userId;
    String username;
    String accessToken;
    String refreshToken;
    Long expiresIn;
    List<String> roleList;
}
