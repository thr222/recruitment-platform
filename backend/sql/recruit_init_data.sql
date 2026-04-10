USE recruit_platform;

INSERT INTO sys_role (id, role_code, role_name, role_status) VALUES
(1, 'CANDIDATE', '求职者', 'ENABLED'),
(2, 'RECRUITER', '招聘者', 'ENABLED'),
(3, 'ADMIN', '管理员', 'ENABLED');

INSERT INTO sys_user_account (
    id, username, password_hash, mobile, email, real_name, avatar_url, gender, register_source, account_status, last_login_at, last_login_ip
) VALUES
(1, 'candidate_demo', '$2a$10$demo', '13800138000', 'candidate_demo@example.com', '张同学', '/static/avatar/candidate-demo.png', 1, 'WEB', 'ENABLED', NOW(), '127.0.0.1'),
(2, 'recruiter_demo', '$2a$10$demo', '13900139000', 'recruiter_demo@example.com', '李招聘', '/static/avatar/recruiter-demo.png', 1, 'WEB', 'ENABLED', NOW(), '127.0.0.1'),
(3, 'admin_demo', '$2a$10$demo', '13700137000', 'admin_demo@example.com', '平台管理员', '/static/avatar/admin-demo.png', 1, 'ADMIN', 'ENABLED', NOW(), '127.0.0.1');

INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO candidate_profile (
    id, user_id, nickname, birthday, current_city, hometown_city, highest_education, work_years, current_status, summary, resume_completion, profile_status
) VALUES
(1, 1, '张同学', '1998-05-18', '310100', '320100', '本科', 3.0, 'OPEN_TO_OPPORTUNITY', '3年 Java 后端经验，熟悉招聘平台、权限体系和投递流程。', 88, 'ENABLED');

INSERT INTO company (
    id, company_name, company_short_name, unified_social_credit_code, company_size, financing_stage, industry_code,
    province_code, city_code, district_code, address_detail, official_website, introduction, logo_url,
    audit_status, business_status, audit_remark, approved_at, approved_by, created_by
) VALUES
(1, '星河科技有限公司', '星河科技', '91330100MA12345678', '100-499人', 'B轮', 'ENTERPRISE_SERVICE',
 '330000', '330100', '330106', '西湖区云栖小镇88号', 'https://www.example.com', '聚焦智能招聘与组织效能产品。', '/static/logo/xinghe.png',
 'APPROVED', 'ACTIVE', '资料完整，审核通过', NOW(), 3, 2),
(2, '云图人才科技有限公司', '云图人才', '91440300MA87654321', '20-99人', 'A轮', 'HR_SAAS',
 '440000', '440300', '440305', '南山区科苑路18号', 'https://www.example.org', '服务中大型企业招聘数字化。', '/static/logo/yuntu.png',
 'APPROVED', 'ACTIVE', '资料完整，审核通过', NOW(), 3, 2);

INSERT INTO company_license (
    id, company_id, license_type, license_no, holder_name, license_image_url, valid_from, valid_to, verify_status
) VALUES
(1, 1, 'BUSINESS_LICENSE', '91330100MA12345678', '星河科技有限公司', '/static/license/xinghe-license.png', '2020-01-01', '2030-12-31', 'PASSED'),
(2, 2, 'BUSINESS_LICENSE', '91440300MA87654321', '云图人才科技有限公司', '/static/license/yuntu-license.png', '2021-01-01', '2031-12-31', 'PASSED');

INSERT INTO company_member (
    id, company_id, user_id, member_type, job_title, member_status, joined_at, invited_by
) VALUES
(1, 1, 2, 'OWNER', '招聘负责人', 'ACTIVE', NOW(), 3);

INSERT INTO job_category (id, parent_id, category_code, category_name, category_level, sort_no, category_status) VALUES
(1, 0, 'TECH', '技术', 1, 1, 'ENABLED'),
(2, 1, 'JAVA_BACKEND', 'Java后端', 2, 1, 'ENABLED'),
(3, 0, 'PRODUCT', '产品', 1, 2, 'ENABLED');

INSERT INTO job_tag (id, tag_name, tag_type, tag_status) VALUES
(1, 'Spring Boot', 'TECH', 'ENABLED'),
(2, 'Redis', 'TECH', 'ENABLED'),
(3, '五险一金', 'BENEFIT', 'ENABLED'),
(4, '招聘SaaS', 'GENERAL', 'ENABLED');

INSERT INTO job_post (
    id, company_id, recruiter_user_id, category_id, title, employment_type, headcount, work_city_code, work_address,
    salary_min, salary_max, salary_months, experience_min, experience_max, education_requirement,
    job_highlights, description_text, status, audit_status, audit_remark, published_at, expire_at, last_refresh_at,
    view_count, favorite_count, apply_count
) VALUES
(1, 1, 2, 2, 'Java后端开发工程师', 'FULL_TIME', 3, '310100', '上海市浦东新区张江路100号',
 25, 40, 14, 3.0, 5.0, '本科',
 '核心业务,技术成长,双休', '负责招聘平台中的职位、投递、权限相关模块建设。', 'ONLINE', 'APPROVED', '审核通过', NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), NOW(),
 128, 16, 8),
