# 招聘系统后端 API 设计

## 1. 基础约定

### 1.1 Base URL
- `/api`

### 1.2 认证方式
- Header：`Authorization: Bearer {accessToken}`

### 1.3 角色定义
- `CANDIDATE`：求职者
- `RECRUITER`：企业招聘者
- `ADMIN`：平台管理员

### 1.4 通用返回结构
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {},
  "requestId": "202604081530001234",
  "timestamp": "2026-04-08T15:30:00"
}
```

### 1.5 分页返回结构
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 125,
    "pages": 13,
    "records": []
  }
}
```

---

## 2. 用户模块

### 2.1 用户注册
- URL：`POST /api/auth/register`
- 权限：匿名

请求参数：
```json
{
  "username": "zhangsan",
  "password": "123456",
  "mobile": "13800138000",
  "roleType": "CANDIDATE"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "注册成功",
  "data": {
    "userId": 10001
  }
}
```

### 2.2 用户登录
- URL：`POST /api/auth/login`
- 权限：匿名

请求参数：
```json
{
  "username": "zhangsan",
  "password": "123456"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "登录成功",
  "data": {
    "accessToken": "xxx",
    "refreshToken": "xxx",
    "expiresIn": 7200,
    "role": "CANDIDATE"
  }
}
```

### 2.3 刷新 Token
- URL：`POST /api/auth/refresh`
- 权限：匿名

请求参数：
```json
{
  "refreshToken": "xxx"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "刷新成功",
  "data": {
    "accessToken": "new-access-token",
    "refreshToken": "new-refresh-token",
    "expiresIn": 7200
  }
}
```

### 2.4 获取当前登录用户信息
- URL：`GET /api/users/me`
- 权限：登录用户

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "userId": 10001,
    "username": "zhangsan",
    "realName": "张三",
    "mobile": "13800138000",
    "email": "zhangsan@example.com",
    "avatarUrl": "/avatar/a.png",
    "roleList": ["CANDIDATE"]
  }
}
```

### 2.5 更新当前用户资料
- URL：`PUT /api/users/me`
- 权限：登录用户

请求参数：
```json
{
  "realName": "张三",
  "email": "zhangsan@example.com",
  "avatarUrl": "/avatar/a.png"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "更新成功",
  "data": true
}
```

### 2.6 管理员分页查询用户
- URL：`GET /api/admin/users`
- 权限：ADMIN

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `keyword`：用户名/手机号/姓名
- `roleType`：角色类型
- `accountStatus`：账号状态

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 50,
    "pages": 5,
    "records": [
      {
        "userId": 10001,
        "username": "zhangsan",
        "mobile": "13800138000",
        "realName": "张三",
        "roleType": "CANDIDATE",
        "accountStatus": "ENABLED",
        "createdAt": "2026-04-08 10:00:00"
      }
    ]
  }
}
```

### 2.7 管理员更新用户状态
- URL：`PATCH /api/admin/users/{userId}/status`
- 权限：ADMIN

请求参数：
```json
{
  "accountStatus": "DISABLED",
  "remark": "违规账号处理"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "状态更新成功",
  "data": true
}
```

---

## 3. 企业模块

### 3.1 企业入驻申请
- URL：`POST /api/recruiter/companies`
- 权限：RECRUITER

请求参数：
```json
{
  "companyName": "星河科技有限公司",
  "companyShortName": "星河科技",
  "unifiedSocialCreditCode": "91330100MA12345678",
  "industryCode": "ENTERPRISE_SERVICE",
  "financingStage": "B轮",
  "companySize": "100-499人",
  "cityCode": "330100",
  "addressDetail": "西湖区云栖小镇88号",
  "officialWebsite": "https://www.example.com",
  "introduction": "聚焦智能招聘与组织效能产品。",
  "licenseImageUrl": "/license/a.png"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "提交成功",
  "data": {
    "companyId": 20001,
    "auditStatus": "PENDING"
  }
}
```

### 3.2 获取当前招聘者所属企业
- URL：`GET /api/recruiter/companies/current`
- 权限：RECRUITER

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "companyId": 20001,
    "companyName": "星河科技有限公司",
    "auditStatus": "APPROVED",
    "businessStatus": "ACTIVE"
  }
}
```

### 3.3 更新企业信息
- URL：`PUT /api/recruiter/companies/{companyId}`
- 权限：RECRUITER

请求参数：
```json
{
  "companyShortName": "星河科技",
  "companySize": "100-499人",
  "financingStage": "C轮",
  "introduction": "更新后的企业介绍"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "更新成功",
  "data": true
}
```

### 3.4 企业详情
- URL：`GET /api/companies/{companyId}`
- 权限：公开

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "companyId": 20001,
    "companyName": "星河科技有限公司",
    "companyShortName": "星河科技",
    "financingStage": "B轮",
    "companySize": "100-499人",
    "cityCode": "330100",
    "introduction": "聚焦智能招聘与组织效能产品。",
    "logoUrl": "/logo/a.png"
  }
}
```

