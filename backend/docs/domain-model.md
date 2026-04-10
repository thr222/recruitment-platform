# Domain Model

## 核心聚合

### 用户与认证
- `sys_user_account`：账号主表
- `candidate_profile`：求职者档案
- `recruiter_profile`：招聘者档案
- `sys_role` / `sys_permission`：权限模型

### 企业与职位
- `company`：企业主体信息
- `company_member`：企业成员与 HR 关系
- `job_post`：职位主表
- `job_post_tag`：职位标签关系

### 简历与投递
- `resume_base`：简历主档
- `resume_education` / `resume_work_exp` / `resume_project_exp`：简历明细
- `job_application`：投递主表
- `application_status_log`：投递状态日志
- `interview_record`：面试记录

### 运营与治理
- `sys_banner`：首页推荐位
- `sys_dict_type` / `sys_dict_data`：业务字典
- `sys_report`：举报与违规处理

## 状态流转建议

### 企业审核状态
- `PENDING`
- `APPROVED`
- `REJECTED`

### 职位状态
- `DRAFT`
- `PENDING_AUDIT`
- `ONLINE`
- `OFFLINE`
- `REJECTED`

### 投递流程状态
- `SUBMITTED`
- `VIEWED`
- `SCREENING`
- `INTERVIEWING`
- `OFFER`
- `REJECTED`
- `CLOSED`
