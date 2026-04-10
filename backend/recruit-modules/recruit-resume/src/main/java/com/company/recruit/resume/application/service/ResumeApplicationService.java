package com.company.recruit.resume.application.service;

import com.company.recruit.resume.api.vo.ResumeVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResumeApplicationService {

    public ResumeVO getMyResume() {
        return ResumeVO.builder()
            .resumeId(60001L)
            .name("张同学")
            .mobile("13800138000")
            .email("demo@example.com")
            .highestEducation("本科")
            .workExperiences(List.of("2022-至今 星河科技 Java后端工程师"))
            .projectExperiences(List.of("招聘平台中台重构：负责权限与职位检索模块"))
            .build();
    }
}