### 3.5 管理员分页审核企业
- URL：`GET /api/admin/companies/audits`
- 权限：ADMIN

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `auditStatus`：审核状态
- `keyword`：企业名称/社会信用代码

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 12,
    "pages": 2,
    "records": [
      {
        "companyId": 20001,
        "companyName": "星河科技有限公司",
        "auditStatus": "PENDING",
        "createdAt": "2026-04-08 11:00:00"
      }
    ]
  }
}
```

### 3.6 管理员审核企业
- URL：`PATCH /api/admin/companies/{companyId}/audit`
- 权限：ADMIN

请求参数：
```json
{
  "auditStatus": "APPROVED",
  "auditRemark": "资料完整，审核通过"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "审核成功",
  "data": true
}
```

---

## 4. 职位模块

### 4.1 发布职位
- URL：`POST /api/recruiter/jobs`
- 权限：RECRUITER

请求参数：
```json
{
  "companyId": 20001,
  "categoryId": 30002,
  "title": "Java后端开发工程师",
  "employmentType": "FULL_TIME",
  "headcount": 3,
  "workCityCode": "310100",
  "workAddress": "上海市浦东新区张江路100号",
  "salaryMin": 25,
  "salaryMax": 40,
  "salaryMonths": 14,
  "experienceMin": 3,
  "experienceMax": 5,
  "educationRequirement": "本科",
  "jobHighlights": ["核心业务", "技术成长", "双休"],
  "tagIds": [1, 2, 3],
  "descriptionText": "负责招聘平台中的职位、投递、权限相关模块建设。"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "职位创建成功",
  "data": {
    "jobId": 30001,
    "status": "DRAFT"
  }
}
```

### 4.2 编辑职位
- URL：`PUT /api/recruiter/jobs/{jobId}`
- 权限：RECRUITER

请求参数：同发布职位

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "更新成功",
  "data": true
}
```

### 4.3 更新职位状态
- URL：`PATCH /api/recruiter/jobs/{jobId}/status`
- 权限：RECRUITER

请求参数：
```json
{
  "status": "ONLINE"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "状态更新成功",
  "data": true
}
```

### 4.4 招聘者分页查询自己发布的职位
- URL：`GET /api/recruiter/jobs`
- 权限：RECRUITER

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `status`：职位状态
- `keyword`：职位标题关键字

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 8,
    "pages": 1,
    "records": [
      {
        "jobId": 30001,
        "title": "Java后端开发工程师",
        "status": "ONLINE",
        "applyCount": 18,
        "viewCount": 120,
        "publishedAt": "2026-04-08 10:30:00"
      }
    ]
  }
}
```

### 4.5 公共职位分页搜索
- URL：`GET /api/jobs`
- 权限：公开

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `keyword`：职位关键词
- `cityCode`：城市编码
- `categoryId`：职位分类 ID
- `salaryMin`：最低薪资筛选
- `salaryMax`：最高薪资筛选
- `experienceMin`：最低经验
- `experienceMax`：最高经验
- `educationRequirement`：学历要求
- `companyId`：企业 ID
- `tagIds`：标签 ID 列表，逗号分隔
- `sortBy`：排序方式，支持 `latest`、`salary`、`hot`

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 125,
    "pages": 13,
    "records": [
      {
        "jobId": 30001,
        "title": "Java后端开发工程师",
        "companyId": 20001,
        "companyName": "星河科技有限公司",
        "workCityCode": "310100",
        "salaryMin": 25,
        "salaryMax": 40,
        "experienceMin": 3,
        "experienceMax": 5,
        "educationRequirement": "本科",
        "status": "ONLINE",
        "publishedAt": "2026-04-08 10:30:00"
      }
    ]
  }
}
```

### 4.6 职位详情
- URL：`GET /api/jobs/{jobId}`
- 权限：公开

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "jobId": 30001,
    "title": "Java后端开发工程师",
    "company": {
      "companyId": 20001,
      "companyName": "星河科技有限公司"
    },
    "salaryMin": 25,
    "salaryMax": 40,
    "descriptionText": "负责招聘平台中的职位、投递、权限相关模块建设。",
    "status": "ONLINE"
  }
}
```

### 4.7 管理员分页审核职位
- URL：`GET /api/admin/jobs/audits`
- 权限：ADMIN

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `auditStatus`：审核状态
- `keyword`：职位标题/企业名称

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 20,
    "pages": 2,
    "records": [
      {
        "jobId": 30001,
        "title": "Java后端开发工程师",
        "companyName": "星河科技有限公司",
        "auditStatus": "PENDING"
      }
    ]
  }
}
```

