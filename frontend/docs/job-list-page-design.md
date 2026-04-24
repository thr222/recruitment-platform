# Product-Level Job List Page Design

目标：把职位列表页从“简单列表/表格”升级为接近 LinkedIn / Indeed 的产品级职位搜索页。页面重点不是后台管理，而是让求职者快速完成「搜索职位 -> 筛选职位 -> 对比职位 -> 进入详情 -> 投递」。

推荐路径：`/jobs`

## 1. 页面布局结构

整体采用三段式布局：

- 顶部：搜索与排序区。
- 左侧：筛选栏。
- 中间：职位卡片列表。
- 右侧可选：职位快速预览区，当前项目可以先不做，优先点击进入详情页。

桌面端布局：

```text
PublicLayout / Navbar
└── JobListPage
    ├── JobSearchHeader
    │   ├── KeywordInput
    │   ├── CityQuickInput
    │   └── SearchButton
    ├── JobListToolbar
    │   ├── ResultSummary
    │   ├── ActiveFilterChips
    │   └── SortSelect
    └── JobSearchLayout
        ├── JobFilterSidebar
        │   ├── CityFilter
        │   ├── SalaryFilter
        │   ├── ExperienceFilter
        │   ├── EducationFilter
        │   ├── WorkTypeFilter
        │   └── ClearFiltersButton
        └── JobResultPanel
            ├── JobCardList
            │   └── JobCard
            ├── EmptyState
            └── PaginationBar
```

移动端布局：

```text
JobListPage
├── JobSearchHeader
├── MobileFilterDrawerButton
├── JobListToolbar
└── JobCardList
```

移动端筛选栏不要常驻左侧，改成抽屉：

- 点击「筛选」按钮打开 `JobFilterDrawer`。
- 抽屉内复用 `JobFilterSidebar`。
- 筛选确认后关闭抽屉并刷新结果。

视觉结构建议：

- 页面背景：浅灰或白色，避免后台感。
- 筛选栏：白底、圆角、轻阴影、sticky。
- 职位卡片：白底、圆角、轻阴影、hover 上移。
- 薪资：作为最强视觉信息，靠右或标题下方高亮。
- 标签：小 chip，用于技能、福利、工作方式。
- 排序：放在列表顶部右侧。

## 2. 每个模块的字段设计

### JobSearchHeader

用途：承接用户最核心的搜索动作。

字段：

```ts
interface JobSearchHeaderState {
  keyword: string
  cityKeyword: string
}
```

展示数据：

- `keyword`：关键词，例如「前端 / Java / 实习 / 产品经理」。
- `cityKeyword`：城市关键词，例如「上海 / 北京 / 杭州」。
- `hotKeywords`：热门搜索词。

交互：

- 回车搜索。
- 点击搜索按钮。
- 点击热门关键词自动填入关键词并搜索。

### JobFilterSidebar

用途：多维筛选职位。

字段：

```ts
interface JobFilterState {
  cityCode: string
  salaryRange: string
  experienceRange: string
  education: string
  workType: string
}
```

筛选项：

- 城市：`cityCode`
- 薪资区间：`salaryRange`
- 工作经验：`experienceRange`
- 学历要求：`education`
- 工作性质：`workType`

推荐选项：

```ts
const cityOptions = [
  { label: '上海', value: '310100' },
  { label: '北京', value: '110100' },
  { label: '杭州', value: '330100' },
  { label: '深圳', value: '440300' }
]

const salaryOptions = [
  { label: '不限', value: '' },
  { label: '10K-20K', value: '10-20' },
  { label: '20K-30K', value: '20-30' },
  { label: '30K-50K', value: '30-50' },
  { label: '50K以上', value: '50-999' }
]

const experienceOptions = [
  { label: '不限', value: '' },
  { label: '应届/实习', value: '0-1' },
  { label: '1-3年', value: '1-3' },
  { label: '3-5年', value: '3-5' },
  { label: '5年以上', value: '5-99' }
]

const educationOptions = [
  { label: '不限', value: '' },
  { label: '大专', value: '大专' },
  { label: '本科', value: '本科' },
  { label: '硕士', value: '硕士' }
]

const workTypeOptions = [
  { label: '不限', value: '' },
  { label: '全职', value: '全职' },
  { label: '实习', value: '实习' },
  { label: '远程', value: '远程' },
  { label: '兼职', value: '兼职' }
]
```

交互：

- 任一筛选项变化后可以立即搜索，也可以点击「应用筛选」后搜索。
- 点击「清空筛选」重置所有条件。
- 当前已选条件同步到顶部 `ActiveFilterChips`。

### JobListToolbar

用途：展示结果数量、当前筛选条件、排序方式。

字段：

```ts
interface JobListToolbarState {
  total: number
  sortBy: 'latest' | 'salary'
  activeFilters: Array<{
    key: string
    label: string
    value: string
  }>
}
```

展示数据：

- 结果数量：`total`
- 当前排序：`sortBy`
- 已选筛选项：`activeFilters`

排序选项：

```ts
const sortOptions = [
  { label: '最新发布', value: 'latest' },
  { label: '薪资最高', value: 'salary' }
]
```

交互：

- 切换排序后重新请求职位列表。
- 点击筛选 chip 的关闭按钮，移除对应筛选条件。

### JobCardList

用途：展示职位卡片列表。

字段：

```ts
interface JobCardListState {
  loading: boolean
  jobs: JobCardData[]
}
```

展示状态：

- 加载中：骨架屏或 `v-loading`。
- 有数据：职位卡片列表。
- 无数据：`EmptyState`。

### JobCard

用途：职位列表页的核心信息单元。

