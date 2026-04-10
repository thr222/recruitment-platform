package com.company.recruit.message.application.service;

import com.company.recruit.message.api.vo.NotifyMessageVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageApplicationService {

    public List<NotifyMessageVO> listNotifications() {
        return List.of(
            NotifyMessageVO.builder()
                .messageId(80001L)
                .title("面试已更新")
                .content("星河科技将你的流程状态更新为面试中")
                .createdAt("2026-04-08 11:00")
                .read(Boolean.FALSE)
                .build()
        );
    }
}
