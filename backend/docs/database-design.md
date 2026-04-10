# 招聘系统数据库设计说明

## 设计思路

### 1. 用户与角色分离
- `sys_user_account` 只存放账号级信息，例如用户名、手机号、密码、账号状态。
- `sys_role` 与 `sys_user_role` 负责角色映射，方便一个账号未来支持多角色，例如同一人既是招聘者又是管理员。
- 求职者业务属性独立在 `candidate_profile`，避免和账号表混杂。

### 2. 企业信息拆分为主体、资质、成员
- `company` 存企业主体信息与审核状态。
- `company_license` 存证照与资质材料，方便后续扩展多种认证材料。
- `company_member` 负责企业与招聘者、面试官之间的组织关系。

### 3. 职位采用主表 + 分类 + 标签关系表
- `job_post` 作为职位主表，保存招聘业务核心字段与状态字段。
- `job_category` 用于职位类目树。
- `job_tag` + `job_post_tag_rel` 支持多标签灵活配置。

### 4. 简历采用主表 + 明细表拆分
- `resume_base` 存求职者核心简历信息和求职意向。
- 教育、工作、项目分别拆表，原因是这些信息天然是一对多结构，拆分后更利于增删改和排序。

### 5. 投递流程独立建模
- `job_application` 是招聘业务主交易表，记录投递关系、当前状态和当前流程阶段。
- `application_status_log` 记录完整状态轨迹，满足审计和流程追溯。
- `interview_record` 单独管理面试安排和反馈，不直接堆在投递表里。

### 6. 通知采用主消息表 + 用户收件箱表
- `notify_message` 存通知内容和业务上下文。
- `notify_user_message` 负责每个用户的读未读、删除状态，支持一条消息分发给多个接收人。

## 表关系说明

### 用户域
- `sys_user_account` 1 -> N `sys_user_role`
- `sys_role` 1 -> N `sys_user_role`
- `sys_user_account` 1 -> 1 `candidate_profile`

### 企业域
- `company` 1 -> N `company_license`
- `company` 1 -> N `company_member`
- `sys_user_account` 1 -> N `company_member`

### 职位域
- `company` 1 -> N `job_post`
- `sys_user_account` 1 -> N `job_post`，表示职位发布人
- `job_category` 1 -> N `job_post`
- `job_post` N -> N `job_tag`，通过 `job_post_tag_rel` 建立关系

### 简历域
- `sys_user_account` 1 -> N `resume_base`
- `resume_base` 1 -> N `resume_education`
- `resume_base` 1 -> N `resume_work_experience`
- `resume_base` 1 -> N `resume_project_experience`

### 招聘流程域
- `job_post` 1 -> N `job_application`
- `company` 1 -> N `job_application`
- `sys_user_account` 1 -> N `job_application`，作为求职者
- `resume_base` 1 -> N `job_application`
- `job_application` 1 -> N `application_status_log`
- `job_application` 1 -> N `interview_record`

### 通知域
- `notify_message` 1 -> N `notify_user_message`
- `sys_user_account` 1 -> N `notify_user_message`
- `notify_message.biz_type + biz_id` 可挂接 `job_application`、`interview_record`、`company` 等业务对象

## 核心状态字段

### 账号状态
- `sys_user_account.account_status`：`ENABLED`、`DISABLED`、`LOCKED`、`CANCELLED`

### 企业审核状态
- `company.audit_status`：`PENDING`、`APPROVED`、`REJECTED`

### 职位状态
- `job_post.status`：`DRAFT`、`PENDING_AUDIT`、`ONLINE`、`OFFLINE`、`REJECTED`、`CLOSED`

### 投递状态
- `job_application.status`：`SUBMITTED`、`VIEWED`、`SCREENING`、`INTERVIEWING`、`OFFER`、`PASSED`、`REJECTED`、`WITHDRAWN`、`CLOSED`

### 面试状态
- `interview_record.interview_status`：`PENDING`、`SCHEDULED`、`COMPLETED`、`CANCELLED`、`NO_SHOW`

### 通知读取状态
- `notify_user_message.read_status`：`UNREAD`、`READ`