package com.company.recruit.message.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NotifyMessageVO {
    Long messageId;
    String title;
    String content;
    String createdAt;
    Boolean read;
}
