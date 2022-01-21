<template>
    <div id="app">
        <b-container class="bv-example-row mt-5">
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
                                <h4 id="signinTitle" align="left" class="mt-3">이메일(ID)</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="email" ref="email" v-model="email" required placeholder="이메일" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">닉네임</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="nickname" ref="nickname" v-model="nickname" required placeholder="이메일"  @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">비밀번호</h4>
                                <b-input-group >
                                    <b-form-input type="password" id="password" ref="password" v-model="password" required placeholder="비밀번호" maxlength="20" @blur="passwordValid" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!passwordValidFlag">
                                    유효하지 않은 비밀번호 입니다.
                                </div>
                                
                                <h4 id="signinTitle" align="left" class="mt-3">비밀번호 확인</h4>
                                <b-input-group >
                                    <b-form-input type="password" id="pwdcheck" ref="pwdcheck" v-model="pwdcheck" required placeholder="비밀번호확인" maxlength="20" @blur="pwdcheckValid" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!pwdcheckFlag">
                                    비밀번호가 동일하지 않습니다.
                                </div>
                            
                                <h4 id="signinTitle" align="left" class="mt-3">휴대전화</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="email" ref="email" v-model="email" required placeholder="휴대전화" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">은행</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="email" ref="email" v-model="email" required placeholder="은행" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">계좌번호</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="email" ref="email" v-model="email" required placeholder="계좌번호" @keyup.enter="checkForm()">
                                    </b-form-input>
                                </b-input-group>
                                
                            </b-form-group>
                            <!-- 회원가입 버튼 -->
                            <b-button id="submitBtn" block class="mt-3 mb-3" @click="checkForm()">
                                회원가입
                            </b-button>
                        </b-form>
                    </b-card>
                </b-col>
                <b-col></b-col>
            </b-row>
        </b-container>
    </div>
</template>
<script>
import EmailValidator from "email-validator"; //이메일 유효성 검사
export default {
    name: "Stylist",
    data() {
        return {
            email: '',
            password: '',
            nickname: '',
            pwdcheck: '',
            error: {
                email: false,
                password: false
            },
            isLogin: false,
            passwordValidFlag: true,
            pwdcheckFlag: true,
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
            else {
                console.log("이메일과 비밀번호를 확인해주세요");
                alert("이메일과 비밀번호를 확인해주세요");
            }
        },
        passwordValid () {
            if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$/.test(this.password)) {
                this.passwordValidFlag = true 
            } else {
                this.passwordValidFlag = false 
            } 
        },
        pwdcheckValid () {
            if (this.password == this.pwdcheck){
                this.pwdcheckFlag = true
            } else {
                this.pwdcheckFlag = false
            }
        }

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
</style>