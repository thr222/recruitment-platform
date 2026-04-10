package com.company.recruit.operation.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BannerVO {
    Long bannerId;
    String title;
    String imageUrl;
    String redirectUrl;
    Integer sortOrder;
}
