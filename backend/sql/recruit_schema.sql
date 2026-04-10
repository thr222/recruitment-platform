CREATE DATABASE IF NOT EXISTS recruit_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE recruit_platform;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS notify_user_message;
DROP TABLE IF EXISTS notify_message;
DROP TABLE IF EXISTS interview_record;
DROP TABLE IF EXISTS application_status_log;
DROP TABLE IF EXISTS job_application;
DROP TABLE IF EXISTS resume_project_experience;
DROP TABLE IF EXISTS resume_work_experience;
DROP TABLE IF EXISTS resume_education;
DROP TABLE IF EXISTS resume_base;
DROP TABLE IF EXISTS job_post_tag_rel;
DROP TABLE IF EXISTS job_tag;
DROP TABLE IF EXISTS job_post;
DROP TABLE IF EXISTS job_category;
DROP TABLE IF EXISTS company_member;
DROP TABLE IF EXISTS company_license;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS candidate_profile;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user_account;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE sys_user_account (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(64) NOT NULL COMMENT '用户名，平台唯一',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    mobile VARCHAR(20) NOT NULL COMMENT '手机号',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    real_name VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    avatar_url VARCHAR(255) DEFAULT NULL COMMENT '头像地址',
    gender TINYINT NOT NULL DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    register_source VARCHAR(32) NOT NULL DEFAULT 'WEB' COMMENT '注册来源：WEB/ADMIN/IMPORT',
    account_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '账号状态：ENABLED/DISABLED/LOCKED/CANCELLED',
    last_login_at DATETIME DEFAULT NULL COMMENT '最后登录时间',
    last_login_ip VARCHAR(64) DEFAULT NULL COMMENT '最后登录IP',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0否 1是',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_account_username (username),
    UNIQUE KEY uk_sys_user_account_mobile (mobile),
    UNIQUE KEY uk_sys_user_account_email (email),
    KEY idx_sys_user_account_status (account_status),
    KEY idx_sys_user_account_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号表';

CREATE TABLE sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_code VARCHAR(32) NOT NULL COMMENT '角色编码：CANDIDATE/RECRUITER/ADMIN',
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '角色状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE sys_user_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_role_user_role (user_id, role_id),
    KEY idx_sys_user_role_role_id (role_id),
    CONSTRAINT fk_sys_user_role_user FOREIGN KEY (user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_sys_user_role_role FOREIGN KEY (role_id) REFERENCES sys_role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

CREATE TABLE candidate_profile (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '求职者档案ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    nickname VARCHAR(64) NOT NULL COMMENT '昵称',
    birthday DATE DEFAULT NULL COMMENT '生日',
    current_city VARCHAR(64) DEFAULT NULL COMMENT '当前所在城市',
    hometown_city VARCHAR(64) DEFAULT NULL COMMENT '籍贯城市',
    highest_education VARCHAR(32) DEFAULT NULL COMMENT '最高学历',
    work_years DECIMAL(4,1) NOT NULL DEFAULT 0.0 COMMENT '总工作年限',
    current_status VARCHAR(32) NOT NULL DEFAULT 'OPEN_TO_OPPORTUNITY' COMMENT '求职状态：OPEN_TO_OPPORTUNITY/NOT_LOOKING/IN_INTERVIEW',
    summary VARCHAR(2000) DEFAULT NULL COMMENT '个人简介',
    resume_completion TINYINT NOT NULL DEFAULT 0 COMMENT '简历完整度 0-100',
    profile_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '档案状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_candidate_profile_user_id (user_id),
    KEY idx_candidate_profile_city_status (current_city, current_status),
    CONSTRAINT fk_candidate_profile_user FOREIGN KEY (user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='求职者档案表';

CREATE TABLE company (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '企业ID',
    company_name VARCHAR(128) NOT NULL COMMENT '企业名称',
    company_short_name VARCHAR(64) DEFAULT NULL COMMENT '企业简称',
    unified_social_credit_code VARCHAR(32) NOT NULL COMMENT '统一社会信用代码',
    company_size VARCHAR(32) DEFAULT NULL COMMENT '公司规模',
    financing_stage VARCHAR(32) DEFAULT NULL COMMENT '融资阶段',
    industry_code VARCHAR(64) DEFAULT NULL COMMENT '行业编码',
    province_code VARCHAR(16) DEFAULT NULL COMMENT '省编码',
    city_code VARCHAR(16) DEFAULT NULL COMMENT '市编码',
    district_code VARCHAR(16) DEFAULT NULL COMMENT '区编码',
    address_detail VARCHAR(255) DEFAULT NULL COMMENT '详细地址',
    official_website VARCHAR(255) DEFAULT NULL COMMENT '官网',
    introduction TEXT COMMENT '企业介绍',
    logo_url VARCHAR(255) DEFAULT NULL COMMENT '企业Logo',
    audit_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '审核状态：PENDING/APPROVED/REJECTED',
    business_status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '经营状态：ACTIVE/CLOSED/FROZEN',
    audit_remark VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
    approved_at DATETIME DEFAULT NULL COMMENT '审核通过时间',
    approved_by BIGINT DEFAULT NULL COMMENT '审核人用户ID',
    created_by BIGINT DEFAULT NULL COMMENT '创建人用户ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_company_credit_code (unified_social_credit_code),
    KEY idx_company_audit_status (audit_status),
    KEY idx_company_city_status (city_code, business_status),
    KEY idx_company_created_by (created_by),
    CONSTRAINT fk_company_approved_by FOREIGN KEY (approved_by) REFERENCES sys_user_account (id),
    CONSTRAINT fk_company_created_by FOREIGN KEY (created_by) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业基础信息表';

CREATE TABLE company_license (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '营业执照记录ID',
    company_id BIGINT NOT NULL COMMENT '企业ID',
    license_type VARCHAR(32) NOT NULL DEFAULT 'BUSINESS_LICENSE' COMMENT '证照类型',
    license_no VARCHAR(64) DEFAULT NULL COMMENT '证照编号',
    holder_name VARCHAR(128) DEFAULT NULL COMMENT '持有人/企业名称',
    license_image_url VARCHAR(255) NOT NULL COMMENT '证照图片地址',
    valid_from DATE DEFAULT NULL COMMENT '有效期开始',
    valid_to DATE DEFAULT NULL COMMENT '有效期结束',
    verify_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '校验状态：PENDING/PASSED/FAILED',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_company_license_company_id (company_id),
    KEY idx_company_license_verify_status (verify_status),
    CONSTRAINT fk_company_license_company FOREIGN KEY (company_id) REFERENCES company (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业资质材料表';

CREATE TABLE company_member (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '企业成员ID',
    company_id BIGINT NOT NULL COMMENT '企业ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    member_type VARCHAR(32) NOT NULL DEFAULT 'RECRUITER' COMMENT '成员类型：OWNER/HR/INTERVIEWER',
    job_title VARCHAR(64) DEFAULT NULL COMMENT '企业内职位名称',
    member_status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '成员状态：ACTIVE/INACTIVE/REMOVED',
    joined_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    invited_by BIGINT DEFAULT NULL COMMENT '邀请人用户ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_company_member_company_user (company_id, user_id),
    KEY idx_company_member_user_id (user_id),
    KEY idx_company_member_status (member_status),
    CONSTRAINT fk_company_member_company FOREIGN KEY (company_id) REFERENCES company (id),
    CONSTRAINT fk_company_member_user FOREIGN KEY (user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_company_member_invited_by FOREIGN KEY (invited_by) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业成员关系表';

CREATE TABLE job_category (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位分类ID',
    parent_id BIGINT NOT NULL DEFAULT 0 COMMENT '父分类ID，0表示一级类目',
    category_code VARCHAR(64) NOT NULL COMMENT '分类编码',
    category_name VARCHAR(64) NOT NULL COMMENT '分类名称',
    category_level TINYINT NOT NULL DEFAULT 1 COMMENT '分类层级：1/2/3',
    sort_no INT NOT NULL DEFAULT 0 COMMENT '排序',
    category_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_job_category_code (category_code),
    KEY idx_job_category_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位分类表';

CREATE TABLE job_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '职位ID',
    company_id BIGINT NOT NULL COMMENT '企业ID',
    recruiter_user_id BIGINT NOT NULL COMMENT '发布人用户ID',
    category_id BIGINT NOT NULL COMMENT '职位分类ID',
    title VARCHAR(128) NOT NULL COMMENT '职位标题',
    employment_type VARCHAR(32) NOT NULL DEFAULT 'FULL_TIME' COMMENT '用工类型：FULL_TIME/PART_TIME/INTERN',
    headcount INT NOT NULL DEFAULT 1 COMMENT '招聘人数',
    work_city_code VARCHAR(16) NOT NULL COMMENT '工作城市编码',
    work_address VARCHAR(255) DEFAULT NULL COMMENT '工作地址',
    salary_min INT NOT NULL COMMENT '最低月薪，单位K或千元按业务约定存储',
    salary_max INT NOT NULL COMMENT '最高月薪',
    salary_months TINYINT NOT NULL DEFAULT 12 COMMENT '薪资月数',
    experience_min DECIMAL(4,1) NOT NULL DEFAULT 0.0 COMMENT '最低经验要求',
    experience_max DECIMAL(4,1) DEFAULT NULL COMMENT '最高经验要求',
    education_requirement VARCHAR(32) DEFAULT NULL COMMENT '学历要求',
    job_highlights VARCHAR(500) DEFAULT NULL COMMENT '职位亮点，逗号分隔或前端渲染文本',
    description_text MEDIUMTEXT NOT NULL COMMENT '职位描述',
    status VARCHAR(32) NOT NULL DEFAULT 'DRAFT' COMMENT '职位状态：DRAFT/PENDING_AUDIT/ONLINE/OFFLINE/REJECTED/CLOSED',
    audit_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '审核状态：PENDING/APPROVED/REJECTED',
    audit_remark VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
    published_at DATETIME DEFAULT NULL COMMENT '发布时间',
    expire_at DATETIME DEFAULT NULL COMMENT '到期时间',
    last_refresh_at DATETIME DEFAULT NULL COMMENT '最近刷新时间',
    view_count INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    favorite_count INT NOT NULL DEFAULT 0 COMMENT '收藏次数',
    apply_count INT NOT NULL DEFAULT 0 COMMENT '投递次数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_job_post_company_status (company_id, status),
    KEY idx_job_post_city_status (work_city_code, status),
    KEY idx_job_post_category_status (category_id, status),
    KEY idx_job_post_recruiter_user_id (recruiter_user_id),
    KEY idx_job_post_published_at (published_at),
    CONSTRAINT fk_job_post_company FOREIGN KEY (company_id) REFERENCES company (id),
    CONSTRAINT fk_job_post_recruiter_user FOREIGN KEY (recruiter_user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_job_post_category FOREIGN KEY (category_id) REFERENCES job_category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位发布表';

CREATE TABLE job_tag (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    tag_name VARCHAR(64) NOT NULL COMMENT '标签名称',
    tag_type VARCHAR(32) NOT NULL DEFAULT 'GENERAL' COMMENT '标签类型：GENERAL/BENEFIT/TECH',
    tag_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_job_tag_name (tag_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位标签表';

CREATE TABLE job_post_tag_rel (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    job_id BIGINT NOT NULL COMMENT '职位ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_job_post_tag_rel_job_tag (job_id, tag_id),
    KEY idx_job_post_tag_rel_tag_id (tag_id),
    CONSTRAINT fk_job_post_tag_rel_job FOREIGN KEY (job_id) REFERENCES job_post (id),
    CONSTRAINT fk_job_post_tag_rel_tag FOREIGN KEY (tag_id) REFERENCES job_tag (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位与标签关系表';

CREATE TABLE resume_base (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '简历ID',
    user_id BIGINT NOT NULL COMMENT '求职者用户ID',
    resume_name VARCHAR(128) NOT NULL COMMENT '简历名称',
    real_name VARCHAR(64) NOT NULL COMMENT '姓名',
    gender TINYINT NOT NULL DEFAULT 0 COMMENT '性别',
    birthday DATE DEFAULT NULL COMMENT '生日',
    mobile VARCHAR(20) NOT NULL COMMENT '手机号',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    degree VARCHAR(32) DEFAULT NULL COMMENT '最高学历',
    work_years DECIMAL(4,1) NOT NULL DEFAULT 0.0 COMMENT '总工作年限',
    current_city VARCHAR(64) DEFAULT NULL COMMENT '当前城市',
    expectation_city VARCHAR(64) DEFAULT NULL COMMENT '期望城市',
    expectation_position VARCHAR(128) DEFAULT NULL COMMENT '期望职位',
    expectation_salary_min INT DEFAULT NULL COMMENT '期望薪资下限',
    expectation_salary_max INT DEFAULT NULL COMMENT '期望薪资上限',
    current_status VARCHAR(32) NOT NULL DEFAULT 'OPEN_TO_OPPORTUNITY' COMMENT '当前求职状态',
    personal_summary VARCHAR(2000) DEFAULT NULL COMMENT '个人优势/简介',
    attachment_url VARCHAR(255) DEFAULT NULL COMMENT '附件简历地址',
    completion_score TINYINT NOT NULL DEFAULT 0 COMMENT '完整度分数 0-100',
    default_flag TINYINT NOT NULL DEFAULT 1 COMMENT '是否默认简历：0否 1是',
    resume_status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '简历状态：ACTIVE/INACTIVE/DELETED',
    last_completed_at DATETIME DEFAULT NULL COMMENT '最后一次完整更新',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_resume_base_user_id (user_id),
    KEY idx_resume_base_default_flag (user_id, default_flag),
    KEY idx_resume_base_status (resume_status),
    CONSTRAINT fk_resume_base_user FOREIGN KEY (user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历主表';

CREATE TABLE resume_education (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '教育经历ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    school_name VARCHAR(128) NOT NULL COMMENT '学校名称',
    college_name VARCHAR(128) DEFAULT NULL COMMENT '学院名称',
    degree VARCHAR(32) NOT NULL COMMENT '学历',
    major_name VARCHAR(128) DEFAULT NULL COMMENT '专业名称',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE DEFAULT NULL COMMENT '结束日期',
    education_type VARCHAR(32) NOT NULL DEFAULT 'FULL_TIME' COMMENT '教育类型：FULL_TIME/PART_TIME',
    rank_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_resume_education_resume_id (resume_id),
    CONSTRAINT fk_resume_education_resume FOREIGN KEY (resume_id) REFERENCES resume_base (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历教育经历表';

CREATE TABLE resume_work_experience (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工作经历ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    company_name VARCHAR(128) NOT NULL COMMENT '公司名称',
    department_name VARCHAR(128) DEFAULT NULL COMMENT '部门名称',
    position_name VARCHAR(128) NOT NULL COMMENT '职位名称',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE DEFAULT NULL COMMENT '结束日期，空表示至今',
    currently_working TINYINT NOT NULL DEFAULT 0 COMMENT '是否在职：0否 1是',
    work_description TEXT COMMENT '工作描述',
    achievement_text TEXT COMMENT '工作成果',
    rank_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_resume_work_experience_resume_id (resume_id),
    CONSTRAINT fk_resume_work_experience_resume FOREIGN KEY (resume_id) REFERENCES resume_base (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历工作经历表';

CREATE TABLE resume_project_experience (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目经历ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    project_name VARCHAR(128) NOT NULL COMMENT '项目名称',
    role_name VARCHAR(128) DEFAULT NULL COMMENT '项目角色',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE DEFAULT NULL COMMENT '结束日期',
    project_description TEXT COMMENT '项目描述',
    responsibility_text TEXT COMMENT '职责说明',
    project_result TEXT COMMENT '项目成果',
    rank_no INT NOT NULL DEFAULT 0 COMMENT '排序号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_resume_project_experience_resume_id (resume_id),
    CONSTRAINT fk_resume_project_experience_resume FOREIGN KEY (resume_id) REFERENCES resume_base (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历项目经历表';

CREATE TABLE job_application (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '投递ID',
    application_no VARCHAR(64) NOT NULL COMMENT '投递单号，业务唯一',
    job_id BIGINT NOT NULL COMMENT '职位ID',
    company_id BIGINT NOT NULL COMMENT '企业ID',
    candidate_user_id BIGINT NOT NULL COMMENT '求职者用户ID',
    recruiter_user_id BIGINT DEFAULT NULL COMMENT '当前负责招聘者用户ID',
    resume_id BIGINT NOT NULL COMMENT '投递使用的简历ID',
    source_type VARCHAR(32) NOT NULL DEFAULT 'DIRECT_APPLY' COMMENT '投递来源：DIRECT_APPLY/RECOMMEND/INVITED',
    status VARCHAR(32) NOT NULL DEFAULT 'SUBMITTED' COMMENT '投递状态：SUBMITTED/VIEWED/SCREENING/INTERVIEWING/OFFER/PASSED/REJECTED/WITHDRAWN/CLOSED',
    process_stage VARCHAR(32) NOT NULL DEFAULT 'NEW' COMMENT '流程阶段：NEW/FIRST_SCREEN/FIRST_INTERVIEW/SECOND_INTERVIEW/HR_INTERVIEW/OFFER/CLOSED',
    candidate_note VARCHAR(255) DEFAULT NULL COMMENT '候选人备注',
    reject_reason VARCHAR(255) DEFAULT NULL COMMENT '淘汰原因',
    latest_status_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近状态变更时间',
    viewed_at DATETIME DEFAULT NULL COMMENT '首次查看时间',
    applied_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投递时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_job_application_no (application_no),
    UNIQUE KEY uk_job_application_job_candidate (job_id, candidate_user_id),
    KEY idx_job_application_candidate_status (candidate_user_id, status),
    KEY idx_job_application_company_status (company_id, status),
    KEY idx_job_application_recruiter_status (recruiter_user_id, status),
    KEY idx_job_application_applied_at (applied_at),
    CONSTRAINT fk_job_application_job FOREIGN KEY (job_id) REFERENCES job_post (id),
    CONSTRAINT fk_job_application_company FOREIGN KEY (company_id) REFERENCES company (id),
    CONSTRAINT fk_job_application_candidate_user FOREIGN KEY (candidate_user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_job_application_recruiter_user FOREIGN KEY (recruiter_user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_job_application_resume FOREIGN KEY (resume_id) REFERENCES resume_base (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位投递记录表';

CREATE TABLE application_status_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '状态日志ID',
    application_id BIGINT NOT NULL COMMENT '投递ID',
    from_status VARCHAR(32) DEFAULT NULL COMMENT '变更前状态',
    to_status VARCHAR(32) NOT NULL COMMENT '变更后状态',
    process_stage VARCHAR(32) DEFAULT NULL COMMENT '流程阶段',
    operator_user_id BIGINT NOT NULL COMMENT '操作人用户ID',
    operator_role VARCHAR(32) NOT NULL COMMENT '操作人角色',
    operation_type VARCHAR(32) NOT NULL DEFAULT 'STATUS_CHANGE' COMMENT '操作类型：STATUS_CHANGE/ASSIGN/REJECT/WITHDRAW/NOTE',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_application_status_log_application_id (application_id),
    KEY idx_application_status_log_operator_user_id (operator_user_id),
    KEY idx_application_status_log_created_at (created_at),
    CONSTRAINT fk_application_status_log_application FOREIGN KEY (application_id) REFERENCES job_application (id),
    CONSTRAINT fk_application_status_log_operator_user FOREIGN KEY (operator_user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投递状态变更日志表';

CREATE TABLE interview_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '面试记录ID',
    application_id BIGINT NOT NULL COMMENT '投递ID',
    round_no TINYINT NOT NULL DEFAULT 1 COMMENT '面试轮次',
    interview_type VARCHAR(32) NOT NULL DEFAULT 'ONLINE' COMMENT '面试类型：ONLINE/OFFLINE/PHONE',
    interview_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '面试状态：PENDING/SCHEDULED/COMPLETED/CANCELLED/NO_SHOW',
    interviewer_user_id BIGINT DEFAULT NULL COMMENT '面试官用户ID',
    coordinator_user_id BIGINT DEFAULT NULL COMMENT '协调人用户ID',
    scheduled_start_at DATETIME DEFAULT NULL COMMENT '计划开始时间',
    scheduled_end_at DATETIME DEFAULT NULL COMMENT '计划结束时间',
    meeting_link VARCHAR(255) DEFAULT NULL COMMENT '线上会议链接',
    interview_address VARCHAR(255) DEFAULT NULL COMMENT '线下面试地址',
    feedback_score TINYINT DEFAULT NULL COMMENT '评分 1-10',
    feedback_result VARCHAR(32) DEFAULT NULL COMMENT '结果：PASS/REJECT/PENDING',
    feedback_text TEXT COMMENT '面试反馈',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_interview_record_application_round (application_id, round_no),
    KEY idx_interview_record_interviewer_user_id (interviewer_user_id),
    KEY idx_interview_record_status_start (interview_status, scheduled_start_at),
    CONSTRAINT fk_interview_record_application FOREIGN KEY (application_id) REFERENCES job_application (id),
    CONSTRAINT fk_interview_record_interviewer_user FOREIGN KEY (interviewer_user_id) REFERENCES sys_user_account (id),
    CONSTRAINT fk_interview_record_coordinator_user FOREIGN KEY (coordinator_user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='面试记录表';

CREATE TABLE notify_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知消息ID',
    biz_type VARCHAR(32) NOT NULL COMMENT '业务类型：APPLICATION/INTERVIEW/COMPANY_AUDIT/SYSTEM',
    biz_id BIGINT DEFAULT NULL COMMENT '业务ID，例如 application_id',
    message_type VARCHAR(32) NOT NULL COMMENT '消息类型：SYSTEM/PROCESS/ALERT',
    title VARCHAR(128) NOT NULL COMMENT '通知标题',
    content VARCHAR(1000) NOT NULL COMMENT '通知正文',
    sender_user_id BIGINT DEFAULT NULL COMMENT '发送人用户ID，系统消息可为空',
    priority_level TINYINT NOT NULL DEFAULT 1 COMMENT '优先级：1普通 2重要 3紧急',
    send_status VARCHAR(32) NOT NULL DEFAULT 'SENT' COMMENT '发送状态：DRAFT/SENT/CANCELLED',
    send_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_notify_message_biz_type_biz_id (biz_type, biz_id),
    KEY idx_notify_message_send_at (send_at),
    CONSTRAINT fk_notify_message_sender_user FOREIGN KEY (sender_user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知消息主表';

CREATE TABLE notify_user_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户通知关系ID',
    message_id BIGINT NOT NULL COMMENT '通知消息ID',
    receiver_user_id BIGINT NOT NULL COMMENT '接收人用户ID',
    receiver_role VARCHAR(32) NOT NULL COMMENT '接收人角色',
    read_status VARCHAR(32) NOT NULL DEFAULT 'UNREAD' COMMENT '读取状态：UNREAD/READ',
    read_at DATETIME DEFAULT NULL COMMENT '阅读时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0否 1是',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_notify_user_message_msg_receiver (message_id, receiver_user_id),
    KEY idx_notify_user_message_receiver_read (receiver_user_id, read_status),
    KEY idx_notify_user_message_created_at (created_at),
    CONSTRAINT fk_notify_user_message_message FOREIGN KEY (message_id) REFERENCES notify_message (id),
    CONSTRAINT fk_notify_user_message_receiver_user FOREIGN KEY (receiver_user_id) REFERENCES sys_user_account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户通知收件箱表';