<template>
    <div id="app">
        <b-card class="text-center mt-2 col-12 col-md-6" id="card">
            <h1 id="signinTitle">로그인</h1>
            <b-form class="text-center form">

            <b-form-group>
                <!-- 이메일 입력 -->
                <b-input-group class="mt-3 input">
                    <b-form-input type="email" id="email" ref="email" v-model="email" required placeholder="이메일" @keyup.enter="checkForm()" @keypress="isAlert = false">
                    </b-form-input>
                    <b-input-group-append v-if="email">
                        <b-button variant="white" @click="remove(0)">
                            <b-icon icon="x-circle-fill" class="xcircle"></b-icon>
                        </b-button>
                    </b-input-group-append>
                </b-input-group>

                <!-- 비밀번호 입력 -->
                <b-input-group class="mt-3">
                    <b-form-input type="password" id="password" ref="password" v-model="password" required placeholder="비밀번호" @keyup.enter="checkForm()" @keypress="isAlert = false">
                    </b-form-input>
                    <b-input-group-append v-if="password">
                        <b-button variant="white" @click="remove(1)">
                            <b-icon icon="x-circle-fill" class="xcircle"></b-icon>
                        </b-button>
                    </b-input-group-append>
                </b-input-group>

                </b-form-group>

                <div class="mt-4" id="warning" role="alert" align="left" v-show="isAlert">
                    이메일과 비밀번호를 확인해주세요.
                </div>

                <!-- 로그인 버튼 -->
                <b-button id="submitBtn" block class="mt-2 mb-3" @click="checkForm()">
                    로그인
                </b-button>
            </b-form>

            <div class="hr-sect">또는</div>
                        
            <!--간편 로그인 시작-->
            <kakao-login />
            <!--간편 로그인 끝-->

            <div class="signup mt-2">
                처음이신가요?
                <p type="button" variant="link" class="text-decoration-none signup" id="goSignup" @click="moveSignup">
                    회원가입
                </p>
            </div>
        </b-card>
    </div>
</template>
<script>
import { mapState, mapActions } from "vuex";
import EmailValidator from "email-validator"; //이메일 유효성 검사
import KakaoLogin from "@/components/signin/KakaoLogin.vue";
const memberStore = "memberStore";

export default {
    name: "Signin",
    data() {
        return {
            email: '',
            password: '',
            error: {
                email: false,
                password: false
            },
            isCheck: false,
            isAlert: false,
        }
    },
    components: {
        KakaoLogin,
    },
    created() {

    },
    computed: {
        ...mapState(memberStore, ["isSignin", "memberInfo"]),
    },
    watch: {
        email: function() {
            this.email = this.email.trim().toLowerCase(); //대문자 방지
        },
    },
    methods: {
        ...mapActions(memberStore, ["memberConfirm", "signInMemberInfo"]),
        async login() { //로그인 기능
            const memberInfo = { //로그인 정보
                email: this.email,
                password: this.password,
            }
            await this.memberConfirm(memberInfo); //로그인 시도
            let accessToken = localStorage.getItem("accessToken");
            if(this.isSignin) {
                await this.signInMemberInfo(accessToken); //발급받은 accessToken으로 사용자 정보 받기
                this.$router.push({name: "Home"}); //로그인 성공시 메인 페이지로 이동
            }else {
                this.isAlert = true;
            }

        },
        checkForm() { //이메일, 비밀번호 제대로 작성하였는지 확인
            //이메일
            if (this.email.trim().length >= 0 && !EmailValidator.validate(this.email))
                this.error.email = true;
            else this.error.email = false;

            //비밀번호 -- 비밀번호 규칙 알아야 함
            if(this.password.trim().length == 0)
                this.error.password = true;
            else this.error.password = false;

            let isCheck = true;
            Object.values(this.error).map(v => {
                if(v) isCheck = false;
            });
            this.isCheck = isCheck;

            if(this.isCheck) this.login();
            else this.isAlert = true;

        },
        remove(num) { //작성한 내용 초기화(이메일, 비밀번호)
            if(num == 0 && this.email != null) this.email = '';
            else if(num == 1 && this.password != null) this.password = '';
        },
        moveSignup() { //회원가입 창으로 이동
            this.$router.push({name: "Signup"});
        },
    }
}
</script>

<style lang="scss" scoped>
$main-color: #8763FB;
#app { text-align: center; padding: 5%; min-width: 320px; margin: 0 auto; display: flex; justify-content: center; position: relative; }
#signinTitle, #warning, .signup, #submitBtn { font-family: "GangwonEdu_OTFBoldA", fantasy; }
#goSignup { color: black; font-weight: bold;}
#card {width: 100%; padding: 5%; position: relative; }
.xcircle { color: gray; }
.form {width: 100%;}
#submitBtn { background: linear-gradient(to right, #8d8eeb, $main-color); width: 100%; border-color: $main-color; vertical-align: middle; font-size: 1.2rem;}
#warning { color: red; font-size: 1rem; }
.signup { font-size: 1rem; }
p { display: inline; }
.hr-sect {
	display: flex;
	flex-basis: 100%;
	align-items: center;
	color: rgba(0, 0, 0, 0.452);
	font-size: 12px;
	margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
	content: "";
	flex-grow: 1;
	background: rgba(0, 0, 0, 0.35);
	height: 1px;
	font-size: 0px;
	line-height: 0px;
	margin: 0px 16px;
}
p#goSignup:hover {color: $main-color;}
#email, #password, ::placeholder { font-family: "SDSamliphopangche_Basic", fantasy; }
</style>