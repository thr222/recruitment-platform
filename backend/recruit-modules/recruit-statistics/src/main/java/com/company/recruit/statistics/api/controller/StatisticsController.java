package com.company.recruit.statistics.api.controller;

import com.company.recruit.common.core.api.Result;
import com.company.recruit.statistics.api.vo.AdminDashboardVO;
import com.company.recruit.statistics.application.service.StatisticsApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class StatisticsController {

    private final StatisticsApplicationService statisticsApplicationService;

    public StatisticsController(StatisticsApplicationService statisticsApplicationService) {
        this.statisticsApplicationService = statisticsApplicationService;
    }

    @GetMapping
    public Result<AdminDashboardVO> dashboard() {
        return Result.success(statisticsApplicationService.getAdminDashboard());
    }
}
