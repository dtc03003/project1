import Vue from 'vue'
import Vuex from 'vuex'
import memberStore from "@/store/modules/memberStore.js";
import noticeStore from "@/store/modules/noticeStore.js";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    memberStore,
    noticeStore,
  },
  state: {
    // getStyles:'',
  },
  actions: {
    logout : function ({commit}){
      commit('SIGNOUT')
    },
  }
});

export default store;
