# 企业招聘与求职平台后端模块结构设计（模块化单体）

## 1. 设计目标

本项目后端采用 `Spring Boot + Spring Security + JWT + MyBatis-Plus + MySQL + Redis`，架构形态为前后端分离的模块化单体。

设计目标：
- 业务边界清晰，便于面试讲解
- 单体部署，降低复杂度，提升交付效率
- 模块职责明确，后续具备平滑拆分能力
- 避免“大 service + 大 mapper + 大 controller”的混乱结构

---

## 2. 整体工程结构

```text
backend
├─ recruit-boot                          -- 启动模块
├─ recruit-common                        -- 公共能力模块
│  ├─ recruit-common-core                -- 常量、统一响应、异常、基础模型
│  ├─ recruit-common-web                 -- Web 配置、全局异常、参数校验
│  ├─ recruit-common-security            -- Spring Security、JWT、权限上下文
│  ├─ recruit-common-mybatis             -- MyBatis-Plus 配置、分页、审计字段
│  ├─ recruit-common-redis               -- Redis key 规范、缓存工具
│  └─ recruit-common-storage             -- 文件上传、附件存储封装
├─ recruit-modules                       -- 业务模块
│  ├─ recruit-auth                       -- 认证授权模块
│  ├─ recruit-user                       -- 用户中心模块
│  ├─ recruit-company                    -- 企业模块
│  ├─ recruit-job                        -- 职位模块
│  ├─ recruit-resume                     -- 简历模块
│  ├─ recruit-application                -- 投递模块
│  ├─ recruit-message                    -- 通知消息模块
│  ├─ recruit-operation                  -- 运营与审核模块
│  └─ recruit-statistics                 -- 统计分析模块
└─ docs                                  -- 架构与接口文档
```

---

## 3. 业务模块划分

### 3.1 `recruit-auth`
- 模块定位：认证与授权中心
- 是否核心模块：是
- 主要职责：
  - 用户注册、登录、退出
  - AccessToken / RefreshToken 签发与刷新
  - 当前登录态校验
  - 用户角色、权限加载
  - 登录黑名单、退出登录、Token 续期
- 典型接口：
  - `POST /api/auth/register`
  - `POST /api/auth/login`
  - `POST /api/auth/refresh`
  - `GET /api/auth/permissions/me`

### 3.2 `recruit-user`
- 模块定位：用户中心
- 是否核心模块：是
- 主要职责：
  - 用户账号资料维护
  - 求职者档案维护
  - 招聘者基础资料维护
  - 账号安全设置
- 典型接口：
  - `GET /api/users/me`
  - `PUT /api/users/me`
  - `GET /api/admin/users`
  - `PATCH /api/admin/users/{userId}/status`

### 3.3 `recruit-company`
- 模块定位：企业管理中心
- 是否核心模块：是
- 主要职责：
  - 企业入驻申请
  - 企业基础信息维护
  - 企业资质材料管理
  - 企业成员管理
  - 企业状态与审核信息维护
- 典型接口：
  - `POST /api/recruiter/companies`
  - `GET /api/recruiter/companies/current`
  - `PUT /api/recruiter/companies/{companyId}`
  - `GET /api/companies/{companyId}`

### 3.4 `recruit-job`
- 模块定位：职位管理中心
- 是否核心模块：是
- 主要职责：
  - 职位发布、编辑、上下架
  - 职位搜索、列表、详情
  - 职位分类与标签管理
  - 职位审核状态管理
- 典型接口：
  - `POST /api/recruiter/jobs`
  - `PUT /api/recruiter/jobs/{jobId}`
  - `GET /api/recruiter/jobs`
  - `GET /api/jobs`
  - `GET /api/jobs/{jobId}`

### 3.5 `recruit-resume`
- 模块定位：简历中心
- 是否核心模块：是
- 主要职责：
  - 在线简历维护
  - 教育经历、工作经历、项目经历维护
  - 附件简历管理
  - 简历完整度计算
- 典型接口：
  - `GET /api/candidate/resumes/default`
  - `POST /api/candidate/resumes`
  - `PUT /api/candidate/resumes/{resumeId}`
  - `POST /api/candidate/resumes/{resumeId}/work-experiences`

### 3.6 `recruit-application`
- 模块定位：招聘流程中心
- 是否核心模块：是
- 主要职责：
  - 发起职位投递
  - 查询投递记录
  - 推进投递状态
  - 维护面试流程
  - 投递状态日志记录
- 典型接口：
  - `POST /api/candidate/applications`
  - `GET /api/candidate/applications`
  - `GET /api/recruiter/applications`
  - `PATCH /api/recruiter/applications/{applicationId}/status`
  - `POST /api/recruiter/applications/{applicationId}/interviews`

### 3.7 `recruit-message`
- 模块定位：消息通知中心
- 是否核心模块：否
- 主要职责：
  - 系统通知
  - 投递流程通知
  - 面试提醒
  - 未读数统计
- 典型场景：
  - 投递成功通知求职者
  - 面试安排通知候选人
  - 企业审核结果通知招聘者

### 3.8 `recruit-operation`
- 模块定位：运营与审核后台
- 是否核心模块：否
- 主要职责：
  - 企业审核
  - 职位审核
  - Banner 配置
  - 字典管理
  - 举报处理
- 典型接口：
  - `GET /api/admin/companies/audits`
  - `PATCH /api/admin/companies/{companyId}/audit`
  - `GET /api/admin/jobs/audits`
  - `PATCH /api/admin/jobs/{jobId}/audit`

### 3.9 `recruit-statistics`
- 模块定位：统计分析模块
- 是否核心模块：否
- 主要职责：
  - 运营看板
  - 企业侧数据统计
  - 职位投递统计
  - 平台聚合数据输出
