package com.company.recruit.statistics.application.service;

import com.company.recruit.statistics.api.vo.AdminDashboardVO;
import org.springframework.stereotype.Service;

@Service
public class StatisticsApplicationService {

    public AdminDashboardVO getAdminDashboard() {
        return AdminDashboardVO.builder()
            .totalUsers(1280)
            .totalCompanies(86)
            .onlineJobs(415)
            .todayApplications(132)
            .build();
    }
}
