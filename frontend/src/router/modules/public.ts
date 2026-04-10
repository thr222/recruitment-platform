import type { RouteRecordRaw } from 'vue-router'
import PublicLayout from '@/layouts/PublicLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: PublicLayout,
    meta: { title: '公共门户', section: 'public' },
    children: [
      {
        path: '',
        name: 'public-home',
        component: () => import('@/views/public/HomeView.vue'),
        meta: { title: '首页', section: 'public' }
      },
      {
        path: 'jobs',
        name: 'public-jobs',
        component: () => import('@/views/public/JobListView.vue'),
        meta: { title: '职位列表', section: 'public' }
      },
      {
        path: 'jobs/:jobId',
        name: 'public-job-detail',
        component: () => import('@/views/public/JobDetailView.vue'),
        meta: { title: '职位详情', section: 'public', hiddenInMenu: true }
      },
      {
        path: 'companies',
        name: 'public-companies',
        component: () => import('@/views/public/CompanyListView.vue'),
        meta: { title: '企业列表', section: 'public' }
      },
      {
        path: 'companies/:companyId',
        name: 'public-company-detail',
        component: () => import('@/views/public/CompanyDetailView.vue'),
        meta: { title: '企业详情', section: 'public', hiddenInMenu: true }
      },
      {
        path: 'login',
        name: 'public-login',
        component: () => import('@/views/public/LoginView.vue'),
        meta: { title: '登录注册', section: 'public', hiddenInMenu: true }
      }
    ]
  }
]

export default routes
