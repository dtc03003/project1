import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
// import VueCookies from 'vue-cookies'

//axios 설정
import axios from "axios";
Vue.prototype.$axios = axios;

Vue.config.productionTip = false

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
// Vue.use(VueCookies);

//kakao javascript 키 설정(cliend_id)
import { KAKAO_APP_KEY } from "@/config";
// 홍준
// window.Kakao.init('30fb9b59db516bf7c56055b26f8daf47')
window.Kakao.init(KAKAO_APP_KEY)
// window.Kakao.init('4e5ee108b9c95fb7ce9ddaa9c7644e57')

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
