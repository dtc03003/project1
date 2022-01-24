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
          <!-- 아래는 로그인 전 보여지는 것 -->
          <div>
            <ul class="navbar-nav me-auto mb-2 mb-md-0 justify-content-end">
              <li class="nav-item">
                <a class="nav-link"><router-link to="/Signup">Sign up</router-link></a>
              </li>
              <li class="nav-item">
                <a class="nav-link"><router-link to="/signin">Sign in</router-link></a>
              </li>
            </ul>
          </div>
          <!-- 아래는 로그인 후 보여지는 것 -->
          <!-- <div>
            <ul class="navbar-nav me-auto mb-2 mb-md-0 justify-content-end">
              <li class="nav-item">
                <a class="nav-link"><router-link to="/signout">Sign out</router-link></a>
              </li>
              <li class="nav-item">
                <a class="nav-link"><router-link to="/mypage">My Page</router-link></a>
              </li>
            </ul>
          </div> -->
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
    <!-- 로그인하면 따로 보여줄 게 있다면? -->
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
</style>