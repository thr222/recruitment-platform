# Job Search And Filter Design

本文档用于落地招聘网站的职位搜索与筛选功能，覆盖前端交互、Spring Boot 接口、数据库字段。目标是让 `/jobs` 页面具备接近 LinkedIn / Indeed 的基础搜索体验。

## 1. 前端交互设计

### 页面入口

页面路径：`/jobs`

核心用户动作：

```text
输入关键词
↓
选择筛选条件
↓
切换排序
↓
刷新职位卡片列表
↓
点击职位进入详情
```

### 页面模块

```text
JobListView
├── JobSearchHeader
│   ├── KeywordInput
│   ├── CityQuickInput
│   └── SearchButton
├── JobFilterSidebar
│   ├── CityFilter
│   ├── SalaryRangeFilter
│   ├── ExperienceFilter
│   ├── EducationFilter
│   └── ResetButton
├── JobListToolbar
│   ├── ResultCount
│   ├── ActiveFilterChips
│   └── SortSelect
└── JobCardList
    ├── JobCard
    ├── EmptyState
    └── Pagination
```

### 前端状态设计

```ts
interface JobSearchForm {
  keyword: string
  cityCode: string
  salaryRange: string
  experienceRange: string
  educationRequirement: string
  sortBy: 'latest' | 'salary'
}
```

页面内部可以把区间字符串转换成接口字段：

```ts
interface JobSearchParams {
  pageNo: number
  pageSize: number
  keyword?: string
  cityCode?: string
  salaryMin?: number
  salaryMax?: number
  experienceMin?: number
  experienceMax?: number
  educationRequirement?: string
  sortBy?: 'latest' | 'salary'
}
```

### 筛选项设计

城市：

```ts
const cityOptions = [
  { label: '上海', value: '310100' },
  { label: '北京', value: '110100' },
  { label: '杭州', value: '330100' },
  { label: '深圳', value: '440300' }
]
```

薪资区间：

```ts
const salaryOptions = [
  { label: '不限', value: '' },
  { label: '10K-20K', value: '10-20' },
  { label: '20K-30K', value: '20-30' },
  { label: '30K-50K', value: '30-50' },
  { label: '50K以上', value: '50-999' }
]
```

经验要求：

```ts
const experienceOptions = [
  { label: '不限', value: '' },
  { label: '应届/实习', value: '0-1' },
  { label: '1-3年', value: '1-3' },
  { label: '3-5年', value: '3-5' },
  { label: '5年以上', value: '5-99' }
]
```

学历要求：

```ts
const educationOptions = [
  { label: '不限', value: '' },
  { label: '大专', value: '大专' },
  { label: '本科', value: '本科' },
  { label: '硕士', value: '硕士' },
  { label: '博士', value: '博士' }
]
```

排序：

```ts
const sortOptions = [
  { label: '最新发布', value: 'latest' },
  { label: '薪资最高', value: 'salary' }
]
```

### 前端交互规则

- 用户输入关键词后按 Enter 触发搜索。
- 用户点击搜索按钮后，重置 `pageNo = 1` 并请求接口。
- 用户切换城市、薪资、经验、学历后，可以立即搜索，也可以点击「应用筛选」后搜索。
- 用户切换排序后立即请求接口。
- 用户点击「清空筛选」后重置所有条件，并请求第一页。
- 所有搜索条件同步到 URL query，刷新页面后保留筛选条件。
- 无结果时展示空状态，并提供「清空筛选」按钮。

### URL Query 示例

```text
/jobs?keyword=Java&cityCode=310100&salaryRange=20-30&experienceRange=3-5&educationRequirement=本科&sortBy=latest&pageNo=1
```

### 前端请求示例

```ts
searchJobs({
  pageNo: 1,
  pageSize: 10,
  keyword: 'Java',
  cityCode: '310100',
  salaryMin: 20,
  salaryMax: 30,
  experienceMin: 3,
  experienceMax: 5,
  educationRequirement: '本科',
  sortBy: 'latest'
})
```

