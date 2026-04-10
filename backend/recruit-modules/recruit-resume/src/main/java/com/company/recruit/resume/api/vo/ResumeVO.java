package com.company.recruit.resume.api.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResumeVO {
    Long resumeId;
    String name;
    String mobile;
    String email;
    String highestEducation;
    List<String> workExperiences;
    List<String> projectExperiences;
}