(2, 2, 2, 3, '招聘产品经理', 'FULL_TIME', 2, '440300', '深圳市南山区软件产业基地',
 18, 28, 13, 3.0, 6.0, '本科',
 'B端产品,业务闭环,成长空间', '负责招聘产品流程设计、数据分析和平台治理。', 'ONLINE', 'APPROVED', '审核通过', NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), NOW(),
 96, 12, 5);

INSERT INTO job_post_tag_rel (job_id, tag_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 3);

INSERT INTO resume_base (
    id, user_id, resume_name, real_name, gender, birthday, mobile, email, degree, work_years,
    current_city, expectation_city, expectation_position, expectation_salary_min, expectation_salary_max,
    current_status, personal_summary, attachment_url, completion_score, default_flag, resume_status, last_completed_at
) VALUES
(1, 1, '默认在线简历', '张同学', 1, '1998-05-18', '13800138000', 'candidate_demo@example.com', '本科', 3.0,
 '上海', '上海', 'Java后端开发工程师', 25, 35,
 'OPEN_TO_OPPORTUNITY', '熟悉 Java、Spring Boot、MySQL、Redis，参与过招聘平台和中后台项目。', '/static/resume/candidate-demo.pdf', 88, 1, 'ACTIVE', NOW());

INSERT INTO resume_education (
    id, resume_id, school_name, college_name, degree, major_name, start_date, end_date, education_type, rank_no
) VALUES
(1, 1, '华东理工大学', '信息学院', '本科', '软件工程', '2016-09-01', '2020-06-30', 'FULL_TIME', 1);

INSERT INTO resume_work_experience (
    id, resume_id, company_name, department_name, position_name, start_date, end_date, currently_working, work_description, achievement_text, rank_no
) VALUES
(1, 1, '星河科技有限公司', '平台研发部', 'Java后端工程师', '2022-01-01', NULL, 1,
 '负责招聘平台认证、职位、投递等后端模块开发。',
 '完成权限体系改造，投递流程平均处理时长下降20%。', 1);

INSERT INTO resume_project_experience (
    id, resume_id, project_name, role_name, start_date, end_date, project_description, responsibility_text, project_result, rank_no
) VALUES
(1, 1, '企业招聘与求职平台', '后端负责人', '2025-01-01', '2025-08-30',
 '面向企业招聘与求职者投递的前后端分离平台。',
 '负责认证授权、职位管理、投递流程、通知系统设计与开发。',
 '完成模块化单体架构搭建并落地核心链路。', 1);

INSERT INTO job_application (
    id, application_no, job_id, company_id, candidate_user_id, recruiter_user_id, resume_id, source_type,
    status, process_stage, candidate_note, reject_reason, latest_status_at, viewed_at, applied_at
) VALUES
(1, 'APPLY202604080001', 1, 1, 1, 2, 1, 'DIRECT_APPLY',
 'INTERVIEWING', 'FIRST_INTERVIEW', '希望优先考虑后端平台方向岗位', NULL, NOW(), DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO application_status_log (
    id, application_id, from_status, to_status, process_stage, operator_user_id, operator_role, operation_type, remark, created_at
) VALUES
(1, 1, NULL, 'SUBMITTED', 'NEW', 1, 'CANDIDATE', 'STATUS_CHANGE', '候选人提交投递', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 1, 'SUBMITTED', 'VIEWED', 'FIRST_SCREEN', 2, 'RECRUITER', 'STATUS_CHANGE', '招聘者已查看简历', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 1, 'VIEWED', 'INTERVIEWING', 'FIRST_INTERVIEW', 2, 'RECRUITER', 'STATUS_CHANGE', '进入一面流程', NOW());

INSERT INTO interview_record (
    id, application_id, round_no, interview_type, interview_status, interviewer_user_id, coordinator_user_id,
    scheduled_start_at, scheduled_end_at, meeting_link, interview_address, feedback_score, feedback_result, feedback_text
) VALUES
(1, 1, 1, 'ONLINE', 'SCHEDULED', 2, 2,
 DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 1 DAY), INTERVAL 1 HOUR),
 'https://meeting.example.com/interview/1', NULL, NULL, NULL, NULL);

INSERT INTO notify_message (
    id, biz_type, biz_id, message_type, title, content, sender_user_id, priority_level, send_status, send_at
) VALUES
(1, 'APPLICATION', 1, 'PROCESS', '投递进度更新', '星河科技已将你的投递推进到面试中，请及时查看面试安排。', 2, 2, 'SENT', NOW()),
(2, 'INTERVIEW', 1, 'ALERT', '面试已安排', '你的一面已安排在明天，请准时参加。', 2, 3, 'SENT', NOW());

INSERT INTO notify_user_message (
    id, message_id, receiver_user_id, receiver_role, read_status, read_at, delete_flag
) VALUES
(1, 1, 1, 'CANDIDATE', 'UNREAD', NULL, 0),
(2, 2, 1, 'CANDIDATE', 'UNREAD', NULL, 0);