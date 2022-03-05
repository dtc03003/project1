<template>
    <div>
        <img src="@/assets/kakao_login_btn.png" alt="카카오 로그인 버튼" @click="kakaoLogin()" />
    </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
const memberStore = "memberStore";
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
            window.Kakao.Auth.login({
                // success: this.getProfile
                success: this.getKakaoToken
            });
            // window.Kakao.Auth.setAccessToken(codes['code']); //로그인 이후 할일!
        }
    },
    computed: {
        ...mapGetters(memberStore, ["checkisSignin"]),
    },
    methods: {
        ...mapActions(memberStore, ["sendKakaoToken", "signInMemberInfo"]),
        kakaoLogin() {
            const params = {
                redirectUri: this.redirect_uri,
            };
            window.Kakao.Auth.authorize(params);
        },
        async getKakaoToken(authObj) {
        // getKakaoToken(authObj) {
            const kakao = {
                access_token: authObj['access_token'],
                expires_in: authObj['expires_in'],
                refresh_token: authObj['refresh_token'],
                refresh_token_expires_in: authObj['refresh_token_expires_in'],
                scope: authObj['scope'],
                token_type: authObj['token_type'],
            };
            await this.sendKakaoToken(kakao); //kakao 로그인 토큰 값 넘기기
            let accessToken = localStorage.getItem("accessToken");
            if(this.checkisSignin) {
                await this.signInMemberInfo(accessToken); //발급받은 accessToken으로 사용자 정보 받기
                this.$router.push({name: "Home"}); //로그인 성공시 메인 페이지로 이동
            }
        },
        
        // getProfile(authObj) {
        //     console.log(authObj);
        //     window.Kakao.API.request({
        //         url: '/v2/user/me',
        //         success: res => {
        //             console.log(res);
        //         }
        //     });
        // }
    }
}
</script>

<style scoped>
div {max-width: 70%; margin: 0 auto;}
img {width: 100%;}
</style>
