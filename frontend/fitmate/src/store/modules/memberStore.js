import { signin, getMemberInfo, reissue, sendKakao } from '@/api/member.js'

/* 회원 관련 상태 관리(vuex) */
const memberStore = {
    namespaced: true,
    state: {
        isSignin: false, //로그인 여부
        accessToken: '',
        // 에러 방지용 null값 미리 넣어주기
        memberInfo:{ 
          id:null, 
          email : "",
          password: "",
          nickname: "", 
          name: "",
          gender:null,
          phone: "", 
          height:null, 
          weight: null,
          top:null,
          bottom:null,
          shoeSize:null,
          authority: "",
          profile:null } ,
      },
    getters: {
      checkMemberInfo: function(state) { return state.memberInfo },
      checkisSignin: function(state) {
        state.isSignin = (localStorage.getItem("accessToken")) ? true : false;
        return state.isSignin;
      },
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
      },
      SIGNOUT: function(state){
        state.accessToken='';
        state.isSignin=false;
      }
    },
    actions: {
      signout: function({commit}) {
        commit('SIGNOUT')
      },
  
      async memberConfirm({ commit }, member) { //로그인(jwt토큰 받기)
        await signin(member, (response) => {
          if(response.status == 200) { //로그인 성공일 경우
            commit("SIGNIN", true);
            commit("SET_IS_ACCESSTOKEN", response.data["accessToken"]);
            //토큰을 로컬 스토리지에 저장(저장소는 아직도 고민중,, 로컬? 세션? 쿠키?)
            localStorage.setItem("grantType", response.data["grantType"]);
            localStorage.setItem("accessToken", response.data["accessToken"]); //로컬 스토리지에 accessToken 저장
            localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
            localStorage.setItem("refreshToken", response.data["refreshToken"]); //로컬 스토리지에 refreshToken 저장

            const now = new Date(Date.now());
            localStorage.setItem("lastDate", now); //마지막 접속일(로그인 시 재기록)
            localStorage.setItem("now", now); //최근 접속일
            localStorage.setItem("refreshDate", new Date(now.setDate(now.getDate()+7))); //refresh만료일
            
          } else { //로그인 실패일 경우
            commit("SIGNIN", false);
          }
        },
        () => {}
        );
      },
  
      async signInMemberInfo({ commit }, accessToken) { //로그인한 사용자 정보 받기
        await getMemberInfo(accessToken, (response) => {
          if(response.status == 200) {
            commit("SET_MEMBER_INFO", response.data);
          }
        },() => {});
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
            localStorage.setItem("grantType", response.data["grantType"]); //이 값은 바뀌지 않을 듯함
            localStorage.setItem("accessToken", response.data["accessToken"]);
            localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
            localStorage.setItem("refreshToken", response.data["refreshToken"]);

            const now = new Date(Date.now());
            localStorage.setItem("lastDate", now); //마지막 접속일(로그인 시 재기록)
            localStorage.setItem("now", now); //최근 접속일
            localStorage.setItem("refreshDate", new Date(now.setDate(now.getDate()+7)));
          }
        },
        () => {});
      },

      async sendKakaoToken({ commit }, accessToken) { //카카오 토큰 서버로 보내기
        await sendKakao(accessToken, (response) => {
          if(response.status == 200) {
            //토큰을 로컬 스토리지에 저장(쿠키에 저장도 가능)
            commit("SIGNIN", true);
            localStorage.setItem("grantType", response.data["grantType"]);
            localStorage.setItem("accessToken", response.data["accessToken"]); //로컬 스토리지에 accessToken 저장
            localStorage.setItem("accessTokenExpiresIn", response.data["accessTokenExpiresIn"]);
            localStorage.setItem("refreshToken", response.data["refreshToken"]); //로컬 스토리지에 refreshToken 저장
          }
        },
        () => {});
      }
    },
    modules: {
    
    }
  };


export default memberStore;