import type { RouteRecordRaw } from 'vue-router'
import RecruiterLayout from '@/layouts/RecruiterLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/recruiter',
    component: RecruiterLayout,
    redirect: '/recruiter/dashboard',
    meta: { requiredRole: 'recruiter', title: '企业端', section: 'recruiter' },
    children: [
      {
        path: 'dashboard',
        name: 'recruiter-dashboard',
        component: () => import('@/views/recruiter/DashboardView.vue'),
        meta: { requiredRole: 'recruiter', title: '招聘工作台', section: 'recruiter' }
      },
      {
        path: 'company',
        name: 'recruiter-company',
        component: () => import('@/views/recruiter/CompanyView.vue'),
        meta: { requiredRole: 'recruiter', title: '企业资料', section: 'recruiter' }
      },
      {
        path: 'jobs',
        name: 'recruiter-jobs',
        component: () => import('@/views/recruiter/JobManageView.vue'),
        meta: { requiredRole: 'recruiter', title: '职位管理', section: 'recruiter' }
      },
      {
        path: 'candidates',
        name: 'recruiter-candidates',
        component: () => import('@/views/recruiter/CandidateManageView.vue'),
        meta: { requiredRole: 'recruiter', title: '候选人管理', section: 'recruiter' }
      },
      {
        path: 'interviews',
        name: 'recruiter-interviews',
        component: () => import('@/views/recruiter/InterviewView.vue'),
        meta: { requiredRole: 'recruiter', title: '面试管理', section: 'recruiter' }
      },
      {
        path: 'messages',
        name: 'recruiter-messages',
        component: () => import('@/views/recruiter/MessagesView.vue'),
        meta: { requiredRole: 'recruiter', title: '沟通中心', section: 'recruiter' }
      }
    ]
  }
]

export default routes
