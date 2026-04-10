package com.company.recruit.operation.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.operation.api.vo.BannerVO;
import com.company.recruit.operation.application.service.OperationApplicationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common/operation")
public class OperationController {

    private final OperationApplicationService operationApplicationService;

    public OperationController(OperationApplicationService operationApplicationService) {
        this.operationApplicationService = operationApplicationService;
    }

    @GetMapping("/banners")
    public Result<List<BannerVO>> listBanners() {
        return Result.success(operationApplicationService.listBanners());
    }
}
