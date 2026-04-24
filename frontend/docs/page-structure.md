# Recruitment Platform Frontend Page Structure

本文档用于指导 Vue 前端组件拆分。每个页面按「页面名称 / 页面路径 / 页面模块 / 模块数据」组织，后续可直接将模块抽成 `components/business` 或页面内局部组件。

## 公共端

### 首页

路径：`/`

模块：

- `Navbar`：Logo、Jobs、Companies、登录、注册、CTA。
- `HeroSection`：主标题、副标题、CTA 按钮、关键词浮动卡片。
- `HeroJobCards`：推荐职位小卡片。

模块数据：

- `Navbar`：导航文案、路由地址、当前选中项、登录状态。
- `HeroSection`：标题、副标题、CTA 文案、CTA 跳转地址、浮动关键词。
- `HeroJobCards`：职位名、公司名、薪资、城市、工作方式、标签。

### 职位列表页

路径：`/jobs`

模块：

- `JobSearchBar`：关键词搜索。
- `JobFilterPanel`：城市、薪资区间、工作经验、学历、工作性质筛选。
- `JobSortBar`：最新、最相关排序。
- `JobCardList`：职位卡片列表。
- `JobCard`：单个职位展示。
- `PaginationBar`：分页。
- `EmptyState`：无结果提示。

模块数据：

- `JobSearchBar`：`keyword`。
- `JobFilterPanel`：`city`、`salaryRange`、`experienceRange`、`education`、`workType`。
- `JobSortBar`：`sortBy`。
- `JobCard`：`jobId`、`title`、`companyName`、`salaryMin`、`salaryMax`、`city`、`experience`、`education`、`workType`、`tags`、`publishedAt`、`companyLogo`。
- `PaginationBar`：`pageNo`、`pageSize`、`total`。

### 职位详情页

路径：`/jobs/:jobId`

模块：

- `JobDetailHeader`：职位名称、薪资、公司、城市、经验、学历。
- `JobActionPanel`：投递按钮、收藏按钮、投递状态。
- `ApplicationTimeline`：投递状态流转。
- `JobDescriptionSection`：职位描述。
- `JobRequirementSection`：任职要求。
- `JobBenefitsSection`：福利标签。
- `CompanySummaryCard`：公司概要。
- `SimilarJobList`：相似职位推荐。

模块数据：

- `JobDetailHeader`：`title`、`salaryMin`、`salaryMax`、`companyName`、`city`、`experience`、`education`、`workType`。
- `JobActionPanel`：`isApplied`、`isFavorited`、`applicationStatus`。
- `ApplicationTimeline`：`status`、`time`、`description`。
- `JobDescriptionSection`：`description`。
- `JobRequirementSection`：`requirements[]`。
- `JobBenefitsSection`：`benefits[]`。
- `CompanySummaryCard`：`companyId`、`companyName`、`industry`、`size`、`stage`、`location`、`summary`。
- `SimilarJobList`：`jobId`、`title`、`companyName`、`salary`、`city`、`tags`。

### 公司列表页

路径：`/companies`

模块：

- `CompanySearchBar`：公司关键词搜索。
- `CompanyFilterPanel`：行业、城市、规模、融资阶段筛选。
- `CompanyCardList`：公司卡片列表。
- `CompanyCard`：公司概要。

模块数据：

- `CompanySearchBar`：`keyword`。
- `CompanyFilterPanel`：`industry`、`city`、`companySize`、`financingStage`。
- `CompanyCard`：`companyId`、`companyName`、`logo`、`industry`、`size`、`city`、`stage`、`activeJobCount`、`tags`、`summary`。

### 公司详情页

路径：`/companies/:companyId`

模块：

- `CompanyHero`：公司名称、Logo、行业、规模、地点。
- `CompanyProfileSection`：公司介绍。
- `CompanyBenefitsSection`：福利与亮点。
- `CompanyJobList`：在招职位。
- `CompanySidebar`：公司档案、官网、联系人。

模块数据：

- `CompanyHero`：`companyName`、`logo`、`industry`、`size`、`city`、`stage`。
- `CompanyProfileSection`：`description`、`values[]`。
- `CompanyBenefitsSection`：`benefits[]`、`highlights[]`。
- `CompanyJobList`：`jobId`、`title`、`salary`、`city`、`experience`、`tags`。
- `CompanySidebar`：`website`、`address`、`contactName`、`contactEmail`。

### 登录注册页

路径：`/login`

模块：

