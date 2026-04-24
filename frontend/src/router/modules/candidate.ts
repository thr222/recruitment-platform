import type { RouteRecordRaw } from 'vue-router'
import CandidateLayout from '@/layouts/CandidateLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/candidate',
    component: CandidateLayout,
    redirect: '/candidate/dashboard',
    meta: { requiredRole: 'candidate', title: '求职者端', section: 'candidate' },
    children: [
      {
        path: 'dashboard',
        name: 'candidate-dashboard',
        component: () => import('@/views/candidate/DashboardView.vue'),
        meta: { requiredRole: 'candidate', title: '求职者工作台', section: 'candidate' }
      },
      {
        path: 'resume',
        name: 'candidate-resume',
        component: () => import('@/views/candidate/ResumeView.vue'),
        meta: { requiredRole: 'candidate', title: '在线简历', section: 'candidate' }
      },
      {
        path: 'applications',
        name: 'candidate-applications',
        component: () => import('@/views/candidate/ApplicationsView.vue'),
        meta: { requiredRole: 'candidate', title: '我的投递', section: 'candidate' }
      },
      {
        path: 'applications/:applicationId',
        name: 'candidate-application-detail',
        component: () => import('@/views/candidate/ApplicationDetailView.vue'),
        meta: { requiredRole: 'candidate', title: '投递详情', section: 'candidate', hiddenInMenu: true }
      },
      {
        path: 'interviews',
        name: 'candidate-interviews',
        component: () => import('@/views/candidate/InterviewsView.vue'),
        meta: { requiredRole: 'candidate', title: '面试邀请', section: 'candidate' }
      },
      {
        path: 'favorites',
        name: 'candidate-favorites',
        component: () => import('@/views/candidate/FavoritesView.vue'),
        meta: { requiredRole: 'candidate', title: '我的收藏', section: 'candidate' }
      },
      {
        path: 'messages',
        name: 'candidate-messages',
        component: () => import('@/views/candidate/MessagesView.vue'),
        meta: { requiredRole: 'candidate', title: '消息中心', section: 'candidate' }
      },
      {
        path: 'profile',
        name: 'candidate-profile',
        component: () => import('@/views/candidate/ProfileView.vue'),
        meta: { requiredRole: 'candidate', title: '个人设置', section: 'candidate' }
      },
      {
        path: 'preferences',
        name: 'candidate-preferences',
        component: () => import('@/views/candidate/PreferencesView.vue'),
        meta: { requiredRole: 'candidate', title: '求职偏好', section: 'candidate' }
      }
    ]
  }
]

export default routes
