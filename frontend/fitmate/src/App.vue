<template>
  <div>
    <div>
      <b-navbar toggleable="md" type="dark" variant="info" style="height:70px;">
        <div class="container-fluid">
          <b-navbar-brand><router-link to="/"><img src="@/assets/logo.png" style="width:60px;" alt=""></router-link></b-navbar-brand>
            <div id="nav">
              <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/">Home</router-link></b-nav-item>
                  <!-- 여기부분 드롭다운으로 잠시 추가했습니다 -->
                  <b-dropdown text="Signup">
                    <b-dropdown-item><router-link to="/Signup/normal" style="text-decoration:none">일반회원</router-link></b-dropdown-item>
                    <b-dropdown-divider></b-dropdown-divider>
                    <b-dropdown-item><router-link to="/Signup/stylist" style="text-decoration:none">스타일리스트</router-link></b-dropdown-item>
                  </b-dropdown>
                </b-navbar-nav>
              </b-collapse>
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
