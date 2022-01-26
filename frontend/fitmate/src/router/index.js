import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import SignupNormal from '@/views/signup/SignupNormal'
import SignupStylist from '@/views/signup/SignupStylist'
import Signin from '@/views/sign/Signin'
import Signup from '@/views/Signup'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signup/signupnormal',
    name: 'SignupNormal',
    component: SignupNormal
  },
  {
    path: '/signup/signupstylist',
    name: 'SignupStylist',
    component: SignupStylist
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin
  },
  // {
  //   path: '/signuot',
  //   name: 'Signout',
  //   component: Signout 
  // },
  // {
  //   path: '/mypage',
  //   name: 'Mypage',
  //   component: Mypage
  // },
  // {
  //   path: '/stylist',
  //   name: 'Stylist',
  //   component: Stylist
  // },
  // {
  //   path: '/stylebook',
  //   name: 'Stylebook',
  //   component: Stylebook
  // },
  // {
  //   path: '/qna',
  //   name: 'Qna',
  //   component: Qna
  // },
  {
    path: "/notice",
    name: "Notice",
    component: () => import("@/views/Notice.vue"),

    children: [
      {
        path: "",
        name: 'NoticeList',
        component: () => import("@/components/Notice/NoticeList.vue"),
      },
      {
        path: "create",
        name: 'NoticeCreate',
        component: () => import("@/components/Notice/NoticeCreate.vue"),
      },
      {
        path: "view/:id",
        name: 'NoticeView',
        component: () => import("@/components/Notice/NoticeView.vue"),
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
