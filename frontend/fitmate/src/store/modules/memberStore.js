import { signin, getMemberInfo, reissue } from '@/api/member.js'

/* 회원 관련 상태 관리(vuex) */
const memberStore = {
    namespaced: true,
    state: {
        isSignin: false, //로그인 여부
        accessToken: '',
        memberInfo: null,
      },
      getters: {
        checkMemberInfo: function(state) { return state.memberInfo },
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
    
        async memberConfirm({ commit }, member) { //로그인(jwt토큰 받기)
          await signin(member, (response) => {
            if(response.status == 200) { //로그인 성공일 경우
              console.log("로그인 성공");
              commit("SIGNIN", true);
              commit("SET_IS_ACCESSTOKEN", response.data["accessToken"]);
              //토큰을 로컬 스토리지에 저장(쿠키에 저장도 가능)
              localStorage.setItem("grantType", response.data["grantType"]);
              localStorage.setItem("accessToken", response.data["accessToken"]); //로컬 스토리지에 accessToken 저장
              localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
              localStorage.setItem("refreshToken", response.data["refreshToken"]); //로컬 스토리지에 refreshToken 저장
            } else { //로그인 실패일 경우
              commit("SIGNIN", false);
              console.log("로그인 실패");
            }
          },
          () => {}
          );
        },
    
        async signInMemberInfo({ commit }, accessToken) { //로그인한 사용자 정보 받기
          await getMemberInfo(accessToken, (response) => {
            if(response.status == 200) {
              console.log("로그인한 사용자 정보 받기 성공");
              commit("SET_MEMBER_INFO", response.data);
            }
          },
          (error) => {
            console.log(error);
          });
        },

        async reissueToken() { //토큰 재발급
          const tokenInfo = {
            "grantType": localStorage.getItem("grantType"),
            "accessToken": localStorage.getItem("accessToken"),
            "accessTokenExpiresIn": localStorage.getItem("accessTokenExpiresIn"),
            "refreshToken": localStorage.getItem("refreshToken")
          }
          await reissue(tokenInfo, (response) => {
            if(response.status == 200) {
              console.log("토큰 재발급 성공");
              // localStorage.setItem("grantType", response.data["grantType"]); //이 값은 바뀌지 않을 듯함
              localStorage.setItem("accessToken", response.data["accessToken"]);
              localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
              localStorage.setItem("refreshToken", response.data["refreshToken"]);
            }
          },
          () => {
            console.log("토큰 재발급 실패");
          });
        },
    },
    modules: {
    
    }
  };


export default memberStore;