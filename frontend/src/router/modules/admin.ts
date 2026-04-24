import type { RouteRecordRaw } from 'vue-router'
import AdminLayout from '@/layouts/AdminLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/dashboard',
    meta: { requiredRole: 'admin', title: '管理端', section: 'admin' },
    children: [
      {
        path: 'dashboard',
        name: 'admin-dashboard',
        component: () => import('@/views/admin/DashboardView.vue'),
        meta: { requiredRole: 'admin', title: '运营看板', section: 'admin' }
      },
      {
        path: 'users',
        name: 'admin-users',
        component: () => import('@/views/admin/UserManageView.vue'),
        meta: { requiredRole: 'admin', title: '用户管理', section: 'admin' }
      },
      {
        path: 'company-audit',
        name: 'admin-company-audit',
        component: () => import('@/views/admin/CompanyAuditView.vue'),
        meta: { requiredRole: 'admin', title: '企业审核', section: 'admin' }
      },
      {
        path: 'job-audit',
        name: 'admin-job-audit',
        component: () => import('@/views/admin/JobAuditView.vue'),
        meta: { requiredRole: 'admin', title: '职位审核', section: 'admin' }
      },
      {
        path: 'dict',
        name: 'admin-dict',
        component: () => import('@/views/admin/DictManageView.vue'),
        meta: { requiredRole: 'admin', title: '字典配置', section: 'admin' }
      },
      {
        path: 'banner',
        name: 'admin-banner',
        component: () => import('@/views/admin/BannerManageView.vue'),
        meta: { requiredRole: 'admin', title: 'Banner 管理', section: 'admin' }
      },
      {
        path: 'reports',
        name: 'admin-reports',
        component: () => import('@/views/admin/ReportManageView.vue'),
        meta: { requiredRole: 'admin', title: '举报处理', section: 'admin' }
      },
      {
        path: 'operation-config',
        name: 'admin-operation-config',
        component: () => import('@/views/admin/OperationConfigView.vue'),
        meta: { requiredRole: 'admin', title: '运营配置', section: 'admin' }
      },
      {
        path: 'permissions',
        name: 'admin-permissions',
        component: () => import('@/views/admin/PermissionManageView.vue'),
        meta: { requiredRole: 'admin', title: '权限管理', section: 'admin' }
      }
    ]
  }
]

export default routes
