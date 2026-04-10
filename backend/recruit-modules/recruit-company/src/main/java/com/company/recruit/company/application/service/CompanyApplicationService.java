package com.company.recruit.company.application.service;

import com.company.recruit.company.api.vo.CompanyProfileVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyApplicationService {

    public CompanyProfileVO getMyCompany() {
        return CompanyProfileVO.builder()
            .companyId(40001L)
            .companyName("星河科技")
            .stage("B轮")
            .city("杭州")
            .auditStatus("APPROVED")
            .tags(List.of("SaaS", "AI", "扁平管理"))
            .build();
    }
}
