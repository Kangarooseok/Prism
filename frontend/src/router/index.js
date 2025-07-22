import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/cctvs',
      component: () => import('../components/ui/CctvGrid.vue'),
    },
    {
      path: '/users',
      component: () => import('../components/ui/UserGrid.vue'),
    },
    {
      path: '/notifications',
      component: () => import('../components/ui/NotificationGrid.vue'),
    },
    {
      path: '/alertSubscriptions',
      component: () => import('../components/ui/AlertSubscriptionGrid.vue'),
    },
    {
      path: '/healthCheckLogs',
      component: () => import('../components/ui/HealthCheckLogGrid.vue'),
    },
    {
      path: '/issues',
      component: () => import('../components/ui/IssueGrid.vue'),
    },
    {
      path: '/tvResolutions',
      component: () => import('../components/ui/TvResolutionGrid.vue'),
    },
    {
      path: '/networkActions',
      component: () => import('../components/ui/NetworkActionGrid.vue'),
    },
  ],
})

export default router;