字段：

```ts
interface JobCardData {
  jobId: number | string
  title: string
  companyName: string
  companyLogo?: string
  salaryMin: number
  salaryMax: number
  city: string
  experience: string
  education: string
  workType: string
  tags: string[]
  benefits: string[]
  publishedAt: string
  companySize?: string
  industry?: string
  isUrgent?: boolean
  isFavorited?: boolean
}
```

卡片展示优先级：

- 第一层：岗位名、薪资。
- 第二层：公司名、城市、经验、学历。
- 第三层：技能标签、福利标签。
- 第四层：发布时间、收藏按钮、查看详情按钮。

卡片建议结构：

```text
JobCard
├── Header
│   ├── JobTitle
│   └── Salary
├── CompanyRow
│   ├── CompanyLogo
│   ├── CompanyName
│   └── Industry / Size
├── MetaRow
│   ├── City
│   ├── Experience
│   ├── Education
│   └── WorkType
├── TagRow
│   ├── SkillTags
│   └── BenefitTags
└── Footer
    ├── PublishedAt
    ├── FavoriteButton
    └── DetailButton
```

交互：

- 点击卡片或「查看详情」进入 `/jobs/:jobId`。
- 点击收藏按钮切换收藏状态。
- hover 时轻微上移并增强阴影。

### EmptyState

用途：筛选无结果时避免空白。

字段：

```ts
interface EmptyStateData {
  title: string
  description: string
  actionText: string
}
```

展示数据：

- 标题：没有找到匹配职位。
- 描述：建议放宽城市、薪资、经验或学历要求。
- 操作：清空筛选。

### PaginationBar

用途：分页浏览职位。

字段：

```ts
interface PaginationState {
  pageNo: number
  pageSize: number
  total: number
}
```

交互：

- 切换页码后重新请求。
- 页码同步到 URL query，便于刷新保留条件。

## 3. Vue 组件拆分建议

推荐目录：

```text
frontend/src/views/public/JobListView.vue
frontend/src/components/jobs/JobSearchHeader.vue
frontend/src/components/jobs/JobFilterSidebar.vue
frontend/src/components/jobs/JobListToolbar.vue
frontend/src/components/jobs/JobCardList.vue
frontend/src/components/jobs/JobCard.vue
frontend/src/components/jobs/JobEmptyState.vue
frontend/src/components/jobs/JobPagination.vue
frontend/src/components/jobs/JobFilterDrawer.vue
```

### JobListView.vue

职责：

- 页面容器。
- 管理筛选状态。
- 管理 URL query 同步。
- 调用 `searchJobs`。
- 将数据传给子组件。

不建议放：

- 大量卡片 UI。
- 筛选项内部布局。
- 复杂状态文案。

### JobSearchHeader.vue

Props：

```ts
interface Props {
  keyword: string
  cityKeyword?: string
  hotKeywords: string[]
}
```

Emits：

```ts
const emit = defineEmits<{
  search: [payload: { keyword: string; cityKeyword?: string }]
  quickSearch: [keyword: string]
}>()
```

### JobFilterSidebar.vue

Props：

```ts
interface Props {
  modelValue: JobFilterState
  cityOptions: OptionItem[]
  salaryOptions: OptionItem[]
  experienceOptions: OptionItem[]
  educationOptions: OptionItem[]
  workTypeOptions: OptionItem[]
}
```

Emits：

```ts
const emit = defineEmits<{
  'update:modelValue': [value: JobFilterState]
  apply: []
  reset: []
}>()
```

### JobListToolbar.vue

Props：

```ts
interface Props {
  total: number
  sortBy: 'latest' | 'salary'
  activeFilters: ActiveFilter[]
}
```

Emits：

```ts
const emit = defineEmits<{
  sortChange: [value: 'latest' | 'salary']
  removeFilter: [key: string]
}>()
```

### JobCardList.vue

Props：

```ts
interface Props {
  loading: boolean
  jobs: JobCardData[]
}
```

Emits：

```ts
const emit = defineEmits<{
  viewDetail: [job: JobCardData]
  toggleFavorite: [job: JobCardData]
}>()
```

### JobCard.vue

Props：

```ts
interface Props {
  job: JobCardData
}
```

Emits：

```ts
const emit = defineEmits<{
  viewDetail: [job: JobCardData]
  toggleFavorite: [job: JobCardData]
}>()
```

### JobFilterDrawer.vue

用途：

- 移动端筛选抽屉。
- 内部复用 `JobFilterSidebar`。

Props：

```ts
interface Props {
  visible: boolean
  filters: JobFilterState
}
```

Emits：

```ts
const emit = defineEmits<{
  'update:visible': [value: boolean]
  apply: [filters: JobFilterState]
  reset: []
}>()
```

## 推荐数据流

```text
JobListView
├── filterForm
├── sortBy
├── pageNo / pageSize
├── jobs
├── total
└── loading

用户操作
↓
子组件 emit
↓
JobListView 更新状态
↓
同步 URL query
↓
调用 searchJobs(params)
↓
更新 jobs / total
```

## 推荐接口参数

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
  workType?: string
  sortBy?: 'latest' | 'salary'
}
```

## 推荐实现顺序

1. 先拆出 `JobCard.vue`，保证列表视觉升级。
2. 再拆出 `JobFilterSidebar.vue`，保证筛选项清晰。
3. 再拆出 `JobListToolbar.vue`，处理结果数和排序。
4. 最后拆 `JobSearchHeader.vue` 和移动端 `JobFilterDrawer.vue`。

这样做的好处是：即使后端接口还不完整，前端页面也已经具备真实招聘网站的产品形态。
