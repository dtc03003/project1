<template>
    <div id="app">
        <b-container class="bv-example-row">
            <b-row>
                <b-col></b-col>
                <b-col class="col-6">
                    <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
                        <h1 id="signinTitle">Fitmate (일반회원)</h1>
                        <b-form class="text-center">       
                            
                            <b-form-group>
                                <h4 id="signinTitle" align="left" class="mt-3">이메일(ID)</h4>
                                <b-input-group class="input">
                                    <b-form-input type="email" v-model="signup.email" required placeholder="이메일(ID) 입력" @blur="emailValid" >
                                    </b-form-input>
                                    <b-button @click="checkEmail">중복체크, 인증 추가필요</b-button>
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
                                    <b-button >중복체크 필요</b-button>
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


                                <div class="hr-sect">선택사항입니다.</div>    


                                <h4 id="signinTitle" class="mt-3" style="display:inline; float:left">휴대전화</h4>
                                <h6 align="left" class="mt-4" style="display:inline; float:right">-를 제외하고 입력해주세요</h6>
                                <b-input-group class="input">
                                    <b-form-input type="text" class="int" v-model="signup.phoneNum" required placeholder="휴대폰 번호 입력" maxlength="11" @blur="phoneValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!phonecheckFlag">
                                    휴대폰 번호를 올바르게 입력해주세요.
                                </div>

                                <div class="col-6" style="float:left">
                                <h4 id="signinTitle" align="left" class="mt-3">키</h4>
                                <b-input-group class="input">
                                    <b-form-input type="number" class="int" v-model="signup.userheight" min="80" max="250" required placeholder="본인의 키 입력">
                                    </b-form-input>
                                </b-input-group>
                                
                                </div>
                                <div class="col-6" style="float:right">
                                <h4 id="signinTitle" align="left" class="mt-3">몸무게</h4>
                                    <b-input-group class="input">
                                        <b-form-input type="number" class="int" v-model="signup.userweight" min="30" max="200" required placeholder="본인의 몸무게 입력">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-6" style="float:left">
                                    <h4 id="signinTitle" align="left" class="mt-3">상의 사이즈</h4>
                                    <b-input-group class="input">
                                        <b-form-input type="number" class="int" v-model="signup.usertop" min="80" max="120" step="5" required placeholder="자주 입는 옷의 상의 사이즈를 입력.">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-6" style="float:right">
                                    <h4 id="signinTitle" align="left" class="mt-3">하의 사이즈</h4>
                                    <b-input-group class="input">
                                        <b-form-input type="number" class="int" v-model="signup.userbottom" min="70" max="110" step="5" required placeholder="자주 입는 옷의 하의 사이즈를 입력.">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-12" style="float:left">

                                <h4 id="signinTitle" style="display:inline; float:left" class="mt-3">신발 사이즈</h4>
                                <b-button v-b-modal.modal-1 class="modalsize mt-3 mb-1">사이즈 표</b-button>
                                <b-modal id="modal-1" size="xl" title="사이즈 표" ok-only>
                                    <center>
                                        <img src="@/assets/size.png" align="center" width="900" height="450">
                                    </center>
                                </b-modal>
                                <b-input-group class="input">
                                    <b-form-input type="number" class="int" v-model="signup.usershoes" min="50" max="300" step="5" required placeholder="자주 신는 신발 사이즈를 입력">
                                    </b-form-input>
                                </b-input-group>
                                </div>

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
import axios from 'axios';
export default {
    name: "Stylist",
    data() {
        return {
            signup: {
                email: '',
                nickname: '',
                password: '',
                gender: null,
                phoneNum: '',
                userheight: '',
                userweight: '',
                usertop: '',
                userbottom: '',
                usershoes: '',
            },
            genderoptions: [
                {text: '남성', value: '0'}, {text: '여성', value: '1'},
            ],
            pwdcheck: '',
            isSignup: true,
            passwordValidFlag: true,
            pwdcheckFlag: true,
            emailValidFlag: true,
            phonecheckFlag: true,
            emailduplication: '',
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
        async Signup() { //로그인 기능
            // const config = { baseUrl: 'http://localhost:9000' };
            const memberInfo = { //로그인 정보
                email: this.signup.email,
                password: this.signup.password,
                name: this.signup.name,
                nickname: this.signup.nickname,
                gender: this.signup.gender,
                phone: this.signup.phoneNum,
                height: this.signup.userheight,
                weight: this.signup.userweight,
                top: this.signup.usertop,
                bottom: this.signup.userbottom,
                shoesSize: this.signup.usershoes,
                authority: "ROLE_MEMBER"

                //nickname(varchar), name(varchar), gender(int) 필수컬럼이므로 같이 넘겨야 하는가?
            }
            //await this.memberConfirm(memberInfo);
            console.log(memberInfo); //임시
            this.$router.push({name: "Signin"}); 
            axios.post('/auth/signup', memberInfo);
        },

        checkEmail() {
            const config = { baseUrl: 'http://localhost:9000' };
            axios.get(`${config.baseUrl}/auth/signup/email/${this.signup.email}`)
            .then(() => {
                alert('사용가능한 이메일입니다.')
                this.emailduplication = true
            })
            .catch(err => {
                console.log(err)
                alert('중복된 이메일입니다.')
                this.emailduplication = false
            })
        },

        checkForm() { 
            if (this.signup.email == '' ||
                this.signup.password == '' ||
                this.pwdcheck == '' ||
                this.signup.nickname == '' ||
                this.signup.gender == null){
                alert('필수 항목을 입력해주세요.')
                return
            }
            if (!this.passwordValidFlag ||
                !this.pwdcheckFlag ||
                !this.emailValidFlag) {
                alert('유효성 검사가 필요합니다.')
                return
                }
            
            this.Signup();
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
.modalsize { 
    display:inline; float:right;
    color: #fff !important;
    background-color: #7e7fb9 !important;
    border-color: #7e7fb9 !important;
}
.hr-sect {
display: flex;
flex-basis: 100%;
align-items: center;
color: rgba(0, 0, 0, 0.35);
font-size: 16px;
margin: 30px 0px 0px 0px;
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

</style>