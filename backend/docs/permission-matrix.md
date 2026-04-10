# Permission Matrix

## Role Overview

### CANDIDATE
- `user:profile:view`
- `user:profile:update`
- `resume:view`
- `resume:create`
- `resume:update`
- `application:create`
- `application:view`
- `application:withdraw`

### RECRUITER
- `company:view`
- `company:edit`
- `job:create`
- `job:update`
- `job:status:update`
- `resume:view`
- `application:view`
- `application:process`
- `interview:create`
- `interview:feedback`

### ADMIN
- `user:view`
- `user:status:update`
- `user:role:assign`
- `company:audit`
- `job:audit`

## Route Rules
- `/api/candidate/**` -> `ROLE_CANDIDATE`
- `/api/recruiter/**` -> `ROLE_RECRUITER`
- `/api/admin/**` -> `ROLE_ADMIN`
- `/api/auth/login`, `/api/auth/register`, `/api/auth/refresh` -> anonymous

## Method-Level Control
Method permissions are implemented with `@PreAuthorize` in controller methods.

Examples already wired in the project:
- job create/update/audit
- company edit/audit
- resume create/update/view
- application create/view/withdraw/process
- interview create/feedback
- user status update
- user role assignment

## Data Scope
- candidate data scope: current `userId`
- recruiter data scope: current `companyIds`
- admin data scope: global

## Storage
- role data: `sys_role`
- permission data: `sys_permission`
- user-role relation: `sys_user_role`
- role-permission relation: `sys_role_permission`
