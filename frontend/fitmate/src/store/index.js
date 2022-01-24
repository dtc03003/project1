import Vue from 'vue'
import Vuex from 'vuex'
import jwt_decode from "jwt-decode"
import { signin, getMemberInfo } from '@/api/member.js'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isSignin: false, //로그인 여부
    accessToken: '',
    memberInfo: null,
  },
  getters: {
  },
  mutations: {
    SIGNIN: (state, isSignin) => {
      state.isSignin = isSignin;
    },
    SET_IS_ACCESSTOKEN: (state, accessToken) => {
      state.accessToken = accessToken;
    },
    SET_MEMBER_INFO(state, memberInfo) {
      state.memberInfo = memberInfo;
    }
  },
  actions: {

    async memberConfirm({ commit }, member) { //로그인 시도(jwt토큰 받기)
      await signin(member, (response) => {
        if(response.status == 200) { //로그인 성공일 경우
          commit("SIGNIN", true);
          commit("SET_IS_ACCESSTOKEN", response.data["accessToken"]);
          //토큰을 로컬 스토리지에 저장? 쿠키에 저장?
          localStorage.setItem("accessToken", response.data["accessToken"]); //로컬 스토리지에 accessToken 저장
          localStorage.setItem("refreshToken", response.data["refreshToken"]); //로컬 스토리지에 refreshToken 저장
          localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
        } else { //로그인 실패일 경우
          commit("SIGNIN", false);
        }
      },
      () => {}
      );
    },

    async memberInfo({ commit }, accessToken) { //로그인한 사용자 정보 받기
      let decode_token = jwt_decode(accessToken);
      getMemberInfo(decode_token, (response) => {
        if(response.status == 200) {
          commit("SET_MEMBER_INFO", response.data.memberInfo);
        }
      },
      (error) => {
        console.log(error);
      });
    }

  },
  modules: {

  }
})
