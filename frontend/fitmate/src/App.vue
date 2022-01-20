<template>
  <div>
    <div>
      <b-navbar toggleable="md" type="dark" variant="info" style="height:70px;">
        <div class="container-fluid">
          <div id="nav">
              <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
              <b-collapse class="justify-content-end" id="nav-collapse" is-nav>
                <b-navbar-nav>
                  <b-nav-item><router-link to="/">Home</router-link></b-nav-item>
                  <b-nav-item><router-link to="/accounts/Signup">Signup</router-link></b-nav-item>
                 </b-navbar-nav>
              </b-collapse>
          </div>
        </div>
      </b-navbar>
    </div>
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
