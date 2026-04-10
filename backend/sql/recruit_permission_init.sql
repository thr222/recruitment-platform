USE recruit_platform;

INSERT INTO sys_permission (id, permission_code, permission_name, permission_type, api_path, http_method, permission_status, sort_no) VALUES
(1, 'user:view', 'Admin view users', 'API', '/api/admin/users', 'GET', 'ENABLED', 10),
(2, 'user:status:update', 'Admin update user status', 'API', '/api/admin/users/{userId}/status', 'PATCH', 'ENABLED', 20),
(3, 'user:role:assign', 'Admin assign roles', 'API', '/api/admin/users/{userId}/roles', 'PUT', 'ENABLED', 30),
(4, 'user:profile:view', 'Candidate view profile', 'API', '/api/users/me', 'GET', 'ENABLED', 40),
(5, 'user:profile:update', 'Candidate update profile', 'API', '/api/users/me', 'PUT', 'ENABLED', 50),
(6, 'company:view', 'Recruiter view current company', 'API', '/api/recruiter/companies/current', 'GET', 'ENABLED', 60),
(7, 'company:edit', 'Recruiter edit company', 'API', '/api/recruiter/companies', 'POST', 'ENABLED', 70),
(8, 'company:audit', 'Admin audit company', 'API', '/api/admin/companies', 'PATCH', 'ENABLED', 80),
(9, 'job:create', 'Recruiter create job', 'API', '/api/recruiter/jobs', 'POST', 'ENABLED', 90),
(10, 'job:update', 'Recruiter update job', 'API', '/api/recruiter/jobs/{jobId}', 'PUT', 'ENABLED', 100),
(11, 'job:status:update', 'Recruiter update job status', 'API', '/api/recruiter/jobs/{jobId}/status', 'PATCH', 'ENABLED', 110),
(12, 'job:audit', 'Admin audit job', 'API', '/api/admin/jobs/{jobId}/audit', 'PATCH', 'ENABLED', 120),
(13, 'resume:view', 'View resume', 'API', '/api/candidate/resumes/default', 'GET', 'ENABLED', 130),
(14, 'resume:create', 'Create resume', 'API', '/api/candidate/resumes', 'POST', 'ENABLED', 140),
(15, 'resume:update', 'Update resume', 'API', '/api/candidate/resumes/{resumeId}', 'PUT', 'ENABLED', 150),
(16, 'application:create', 'Candidate apply job', 'API', '/api/candidate/applications', 'POST', 'ENABLED', 160),
(17, 'application:view', 'View application', 'API', '/api/candidate/applications', 'GET', 'ENABLED', 170),
(18, 'application:withdraw', 'Candidate withdraw application', 'API', '/api/candidate/applications/{applicationId}/withdraw', 'PATCH', 'ENABLED', 180),
(19, 'application:process', 'Recruiter process application', 'API', '/api/recruiter/applications/{applicationId}/status', 'PATCH', 'ENABLED', 190),
(20, 'interview:create', 'Recruiter create interview', 'API', '/api/recruiter/applications/{applicationId}/interviews', 'POST', 'ENABLED', 200),
(21, 'interview:feedback', 'Recruiter submit interview feedback', 'API', '/api/recruiter/interviews/{interviewId}/result', 'PATCH', 'ENABLED', 210)
ON DUPLICATE KEY UPDATE
permission_name = VALUES(permission_name),
permission_type = VALUES(permission_type),
api_path = VALUES(api_path),
http_method = VALUES(http_method),
permission_status = VALUES(permission_status),
sort_no = VALUES(sort_no);

INSERT INTO sys_role_permission (role_id, permission_id) VALUES
(1, 4), (1, 5), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18),
(2, 6), (2, 7), (2, 9), (2, 10), (2, 11), (2, 13), (2, 17), (2, 19), (2, 20), (2, 21),
(3, 1), (3, 2), (3, 3), (3, 8), (3, 12)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id), permission_id = VALUES(permission_id);
