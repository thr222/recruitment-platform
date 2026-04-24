package com.company.recruit.job.service;

import com.company.recruit.common.core.api.PageResponse;
import com.company.recruit.job.dto.AuditJobRequest;
import com.company.recruit.job.dto.JobCreateRequest;
import com.company.recruit.job.dto.JobUpdateRequest;
import com.company.recruit.job.dto.UpdateJobStatusRequest;
import com.company.recruit.job.query.CandidateRecommendJobQuery;
import com.company.recruit.job.query.JobAuditPageQuery;
import com.company.recruit.job.query.JobSearchQuery;
import com.company.recruit.job.query.RecruiterJobPageQuery;
import com.company.recruit.job.vo.JobAuditItemVO;
import com.company.recruit.job.vo.JobCreateResultVO;
import com.company.recruit.job.vo.JobDetailVO;
import com.company.recruit.job.vo.PublicJobItemVO;
import com.company.recruit.job.vo.RecommendedJobItemVO;
import com.company.recruit.job.vo.RecruiterJobItemVO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

@Service
public class JobFacadeService {

    private final JobRecommendationService jobRecommendationService;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JobFacadeService(
        JobRecommendationService jobRecommendationService,
        NamedParameterJdbcTemplate jdbcTemplate
    ) {
        this.jobRecommendationService = jobRecommendationService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public JobCreateResultVO createJob(JobCreateRequest request) {
        return JobCreateResultVO.builder().jobId(30001L).status("DRAFT").build();
    }

    public boolean updateJob(Long jobId, JobUpdateRequest request) {
        return true;
    }

    public boolean updateJobStatus(Long jobId, UpdateJobStatusRequest request) {
        return true;
    }

    public PageResponse<RecruiterJobItemVO> pageRecruiterJobs(RecruiterJobPageQuery query) {
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            1L,
            List.of(
                RecruiterJobItemVO.builder()
                    .jobId(30001L)
                    .title("Java Backend Engineer")
                    .status("ONLINE")
                    .applyCount(18)
                    .viewCount(120)
                    .publishedAt("2026-04-08 10:30:00")
                    .build()
            )
        );
    }

    public PageResponse<PublicJobItemVO> searchJobs(JobSearchQuery query) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("limit", query.getPageSize())
            .addValue("offset", (query.getPageNo() - 1) * query.getPageSize());
        String whereClause = buildPublicSearchWhereClause(query, params);

