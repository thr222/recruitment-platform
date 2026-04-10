package com.company.recruit.resume.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecruiterResumeVO {
    Long resumeId;
    String realName;
    String degree;
    List<String> workExperiences;
    List<String> projectExperiences;
}