## 2. 后端接口设计（Spring Boot）

### 接口定义

接口路径：

```http
GET /api/jobs
```

用途：

- 公共职位搜索。
- 支持关键词、城市、薪资、经验、学历、排序、分页。

### 请求参数

```java
public class JobSearchQuery extends PageQuery {
    private String keyword;
    private String cityCode;
    private Integer salaryMin;
    private Integer salaryMax;
    private Integer experienceMin;
    private Integer experienceMax;
    private String educationRequirement;
    private String sortBy;
}
```

当前项目中 `JobSearchQuery` 已支持这些字段，并且额外支持：

- `categoryId`
- `companyId`
- `companySize`
- `tagIds`

建议保留扩展字段，但前端基础搜索先只使用：

- `keyword`
- `cityCode`
- `salaryMin`
- `salaryMax`
- `experienceMin`
- `experienceMax`
- `educationRequirement`
- `sortBy`
- `pageNo`
- `pageSize`

### Controller 设计

```java
@RestController
@RequestMapping("/api/jobs")
public class PublicJobController {

    private final JobFacadeService jobFacadeService;

    @GetMapping
    public ApiResponse<PageResponse<PublicJobItemVO>> searchJobs(JobSearchQuery query) {
        return ApiResponse.success(jobFacadeService.searchJobs(query));
    }
}
```

### Service 查询逻辑

关键词搜索范围：

```sql
jp.title LIKE :keyword
OR jp.description_text LIKE :keyword
OR jp.job_highlights LIKE :keyword
OR c.company_name LIKE :keyword
OR c.company_short_name LIKE :keyword
```

筛选逻辑：

```sql
jp.status = 'ONLINE'
AND jp.audit_status = 'APPROVED'
AND c.business_status = 'ACTIVE'
AND (:cityCode IS NULL OR jp.work_city_code = :cityCode)
AND (:salaryMin IS NULL OR jp.salary_max >= :salaryMin)
AND (:salaryMax IS NULL OR jp.salary_min <= :salaryMax)
AND (:experienceMin IS NULL OR COALESCE(jp.experience_max, jp.experience_min) >= :experienceMin)
AND (:experienceMax IS NULL OR jp.experience_min <= :experienceMax)
AND (:educationRequirement IS NULL OR jp.education_requirement = :educationRequirement)
```

排序逻辑：

```java
private String buildPublicSearchOrderBy(String sortBy) {
    if ("salary".equalsIgnoreCase(sortBy)) {
        return "ORDER BY jp.salary_max DESC, jp.salary_min DESC, COALESCE(jp.published_at, jp.created_at) DESC";
    }
    return "ORDER BY COALESCE(jp.published_at, jp.created_at) DESC";
}
```

分页逻辑：

```sql
LIMIT :limit OFFSET :offset
```

### 响应结构

```ts
interface PageResponse<T> {
  records: T[]
  total: number
  pageNo: number
  pageSize: number
}
```

职位列表项：

```java
public class PublicJobItemVO {
    private Long jobId;
    private String title;
    private Long companyId;
    private String companyName;
    private String companySize;
    private String workCityCode;
    private Integer salaryMin;
    private Integer salaryMax;
    private Integer experienceMin;
    private Integer experienceMax;
    private String educationRequirement;
    private String status;
    private String publishedAt;
}
```

建议后续增强返回字段：

```java
private String employmentType;
private String companyLogo;
private String industry;
private List<String> tags;
private List<String> benefits;
```

## 3. 数据库字段建议

### 当前核心表

职位表：`job_post`

当前项目已有关键字段：

```sql
id BIGINT
company_id BIGINT
category_id BIGINT
title VARCHAR(128)
employment_type VARCHAR(32)
work_city_code VARCHAR(16)
work_address VARCHAR(255)
salary_min INT
salary_max INT
salary_months TINYINT
experience_min DECIMAL(4,1)
experience_max DECIMAL(4,1)
education_requirement VARCHAR(32)
job_highlights VARCHAR(500)
description_text MEDIUMTEXT
status VARCHAR(32)
audit_status VARCHAR(32)
published_at DATETIME
last_refresh_at DATETIME
view_count INT
favorite_count INT
apply_count INT
```