        Long total = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM job_post jp JOIN company c ON c.id = jp.company_id " + whereClause,
            params,
            Long.class
        );

        String sql = """
            SELECT
              jp.id AS job_id,
              jp.title,
              jp.company_id,
              c.company_name,
              c.company_size,
              jp.work_city_code,
              jp.salary_min,
              jp.salary_max,
              jp.experience_min,
              jp.experience_max,
              jp.education_requirement,
              jp.status,
              DATE_FORMAT(COALESCE(jp.published_at, jp.created_at), '%Y-%m-%d %H:%i:%s') AS published_at
            FROM job_post jp
            JOIN company c ON c.id = jp.company_id
            """ + whereClause + " " + buildPublicSearchOrderBy(query.getSortBy()) + " LIMIT :limit OFFSET :offset";

        List<PublicJobItemVO> records = jdbcTemplate.query(sql, params, new PublicJobItemRowMapper());
        return PageResponse.of(query.getPageNo(), query.getPageSize(), total, records);
    }

    private String buildPublicSearchWhereClause(JobSearchQuery query, MapSqlParameterSource params) {
        List<String> conditions = new ArrayList<>();
        conditions.add("jp.status = 'ONLINE'");
        conditions.add("jp.audit_status = 'APPROVED'");
        conditions.add("c.business_status = 'ACTIVE'");

        if (StringUtils.hasText(query.getKeyword())) {
            conditions.add("""
                (
                  jp.title LIKE :keyword
                  OR jp.description_text LIKE :keyword
                  OR jp.job_highlights LIKE :keyword
                  OR c.company_name LIKE :keyword
                  OR c.company_short_name LIKE :keyword
                )
                """);
            params.addValue("keyword", "%" + query.getKeyword().trim() + "%");
        }
        if (StringUtils.hasText(query.getCityCode())) {
            conditions.add("jp.work_city_code = :cityCode");
            params.addValue("cityCode", query.getCityCode());
        }
        if (query.getCategoryId() != null) {
            conditions.add("jp.category_id = :categoryId");
            params.addValue("categoryId", query.getCategoryId());
        }
        if (query.getCompanyId() != null) {
            conditions.add("jp.company_id = :companyId");
            params.addValue("companyId", query.getCompanyId());
        }
        if (query.getSalaryMin() != null) {
            conditions.add("jp.salary_max >= :salaryMin");
            params.addValue("salaryMin", query.getSalaryMin());
        }
        if (query.getSalaryMax() != null) {
            conditions.add("jp.salary_min <= :salaryMax");
            params.addValue("salaryMax", query.getSalaryMax());
        }
        if (query.getExperienceMin() != null) {
            conditions.add("COALESCE(jp.experience_max, jp.experience_min) >= :experienceMin");
            params.addValue("experienceMin", query.getExperienceMin());
        }
        if (query.getExperienceMax() != null) {
            conditions.add("jp.experience_min <= :experienceMax");
            params.addValue("experienceMax", query.getExperienceMax());
        }
        if (StringUtils.hasText(query.getCompanySize())) {
            conditions.add("c.company_size = :companySize");
            params.addValue("companySize", query.getCompanySize());
        }
        if (StringUtils.hasText(query.getEducationRequirement())) {
            conditions.add("jp.education_requirement = :educationRequirement");
            params.addValue("educationRequirement", query.getEducationRequirement());
        }
        if (query.getTagIds() != null && !query.getTagIds().isEmpty()) {
            conditions.add("""
                EXISTS (
                  SELECT 1
                  FROM job_post_tag_rel rel
                  WHERE rel.job_id = jp.id AND rel.tag_id IN (:tagIds)
                )
                """);
            params.addValue("tagIds", query.getTagIds());
        }

        return " WHERE " + String.join(" AND ", conditions);
    }

    private String buildPublicSearchOrderBy(String sortBy) {
        if ("salary".equalsIgnoreCase(sortBy)) {
            return "ORDER BY jp.salary_max DESC, jp.salary_min DESC, COALESCE(jp.published_at, jp.created_at) DESC";
        }
        if ("hot".equalsIgnoreCase(sortBy)) {
            return "ORDER BY jp.apply_count DESC, jp.view_count DESC, COALESCE(jp.published_at, jp.created_at) DESC";
        }
        return "ORDER BY COALESCE(jp.published_at, jp.created_at) DESC, jp.id DESC";
    }

    private static class PublicJobItemRowMapper implements RowMapper<PublicJobItemVO> {
        @Override
        public PublicJobItemVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return PublicJobItemVO.builder()
                .jobId(rs.getLong("job_id"))
                .title(rs.getString("title"))
                .companyId(rs.getLong("company_id"))
                .companyName(rs.getString("company_name"))
                .companySize(rs.getString("company_size"))
                .workCityCode(rs.getString("work_city_code"))
                .salaryMin(rs.getObject("salary_min", Integer.class))
                .salaryMax(rs.getObject("salary_max", Integer.class))
                .experienceMin(toInteger(rs.getObject("experience_min", BigDecimal.class)))
                .experienceMax(toInteger(rs.getObject("experience_max", BigDecimal.class)))
                .educationRequirement(rs.getString("education_requirement"))
                .status(rs.getString("status"))
                .publishedAt(rs.getString("published_at"))
                .build();
        }

        private static Integer toInteger(BigDecimal value) {
            return value == null ? null : value.intValue();
        }
    }

    public JobDetailVO getJobDetail(Long jobId) {
        jobRecommendationService.recordJobViewBehavior(jobId);
        return JobDetailVO.builder()
            .jobId(jobId)
            .title("Java Backend Engineer")
            .company(JobDetailVO.JobCompanyVO.builder().companyId(20001L).companyName("Galaxy Talent Tech").build())
            .salaryMin(25)
            .salaryMax(40)
            .descriptionText("Build job, application, and permission modules for the recruitment platform.")
            .status("ONLINE")
            .build();
    }

    public PageResponse<RecommendedJobItemVO> pageCandidateRecommendations(CandidateRecommendJobQuery query) {
        return jobRecommendationService.pageCandidateRecommendations(query);
    }

    public PageResponse<JobAuditItemVO> pageJobAudits(JobAuditPageQuery query) {
        return PageResponse.of(
            query.getPageNo(),
            query.getPageSize(),
            1L,
            List.of(
                JobAuditItemVO.builder()
                    .jobId(30001L)
                    .title("Java Backend Engineer")
                    .companyName("Galaxy Talent Tech")
                    .auditStatus("PENDING")
                    .build()
            )
        );
    }

    public boolean auditJob(Long jobId, AuditJobRequest request) {
        return true;
    }
}
