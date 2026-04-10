package com.company.recruit.message.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.message.api.vo.NotifyMessageVO;
import com.company.recruit.message.application.service.MessageApplicationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate/messages")
public class MessageController {

    private final MessageApplicationService messageApplicationService;

    public MessageController(MessageApplicationService messageApplicationService) {
        this.messageApplicationService = messageApplicationService;
    }

    @GetMapping("/notifications")
    public Result<List<NotifyMessageVO>> listNotifications() {
        return Result.success(messageApplicationService.listNotifications());
    }
}
