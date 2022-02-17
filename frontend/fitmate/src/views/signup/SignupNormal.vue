<template>
    <div id="app">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
            <h1 id="signinTitle" class="mt-3">Fitmate 일반회원</h1>
            <b-form class="text-center">       
                
                <b-form-group>
                    <h4 align="left" class="mt-3 formTitle">이메일(ID)</h4>
                    <b-input-group class="input">
                        <b-form-input type="email" v-model="signup.email" required placeholder="이메일(ID) 입력" @blur="emailValid" :disabled="emailduplication == true">
                        </b-form-input>
                        <b-button class="smallBtn" v-show="!emailduplication" @click="checkEmail">중복체크</b-button>
                        <b-button class="smallBtn" v-show="emailduplication" @click="emailduplication = !emailduplication">재입력</b-button>
                    </b-input-group>
                    <div v-if="!emailValidFlag" class="info">
                        유효하지 않은 이메일형식입니다.
                    </div>

                    <h4 align="left" class="mt-3 formTitle">이름</h4>
                    <b-input-group class="input">
                        <b-form-input type="text" id="name" v-model="signup.name" required placeholder="이름" maxlength="6" >
                        </b-form-input>
                    </b-input-group>

                    <h4 align="left" class="mt-3 formTitle">닉네임</h4>
                    <b-input-group class="input">
                        <b-form-input type="text" id="nickname" v-model="signup.nickname" required placeholder="사용하고자 하는 닉네임 입력" maxlength="10" :disabled="nickduplication == true">
                        </b-form-input>
                        
                        <b-button class="smallBtn" v-show="!nickduplication" @click="checkNick">중복체크</b-button>
                        <b-button class="smallBtn" v-show="nickduplication" @click="nickduplication = !nickduplication">재입력</b-button>
                    </b-input-group>

                    <h4 align="left" class="mt-3 formTitle">비밀번호</h4>
                    <b-input-group >
                        <b-form-input type="password" id="password" v-model="signup.password" required placeholder="비밀번호" maxlength="100" @blur="passwordValid">
                        </b-form-input>
                    </b-input-group>
                    <div v-if="!signup.password" class="mt-1 info">
                        대문자, 소문자, 숫자를 포함한 8~16자리를 입력하세요.
                    </div>
                    <div v-if="!passwordValidFlag" class="info warningpwd">
                        유효하지 않은 비밀번호 입니다.
                    </div>
                                
                    <h4 align="left" class="mt-3 formTitle">비밀번호 확인</h4>
                    <b-input-group >
                        <b-form-input type="password" id="pwdcheck" v-model="pwdcheck" required placeholder="비밀번호확인" maxlength="20" @blur="pwdcheckValid">
                        </b-form-input>
                    </b-input-group>
                    <div v-if="!pwdcheckFlag" class="mt-1 info warningpwd">
                        비밀번호가 동일하지 않습니다.
                    </div>
                            
                    <h4 align="left" class="mt-3 formTitle">성별</h4>
                    <b-form-group align="left" class="radio">
                        <b-form-radio-group v-model="signup.gender" name="gender-radios" :options="genderoptions" plain>
                        </b-form-radio-group>
                    </b-form-group>


                    <div class="hr-sect">선택사항입니다.</div>    


                    <h4 class="mt-3 formTitle" style="display:inline; float:left">휴대전화</h4>
                    <h6 align="left" class="mt-4 info" style="display:inline; float:right">-를 제외하고 입력해주세요</h6>
                    <b-input-group class="input">
                        <b-form-input type="text" v-model="signup.phoneNum" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="휴대폰 번호 입력" maxlength="11" @blur="phoneValid">
                        </b-form-input>
                    </b-input-group>
                    <div v-if="!phonecheckFlag">
                        <p class="mt-1 info">휴대폰 번호를 올바르게 입력해주세요.</p>
                    </div>

                    <div class="col-6" style="float:left">
                    <h4 align="left" class="mt-3 formTitle">키</h4>
                    <b-input-group class="input">
                        <b-form-input type="text" v-model="signup.userheight" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="본인의 키 입력">
                        </b-form-input>
                    </b-input-group>
                                
                    </div>
                    <div class="col-6" style="float:right">
                    <h4 align="left" class="mt-3 formTitle">몸무게</h4>
                        <b-input-group class="input">
                            <b-form-input type="text" v-model="signup.userweight" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="본인의 몸무게 입력">
                            </b-form-input>
                        </b-input-group>
                    </div>

                    <div class="col-6" style="float:left">
                        <h4 align="left" class="mt-3 formTitle">상의 사이즈</h4>
                        <b-input-group class="input">
                            <b-form-input type="text" v-model="signup.usertop" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 입는 옷의 상의 사이즈를 입력">
                            </b-form-input>
                        </b-input-group>
                    </div>

                    <div class="col-6" style="float:right">
                        <h4 align="left" class="mt-3 formTitle">하의 사이즈</h4>
                        <b-input-group class="input">
                            <b-form-input type="text" v-model="signup.userbottom" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 입는 옷의 하의 사이즈를 입력">
                            </b-form-input>
                        </b-input-group>
                    </div>

                    <div class="col-12" style="float:left">
                    <h4 style="display:inline; float:left" class="mt-3 formTitle">신발 사이즈</h4>
                    <b-button v-b-modal.modal-1 class="smallBtn mt-3 mb-1">사이즈 표</b-button>
                    <b-modal id="modal-1" size="xl" title="사이즈 표" ok-only ok-title="닫기" ok-variant="secondary">
                        <center>
                            <img src="@/assets/size.png" align="center" width="90%" height="50%">
                        </center>
                    </b-modal>
                    <b-input-group class="input">
                        <b-form-input type="text" v-model="signup.usershoes" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 신는 신발 사이즈를 입력">
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
    </div>
