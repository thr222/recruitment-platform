package com.company.recruit.user.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurrentUserVO {
    Long userId;
    String username;
    String realName;
    String mobile;
    String email;
    String avatarUrl;
    List<String> roleList;
}