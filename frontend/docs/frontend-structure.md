# Frontend Structure

## Tech Stack
- Vue 3
- TypeScript
- Vite
- Pinia
- Element Plus

## Recommended Directory Structure

```text
frontend/
├─ public/
├─ docs/
│  └─ frontend-structure.md
├─ src/
│  ├─ api/
│  │  ├─ request.ts
│  │  └─ modules/
│  │     ├─ index.ts
│  │     ├─ auth.ts
│  │     ├─ user.ts
│  │     ├─ company.ts
│  │     ├─ job.ts
│  │     ├─ resume.ts
│  │     ├─ application.ts
│  │     └─ admin.ts
│  ├─ assets/
│  ├─ components/
│  │  ├─ common/
│  │  └─ business/
│  ├─ layouts/
│  │  ├─ PublicLayout.vue
│  │  ├─ CandidateLayout.vue
│  │  ├─ RecruiterLayout.vue
│  │  └─ AdminLayout.vue
│  ├─ router/
│  │  ├─ index.ts
│  │  ├─ guard.ts
│  │  └─ modules/
│  │     ├─ public.ts
│  │     ├─ candidate.ts
│  │     ├─ recruiter.ts
│  │     └─ admin.ts
│  ├─ stores/
│  │  ├─ auth.ts
│  │  ├─ app.ts
│  │  ├─ dict.ts
│  │  ├─ message.ts
│  │  ├─ candidate.ts
│  │  ├─ recruiter.ts
│  │  └─ admin.ts
│  ├─ styles/
│  ├─ types/
│  │  ├─ auth.ts
│  │  ├─ router.ts
│  │  ├─ company.ts
│  │  ├─ job.ts
│  │  └─ resume.ts
│  ├─ utils/
│  ├─ views/
│  │  ├─ public/
│  │  ├─ candidate/
│  │  ├─ recruiter/
│  │  └─ admin/
│  ├─ App.vue
│  └─ main.ts
├─ package.json
├─ tsconfig.json
└─ vite.config.ts
```

## Page Division

### Public
- Home
- Login
- JobList
- JobDetail
- CompanyList
- CompanyDetail

### Candidate
- Dashboard
- Resume
- Applications
- Favorites
- Messages
- Profile

### Recruiter
- Dashboard
- Company
- Jobs
- Candidates
- Interviews
- Messages

### Admin
- Dashboard
- Users
- CompanyAudit
- JobAudit
- DictManage
- BannerManage
- ReportManage

## Router Design
- Public routes and business routes are separated.
- Each role has its own route module and layout.
- `meta.requiredRole` controls route access.
- `meta.title` drives document title and menu labeling.
- Detail pages use `hiddenInMenu` to avoid cluttering side menus.

## Pinia Design

### Global Stores
- `auth`: token, role, user info, permissions
- `app`: title, breadcrumb, layout state
- `dict`: dictionary cache and filter options
- `message`: unread count and notifications

### Role Stores
- `candidate`: resume completion and application summary
- `recruiter`: company context and recruiting metrics
- `admin`: pending audits and governance summary

## API Encapsulation
- `src/api/request.ts` holds the axios instance.
- request interceptors inject the JWT token.
- `api/modules` is split by domain instead of page.
- `api/modules/index.ts` provides a unified export entry.