</template>
<script>
import axios from 'axios';
import { FITMATE_BASE_URL } from "@/config";
import Swal from 'sweetalert2'
export default {
    name: "Stylist",
    data() {
        return {
            signup: {
                email: '',
                nickname: '',
                password: '',
                gender: '',
                phoneNum: '',
                userheight: '',
                userweight: '',
                usertop: '',
                userbottom: '',
                usershoes: '',
            },
            genderoptions: [
                { text: '남성', value: 0 }, { text: '여성', value: 1 },
            ],
            pwdcheck: '',
            isSignup: true,
            passwordValidFlag: true,
            pwdcheckFlag: true,
            emailValidFlag: true,
            phonecheckFlag: true,
            emailduplication: '',
            nickduplication: '',
        }
    },
    created() {

    },
    
    watch: {
        email: function() {
            this.signup.email = this.signup.email.trim().toLowerCase(); 
        },
    },
    methods: {
        async Signup() { 
            const memberInfo = { 
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
                shoeSize: this.signup.usershoes,
                authority: "ROLE_MEMBER"
            }
            this.$router.push({name: "Signin"}); 
            axios.post(`${ FITMATE_BASE_URL }/auth/signup`, memberInfo);
        },

        checkEmail() {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })
            axios.get(`${FITMATE_BASE_URL}/auth/signup/email/${this.signup.email}`)
            .then(() => {
                if (this.emailValidFlag == true) {
                    this.emailduplication = true
                    Toast.fire({
                        icon: 'success',
                        title: '사용가능한 이메일입니다!'
                    })
                } else {
                    Toast.fire({
                        icon: 'error',
                        title: '이메일 형식에 맞게 입력해주세요!'
                    })
                }   
            })
            .catch(() => {
                if (this.signup.email == ''){
                    Toast.fire({
                        icon: 'error',
                        title: '이메일을 입력해주세요!'
                    })
                } else {
                    Toast.fire({
                        icon: 'error',
                        title: '중복된 이메일입니다!'
                    })
                }
            })
        },

        checkNick() {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })
            axios.get(`${FITMATE_BASE_URL}/auth/signup/nickname/${this.signup.nickname}`)
            .then(() => {
                this.nickduplication = true
                
                Toast.fire({
                    icon: 'success',
                    title: '사용가능한 닉네임입니다!'
                })
            })
            .catch(() => {
                if (this.signup.nickname == ''){
                    Toast.fire({
                        icon: 'error',
                        title: '닉네임을 입력해주세요!'
                    })
                } else {
                    Toast.fire({
                        icon: 'error',
                        title: '중복된 닉네임입니다!'
                    })
                }
            })
        },

        checkForm() { 
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })
            if (this.signup.email == '' ||
                this.signup.password == '' ||
                this.pwdcheck == '' ||
                this.signup.nickname == '' ||
                this.signup.gender == null){
                Toast.fire({
                    icon: 'error',
                    title: '필수 항목을 입력해주세요!'
                })
                return
            }
            if (!this.passwordValidFlag ||
                !this.pwdcheckFlag ||
                !this.emailValidFlag) {
                Toast.fire({
                    icon: 'error',
                    title: '유효성 검사가 필요합니다!'
                })
                return
            }
            if (this.emailduplication == '' ||
                this.nickduplication == ''){
                Toast.fire({
                    icon: 'error',
                    title: '중복체크를 해주세요!'
                })
                return
            }
            this.Signup();
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
@media screen and (max-width: 768px) {
    #app { text-align: center; padding: 5%; max-width: 90%; min-width: 320px; margin: 0 auto; display: flex; justify-content: center; position: relative; }    
}
@media screen and (min-width: 768px) and (max-width: 996px){
    #app { text-align: center; padding: 5%; max-width: 80%; min-width: 320px; margin: 0 auto; display: flex; justify-content: center; position: relative; }
}
@media screen and (min-width: 992px){
    #app { text-align: center; padding: 5%; max-width: 60%; min-width: 320px; margin: 0 auto; display: flex; justify-content: center; position: relative; }
}

#signinTitle { font-family: "SDSamliphopangche_Basic", "ImcreSoojin", "Cafe24Ohsquare", fantasy; }
#goJoin { color: black; }
.xcircle { color: gray; }
#submitBtn { background: linear-gradient(to right, #8d8eeb, #8763FB); border-color: #8763FB; width: 100%;} /* 올해의 색상코드: #6667AB */
.smallBtn { 
    display:inline; float:right;
    color: #fff !important;
    background: linear-gradient(to right, #8d8eeb, #8763FB) !important;
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
.formTitle, ::placeholder {font-family: "SDSamliphopangche_Basic", fantasy;}
.info, .radio, .smallBtn, #submitBtn { font-family: "GangwonEdu_OTFBoldA", fantasy; }
.warningpwd { color: red; }
.input {font-family: 'Pretendard-SemiBold', fantasy;}
</style>