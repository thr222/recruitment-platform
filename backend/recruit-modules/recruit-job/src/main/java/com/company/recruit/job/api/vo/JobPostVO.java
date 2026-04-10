package com.company.recruit.job.api.vo;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobPostVO {
    Long jobId;
    String title;
    String companyName;
    String city;
    String salaryRange;
    String experienceText;
    List<String> tags;
}
