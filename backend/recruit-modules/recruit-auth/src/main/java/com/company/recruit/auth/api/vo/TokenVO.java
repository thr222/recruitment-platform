package com.company.recruit.auth.api.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TokenVO {
    String accessToken;
    String refreshToken;
    Long expiresIn;
    List<String> roleList;
}