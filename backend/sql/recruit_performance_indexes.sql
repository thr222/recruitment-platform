-- Performance indexes for recruitment platform hot paths.

ALTER TABLE job_post
    ADD KEY idx_job_post_status_refresh_city (status, last_refresh_at, work_city_code),
    ADD KEY idx_job_post_status_salary (status, salary_min, salary_max),
    ADD KEY idx_job_post_status_hot (status, apply_count, view_count, published_at);

ALTER TABLE job_application
    ADD KEY idx_job_application_candidate_stage_time (candidate_user_id, process_stage, latest_status_at),
    ADD KEY idx_job_application_company_job_stage (company_id, job_id, process_stage, latest_status_at),
    ADD KEY idx_job_application_recruiter_stage_time (recruiter_user_id, process_stage, latest_status_at);

ALTER TABLE resume_base
    ADD KEY idx_resume_base_user_status_updated (user_id, resume_status, updated_at),
    ADD KEY idx_resume_base_expectation_city_position (expectation_city, expectation_position);

ALTER TABLE interview_record
    ADD KEY idx_interview_record_application_status (application_id, interview_status),
    ADD KEY idx_interview_record_coordinator_status (coordinator_user_id, interview_status, scheduled_start_at);

ALTER TABLE notify_user_message
    ADD KEY idx_notify_user_message_receiver_delete_read (receiver_user_id, delete_flag, read_status, created_at);

ALTER TABLE company_member
    ADD KEY idx_company_member_company_status_type (company_id, member_status, member_type);