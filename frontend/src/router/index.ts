import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import publicRoutes from './modules/public'
import candidateRoutes from './modules/candidate'
import recruiterRoutes from './modules/recruiter'
import adminRoutes from './modules/admin'
import { authGuard, afterRouteChange } from './guard'

const routes: RouteRecordRaw[] = [
  ...publicRoutes,
  ...candidateRoutes,
  ...recruiterRoutes,
  ...adminRoutes
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => authGuard(to))
router.afterEach((to) => {
  afterRouteChange(to)
})

export default router
