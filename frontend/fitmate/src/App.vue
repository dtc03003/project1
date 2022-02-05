<template>
  <div>
    <!-- navbar 전체 padding값 기본은 30px, 조절하기 나름 -->
    <nav id="nav" class="navbar navbar-expand-md" style="padding:25px;">
      <div class="container-fluid p-0 justify-content-center" style="height:6em">
        <div class="row" style="width:100%;">
          <!-- 로고 중앙 정렬, 누르면 홈 페이지로 가도록 -->
          <div id="mainbar" class="col-12 justify-content-center">
            <div id="logo">
              <a class="navbar-brand" style="font-size:130%;"><router-link to="/">Fitmate</router-link></a>
            </div>
          </div>
          <!-- 우측 사이드 정렬 -->
          <!-- 사이드로 밀기 위해서는 토글 버튼이.. 다 사라짐... 넘나 힘들... 토글 고민해야 함-->
          <!-- 로그인 후 보여지는 것: Sign out, My page -->
          <div v-if="checkisSignin">
            <ul class="navbar-nav me-auto mb-2 mb-md-0 justify-content-end">
              <li class="nav-item">
                <a class="nav-link"><router-link to="#" @click.native="signout">Sign out</router-link></a>
              </li>
              <!-- 일반회원일 경우 -->
              <li v-if="checkMemberInfo.authority=='ROLE_MEMBER'" class="nav-item">
                <a class="nav-link"><router-link to="/mypage">My Page</router-link></a>
              </li>
              <!-- 스타일리스트? -->
              <li v-if="checkMemberInfo.authority=='ROLE_STYLIST'" class="nav-item">
                <a class="nav-link"><router-link to="/portfolio">Portfolio(임시)</router-link></a>
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
import { mapGetters } from 'vuex'
import memberStore from '@/store/modules/memberStore'

export default {
  name: 'App',
  data: function() {
    return {
      memberStore,
    }
  },
  methods:{
    signout: function() {
      this.isSignin = false
      localStorage.clear()
      this.$store.dispatch('signout')      
      this.$router.push({name:'Signin'})
      window.location.reload()
    }    
  },
  computed:{
    ...mapGetters (
      'memberStore', ["checkisSignin", 'checkMemberInfo']
    ),
  },
};
</script>

<style lang="scss">
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

// #logo {
//   height: 0;
// }

#subbar {
  display: inline-flex;
  // justify-content: center;
}

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
</style>