- `AuthTabs`：登录 / 注册切换。
- `LoginForm`：账号密码登录。
- `RegisterForm`：角色选择、基础注册信息。
- `RoleEntryPanel`：求职者 / 招聘方 / 管理员入口说明。

模块数据：

- `LoginForm`：`username`、`password`、`rememberMe`。
- `RegisterForm`：`role`、`name`、`phone`、`email`、`password`。
- `RoleEntryPanel`：`roleName`、`description`、`defaultRoute`。

## 求职者端

### 求职者工作台

路径：`/candidate/dashboard`

模块：

- `CandidateOverviewCards`：投递、面试、收藏、简历完整度。
- `RecommendedJobList`：推荐职位。
- `ApplicationProgressList`：近期投递进度。
- `ResumeHealthCard`：简历完善建议。
- `MessagePreviewList`：消息提醒。

模块数据：

- `CandidateOverviewCards`：`applicationCount`、`interviewCount`、`favoriteCount`、`resumeCompletion`。
- `RecommendedJobList`：`jobId`、`title`、`companyName`、`salary`、`city`、`matchRate`。
- `ApplicationProgressList`：`applicationId`、`jobTitle`、`companyName`、`status`、`updatedAt`。
- `ResumeHealthCard`：`completion`、`missingItems[]`、`suggestions[]`。
- `MessagePreviewList`：`messageId`、`senderName`、`content`、`createdAt`、`unread`。

### 我的简历

路径：`/candidate/resume`

模块：

- `ResumeBasicInfo`：基本信息。
- `ResumeEducationList`：教育经历。
- `ResumeExperienceList`：工作 / 实习经历。
- `ResumeProjectList`：项目经历。
- `ResumeSkillTags`：技能标签。
- `ResumeAttachmentUploader`：附件简历上传。
- `ResumePreviewPanel`：简历预览。

模块数据：

- `ResumeBasicInfo`：`name`、`gender`、`phone`、`email`、`city`、`jobIntention`。
- `ResumeEducationList`：`school`、`major`、`degree`、`startDate`、`endDate`。
- `ResumeExperienceList`：`companyName`、`position`、`startDate`、`endDate`、`description`。
- `ResumeProjectList`：`projectName`、`role`、`description`、`result`。
- `ResumeSkillTags`：`skills[]`。
- `ResumeAttachmentUploader`：`fileName`、`fileUrl`、`uploadedAt`。

### 我的投递

路径：`/candidate/applications`

模块：

- `ApplicationStats`：投递统计。
- `ApplicationFilterBar`：状态筛选、关键词。
- `ApplicationList`：投递记录列表。
- `ApplicationCard`：单条投递记录。

模块数据：

- `ApplicationStats`：`total`、`interviewing`、`rejected`、`offered`。
- `ApplicationFilterBar`：`keyword`、`status`、`dateRange`。
- `ApplicationCard`：`applicationId`、`jobTitle`、`companyName`、`salary`、`city`、`status`、`stageText`、`updatedAt`。

### 投递详情

路径：`/candidate/applications/:applicationId`

模块：

- `ApplicationJobSummary`：职位概要。
- `ApplicationStatusPanel`：当前投递状态。
- `ApplicationTimeline`：投递状态时间线。
- `ApplicationNextStepCard`：下一步建议。
- `ApplicationActions`：查看沟通、更新简历、撤回投递。

模块数据：

- `ApplicationJobSummary`：`jobTitle`、`companyName`、`salary`、`city`、`experience`、`education`、`tags`。
- `ApplicationStatusPanel`：`status`、`progress`、`lastUpdatedAt`。
- `ApplicationTimeline`：`status`、`time`、`description`。
- `ApplicationNextStepCard`：`suggestions[]`。

### 收藏职位

路径：`/candidate/favorites`

模块：

- `FavoriteFilterBar`：关键词、城市、状态筛选。
- `FavoriteJobList`：收藏职位列表。
- `FavoriteJobCard`：收藏职位卡片。

模块数据：

- `FavoriteFilterBar`：`keyword`、`city`、`status`。
- `FavoriteJobCard`：`jobId`、`title`、`companyName`、`salary`、`city`、`tags`、`favoritedAt`、`isOnline`。

### 面试邀请

路径：`/candidate/interviews`

模块：

- `InterviewFilterBar`：面试状态筛选。
- `InterviewList`：面试邀请列表。
- `InterviewCard`：面试信息和操作。

模块数据：

- `InterviewFilterBar`：`status`。
- `InterviewCard`：`interviewId`、`jobTitle`、`companyName`、`time`、`type`、`interviewer`、`status`、`meetingLink`。

