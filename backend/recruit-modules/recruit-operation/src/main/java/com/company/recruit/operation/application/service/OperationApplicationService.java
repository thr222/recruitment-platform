package com.company.recruit.operation.application.service;

import com.company.recruit.operation.api.vo.BannerVO;
import com.company.recruit.operation.api.vo.CompanyAuditVO;
import com.company.recruit.operation.api.vo.JobAuditVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OperationApplicationService {

    public List<BannerVO> listBanners() {
        return List.of(
            BannerVO.builder()
                .bannerId(90001L)
                .title("春招专场")
                .imageUrl("/static/banner/spring.png")
                .redirectUrl("/jobs?scene=spring")
                .sortOrder(1)
                .build()
        );
    }

    public List<CompanyAuditVO> listCompanyAudits() {
        return List.of(
            CompanyAuditVO.builder()
                .companyId(40002L)
                .companyName("云图人才")
                .auditStatus("PENDING")
                .submittedAt("2026-04-08 09:00")
                .build()
        );
    }

    public List<JobAuditVO> listJobAudits() {
        return List.of(
            JobAuditVO.builder()
                .jobId(50003L)
                .title("测试开发工程师")
                .companyName("星河科技")
                .auditStatus("PENDING")
                .submittedAt("2026-04-07 18:15")
                .build()
        );
    }
}
