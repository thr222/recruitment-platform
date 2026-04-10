package com.company.recruit.job.service;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.common.redis.RedisKeyRegistry;
import com.company.recruit.common.security.context.LoginUser;
import com.company.recruit.common.security.context.SecurityUtils;
import com.company.recruit.job.query.CandidateRecommendJobQuery;
import com.company.recruit.job.vo.RecommendedJobItemVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Rule-based job recommendation service.
 *
 * <p>Current version provides a development skeleton and mock data. The real implementation
 * should load behavior profile, compute preference weights, filter applied jobs, and cache the
 * final page result in Redis.</p>
 */
@Service
public class JobRecommendationService {

    public PageResponse<RecommendedJobItemVO> pageCandidateRecommendations(CandidateRecommendJobQuery query) {
        LoginUser loginUser = SecurityUtils.getCurrentLoginUser();
        Long userId = loginUser == null ? 0L : loginUser.getUserId();

        // Future implementation outline:
        // 1. Load Redis profile: RedisKeyRegistry.RECOMMEND_PROFILE + userId
        // 2. If absent, aggregate recent view/apply behavior into a rule profile.
        // 3. Build candidate jobs by city/category/salary overlap.
        // 4. Exclude jobs already applied or recently skipped.
        // 5. Cache paged result with RedisKeyRegistry.RECOMMEND_PAGE + userId + scene + pageNo.
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            2L,
            List.of(
                RecommendedJobItemVO.builder()
                    .jobId(30021L)
                    .title("Java Backend Engineer")
                    .companyId(20001L)
                    .companyName("Galaxy Talent Tech")
                    .workCityCode("310100")
                    .salaryMin(25)
                    .salaryMax(35)
                    .experienceMin(3)
                    .experienceMax(5)
                    .educationRequirement("Bachelor")
                    .status("ONLINE")
                    .recommendReason("Matched with your recent applications in backend roles and Shanghai jobs")
                    .recommendScore(92)
                    .matchedFeatures(List.of("category:backend", "city:310100", "salary:25-35K"))
                    .publishedAt("2026-04-08 14:00:00")
                    .build(),
                RecommendedJobItemVO.builder()
                    .jobId(30022L)
                    .title("Platform Development Engineer")
                    .companyId(20003L)
                    .companyName("Northstar Cloud")
                    .workCityCode("310100")
                    .salaryMin(28)
                    .salaryMax(40)
                    .experienceMin(3)
                    .experienceMax(5)
                    .educationRequirement("Bachelor")
                    .status("ONLINE")
                    .recommendReason("Users with similar apply behavior also viewed this platform role")
                    .recommendScore(88)
                    .matchedFeatures(List.of("behavior:apply", "city:310100", "keyword:platform"))
                    .publishedAt("2026-04-08 09:30:00")
                    .build()
            )
        );
    }

    public void recordJobViewBehavior(Long jobId) {
        LoginUser loginUser = SecurityUtils.getCurrentLoginUser();
        if (loginUser == null || loginUser.getUserId() == null) {
            return;
        }
        Long userId = loginUser.getUserId();
        // Future implementation outline:
        // 1. Increment Redis sorted sets / hashes using keys under RedisKeyRegistry.
        // 2. Batch flush to candidate_job_behavior table asynchronously.
        String profileKey = RedisKeyRegistry.RECOMMEND_PROFILE + userId;
        String behaviorKey = RedisKeyRegistry.RECOMMEND_BEHAVIOR_JOB + userId;
        String pageKey = RedisKeyRegistry.RECOMMEND_PAGE + userId;
        // Suppress unused variable warnings until the real Redis write is wired in.
        if (profileKey.isEmpty() || behaviorKey.isEmpty() || pageKey.isEmpty() || jobId == null) {
            return;
        }
    }
}