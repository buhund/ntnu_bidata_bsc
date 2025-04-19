/**
 * This file contains the router configuration for the application.
 */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from "@/views/LoginView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'loginWithPassword',
      component: () => import('../views/LoginWithPasswordView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue')
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue')
    },
    {
      path: '/sparesti',
      name: 'sparesti',
      component: () => import('../views/SparestiView.vue')
    },
    {
      path: '/badges',
      name: 'badges',
      component: () => import('../views/BadgesView.vue')
    },
    {
      path: '/overview',
      name: 'overview',
      component: () => import('../views/OverviewView.vue')
    },
    {
      path: '/config',
      name: 'config',
      component: () => import('../views/ConfigurationView.vue')
    },
    {
      path: '/goalConfig',
      name: 'goalConfig',
      component: () => import('../views/CreateSavingsGoalConfigurationView.vue')
    },
    {
      path: '/forgotPassword',
      name: 'forgotPassword',
      component: () => import('../views/ForgotPasswordView.vue')
    },
    {
      path: '/editPassword',
      name: 'editPassword',
      component: () => import('../views/EditPasswordView.vue')
    },
    {
      path: '/challengeConfig',
      name: 'challengeConfig',
      component: () => import('../views/GetChallengeConfigView.vue')
    },
    {
      path: '/uploadFileConfig',
      name: 'uploadFileConfig',
      component: () => import('../views/UploadFileConfigurationView.vue')
    },
    {
      path: '/changeOfHabitConfig',
      name: 'changeOfHabitConfig',
      component: () => import('../views/ChangeOfHabitConfigView.vue')
    },
  ]
})

export default router
