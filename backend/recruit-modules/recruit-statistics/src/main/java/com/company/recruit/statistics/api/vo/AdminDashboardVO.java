package com.company.recruit.statistics.api.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminDashboardVO {
    Integer totalUsers;
    Integer totalCompanies;
    Integer onlineJobs;
    Integer todayApplications;
}
