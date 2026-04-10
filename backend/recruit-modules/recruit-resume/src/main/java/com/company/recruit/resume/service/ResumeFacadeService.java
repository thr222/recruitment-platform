package com.company.recruit.resume.service;

import com.company.recruit.resume.dto.ProjectExperienceCreateRequest;
import com.company.recruit.resume.dto.ResumeCreateRequest;
import com.company.recruit.resume.dto.ResumeUpdateRequest;
import com.company.recruit.resume.dto.WorkExperienceCreateRequest;
import com.company.recruit.resume.vo.ProjectExperienceCreateResultVO;
import com.company.recruit.resume.vo.RecruiterResumeVO;
import com.company.recruit.resume.vo.ResumeCreateResultVO;
import com.company.recruit.resume.vo.ResumeSummaryVO;
import com.company.recruit.resume.vo.WorkExperienceCreateResultVO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResumeFacadeService {
    public ResumeSummaryVO getDefaultResume() { return ResumeSummaryVO.builder().resumeId(40001L).resumeName("默认在线简历").realName("张三").mobile("13800138000").email("zhangsan@example.com").degree("本科").completionScore(88).currentStatus("OPEN_TO_OPPORTUNITY").build(); }
    public ResumeCreateResultVO createResume(ResumeCreateRequest request) { return ResumeCreateResultVO.builder().resumeId(40001L).build(); }
    public boolean updateResume(Long resumeId, ResumeUpdateRequest request) { return true; }
    public WorkExperienceCreateResultVO createWorkExperience(Long resumeId, WorkExperienceCreateRequest request) { return WorkExperienceCreateResultVO.builder().workExperienceId(50001L).build(); }
    public ProjectExperienceCreateResultVO createProjectExperience(Long resumeId, ProjectExperienceCreateRequest request) { return ProjectExperienceCreateResultVO.builder().projectExperienceId(60001L).build(); }
    public RecruiterResumeVO getRecruiterResume(Long resumeId) { return RecruiterResumeVO.builder().resumeId(resumeId).realName("张三").degree("本科").workExperiences(List.of("2022-至今 星河科技 Java后端工程师")).projectExperiences(List.of("企业招聘与求职平台：负责后端架构与核心模块开发")).build(); }
}