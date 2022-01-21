import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Normal from '@/views/signup/Normal'
import Stylist from '@/views/signup/Stylist'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home 
  },
  {
    path: '/signup/stylist',
    name: 'Stylist',
    component: Stylist
  },
  {
    path: '/signup/normal',
    name: 'Normal',
    component: Normal
  },
  // {
  //   path: '/signin',
  //   name: 'Signin',
  //   component: Signin
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
  // {
  //   path: '/notice',
  //   name: 'Notice',
  //   component: Notice
  // },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
