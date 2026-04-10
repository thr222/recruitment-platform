package com.company.recruit.user.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Registration response.
 */
@Value
@Builder
public class UserRegisterVO {
    Long userId;
    String username;
    List<String> roleList;
}