### 消息通知

路径：`/candidate/messages`

模块：

- `ConversationList`：会话列表。
- `MessageThread`：消息详情。
- `MessageComposer`：回复输入框。

模块数据：

- `ConversationList`：`conversationId`、`senderName`、`companyName`、`lastMessage`、`lastAt`、`unreadCount`。
- `MessageThread`：`messageId`、`senderType`、`content`、`createdAt`。
- `MessageComposer`：`content`、`attachments[]`。

### 求职偏好

路径：`/candidate/preferences`

模块：

- `PreferenceKeywordEditor`：目标岗位关键词。
- `PreferenceLocationEditor`：期望城市。
- `PreferenceSalaryEditor`：期望薪资。
- `PreferenceWorkTypeEditor`：工作性质。
- `PreferenceNoticeSetting`：推荐通知开关。

模块数据：

- `PreferenceKeywordEditor`：`keywords[]`。
- `PreferenceLocationEditor`：`cities[]`。
- `PreferenceSalaryEditor`：`salaryRange`。
- `PreferenceWorkTypeEditor`：`workTypes[]`。
- `PreferenceNoticeSetting`：`noticeEnabled`。

### 个人中心

路径：`/candidate/profile`

模块：

- `AccountInfoForm`：账号基础信息。
- `SecuritySettingPanel`：密码、手机号、邮箱。
- `PrivacySettingPanel`：简历可见性。

模块数据：

- `AccountInfoForm`：`name`、`avatar`、`phone`、`email`、`city`。
- `SecuritySettingPanel`：`hasPassword`、`phoneVerified`、`emailVerified`。
- `PrivacySettingPanel`：`resumeVisible`、`allowRecruiterContact`。

## 招聘方端

### 招聘工作台

路径：`/recruiter/dashboard`

模块：

- `RecruiterMetricCards`：在招职位、简历数、待处理、今日浏览。
- `RecentApplicationFeed`：最近投递动态。
- `RecruitPipelineChart`：招聘漏斗。
- `PriorityCandidateList`：待处理候选人。
- `QuickActionPanel`：发布职位、查看候选人。

模块数据：

- `RecruiterMetricCards`：`activeJobCount`、`resumeCount`、`pendingCandidateCount`、`todayViewCount`。
- `RecentApplicationFeed`：`candidateName`、`jobTitle`、`appliedAt`、`status`。
- `RecruitPipelineChart`：`stage`、`count`、`percentage`。
- `PriorityCandidateList`：`candidateId`、`name`、`jobTitle`、`matchRate`、`status`。

### 企业认证

路径：`/recruiter/certification`

模块：

- `CertificationStatusCard`：认证状态。
- `CompanyCertificationForm`：企业认证表单。
- `LicenseUploader`：营业执照上传。

模块数据：

- `CertificationStatusCard`：`auditStatus`、`submittedAt`、`auditReason`。
- `CompanyCertificationForm`：`companyName`、`licenseNo`、`contactName`、`contactPhone`、`address`。
- `LicenseUploader`：`licenseFileUrl`、`fileName`。

### 公司主页管理

路径：`/recruiter/company`

模块：

- `CompanyBasicForm`：公司基本资料。
- `CompanyIntroEditor`：公司介绍。
- `CompanyBenefitEditor`：福利标签。
- `CompanyMediaUploader`：Logo、图片上传。
- `CompanyPreviewPanel`：公司主页预览。

模块数据：

- `CompanyBasicForm`：`companyName`、`industry`、`size`、`stage`、`city`、`website`。
- `CompanyIntroEditor`：`description`、`values[]`。
- `CompanyBenefitEditor`：`benefits[]`、`highlights[]`。
- `CompanyMediaUploader`：`logoUrl`、`coverUrl`、`gallery[]`。

### 职位管理

路径：`/recruiter/jobs`

模块：

- `JobManageStats`：职位统计。
- `JobManageFilterBar`：关键词、状态筛选。
- `RecruiterJobCardList`：职位卡片列表。
- `RecruiterJobCard`：职位状态和操作。
- `JobEditDialog`：新建 / 编辑职位。
- `JobAnalyticsDrawer`：职位浏览数据。

模块数据：

- `JobManageStats`：`total`、`active`、`auditing`、`closed`、`weeklyApplications`。
- `JobManageFilterBar`：`keyword`、`status`。
- `RecruiterJobCard`：`jobId`、`title`、`salary`、`city`、`experience`、`education`、`workType`、`status`、`applicantCount`、`viewCount`、`tags`。
- `JobEditDialog`：`title`、`salary`、`city`、`experience`、`education`、`workType`、`tags`、`description`。
- `JobAnalyticsDrawer`：`viewCount`、`todayViewCount`、`applicantCount`、`conversionRate`。

