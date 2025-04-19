import { createRouter, createWebHistory } from 'vue-router'
import LogInView from '@/views/LogInView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'LogIn',
      component: LogInView
    },
    {
      path: '/History',
      name: 'History',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/HistoryView.vue')
    },
    {
      path: '/Bugs',
      name: 'Bugs',
      component: () => import('../views/BugView.vue')
    },
    {
      path: '/Home',
      name: 'Home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/Calculator',
      name: 'Calculator',
      component: () => import('../views/CalculatorView.vue')
    }
  ]
})

export default router
