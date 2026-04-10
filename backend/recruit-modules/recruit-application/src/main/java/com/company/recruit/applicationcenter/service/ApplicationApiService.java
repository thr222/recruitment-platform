package com.company.recruit.applicationcenter.service;

import com.company.recruit.applicationcenter.dto.CreateApplicationRequest;
import com.company.recruit.applicationcenter.dto.CreateInterviewRequest;
import com.company.recruit.applicationcenter.dto.UpdateApplicationStatusRequest;
import com.company.recruit.applicationcenter.dto.UpdateInterviewResultRequest;
import com.company.recruit.applicationcenter.dto.WithdrawApplicationRequest;
import com.company.recruit.applicationcenter.query.CandidateApplicationPageQuery;
import com.company.recruit.applicationcenter.query.RecruiterApplicationPageQuery;
import com.company.recruit.applicationcenter.vo.ApplicationCreateResultVO;
import com.company.recruit.applicationcenter.vo.ApplicationTimelineVO;
import com.company.recruit.applicationcenter.vo.CandidateApplicationDetailVO;
import com.company.recruit.applicationcenter.vo.CandidateApplicationItemVO;
import com.company.recruit.applicationcenter.vo.InterviewCreateResultVO;
import com.company.recruit.applicationcenter.vo.RecruiterApplicationItemVO;
import com.company.recruit.common.core.api.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ApplicationApiService {
    public ApplicationCreateResultVO createApplication(CreateApplicationRequest request) { return ApplicationCreateResultVO.builder().applicationId(50001L).status("SUBMITTED").build(); }
    public PageResponse<CandidateApplicationItemVO> pageCandidateApplications(CandidateApplicationPageQuery query) {
        return PageResponse.of(query.getPageNo(), query.getPageSize(), 1L, List.of(CandidateApplicationItemVO.builder().applicationId(50001L).jobId(30001L).jobTitle("Java后端开发工程师").companyName("星河科技有限公司").status("INTERVIEWING").processStage("FIRST_INTERVIEW").appliedAt("2026-04-08 10:00:00").build()));
    }
    public CandidateApplicationDetailVO getCandidateApplicationDetail(Long applicationId) {
        return CandidateApplicationDetailVO.builder().applicationId(applicationId).jobTitle("Java后端开发工程师").companyName("星河科技有限公司").status("INTERVIEWING").processStage("FIRST_INTERVIEW").timeline(List.of(ApplicationTimelineVO.builder().fromStatus("SUBMITTED").toStatus("VIEWED").operatorRole("RECRUITER").createdAt("2026-04-09 09:00:00").build())).build();
    }
    public boolean withdrawApplication(Long applicationId, WithdrawApplicationRequest request) { return true; }
    public PageResponse<RecruiterApplicationItemVO> pageRecruiterApplications(RecruiterApplicationPageQuery query) {
        return PageResponse.of(query.getPageNo(), query.getPageSize(), 1L, List.of(RecruiterApplicationItemVO.builder().applicationId(50001L).candidateUserId(10001L).candidateName("张三").jobTitle("Java后端开发工程师").status("SCREENING").processStage("FIRST_SCREEN").appliedAt("2026-04-08 10:00:00").build()));
    }
    public boolean updateApplicationStatus(Long applicationId, UpdateApplicationStatusRequest request) { return true; }
    public InterviewCreateResultVO createInterview(Long applicationId, CreateInterviewRequest request) { return InterviewCreateResultVO.builder().interviewId(60001L).interviewStatus("SCHEDULED").build(); }
    public boolean updateInterviewResult(Long interviewId, UpdateInterviewResultRequest request) { return true; }
}