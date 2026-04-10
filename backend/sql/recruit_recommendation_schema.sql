-- Rule-based recommendation tables for recruitment platform.
-- Behavior and recommendation tables intentionally avoid foreign keys on hot write paths
-- to reduce write amplification and improve async batch ingestion throughput.

DROP TABLE IF EXISTS candidate_job_recommendation;
DROP TABLE IF EXISTS candidate_recommend_profile;
DROP TABLE IF EXISTS candidate_job_behavior;

CREATE TABLE candidate_job_behavior (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Behavior log id',
    user_id BIGINT NOT NULL COMMENT 'Candidate user id',
    job_id BIGINT NOT NULL COMMENT 'Job id',
    behavior_type VARCHAR(32) NOT NULL COMMENT 'VIEW/APPLY/FAVORITE',
    behavior_weight DECIMAL(6,2) NOT NULL DEFAULT 1.00 COMMENT 'Rule weight for recommendation scoring',
    scene VARCHAR(32) NOT NULL DEFAULT 'JOB_DETAIL' COMMENT 'Where the behavior happened',
    occurred_at DATETIME NOT NULL COMMENT 'Business time of behavior',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Insert time',
    PRIMARY KEY (id),
    KEY idx_candidate_job_behavior_user_time (user_id, occurred_at),
    KEY idx_candidate_job_behavior_user_type_time (user_id, behavior_type, occurred_at),
    KEY idx_candidate_job_behavior_job_type_time (job_id, behavior_type, occurred_at),
    KEY idx_candidate_job_behavior_user_job_type (user_id, job_id, behavior_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Candidate behavior facts for rule-based recommendation';

CREATE TABLE candidate_recommend_profile (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Profile id',
    user_id BIGINT NOT NULL COMMENT 'Candidate user id',
    top_city_codes VARCHAR(255) DEFAULT NULL COMMENT 'Top interested cities, comma separated',
    top_category_ids VARCHAR(255) DEFAULT NULL COMMENT 'Top interested categories, comma separated',
    top_keywords VARCHAR(500) DEFAULT NULL COMMENT 'Top extracted keywords',
    preferred_salary_min INT DEFAULT NULL COMMENT 'Preferred salary min inferred from behavior',
    preferred_salary_max INT DEFAULT NULL COMMENT 'Preferred salary max inferred from behavior',
    last_apply_at DATETIME DEFAULT NULL COMMENT 'Last apply behavior time',
    last_view_at DATETIME DEFAULT NULL COMMENT 'Last view behavior time',
    profile_version INT NOT NULL DEFAULT 1 COMMENT 'Rule profile version',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Profile update time',
    PRIMARY KEY (id),
    UNIQUE KEY uk_candidate_recommend_profile_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Aggregated candidate preference profile';

CREATE TABLE candidate_job_recommendation (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Recommendation snapshot id',
    user_id BIGINT NOT NULL COMMENT 'Candidate user id',
    job_id BIGINT NOT NULL COMMENT 'Recommended job id',
    recommend_scene VARCHAR(32) NOT NULL DEFAULT 'HOME_FEED' COMMENT 'HOME_FEED/APPLY_SUCCESS/DASHBOARD',
    recommend_reason VARCHAR(255) NOT NULL COMMENT 'Human-readable reason for UI',
    recommend_score INT NOT NULL COMMENT 'Rule score from 0 to 100',
    rank_no INT NOT NULL COMMENT 'Ranking position',
    source_type VARCHAR(32) NOT NULL DEFAULT 'RULE' COMMENT 'RULE/HOT_FALLBACK',
    recommendation_date DATE NOT NULL COMMENT 'Snapshot date',
    expire_at DATETIME DEFAULT NULL COMMENT 'Snapshot expiration time',
    clicked_flag TINYINT NOT NULL DEFAULT 0 COMMENT 'Whether user clicked recommended job',
    applied_flag TINYINT NOT NULL DEFAULT 0 COMMENT 'Whether user applied recommended job',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Insert time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    PRIMARY KEY (id),
    UNIQUE KEY uk_candidate_job_recommendation_user_job_scene_date (user_id, job_id, recommend_scene, recommendation_date),
    KEY idx_candidate_job_recommendation_user_scene_rank (user_id, recommend_scene, recommendation_date, rank_no),
    KEY idx_candidate_job_recommendation_user_score (user_id, recommendation_date, recommend_score)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Rule-based recommendation snapshot';