### 4.8 管理员审核职位
- URL：`PATCH /api/admin/jobs/{jobId}/audit`
- 权限：ADMIN

请求参数：
```json
{
  "auditStatus": "APPROVED",
  "auditRemark": "内容合规，审核通过"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "审核成功",
  "data": true
}
```

---

## 5. 简历模块

### 5.1 获取默认简历
- URL：`GET /api/candidate/resumes/default`
- 权限：CANDIDATE

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "resumeId": 40001,
    "resumeName": "默认在线简历",
    "realName": "张三",
    "mobile": "13800138000",
    "email": "zhangsan@example.com",
    "degree": "本科",
    "completionScore": 88,
    "currentStatus": "OPEN_TO_OPPORTUNITY"
  }
}
```

### 5.2 创建简历
- URL：`POST /api/candidate/resumes`
- 权限：CANDIDATE

请求参数：
```json
{
  "resumeName": "默认在线简历",
  "realName": "张三",
  "mobile": "13800138000",
  "email": "zhangsan@example.com",
  "degree": "本科",
  "expectationCity": "310100",
  "expectationPosition": "Java后端开发工程师",
  "expectationSalaryMin": 25,
  "expectationSalaryMax": 35,
  "personalSummary": "3年Java后端经验"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "创建成功",
  "data": {
    "resumeId": 40001
  }
}
```

### 5.3 更新简历基础信息
- URL：`PUT /api/candidate/resumes/{resumeId}`
- 权限：CANDIDATE

请求参数：同创建简历

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "更新成功",
  "data": true
}
```

### 5.4 新增工作经历
- URL：`POST /api/candidate/resumes/{resumeId}/work-experiences`
- 权限：CANDIDATE

请求参数：
```json
{
  "companyName": "星河科技有限公司",
  "positionName": "Java后端工程师",
  "startDate": "2022-01-01",
  "endDate": "2024-12-31",
  "currentlyWorking": 0,
  "workDescription": "负责认证、职位、投递模块开发",
  "achievementText": "完成权限体系改造"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "新增成功",
  "data": {
    "workExperienceId": 50001
  }
}
```

### 5.5 新增项目经历
- URL：`POST /api/candidate/resumes/{resumeId}/project-experiences`
- 权限：CANDIDATE

请求参数：
```json
{
  "projectName": "企业招聘与求职平台",
  "roleName": "后端负责人",
  "startDate": "2025-01-01",
  "endDate": "2025-08-30",
  "projectDescription": "招聘系统平台",
  "responsibilityText": "负责后端架构与核心模块开发",
  "projectResult": "完成投递闭环"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "新增成功",
  "data": {
    "projectExperienceId": 60001
  }
}
```

### 5.6 招聘者查看候选人简历
- URL：`GET /api/recruiter/resumes/{resumeId}`
- 权限：RECRUITER

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "resumeId": 40001,
    "realName": "张三",
    "degree": "本科",
    "workExperiences": [],
    "projectExperiences": []
  }
}
```

---

## 6. 投递模块

### 6.1 发起投递
- URL：`POST /api/candidate/applications`
- 权限：CANDIDATE

请求参数：
```json
{
  "jobId": 30001,
  "resumeId": 40001,
  "candidateNote": "希望优先考虑后端平台方向岗位"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "投递成功",
  "data": {
    "applicationId": 50001,
    "status": "SUBMITTED"
  }
}
```

### 6.2 求职者分页查询我的投递
- URL：`GET /api/candidate/applications`
- 权限：CANDIDATE

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `status`：投递状态
- `keyword`：职位名称/企业名称

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 15,
    "pages": 2,
    "records": [
      {
        "applicationId": 50001,
        "jobId": 30001,
        "jobTitle": "Java后端开发工程师",
        "companyName": "星河科技有限公司",
        "status": "INTERVIEWING",
        "processStage": "FIRST_INTERVIEW",
        "appliedAt": "2026-04-08 10:00:00"
      }
    ]
  }
}
```