- 典型场景：
  - 管理后台首页统计
  - 招聘者工作台数据汇总

---

## 4. 核心模块说明

### 4.1 核心业务闭环模块
以下模块构成招聘系统的主链路，属于必须优先搭建的核心模块：

- `recruit-auth`
- `recruit-user`
- `recruit-company`
- `recruit-job`
- `recruit-resume`
- `recruit-application`

### 4.2 核心闭环说明
完整主流程如下：

```text
用户注册/登录
-> 完善用户资料
-> 企业入驻/审核通过
-> 招聘者发布职位
-> 求职者完善简历
-> 求职者搜索职位并投递
-> 招聘者查看候选人并推进流程
-> 系统发送通知
```

### 4.3 辅助模块说明
以下模块不属于首要交易主链路，但能显著提升项目完整度：

- `recruit-message`
- `recruit-operation`
- `recruit-statistics`

---

## 5. 标准包结构

所有业务模块统一采用以下包结构，便于团队协作与后续扩展：

```text
com.company.recruit.xxx
├─ controller
├─ service
├─ manager
├─ mapper
├─ entity
├─ dto
├─ vo
├─ query
├─ enums
├─ constant
├─ convert
└─ repository
```

### 5.1 各层职责说明

#### `controller`
- 对外暴露 REST 接口
- 接收请求参数
- 做基础参数校验
- 调用 service 返回统一响应

#### `service`
- 承载核心业务逻辑
- 管理事务边界
- 负责流程状态变更

#### `manager`
- 处理复杂业务编排
- 聚合多个 service / mapper
- 适合放跨表、跨模块协同逻辑

#### `mapper`
- 持久化访问层
- 对应 MyBatis-Plus Mapper
- 承接复杂 SQL 查询

#### `entity`
- 数据库实体对象
- 与表结构一一映射

#### `dto`
- 请求入参对象
- 对应接口的 request body

#### `vo`
- 响应出参对象
- 对应接口返回给前端的数据结构

#### `query`
- 分页与筛选查询对象
- 适合职位搜索、列表筛选、审核列表

#### `enums`
- 维护业务状态枚举
- 如职位状态、投递状态、审核状态

#### `constant`
- 模块内常量定义
- 如缓存 key 前缀、默认值、权限编码前缀

#### `convert`
- DTO / Entity / VO 转换层
- 可使用 MapStruct，也可先手写

#### `repository`
- 对 mapper 做领域化封装
- 避免 service 直接堆复杂 SQL 调用

---

## 6. 模块交互原则

### 6.1 基本原则
- `controller` 只调用本模块 `service`
- 模块之间优先通过 `service` 或 `manager` 交互
- 不允许跨模块直接调用对方 `mapper`
- 公共能力统一通过 `recruit-common-*` 获取
- 统计模块只做聚合，不承接主流程写操作

### 6.2 推荐交互关系

#### `auth -> user`
- 注册成功后初始化用户资料或求职者档案
- 登录后查询用户基础角色信息

#### `company -> user`
- 企业成员管理时查询用户信息
- 邀请招聘者加入企业时需要关联账号

#### `job -> company`
- 发布职位前校验企业状态是否可发布
- 校验当前招聘者是否属于该企业

#### `application -> job`
- 投递前校验职位是否在线
- 校验职位是否允许继续接收投递

#### `application -> resume`
- 投递前校验简历完整度
- 校验简历是否属于当前求职者

#### `application -> message`
- 投递成功后发送系统通知
- 状态推进后通知候选人和招聘方

#### `operation -> company`
- 管理员审核企业并回写审核状态

#### `operation -> job`
- 管理员审核职位并更新审核状态

#### `statistics -> job/company/application`
- 聚合职位数、企业数、投递数等统计数据

---

## 7. 推荐依赖关系

```text
recruit-boot
  -> recruit-common-core
  -> recruit-common-web
  -> recruit-common-security
  -> recruit-common-mybatis
  -> recruit-common-redis
  -> recruit-common-storage
  -> recruit-auth
  -> recruit-user
  -> recruit-company
  -> recruit-job
  -> recruit-resume
  -> recruit-application
  -> recruit-message
  -> recruit-operation
  -> recruit-statistics
```

业务交互建议：

```text
auth -> user
company -> user
job -> company
application -> job
application -> resume
application -> message
operation -> company
operation -> job
statistics -> company/job/application
```

---

## 8. 项目搭建顺序建议

建议按以下顺序逐步搭建，优先打通招聘平台主链路：

1. `recruit-boot`
2. `recruit-common-core`
3. `recruit-common-web`
4. `recruit-common-security`
5. `recruit-common-mybatis`
6. `recruit-common-redis`
7. `recruit-auth`
8. `recruit-user`
9. `recruit-company`
10. `recruit-job`
11. `recruit-resume`
12. `recruit-application`
13. `recruit-message`
14. `recruit-operation`
15. `recruit-statistics`

---

## 9. 搭建建议

### 9.1 首期优先落地
首期建议先实现以下接口链路：
- 注册/登录
- 当前用户信息
- 企业入驻
- 发布职位
- 职位搜索
- 简历维护
- 发起投递
- 招聘者处理投递

### 9.2 二期扩展能力
二期可继续扩展：
- 收藏职位
- 举报职位
- 企业成员邀请
- 面试评价
- 首页推荐位配置
- 数据统计与图表

### 9.3 面试表达建议
如果用于面试讲解，可以突出三点：
- 采用模块化单体而非微服务，是为了在项目规模可控阶段优先保证业务边界清晰和交付效率
- 核心模块围绕招聘闭环拆分，保证从注册到投递到面试推进形成完整业务链路
- 模块之间通过 service 交互，避免跨模块直连 mapper，方便后续平滑演进