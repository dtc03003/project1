<template>
    <div id="app">
        <b-container class="bv-example-row">
            <b-row>
                <b-col></b-col>
                <b-col class="col-6">
                    <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
                        <h1 id="signinTitle">Fitmate (스타일리스트)</h1>
                        <b-form class="text-center">       
                            
                            <b-form-group>
                                <h4 id="signinTitle" align="left" class="mt-3">이메일(ID)</h4>
                                <b-input-group class="input">
                                    <b-form-input type="email" v-model="signup.email" required placeholder="이메일" @blur="emailValid">
                                    </b-form-input>
                                    <b-button>중복체크, 인증 추가필요</b-button>
                                </b-input-group>
                                <div v-if="!emailValidFlag">
                                    유효하지 않은 이메일형식입니다.
                                </div>

                                <h4 id="signinTitle" align="left" class="mt-3">이름</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="name" v-model="signup.name" required placeholder="이름" maxlength="6" >
                                    </b-form-input>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">닉네임</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="nickname" v-model="signup.nickname" required placeholder="사용하고자 하는 닉네임 입력" maxlength="10" >
                                    </b-form-input>
                                    <b-button>중복체크 필요</b-button>
                                </b-input-group>

                                <h4 id="signinTitle" align="left" class="mt-3">비밀번호</h4>
                                <b-input-group >
                                    <b-form-input type="password" id="password" v-model="signup.password" required placeholder="비밀번호" maxlength="100" @blur="passwordValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!signup.password">
                                    대문자, 소문자, 숫자를 포함한 8~16자리를 입력하세요.
                                </div>
                                <div v-if="!passwordValidFlag">
                                    유효하지 않은 비밀번호 입니다.
                                </div>
                                
                                <h4 id="signinTitle" align="left" class="mt-3">비밀번호 확인</h4>
                                <b-input-group >
                                    <b-form-input type="password" id="pwdcheck" v-model="pwdcheck" required placeholder="비밀번호확인" maxlength="20" @blur="pwdcheckValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!pwdcheckFlag">
                                    비밀번호가 동일하지 않습니다.
                                </div>
                            
                                <h4 id="signinTitle" align="left" class="mt-3">성별</h4>
                                <b-form-group align="left" >
                                    <b-form-radio-group v-model="signup.gender" name="gender-radios" :options="genderoptions" plain >
                                    </b-form-radio-group>
                                </b-form-group>

                                <h4 id="signinTitle" align="left" class="mt-3" style="display:inline; float:left">휴대전화</h4>
                                <h6 align="left" class="mt-4" style="display:inline; float:right">-를 제외하고 입력해주세요</h6>
                                <b-input-group class="input">
                                    <b-form-input type="text" class="int" v-model="signup.phoneNum" required placeholder="휴대전화" maxlength="11" @blur="phoneValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!phonecheckFlag">
                                    휴대폰 번호를 올바르게 입력해주세요.
                                </div>

                                <!-- <h4 id="signinTitle" align="left" class="mt-3">은행</h4>
                                <b-input-group>
                                    <b-form-select v-model="signup.bank" :options="bankoptions" class="border" required placeholder="은행">
                                    </b-form-select>
                                </b-input-group>
                                

                                <h4 id="signinTitle" align="left" class="mt-3">계좌번호</h4>
                                <b-input-group class="input">
                                    <b-form-input type="text" v-model="signup.bankaccount" required placeholder="계좌번호" maxlength="30" @blur=bankaccountValid>
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!bankaccountFlag">
                                    계좌 번호를 정확하게 입력해주세요.
                                </div> -->
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
// import EmailValidator from "email-validator"; //이메일 유효성 검사
export default {
    name: "Stylist",
    data() {
        return {
            signup: {
                email: '',
                password: '',
                nickname: '',
                name: '',
                gender: null,
                phoneNum: '',
                // bank: '국민',
                // bankaccount: '',
            },
            genderoptions: [
                {text: '남성', value: '0'}, {text: '여성', value: '1'},
            ],
            // bankoptions: [
            //     {value: '국민', text: '왜'},
            //     {value: '농협', text: '이렇게'},
            //     {value: '신한', text: '나오는거지'},
            // ],
            pwdcheck: '',
            isSignup: true,
            passwordValidFlag: true,
            pwdcheckFlag: true,
            emailValidFlag: true,
            phonecheckFlag: true,
            // bankaccountFlag: true,
        }
    },
    created() {

    },
    
    watch: {
        email: function() {
            this.signup.email = this.signup.email.trim().toLowerCase(); //대문자 방지
        }
    },
    methods: {
         async Signup() { 
            const memberInfo = { 
                email: this.signup.email,
                password: this.signup.password,
                nickname: this.signup.nickname,
                name: this.signup.name,
                gender: this.signup.gender,
                phone: this.signup.phoneNum,
                authority: "ROLE_STYLIST"
                // bank: this.signup.bank,
                // bankaccount: this.signup.bankaccount,
            }
            //await this.memberConfirm(memberInfo);
            console.log(memberInfo); //임시
            if(this.isSignup) {
                this.$router.push({name: "Signin"}); 
            }
         },
        checkForm() { 
            if (this.signup.email == '' ||
                this.signup.password == '' ||
                this.pwdcheck == '' ||
                this.signup.nickname == '' ||
                this.signup.phoneNum == '' ||
                this.signup.gender == null) {
                // this.signup.bank == null ||
                // this.signup.bankaccount == '' ){
                alert('필수 항목을 입력해주세요.')
                return
            }
            if (!this.passwordValidFlag ||
                !this.pwdcheckFlag ||
                !this.emailValidFlag ||
                !this.phonecheckFlag) {
                // !this.bankaccountFlag) {
                alert('유효성 검사가 필요합니다.')
                return
                }
            this.Signup()
            // this.$router.push({ name: 'Home', params: {signup: this.signup}})
        },
        emailValid () {
            if (/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$/.test(this.signup.email)) {
                this.emailValidFlag = true
            } else {
                this.emailValidFlag = false
            }
        },
        passwordValid () {
            if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,16}$/.test(this.signup.password)) {
                this.passwordValidFlag = true 
            } else {
                this.passwordValidFlag = false 
            } 
        },
        pwdcheckValid () {
            if (this.signup.password == this.pwdcheck){
                this.pwdcheckFlag = true
            } else {
                this.pwdcheckFlag = false
            }
        },
        phoneValid () {
            if (/^[0-9]{11}$/.test(this.signup.phoneNum)) {
                this.phonecheckFlag = true
            } else {
                this.phonecheckFlag = false
            }
        },
        // bankaccountValid () {
        //     if (/^[0-9]{5,30}$/.test(this.signup.bankaccount)) {
        //         this.bankaccountFlag = true
        //     } else {
        //         this.bankaccountFlag = false
        //     }
        // }

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