### 6.3 求职者查看投递详情
- URL：`GET /api/candidate/applications/{applicationId}`
- 权限：CANDIDATE

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "applicationId": 50001,
    "jobTitle": "Java后端开发工程师",
    "companyName": "星河科技有限公司",
    "status": "INTERVIEWING",
    "processStage": "FIRST_INTERVIEW",
    "timeline": [
      {
        "fromStatus": "SUBMITTED",
        "toStatus": "VIEWED",
        "operatorRole": "RECRUITER",
        "createdAt": "2026-04-09 09:00:00"
      }
    ]
  }
}
```

### 6.4 撤回投递
- URL：`PATCH /api/candidate/applications/{applicationId}/withdraw`
- 权限：CANDIDATE

请求参数：
```json
{
  "reason": "岗位方向调整"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "撤回成功",
  "data": true
}
```

### 6.5 招聘者分页查询候选人投递
- URL：`GET /api/recruiter/applications`
- 权限：RECRUITER

请求参数：
- `pageNo`：页码
- `pageSize`：每页条数
- `jobId`：职位 ID
- `status`：投递状态
- `processStage`：流程阶段
- `keyword`：候选人姓名/职位名称

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "pageNo": 1,
    "pageSize": 10,
    "total": 30,
    "pages": 3,
    "records": [
      {
        "applicationId": 50001,
        "candidateUserId": 10001,
        "candidateName": "张三",
        "jobTitle": "Java后端开发工程师",
        "status": "SCREENING",
        "processStage": "FIRST_SCREEN",
        "appliedAt": "2026-04-08 10:00:00"
      }
    ]
  }
}
```

### 6.6 招聘者推进投递状态
- URL：`PATCH /api/recruiter/applications/{applicationId}/status`
- 权限：RECRUITER

请求参数：
```json
{
  "status": "INTERVIEWING",
  "processStage": "FIRST_INTERVIEW",
  "remark": "进入一面流程"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "状态更新成功",
  "data": true
}
```

### 6.7 创建面试安排
- URL：`POST /api/recruiter/applications/{applicationId}/interviews`
- 权限：RECRUITER

请求参数：
```json
{
  "roundNo": 1,
  "interviewType": "ONLINE",
  "scheduledStartAt": "2026-04-10 15:00:00",
  "scheduledEndAt": "2026-04-10 16:00:00",
  "interviewerUserId": 20002,
  "meetingLink": "https://meeting.example.com/abc"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "面试创建成功",
  "data": {
    "interviewId": 60001,
    "interviewStatus": "SCHEDULED"
  }
}
```

### 6.8 更新面试结果
- URL：`PATCH /api/recruiter/interviews/{interviewId}/result`
- 权限：RECRUITER

请求参数：
```json
{
  "feedbackScore": 8,
  "feedbackResult": "PASS",
  "feedbackText": "技术基础扎实，表达清晰"
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "面试结果已提交",
  "data": true
}
```

---

## 7. 权限接口

### 7.1 获取当前用户权限信息
- URL：`GET /api/auth/permissions/me`
- 权限：登录用户

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": {
    "userId": 10001,
    "roleList": ["RECRUITER"],
    "permissionList": [
      "company:view",
      "company:edit",
      "job:create",
      "job:update",
      "application:process"
    ],
    "dataScope": {
      "companyIds": [20001]
    }
  }
}
```

### 7.2 管理员给用户分配角色
- URL：`PUT /api/admin/users/{userId}/roles`
- 权限：ADMIN

请求参数：
```json
{
  "roleCodes": ["RECRUITER"]
}
```

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "角色分配成功",
  "data": true
}
```

### 7.3 管理员查询角色列表
- URL：`GET /api/admin/roles`
- 权限：ADMIN

请求参数：无

返回结构：
```json
{
  "code": "SUCCESS",
  "message": "ok",
  "data": [
    {
      "roleCode": "ADMIN",
      "roleName": "管理员"
    },
    {
      "roleCode": "RECRUITER",
      "roleName": "招聘者"
    },
    {
      "roleCode": "CANDIDATE",
      "roleName": "求职者"
    }
  ]
}
```

---

## 8. Swagger 分组建议
- `AuthController`：认证与权限
- `UserController`：用户中心
- `CompanyController`：企业管理
- `JobController`：职位管理
- `ResumeController`：简历管理
- `ApplicationController`：投递与面试流程
- `AdminController`：后台审核与权限管理

## 9. 权限矩阵建议

| 接口范围 | 求职者 | 招聘者 | 管理员 |
| --- | --- | --- | --- |
| `/api/users/me/**` | 是 | 是 | 是 |
| `/api/candidate/**` | 是 | 否 | 否 |
| `/api/recruiter/**` | 否 | 是 | 否 |
| `/api/admin/**` | 否 | 否 | 是 |
| `/api/jobs/**` | 是 | 是 | 是 |
| `/api/companies/**` | 是 | 是 | 是 |