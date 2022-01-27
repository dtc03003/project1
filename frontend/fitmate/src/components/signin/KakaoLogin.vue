<template>
    <div class="about">
        <img src="@/assets/kakao_login_btn.png" alt="카카오 로그인 버튼" @click="kakaoLogin()" />
    </div>
</template>

<script>
const codes = {}; //간편로그인 시 인증 코드 받을 부분

export default {
    name: "KakaoLogin",
    data() {
        return {
            redirect_uri:"http://localhost:8080/signin", //url은 추후 바뀔 수 있음
        }
    },
    created() {
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { codes[key] = value; });
        if(codes['code']) {
            console.log("kakao 인증코드 \n" + codes['code']);
            window.Kakao.Auth.login({
                success: this.getProfile
            });
            // window.Kakao.Auth.setAccessToken(codes['code']); //로그인 이후 할일!
        }
    },
    methods: {
        kakaoLogin() {
            const params = {
                redirectUri: this.redirect_uri,
            };
            window.Kakao.Auth.authorize(params);
        },
        getProfile(authObj) {
            console.log(authObj);
            window.Kakao.API.request({
                url: '/v2/user/me',
                success: res => {
                    console.log(res);
                }
            });
        }
    }
}
</script>

<style scoped>
img {width: 80%;};
</style>
