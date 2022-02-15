import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import SignupNormal from '@/views/signup/SignupNormal'
import SignupStylist from '@/views/signup/SignupStylist'
import Signin from '@/views/sign/Signin'
import Signup from '@/views/Signup'
import Stylist from '@/views/Stylist'
import Portfolio from '@/views/Portfolio'
import Portfolionope from '@/views/Portfolionope'
import Stylebook from '@/views/Stylebook'
import Mypage from '@/views/Mypage'
import Modify from '@/views/Modify'
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
  {
    path: '/portfolionope/:nickname',
    name: 'Portfolionope',
    component: Portfolionope
  },
  {
    path: '/portfolio/:nickname',
    name: 'Portfolio',
    component: Portfolio
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import("@/views/order/Order.vue")
  },
  {
    path: "/order/approval",
    name: 'OrderApproval',
    component: () => import("@/views/order/OrderApproval.vue")
  },
  {
    path: "/order/cancel",
    name: 'OrderCancel',
    component: () => import("@/views/order/OrderCancel.vue"),
  },
  {
    path: "/order/fail",
    name: 'OrderFail',
    component: () => import("@/views/order/OrderFail.vue"),
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage
  },
  {
    path: '/stylist',
    name: 'Stylist',
    component: Stylist
  },
  {
    path: '/stylebook',
    name: 'Stylebook',
    component: Stylebook
  },
  {
    path: '/modify',
    name: 'Modify',
    component: Modify
  },
  {
    path: "/notice",
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

  {
    path: '/qna',
    component: () => import("@/views/QnA.vue"),

    children: [
      {
        path: "",
        name: 'QnAList',
        component: () => import("@/components/QnA/QnAList.vue"),
      },
      {
        path: "create",
        name: 'QnACreate',
        component: () => import("@/components/QnA/QnACreate.vue"),
      },
      {
        path: "view/:id",
        name: 'QnAView',
        component: () => import("@/components/QnA/QnAView.vue"),
      },
    ]
  },
  {
    path: "/room/:hostname",
    name: "Room",
    component : () => import("@/views/Room.vue"),
  },
  // {
  //   path: "/conference",
  //   name: "Conference",
  //   component : () => import("@/views/Conference.vue"),
  // },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
