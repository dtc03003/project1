<template>
  <div>
    <div>
      <b-navbar toggleable="md" type="dark" variant="info" style="height:70px;">
        <div class="container-fluid">
          <!-- 브랜드 버튼 클릭해도 홈으로 가도록 설정 -->
          <b-navbar-brand><router-link to="/"><img src="@/assets/logo.png" style="width:60px; justify-contents:center;" alt=""></router-link></b-navbar-brand>

          <!-- 네브바 카테고리들 -->
          <div id="nav">
            <!-- <span v-if="isLogin"> -->
              <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/signup">Sign up</router-link></b-nav-item>
                  <b-nav-item><router-link to="/signin">Sign in</router-link></b-nav-item>
                </b-navbar-nav>
              </b-collapse>
            <!-- </span> -->
            <!-- <span v-else> -->
              <!-- <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/signout">Sign Out</router-link></b-nav-item>
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
