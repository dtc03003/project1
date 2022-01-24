<template>
    <div class="about">
        <img src="@/assets/kakao_login_btn.png" alt="카카오 로그인 버튼" @click="kakaoLogin()" />
    </div>
</template>

<script>
const params = {}; //간편로그인 시 인증 코드 받을 부분

export default {
    name: "KakaoLogin",
    data() {
        return {
            redirect_uri: "http://localhost:8080/signin", //url은 추후 바뀔 수 있음
        }
    },
    created() {
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
        console.log(params);
    },
    methods: {
        kakaoLogin() {
            const params = {
                redirectUri: this.redirect_uri,
            };
            window.Kakao.Auth.authorize(params);
        },
    }
}
</script>
