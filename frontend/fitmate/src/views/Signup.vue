<template>
  <div id="app">
    <b-container class="bv-example-row mt-5">
        <b-row>
        <b-col></b-col>
        <b-col class="col-12">
            <b-card class="text-center mt-3" style="max-width: 90rem" align="left">
                <h1 id="signinTitle">Fitmate 회원가입</h1>
                <b-card style="max-width: 40rem; float:left;">
                    <router-link class="navbar-brand" :to="{ name: 'SignupNormal' }">
                        <img id="normal" src="@/assets/normal.png" width="500" height="700">
                    </router-link>
                    <br>
                    <h3 id="signinTitle">일반회원</h3> 
                </b-card>
                <b-card style="max-width: 40rem; float:right;">
                    <router-link class="navbar-brand" :to="{ name: 'SignupStylist' }">
                        <img id="stylist" src="@/assets/stylist.png" width="500" height="700">
                    </router-link>
                    <br>
                    <h3 id="signinTitle">스타일리스트</h3> 
                </b-card>
            </b-card>
        </b-col>
        <b-col></b-col>
    </b-row>    
    </b-container>
  </div>
</template>

<script>
export default {
    methods: {
        
    }
}
</script>

<style scoped>
@font-face {
    font-family: 'Cafe24Ohsquare';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Cafe24Ohsquare.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
#app { text-align: center; }
#signinTitle { font-family: "Cafe24Ohsquare", fantasy; }

</style>