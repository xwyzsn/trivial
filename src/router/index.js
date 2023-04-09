import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      name:'home',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login/login.vue')
    },
    {
      path:'/repair',
      name:'repair',
      component: () => import('../views/layout/RepairLayout.vue'),
      redirect:'/repair/index',
      children:[
        {path:'index',name:'repairIndex',component: () => import('../views/repair/personPage.vue')},
        {path:'order',name:'repairOrder',component: () => import('../views/repair/fixPage.vue')},
        {path:'history',name:'repairHistory',component: () => import('../views/repair/historyPage.vue')},
      ]
    },
    {
      path:'/admin',
      name:'admin',
      component: () => import('../views/layout/AdminLayout.vue'),
      redirect:'/admin/usermanage',
      children:[
        // {path:'index',name:'adminIndex',component: () => import('../views/admin/adminIndex.vue')},
        {path:'usermanage',name:'usermanage',component: () => import('../views/admin/userManagement.vue')},
        {path:'chargemanage',name:'chargemanage',component: () => import('../views/admin/chargerManagement.vue')},
        {path:'ordermanage',name:'ordermanage',component: () => import('../views/admin/orderManagement.vue')},
        {path:'mantainmanage',name:'mantainmanage',component: () => import('../views/admin/mantainerManagement.vue')},
        {path:'personmanage',name:'personmanage',component: () => import('../views/admin/personManagement.vue')},
        {path:'history',name:'historyAnly',component: () => import('../views/admin/historyAnly.vue')},
        {path:'report',name:'report',component: () => import('../views/admin/reportManagement.vue')},
      ]
    },
    {
      path:'/sys',
      name:'sys',
      component: () => import('../views/layout/MainLayout.vue'),
      redirect:'/sys/user',
      children:[
        {path:'user',name:'user',component: () => import('../views/user/UserLayout.vue'),
        redirect:'/sys/user/index',
        children:[
          {path:'index',name:'sysindex',component: () => import('../views/user/IndexView.vue')},
          {path:'order',name:'sysorder',component: () => import('../views/user/OrderView.vue')},
          {path:'car',name:'syscar',component: () => import('../views/user/CarView.vue')},
          {path:'wallet',name:'syswallet',component: () => import('../views/user/WalletView.vue')},
          {path:'feedback',name:'sysfeedback',component: () => import('../views/user/FeedbackView.vue')},
        ]
      },
      {path:'index',name:'indexshow',component: () => import('../views/Index/IndexView.vue')},
      {path:'annouce',name:'announcement',component: () => import('../views/announcement/announcementView.vue')},
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  let info = sessionStorage.getItem('info')
  if (!info) return next('/login')
  next()
})


export default router
