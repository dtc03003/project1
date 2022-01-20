<template>
  <div>
    <div>
      <b-navbar toggleable="md" type="dark" variant="info" style="height:70px;">
        <div class="container-fluid">
          <!-- 브랜드 버튼 클릭해도 홈으로 가도록 설정 -->
          <!-- <b-navbar-brand><router-link to="/"><img src="@/assets/youth_logo.png" style="width:60px;" alt=""></router-link></b-navbar-brand> -->

          <!-- 네브바 카테고리들 -->
          <div id="nav">
            <!-- <span v-if="isLogin"> -->
              <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/">Home</router-link></b-nav-item>
                  <!-- <b-nav-item><router-link to="/outcome">요금청구</router-link></b-nav-item>
                  <b-nav-item><router-link v-if="currentUser.authority==2 | this.currentUser.authority==4 | currentUser.is_superuser" to="/income">수입입력</router-link></b-nav-item>
                  <b-nav-item><router-link to="/book">장부 확인</router-link></b-nav-item>
                  <b-nav-item><router-link to="/board">자유게시판</router-link></b-nav-item>
                  <b-nav-item><router-link v-if="currentUser.authority==3 | this.currentUser.authority==5 | currentUser.is_superuser" to="/manager">관리자용</router-link></b-nav-item>
                  <b-nav-item><router-link @click.native="logout" to="#">Logout</router-link></b-nav-item>-->
                </b-navbar-nav>
              </b-collapse>
            <!-- </span> -->
            <!-- <span v-else> -->
              <!-- <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/login">로그인</router-link></b-nav-item>
                  <b-nav-item><router-link to="/signup">회원가입</router-link></b-nav-item>
                </b-navbar-nav>
              </b-collapse>             -->
            <!-- </span> -->
          </div>
        </div>
      </b-navbar>
    </div>
    <!-- <router-view @login="isLogin=true"/>  -->
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