### 职位数据

路径：`/recruiter/jobs/:jobId/insights`

模块：

- `JobInsightHeader`：职位名称和状态。
- `JobInsightMetricCards`：曝光、浏览、投递、转化率。
- `TrafficSourceChart`：流量来源。
- `JobOptimizationTips`：优化建议。

模块数据：

- `JobInsightHeader`：`jobId`、`title`、`status`。
- `JobInsightMetricCards`：`impressionCount`、`viewCount`、`applicationCount`、`conversionRate`。
- `TrafficSourceChart`：`sourceName`、`percentage`。
- `JobOptimizationTips`：`tips[]`。

### 候选人管理

路径：`/recruiter/candidates`

模块：

- `CandidateFilterBar`：职位、状态、关键词筛选。
- `CandidatePipelineStats`：候选人状态统计。
- `CandidateCardList`：候选人列表。
- `CandidatePreviewPanel`：简历预览。
- `InterviewInviteDialog`：面试邀请弹窗。

模块数据：

- `CandidateFilterBar`：`jobId`、`status`、`keyword`。
- `CandidatePipelineStats`：`newCount`、`viewedCount`、`passedCount`、`interviewingCount`。
- `CandidateCardList`：`candidateId`、`name`、`jobTitle`、`city`、`experience`、`education`、`appliedAt`、`status`、`matchRate`、`tags`、`resumeSummary`。
- `CandidatePreviewPanel`：`name`、`jobTitle`、`resumeSummary`、`note`、`history[]`。
- `InterviewInviteDialog`：`interviewTime`、`interviewer`、`interviewType`。

### 候选人详情

路径：`/recruiter/candidates/:candidateId`

模块：

- `CandidateDetailHeader`：候选人概要。
- `ResumeSummarySection`：简历摘要。
- `CandidateProjectSection`：项目经历。
- `CandidateStageActions`：状态操作。
- `RecruiterNoteEditor`：招聘备注。

模块数据：

- `CandidateDetailHeader`：`candidateId`、`name`、`jobTitle`、`city`、`experience`、`education`、`matchRate`、`status`。
- `ResumeSummarySection`：`summary`、`skills[]`。
- `CandidateProjectSection`：`projectName`、`role`、`description`、`result`。
- `CandidateStageActions`：`availableActions[]`、`nextStatus`。
- `RecruiterNoteEditor`：`note`。

### 面试邀请管理

路径：`/recruiter/interviews`

模块：

- `InterviewCalendar`：面试日程。
- `InterviewList`：面试列表。
- `InterviewCard`：单场面试。
- `InterviewFeedbackDialog`：面试反馈。

模块数据：

- `InterviewCalendar`：`date`、`interviewCount`。
- `InterviewCard`：`interviewId`、`candidateName`、`jobTitle`、`time`、`type`、`interviewer`、`status`。
- `InterviewFeedbackDialog`：`result`、`feedback`、`nextStep`。

### 招聘方消息中心

路径：`/recruiter/messages`

模块：

- `RecruiterConversationList`：候选人会话。
- `RecruiterMessageThread`：消息详情。
- `CandidateContextCard`：候选人上下文。

模块数据：

- `RecruiterConversationList`：`conversationId`、`candidateName`、`jobTitle`、`lastMessage`、`lastAt`、`unreadCount`。
- `RecruiterMessageThread`：`messageId`、`senderType`、`content`、`createdAt`。
- `CandidateContextCard`：`candidateId`、`status`、`resumeSummary`、`matchRate`。

## 管理员端

### 运营看板

路径：`/admin/dashboard`

模块：

- `AdminMetricCards`：用户、企业、职位、投递数据。
- `PlatformTrendChart`：平台趋势。
- `AuditQueueOverview`：审核队列。
- `RiskAlertList`：风险提醒。

模块数据：

- `AdminMetricCards`：`userCount`、`companyCount`、`jobCount`、`applicationCount`。
- `PlatformTrendChart`：`date`、`newUsers`、`newJobs`、`applications`。
- `AuditQueueOverview`：`companyAuditCount`、`jobAuditCount`、`reportCount`。
- `RiskAlertList`：`riskId`、`type`、`title`、`level`、`createdAt`。

### 用户管理

路径：`/admin/users`

模块：

