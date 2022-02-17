<template>
  <div id="main">
    <!-- navbar 전체 padding값 기본은 30px, 조절하기 나름 -->
    <nav id="nav" class="navbar navbar-expand-md" style="padding:25px;">
      <div class="container-fluid p-0 justify-content-center" style="height:7em">
        <div class="row" style="width:100%;">

          <!-- 로고 중앙 정렬, 누르면 홈 페이지로 가도록 -->
          <div id="mainbar" class="justify-content-center">
            <div id="logo" @click="checkToken">
              <!-- <router-link to="/"><img src="@/assets/Fitmate.png" style="width:9rem;" alt=""></router-link> -->
              <a class="navbar-brand" style="font-size:130%;"><router-link to="/">Fitmate</router-link></a>
            </div>
          </div>
          <!-- 우측 사이드 정렬 -->
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
                <a class="mx-4"><router-link to="/qna">Q&A</router-link></a>
              </li>
              <li class="nav-item">
                <a class="mx-4"><router-link to="/notice">Notice</router-link></a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>

    <body>
      <div id="wrap" >
        <!-- 본문 section -->
        <section>
          <div>
            <router-view></router-view>
          </div>
        </section>


        <!-- 푸터 footer -->
        <footer id="footer" class="footer">
          <!-- 세 섹션으로 나누자 -->
          <div class="row">
            <div id="instant" class="col-12 col-md-3" >
              <div class="d-flex justify-content-center">
                <img id="footerimg" src="@/assets/hanger.png">
              </div>
            </div>
            <div id="footerfont" class="col-12 col-md-7 px-4">
              <div><h4 class="d-inline">ⓒFitmate </h4><h6 class="d-inline"> We're Fitmate!!!</h6></div>
              <div><span>Fitmate's mates : Backend-서지원, 이홍준 | Frontend-김동현, 박현진, 서지원, 정종혁</span></div>
              <div><p>본 사이트의 아이디어, 디자인을 포함한 콘텐츠는 저작권법의 보호를 받는지는 잘 모르겠습니다.</p></div>
              <div><p>아무튼 퍼가지 마세요..</p></div>
            </div>
            <div id="footersns" class="col-12 col-md-2 d-flex justify-content-center">
              <v-icon class="me-2">mdi-instagram</v-icon>
              <v-icon class="me-2">mdi-youtube</v-icon>
              <v-icon class="me-2">mdi-twitter</v-icon>
              <v-icon class="me-2">mdi-facebook</v-icon>
            </div>
          </div>
        </footer>
      </div>
    </body>
    
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
        .then(()=> {
          this.portfolioconfirm = true
        })
        .catch(()=> {})
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
// 메인
#main {
  min-width: 320px;


// 네비게이션 바
}
#nav {
  padding: 30px;
  background-color: rgb(102,103, 171);
  a {
    font-weight: bold;
    color: #d7dee6;
    text-decoration: none;
  }
  font-family: 'LeferiPoint-WhiteObliqueA';
  font-weight: bold;
}

// 네브바 내부 하단 정렬된 글꼴
#mainbar {
  display: inline-flex;
  // font-family: 'GangwonEdu_OTFBoldA';
  font-family: 'LeferiPoint-BlackObliqueA'; 
}

// 페이지 제목
#subbar {
  display: inline-flex;
  padding-top: 3rem;
  font-family: 'LeferiPoint-BlackObliqueA';
}

// input, dropdown 등에 적용
#inputtext{
  font-family: 'LeferiPoint-WhiteObliqueA';
  font-weight: bold;
}

// 댓글
#comment{
  font-family: 'Pretendard-Regular';
}

// 아바타 하단 이름 등에 적용
#name{
  font-family: 'LeferiPoint-WhiteObliqueA';
  font-weight: bold;
}

// 로그인 해주세요 등등 알람 문구
#loginplz{
  font-family: 'GangwonEdu_OTFBoldA';
}

// 후버 효과니까 지우면 안돼요
li:hover{
  backface-visibility: hidden;
  transform: scale(1.08, 1.08);
  opacity: 1;  
}


// 아래부터는 footer영역에 관해서
#wrap{
  min-height: 100vh;
  position: relative;
  width: 100%;
}

#footer{
  width: 100%;
  height: 10rem;
  bottom: 0px;
  position: absolute;
  background-color: #cbc7cf;
  align-content: center;
}

section{
  padding-bottom: 15rem;
}

html, body {
  margin: 0;
  padding: 0;
}

#footertext{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  color: #6d6d6d;
  background-color: #cbc7cf;
}

#footerfont{
  font-family: 'LeferiPoint-WhiteObliqueA';
  background-color: #cbc7cf;
  padding-top: 2rem;
  h4 {
    font-family: 'LeferiPoint-BlackObliqueA';
  }
  span {
    font-size: 1rem;
  }
  p {
    font-size: 0.8rem;
  }
}

#footerimg {
  display: flex;

  // padding-top: 0.3rem;
  justify-content: center;
  align-content: center;
  width:9rem;
}

#footersns {
  background-color: #cbc7cf;
}

#instant {
  background-color: #cbc7cf;
}

//버튼 색 입히기(애니메이션)
$ease_out: cubic-bezier(0.165, 0.84, 0.44, 1);
$btn-color: rgb(102,103, 171);
@mixin transition() {
    transition: 700ms $ease_out;
    &:hover{
        transition: 400ms $ease_out;
    }
}
#gradient-btn{
    display: inline-block;
    padding: 0.75em 1.25em;
    border-radius: 0.5rem;
    color: $btn-color;
    margin-top:2rem;
    font-weight: bold;
    font-size: 0.8rem;
    letter-spacing: 2px;
    text-transform: uppercase;
    text-decoration: none;
    background: linear-gradient(to right, rgba($btn-color, 0) 25%, rgba($btn-color, .8) 75%);
    background-position: 1% 50%;
    background-size: 400% 300%;
    border: 1px solid #8763FB;
    @include transition;
    
    &:hover{
        color: white;
        color: #fff;
        background-position: 99% 50%;
    }
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
    font-family: 'LeferiPoint-WhiteObliqueA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-WhiteObliqueA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
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

@font-face {
    font-family: 'LeferiPoint-BlackObliqueA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackObliqueA.woff') format('woff');
    font-weight: normal;
}

@font-face {
    font-family: 'Pretendard-SemiBold';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff') format('woff');
    font-weight: 600;
    font-style: normal;
}

@font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
}
</style>