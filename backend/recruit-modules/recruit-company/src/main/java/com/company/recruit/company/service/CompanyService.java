package com.company.recruit.company.service;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.company.dto.AuditCompanyRequest;
import com.company.recruit.company.dto.CreateCompanyRequest;
import com.company.recruit.company.dto.UpdateCompanyRequest;
import com.company.recruit.company.query.CompanyAuditPageQuery;
import com.company.recruit.company.vo.CompanyAuditItemVO;
import com.company.recruit.company.vo.CompanyCreateResultVO;
import com.company.recruit.company.vo.CompanyDetailVO;
import com.company.recruit.company.vo.CurrentCompanyVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    public CompanyCreateResultVO createCompany(CreateCompanyRequest request) {
        return CompanyCreateResultVO.builder().companyId(20001L).auditStatus("PENDING").build();
    }

    public CurrentCompanyVO getCurrentCompany() {
        return CurrentCompanyVO.builder().companyId(20001L).companyName("星河科技有限公司").auditStatus("APPROVED").businessStatus("ACTIVE").build();
    }

    public boolean updateCompany(Long companyId, UpdateCompanyRequest request) {
        return true;
    }

    public CompanyDetailVO getCompanyDetail(Long companyId) {
        return CompanyDetailVO.builder().companyId(companyId).companyName("星河科技有限公司").companyShortName("星河科技").financingStage("B轮").companySize("100-499人").cityCode("330100").introduction("聚焦智能招聘与组织效能产品。" ).logoUrl("/logo/xinghe.png").build();
    }

    public PageResponse<CompanyAuditItemVO> pageCompanyAudits(CompanyAuditPageQuery query) {
        return PageResponse.of(query.getPageNo(), query.getPageSize(), 1L, List.of(CompanyAuditItemVO.builder().companyId(20001L).companyName("星河科技有限公司").auditStatus("PENDING").createdAt("2026-04-08 11:00:00").build()));
    }

    public boolean auditCompany(Long companyId, AuditCompanyRequest request) {
        return true;
    }
}