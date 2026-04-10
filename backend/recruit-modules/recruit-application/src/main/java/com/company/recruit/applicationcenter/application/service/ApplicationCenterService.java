package com.company.recruit.applicationcenter.application.service;

import com.company.recruit.applicationcenter.api.vo.ApplicationVO;
import com.company.recruit.applicationcenter.api.vo.RecruiterCandidateVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ApplicationCenterService {

    public List<ApplicationVO> listMyApplications() {
        return List.of(
            ApplicationVO.builder()
                .applicationId(70001L)
                .jobId(50001L)
                .jobTitle("Java后端开发工程师")
                .companyName("星河科技")
                .status("INTERVIEWING")
                .lastActionTime("2026-04-08 14:30")
                .build()
        );
    }

    public List<RecruiterCandidateVO> listRecruiterCandidates() {
        return List.of(
            RecruiterCandidateVO.builder()
                .applicationId(70001L)
                .candidateName("王同学")
                .jobTitle("Java后端开发工程师")
                .stage("SCREENING")
                .lastActionTime("2026-04-08 11:10")
                .build(),
            RecruiterCandidateVO.builder()
                .applicationId(70002L)
                .candidateName("陈同学")
                .jobTitle("测试开发工程师")
                .stage("INTERVIEWING")
                .lastActionTime("2026-04-08 09:30")
                .build()
        );
    }
}
