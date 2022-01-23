import Vue from 'vue'
import Vuex from 'vuex'
import { signin } from '@/api/member.js'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isSignin: false,
  },
  mutations: {

  },
  actions: {

    async memberConfirm({ commit }, member) {

      await signin(member, (response) => {
        //로그인 성공일 경우

        //로그인 실패일 경우

        console.log(response);
        console.log(commit); //임시
      },
      () => {}
      );
    },

  },
  modules: {

  }
})
