<template>
  <div>
    <nav id="nav" class="navbar navbar-expand-lg">
      <div class="container-fluid">
        <a class="navbar-brand"><router-link to="/"><h1 style="text-decoration:none;">Fitmate</h1></router-link></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="#"><router-link to="/signup">Sign up</router-link></a>
            </li>
            <li class="nav-item">
              <a class="nav-link"><router-link to="/signin">Sign in</router-link></a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
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

<style>

nav {
  background-color: rgb(110, 88, 150);
  text-decoration: none;
}

</style>