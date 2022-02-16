<template>
    <div id="app">
        <b-container class="mt-5">
            <b-row>
                <b-col class="col-12">
                    <b-card class="text-center mt-3" style="max-width: 90rem" align="left">
                        <h1 id="signinTitle">Fitmate 회원가입</h1>
                        <b-row>
                            <b-col cols="md-6 12">
                                <b-card id="cards" border-variant="light">
                                <router-link class="navbar-brand cardouter" :to="{ name: 'SignupNormal' }">
                                    <img id="normal" class="signupimg" src="@/assets/normal.png" width="100%" height="60%">
                                    <p class="signuptext"> 일반회원</p>
                                </router-link>
                            </b-card>
                            </b-col>
                            <b-col cols="md-6 12">
                                <b-card id="cards" border-variant="light">
                                <router-link class="navbar-brand" :to="{ name: 'SignupStylist' }">
                                    <img id="stylist" class="signupimg" src="@/assets/stylist.png" width="100%" height="60%">
                                    <p class="signuptext">스타일리스트</p>
                                </router-link>
                            </b-card>
                            </b-col>
                        </b-row>
                    </b-card>
                </b-col>
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
#app { text-align: center; max-width: 100%; min-width: 320px; }
#signinTitle { font-family: "SDSamliphopangche_Basic", "ImcreSoojin", fantasy; }
#cards {
    margin: 5px;
    max-width: 100%;
    display: inline-block;
    position:relative;
}
p.cardTitle { font-family: "GangwonEdu_OTFBoldA", fantasy; font-size: 1.5rem; text-align: center;} /*폰트 크기 수정 필수 */
img.signupimg:hover {opacity: 0.5;}
img.signupimg:hover + .signuptext {visibility: visible;}
.cardouter {position: relative;}
.signuptext {text-align: center; color: black; font-size: 2rem; position:absolute; font-family: "GangwonEdu_OTFBoldA", fantasy;
            top: 50%; left: 50%; transform: translate(-50%, -50%); visibility: hidden; color: rgb(84, 86, 182);}
</style>