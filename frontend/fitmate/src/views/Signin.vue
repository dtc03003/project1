<template>
    <div id="app">
        <b-container class="bv-example-row mt-3">
            <!-- <b-row>
                    <h1 id="signinTitle">Fitmate</h1>
            </b-row> -->
            <b-row>
                <b-col></b-col>
                <b-col class="col-6">
                    <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
                        <h1 id="signinTitle">Fitmate</h1>
                        <b-form class="text-center">

                            <b-form-group>

                                <!-- 이메일 입력 -->
                                <b-input-group class="mt-3 input">
                                    <b-form-input type="email" id="email" ref="email" v-model="email" required placeholder="이메일" @keyup.enter="checkForm()">
                                    </b-form-input>
                                    <b-input-group-append v-if="email">
                                        <b-button variant="white" @click="remove(0)">
                                            <b-icon icon="x-circle-fill" class="xcircle"></b-icon>
                                        </b-button>
                                    </b-input-group-append>
                                </b-input-group>

                                <!-- 비밀번호 입력 -->
                                <b-input-group class="mt-3">
                                    <b-form-input type="password" id="password" ref="password" v-model="password" required placeholder="비밀번호" @keyup.enter="checkForm()">
                                    </b-form-input>
                                    <b-input-group-append v-if="password">
                                        <b-button variant="white" @click="remove(1)">
                                            <b-icon icon="x-circle-fill" class="xcircle"></b-icon>
                                        </b-button>
                                    </b-input-group-append>
                                </b-input-group>

                            </b-form-group>

                            <div class="mt-4" id="warning" role="alert" align="left" v-if="isAlert">
                                이메일과 비밀번호를 확인해주세요.
                            </div>

                            <!-- 로그인 버튼 -->
                            <b-button id="submitBtn" block class="mt-3 mb-3" @click="checkForm()">
                                로그인
                            </b-button>

                        </b-form>

                        처음이신가요?
                    <b-button type="button" variant="link" class="text-decoration-none" id="goJoin" @click="moveSignup">
                        회원가입
                    </b-button>
                    </b-card>
                </b-col>
                <b-col></b-col>
            </b-row>
            
            <!-- <b-row>
                <b-col>
                    처음이신가요?
                    <b-button type="button" variant="link" class="text-decoration-none" id="goJoin" @click="moveSignup">
                        회원가입
                    </b-button>
                </b-col>
            </b-row> -->
        </b-container>
    </div>
</template>
<script>
import EmailValidator from "email-validator"; //이메일 유효성 검사
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
            isLogin: false,
            isAlert: false,
        }
    },
    created() {

    },
    watch: {
        email: function() {
            this.email = this.email.trim().toLowerCase(); //대문자 방지
        }
    },
    methods: {
        checkForm() { //이메일, 비밀번호 제대로 작성하였는지 확인
            //이메일
            if (this.email.trim().length >= 0 && !EmailValidator.validate(this.email))
                this.error.email = true;
            else this.error.email = false;

            //비밀번호 -- 비밀번호 규칙 알아야 함
            if(this.password.trim().length == 0)
                this.error.password = true;
            else this.error.password = false;

            let isLogin = true;
            Object.values(this.error).map(v => {
                if(v) isLogin = false;
            });
            this.isLogin = isLogin;

            if(this.isLogin) this.login();
            else this.isAlert = true;

        },
        login() { //로그인 기능
            const memberInfo = { //로그인 정보
                email: this.email,
                password: this.password,
                //nickname(varchar), name(varchar), gender(int) 필수컬럼이므로 같이 넘겨야 하는가?
            }
            console.log(memberInfo);
            this.$router.push({name: "Home"}); //임시로 지정
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

<style scoped>
@font-face {
    font-family: 'Cafe24Ohsquare';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Cafe24Ohsquare.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

#app { text-align: center; padding: 5%; }
#signinTitle { font-family: "Cafe24Ohsquare", fantasy; }
#goJoin { color: black; }
.xcircle { color: gray; }
#submitBtn { background-color: #7e7fb9; border-color: gray; width: 100%;} /* 올해의 색상코드: #6667AB */
#warning { color: red; font-size: 10pt; }
</style>