- `UserFilterBar`：角色、状态、关键词筛选。
- `UserTable`：用户列表。
- `UserDetailDrawer`：用户详情。
- `UserActionPanel`：启用、禁用、重置密码。

模块数据：

- `UserFilterBar`：`role`、`status`、`keyword`。
- `UserTable`：`userId`、`name`、`role`、`phone`、`email`、`status`、`createdAt`。
- `UserDetailDrawer`：`profile`、`loginHistory[]`、`riskRecords[]`。

### 企业审核

路径：`/admin/company-audit`

模块：

- `CompanyAuditFilterBar`：审核状态、关键词。
- `CompanyAuditTable`：企业审核列表。
- `CompanyAuditDetailDrawer`：企业资料详情。
- `AuditActionPanel`：通过、驳回。

模块数据：

- `CompanyAuditFilterBar`：`status`、`keyword`。
- `CompanyAuditTable`：`companyId`、`companyName`、`licenseNo`、`submitter`、`status`、`submittedAt`。
- `CompanyAuditDetailDrawer`：`companyName`、`licenseNo`、`licenseFileUrl`、`contactName`、`contactPhone`、`address`。
- `AuditActionPanel`：`auditResult`、`auditReason`。

### 职位审核

路径：`/admin/job-audit`

模块：

- `JobAuditFilterBar`：状态、城市、关键词。
- `JobAuditTable`：职位审核列表。
- `JobAuditDetailDrawer`：职位详情。
- `JobRiskTags`：敏感词 / 风险提示。
- `AuditActionPanel`：通过、驳回。

模块数据：

- `JobAuditFilterBar`：`status`、`city`、`keyword`。
- `JobAuditTable`：`jobId`、`title`、`companyName`、`salary`、`city`、`status`、`submittedAt`。
- `JobAuditDetailDrawer`：`description`、`requirements`、`benefits`。
- `JobRiskTags`：`riskTags[]`。
- `AuditActionPanel`：`auditResult`、`auditReason`。

### 举报处理

路径：`/admin/reports`

模块：

- `ReportFilterBar`：举报类型、处理状态、风险等级。
- `ReportTable`：举报列表。
- `ReportDetailDrawer`：举报详情。
- `ReportActionPanel`：忽略、下架、封禁、转人工复核。

模块数据：

- `ReportFilterBar`：`reportType`、`status`、`riskLevel`。
- `ReportTable`：`reportId`、`targetType`、`targetName`、`reason`、`reporter`、`status`、`createdAt`。
- `ReportDetailDrawer`：`description`、`evidenceUrls[]`、`targetSnapshot`。
- `ReportActionPanel`：`action`、`remark`。

### 运营配置

路径：`/admin/operation-config`

模块：

- `AuditRuleConfig`：审核规则开关。
- `RecommendConfig`：推荐数量配置。
- `HotKeywordConfig`：热门搜索词配置。
- `RiskControlConfig`：风控配置。

模块数据：

- `AuditRuleConfig`：`enableCompanyAudit`、`enableJobAudit`。
- `RecommendConfig`：`homeRecommendCount`、`jobRecommendCount`。
- `HotKeywordConfig`：`keywords`。
- `RiskControlConfig`：`enableReportAutoHide`、`riskThreshold`。

### 字典配置

路径：`/admin/dict`

模块：

- `DictCategoryList`：字典分类。
- `DictItemTable`：字典项。
- `DictEditDialog`：新增 / 编辑字典。

模块数据：

- `DictCategoryList`：`categoryCode`、`categoryName`。
- `DictItemTable`：`dictId`、`label`、`value`、`sort`、`enabled`。
- `DictEditDialog`：`label`、`value`、`sort`、`enabled`。

### Banner 管理

路径：`/admin/banner`

模块：

- `BannerTable`：Banner 列表。
- `BannerEditDialog`：新增 / 编辑 Banner。
- `BannerPreview`：预览。

模块数据：

- `BannerTable`：`bannerId`、`title`、`imageUrl`、`linkUrl`、`sort`、`enabled`。
- `BannerEditDialog`：`title`、`imageUrl`、`linkUrl`、`sort`、`enabled`。

### 权限管理

路径：`/admin/permissions`

模块：

- `RoleCardList`：角色卡片。
- `PermissionMatrix`：权限矩阵。
- `RolePermissionEditor`：角色权限编辑。

模块数据：

- `RoleCardList`：`roleCode`、`roleName`、`description`、`userCount`。
- `PermissionMatrix`：`roleCode`、`permissionCode`、`enabled`。
- `RolePermissionEditor`：`roleCode`、`permissions[]`。
