package com.company.recruit.user.application.service;

import com.company.recruit.user.api.vo.CandidateProfileVO;
import org.springframework.stereotype.Service;

@Service
public class CandidateProfileApplicationService {

    public CandidateProfileVO getCurrentProfile() {
        return CandidateProfileVO.builder()
            .userId(30001L)
            .nickname("张同学")
            .avatar("/static/avatar/candidate-demo.png")
            .currentCity("上海")
            .resumeCompletion(85)
            .build();
    }
}
