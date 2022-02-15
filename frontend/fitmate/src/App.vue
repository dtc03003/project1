<template>
  <div id="main">
    <!-- navbar 전체 padding값 기본은 30px, 조절하기 나름 -->
    <nav id="nav" class="navbar navbar-expand-md" style="padding:25px;">
      <div class="container-fluid p-0 justify-content-center" style="height:7em">
        <div class="row" style="width:100%;">
          <!-- 로고 중앙 정렬, 누르면 홈 페이지로 가도록 -->
          <div id="mainbar" class="justify-content-center">
            <div id="logo" @click="checkToken">
              <router-link to="/"><img src="@/assets/Fitmate.png" style="width:9rem;" alt=""></router-link>
              <!-- <a class="navbar-brand" style="font-size:130%;"><router-link to="/">Fitmate</router-link></a> -->
            </div>
          </div>
          <!-- 우측 사이드 정렬 -->
          <!-- 사이드로 밀기 위해서는 토글 버튼이.. 다 사라짐... 넘나 힘들... 토글 고민해야 함-->
          <!-- 로그인 후 보여지는 것: Sign out, My page -->
          <div v-if="checkisSignin">
            <ul class="navbar-nav me-auto mb-md-0 justify-content-end">
              <li class="nav-item">
                <a class="nav-link"><router-link to="#" @click.native="signout">Sign out</router-link></a>
              </li>
              <!-- 일반회원일 경우 -->
              <li v-if="checkMemberInfo.authority=='ROLE_MEMBER'" class="nav-item">
                <a class="nav-link"><router-link to="/mypage">My Page</router-link></a>
              <!-- </li> -->
              <!-- 스타일리스트? -->
              <li v-if="checkMemberInfo.authority=='ROLE_STYLIST' && !portfolioconfirm" class="nav-item">
                <a class="nav-link"><router-link :to="`/portfolionope/${checkMemberInfo.nickname}`">Portfolio</router-link></a>
              </li>
              <li v-if="checkMemberInfo.authority=='ROLE_STYLIST' && portfolioconfirm" class="nav-item">
                <a class="nav-link"><router-link :to="`/portfolio/${checkMemberInfo.nickname}`">Portfolio</router-link></a>
              </li>
            </ul>
          </div>
          <!-- 로그인 전 보여지는 것: Sign up, Sign in -->
          <div v-else>
            <ul class="navbar-nav me-auto mb-2 mb-md-0 justify-content-end">
              <li class="nav-item">
                <a class="nav-link"><router-link to="/Signup">Sign up</router-link></a>
              </li>
              <li class="nav-item">
                <a class="nav-link"><router-link to="/signin">Sign in</router-link></a>
              </li>
            </ul>
          </div>
          <!-- 언더라인 정렬, 간격은 추후 논의할 것 -->
          <div>
            <ul class="navbar-nav me-auto mb-2 mb-md-0 justify-content-center">
              <li class="nav-item">
                <a class="mx-4"><router-link to="/stylist">Stylist</router-link></a>
              </li>
              <li class="nav-item">
                <a class="mx-4"><router-link to="/stylebook">Style book</router-link></a>
              </li>
              <li class="nav-item">
                <a class="mx-4"><router-link to="/qna">QnA</router-link></a>
              </li>
              <li class="nav-item">
                <a class="mx-4"><router-link to="/notice">Notice</router-link></a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
    <router-view></router-view>

    <footer>
      <p>아래는 시험용</p>
      {{checkMemberInfo}}
    </footer>
    
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters, mapActions } from 'vuex';
import memberStore from '@/store/modules/memberStore'
import { FITMATE_BASE_URL } from "@/config";
export default {
  name: 'App',
  data: function() {
    return {
      memberStore,
      portfolioconfirm: '',
    }
  },
  created() {
    this.checkToken();
  },
  mounted() {
    this.portfoliobeing()
  },
  methods:{
    ...mapActions("memberStore", ["reissueToken"]),
    signout: function() {
      this.isSignin = false
      localStorage.clear()
      this.$store.dispatch('signout')      
      this.$router.push({name:'Signin'})
      window.location.reload()
    },
    portfoliobeing() {
      if (this.checkMemberInfo.authority == 'ROLE_STYLIST') {
        axios({
          url: `${FITMATE_BASE_URL}/api/v1/portfolio/${this.checkMemberInfo.nickname}`,
          method: 'get',
        })
        .then((res)=> {
          this.portfolioconfirm = true
          console.log(res.data)
        })
        .catch(()=> {console.log('포트폴리오 없음')})
      } else {
        return
      }
    },
    checkToken() {
      const refreshDate = localStorage.getItem("refreshDate") ? new Date(localStorage.getItem("refreshDate")) : null;
      const lastDate = localStorage.getItem("lastDate") ? new Date(localStorage.getItem("lastDate")) : null;
      const now = new Date(Date.now());
      if(refreshDate && refreshDate <= now) { //현재 접속이 refreshToken 만료일/시간과 같거나 더 지난 경우
        localStorage.clear();
        window.location.reload();
      }
      else {
        ///마지막 접속일로부터 23시간 이상일시 -> clear
        //그렇지 않다면 accesstoken토큰 재발급 및 lastDate 갱신
        if(lastDate) {
          lastDate.setHours(lastDate.getHours()+23); //23시간 후(토큰 유효기간 24시간)
          if(lastDate <= now) {
            localStorage.clear();
            window.location.reload()
          }else {
            this.reissueToken();
          }
        }
      }
    }
  },
  computed:{
    ...mapGetters (
      'memberStore', ["checkisSignin", 'checkMemberInfo']
    ),
  },
  watch: {
    checkMemberInfo: function() {
      this.portfoliobeing()
    }
  }
};
</script>

<style lang="scss">
#main { min-width: 320px; }
#nav {
  padding: 30px;
  background-color: rgb(102,103, 171);
  a {
    font-weight: bold;
    color: #d7dee6;
    text-decoration: none;
  }
}

#mainbar {
  display: inline-flex;
}

#subbar {
  display: inline-flex;
  // justify-content: center;
}

li:hover{
  backface-visibility: hidden;
  transform: scale(1.08, 1.08);
  opacity: 1;  
}

/* 폰트 영역(나중에 따로 분리) */
@font-face {
    font-family: 'Cafe24Ssurround';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/Cafe24Ssurround.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'ChosunGu';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@1.0/ChosunGu.woff') format('woff');
}

@font-face {
    font-family: 'GangwonEdu_OTFBoldA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'Cafe24Ohsquare';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Cafe24Ohsquare.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'ImcreSoojin';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.3/ImcreSoojin.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'SDSamliphopangche_Basic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Basic.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>