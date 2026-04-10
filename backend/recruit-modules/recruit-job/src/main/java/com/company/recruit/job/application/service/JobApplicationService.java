package com.company.recruit.job.application.service;

import com.company.recruit.job.api.vo.JobPostVO;
import com.company.recruit.job.api.vo.RecruiterJobVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {

    public List<JobPostVO> listPublicJobs() {
        return List.of(
            JobPostVO.builder()
                .jobId(50001L)
                .title("Java后端开发工程师")
                .companyName("星河科技")
                .city("上海")
                .salaryRange("25K-40K")
                .experienceText("3-5年")
                .tags(List.of("Spring Boot", "MySQL", "Redis"))
                .build(),
            JobPostVO.builder()
                .jobId(50002L)
                .title("招聘产品经理")
                .companyName("云图人才")
                .city("深圳")
                .salaryRange("18K-28K")
                .experienceText("3年+")
                .tags(List.of("招聘SaaS", "B端产品", "数据分析"))
                .build()
        );
    }

    public List<RecruiterJobVO> listRecruiterJobs() {
        return List.of(
            RecruiterJobVO.builder()
                .jobId(50001L)
                .title("Java后端开发工程师")
                .salaryRange("25K-40K")
                .status("ONLINE")
                .candidateCount(23)
                .updatedAt("2026-04-08 10:20")
                .build(),
            RecruiterJobVO.builder()
                .jobId(50003L)
                .title("测试开发工程师")
                .salaryRange("20K-30K")
                .status("PENDING_AUDIT")
                .candidateCount(0)
                .updatedAt("2026-04-07 18:15")
                .build()
        );
    }
}
