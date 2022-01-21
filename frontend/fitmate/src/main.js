import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

//axios 설정
import axios from "axios";
Vue.prototype.$axios = axios;

Vue.config.productionTip = false

Vue.use(BootstrapVue)

Vue.use(IconsPlugin);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