企业表：`company`

搜索会用到：

```sql
id BIGINT
company_name VARCHAR(128)
company_short_name VARCHAR(64)
company_size VARCHAR(32)
industry_code VARCHAR(64)
city_code VARCHAR(16)
logo_url VARCHAR(255)
business_status VARCHAR(32)
```

标签表：`job_tag`

```sql
id BIGINT
tag_name VARCHAR(64)
tag_type VARCHAR(32)
tag_status VARCHAR(32)
```

职位标签关系表：`job_post_tag_rel`

```sql
job_id BIGINT
tag_id BIGINT
```

### 建议补强字段

如果后续要进一步接近 LinkedIn / Indeed，建议给 `job_post` 增加或明确使用：

```sql
employment_type VARCHAR(32) NOT NULL DEFAULT 'FULL_TIME' COMMENT 'FULL_TIME/INTERNSHIP/PART_TIME/REMOTE'
remote_type VARCHAR(32) DEFAULT NULL COMMENT 'ONSITE/HYBRID/REMOTE'
search_weight INT NOT NULL DEFAULT 0 COMMENT '人工或运营加权'
quality_score INT NOT NULL DEFAULT 0 COMMENT '职位质量分'
```

如果要支持更好的排序，可以增加：

```sql
last_active_at DATETIME DEFAULT NULL COMMENT '职位最近活跃时间'
```

### 推荐索引

基础搜索索引：

```sql
ALTER TABLE job_post
    ADD KEY idx_job_post_status_city_time (status, audit_status, work_city_code, published_at),
    ADD KEY idx_job_post_status_salary (status, audit_status, salary_min, salary_max),
    ADD KEY idx_job_post_status_experience (status, audit_status, experience_min, experience_max),
    ADD KEY idx_job_post_status_education (status, audit_status, education_requirement);
```

公司过滤索引：

```sql
ALTER TABLE company
    ADD KEY idx_company_status_name (business_status, company_name),
    ADD KEY idx_company_status_city (business_status, city_code);
```

关键词搜索建议：

- 简单版：`LIKE` 查询职位名、公司名、职位描述。
- 进阶版：MySQL FULLTEXT 或 Elasticsearch。
- 当前毕业设计阶段建议先用 `LIKE + 条件查询`，功能完整、成本低、容易演示。

### 查询结果字段建议

职位列表页最少需要：

```sql
jp.id AS job_id
jp.title
c.id AS company_id
c.company_name
c.company_size
c.logo_url
jp.work_city_code
jp.salary_min
jp.salary_max
jp.experience_min
jp.experience_max
jp.education_requirement
jp.employment_type
jp.job_highlights
jp.published_at
```

卡片渲染字段：

```ts
interface JobListItem {
  jobId: number
  title: string
  companyId: number
  companyName: string
  companyLogo?: string
  companySize?: string
  workCityCode: string
  salaryMin: number
  salaryMax: number
  experienceMin?: number
  experienceMax?: number
  educationRequirement?: string
  employmentType?: string
  tags?: string[]
  publishedAt?: string
}
```

## 当前项目落地状态

已具备：

- 前端已有 `searchJobs(params)`。
- 后端已有 `GET /api/jobs` 搜索入口。
- 后端已有 `JobSearchQuery`。
- 后端 SQL 查询已支持关键词、城市、薪资、经验、学历、公司规模、排序。
- 数据库 `job_post` 已有职位搜索所需核心字段。

建议下一步：

1. 前端 `JobSearchParams` 补上 `educationRequirement`。
2. 职位列表页筛选栏接入学历筛选。
3. 后端列表返回补 `employmentType`、`companyLogo`、`tags`。
4. 列表页用 `ModernJobCard.vue` 渲染职位卡片。
