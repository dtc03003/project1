<template>
    <div id="app">
        <!-- <input type="text" v-model="signup.usertop" minlength="80" maxlength="120"> -->
        <b-container class="bv-example-row">
            <b-row>
                <b-col></b-col>
                <b-col class="col-6">
                    <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
                        <h1 id="signinTitle">회원정보 수정</h1>
                        <b-form class="text-center">                                  
                            <b-form-group>
                                <h3  id="signinTitle" align="left" class="mt-3">&#x1F464; 계정 정보 </h3>
                                <div>
                                  <h5 id="signinTitle" align="left" class="mt-3">프로필사진</h5>
                                    <div class="profile-img">
                                        <img class="profile-user-img" :src="userinfo.profile">
                                    </div>
                                   <input ref="image" id="input"
                                      type="file" name="image" accept="image/*" multiple="multiple"
                                      class="hidden"
                                      @change="uploadImage()">
                                </div>
                                <h5 id="signinTitle" align="left" class="mt-3">이메일(ID)</h5>
                                <b-input-group class="input">
                                    <b-form-input type="email" v-model="userinfo.email" disabled>
                                    </b-form-input>
                                </b-input-group>

                                <h5 id="signinTitle" align="left" class="mt-3">이름</h5>
                                <b-input-group class="input">
                                    <b-form-input type="text" v-model="userinfo.name" disabled>
                                    </b-form-input>
                                </b-input-group>

                                <h5 id="signinTitle" align="left" class="mt-3">닉네임</h5>
                                <b-input-group class="input">
                                    <b-form-input type="text" id="nickname" v-model="userinfo.nickname" required placeholder="사용하고자 하는 닉네임 입력" maxlength="10" :disabled="nickduplication == true">
                                    </b-form-input>
                                    <b-button class="smallBtn" v-show="!nickduplication" @click="checkNick">중복체크</b-button>
                                    <b-button class="smallBtn" v-show="nickduplication" @click="nickduplication = !nickduplication">수정</b-button>
                                </b-input-group>

                                <b-button id="submitBtn" block class="mt-3 mb-3" @click="checkProfile()">
                                    계정정보 변경
                                </b-button>

                                <hr>

                                <h3 id="signinTitle" align="left" class="mt-3">&#x1F512; 계정 보안</h3>
                                <h5 id="signinTitle" align="left" class="mt-3">변경할 비밀번호</h5>
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
                                
                                <h5 id="signinTitle" align="left" class="mt-3">비밀번호 확인</h5>
                                <b-input-group >
                                    <b-form-input type="password" id="pwdcheck" v-model="pwdcheck" required placeholder="비밀번호확인" maxlength="20" @blur="pwdcheckValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!pwdcheckFlag">
                                    비밀번호가 동일하지 않습니다.
                                </div>

                                <b-button id="submitBtn" block class="mt-3 mb-3" @click="checkPassword()">
                                    비밀번호 변경
                                </b-button>

                                <hr>

                                <h3 id="signinTitle" align="left" class="mt-3">&#x2705; 부가정보 입력</h3>
                                <h5 id="signinTitle" class="mt-3" style="display:inline; float:left">휴대전화</h5>
                                <h6 align="left" class="mt-4" style="display:inline; float:right">-를 제외하고 입력해주세요</h6>
                                <b-input-group class="input">
                                    <b-form-input type="text" v-model="userinfo.phone" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="휴대폰 번호 입력" maxlength="11" @blur="phoneValid">
                                    </b-form-input>
                                </b-input-group>
                                <div v-if="!phonecheckFlag">
                                    휴대폰 번호를 올바르게 입력해주세요.
                                </div>

                                <div class="col-6" style="float:left">
                                <h5 id="signinTitle" align="left" class="mt-3">키</h5>
                                <b-input-group class="input">
                                    <b-form-input type="text" v-model="userinfo.height" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="본인의 키 입력">
                                    </b-form-input>
                                </b-input-group>
                                
                                </div>
                                <div class="col-6" style="float:right">
                                <h5 id="signinTitle" align="left" class="mt-3">몸무게</h5>
                                    <b-input-group class="input">
                                        <b-form-input type="text" v-model="userinfo.weight" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="본인의 몸무게 입력">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-6" style="float:left">
                                    <h5 id="signinTitle" align="left" class="mt-3">상의 사이즈</h5>
                                    <b-input-group class="input">
                                        <b-form-input type="text" v-model="userinfo.top" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 입는 옷의 상의 사이즈를 입력">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-6" style="float:right">
                                    <h5 id="signinTitle" align="left" class="mt-3">하의 사이즈</h5>
                                    <b-input-group class="input">
                                        <b-form-input type="text" v-model="userinfo.bottom" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 입는 옷의 하의 사이즈를 입력">
                                        </b-form-input>
                                    </b-input-group>
                                </div>

                                <div class="col-12" style="float:left">
                                <h5 id="signinTitle" style="display:inline; float:left" class="mt-3">신발 사이즈</h5>
                                <b-button v-b-modal.modal-1 class="smallBtn mt-3 mb-1">사이즈 표</b-button>
                                <b-modal id="modal-1" size="xl" title="사이즈 표" ok-only>
                                    <center>
                                        <img src="@/assets/size.png" align="center" width="900" height="450">
                                    </center>
                                </b-modal>
                                <b-input-group class="input">
                                    <b-form-input type="text" v-model="userinfo.shoeSize" maxlength="3" oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" required placeholder="자주 신는 신발 사이즈를 입력">
                                    </b-form-input>
                                </b-input-group>
                                </div>

                            </b-form-group>

                            <b-button id="submitBtn" block class="mt-3 mb-3" @click="Modify()">
                                부가정보 저장
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
import axios from 'axios'
import { FITMATE_BASE_URL } from "@/config";
import { mapState, mapActions } from "vuex";
// import mapActions from "vuex";
const memberStore = "memberStore";

export default {
    data() {
        return {
            userinfo: '',
            signup: {
                password: '',
            },
            genderoptions: [
                { text: '남성', value: 0 }, { text: '여성', value: 1 },
            ],
            currentnickname: '',
            currentpassword: '',
            pwdcheck: '',
            isSignup: true,
            passwordValidFlag: true,
            pwdcheckFlag: true,
            phonecheckFlag: true,
            nickduplication: true,
            memberStore,
        }
    },
    computed: {
        ...mapState(memberStore, ["isSignin", "memberInfo"]),
    },
    async mounted() {
        const accessToken = localStorage.getItem("accessToken");
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        await axios.get(`${FITMATE_BASE_URL}/api/v1/member/me`)
        .then((res) => {
            this.userinfo = res.data
            this.currentnickname = this.userinfo.nickname
        })
    },
    methods: {
        ...mapActions(memberStore, ["reissueToken", "signInMemberInfo"]),
        PwdModify() {
            const pwdInfo = {
                password : this.signup.password
            } 
            axios.put(`${FITMATE_BASE_URL}/api/v1/member/me/password`, pwdInfo)
            .then(() => {
                alert('비밀번호가 변경되었습니다.')
                this.reissueToken()
                let accessToken = localStorage.getItem("accessToken");
                this.signInMemberInfo(accessToken); //발급받은 accessToken으로 사용자 정보 받기
                window.location.reload()
            })
            .catch((err)=>console.log(err))
        },
        Modify() { 
            const memberInfo = { 
                email: this.userinfo.email,
                name: this.userinfo.name,
                nickname: this.userinfo.nickname,
                gender: this.userinfo.gender,
                phone: this.userinfo.phone,
                height: this.userinfo.height,
                weight: this.userinfo.weight,
                top: this.userinfo.top,
                bottom: this.userinfo.bottom,
                shoeSize: this.userinfo.shoeSize,
                profile: this.userinfo.profile,
            }
            console.log(memberInfo); 
            axios.put(`${FITMATE_BASE_URL}/api/v1/member/me`, memberInfo)
            .then(() => {
                alert('회원정보가 수정되었습니다.')
                this.reissueToken()
                let accessToken = localStorage.getItem("accessToken");
                this.signInMemberInfo(accessToken); //발급받은 accessToken으로 사용자 정보 받기
                console.log(this.memberInfo);
                window.location.reload()
            })
            .catch((err) => {
                console.log(err)
                alert(err)
            })
        },
        checkProfile() { 
            if (this.userinfo.nickname == ''){
                alert('필수 항목을 입력해주세요.')
                return
            }
            if (this.nickduplication == ''){
                alert('중복체크를 해주세요!')
                return
            }
            this.Modify();
        },
        checkPassword() { 
            if (this.signup.password == '' ||
                this.pwdcheck == ''){
                alert('필수 항목을 입력해주세요.')
                return
            }
            if (!this.passwordValidFlag ||
                !this.pwdcheckFlag) {
                alert('유효성 검사가 필요합니다. \n대문자, 소문자, 숫자를 포함한 8~16자리를 입력하세요.')
                return
            }
            this.PwdModify();
        },

        checkNick() {
            if (this.currentnickname == this.userinfo.nickname){
                this.nickduplication = true
                alert('닉네임이 동일합니다!')
                return
            }
            axios.get(`${FITMATE_BASE_URL}/auth/signup/nickname/${this.userinfo.nickname}`)
            .then(() => {
                this.nickduplication = true
                alert('사용가능한 닉네임입니다.')
            })
            .catch(() => {
                if (this.userinfo.nickname == ''){
                    alert('닉네임을 입력해주세요.')
                }
                else {
                    alert('중복된 닉네임입니다.')
                }
            })
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
            if (/^[0-9]{11}$/.test(this.userinfo.phone)) {
                this.phonecheckFlag = true
            } else {
                this.phonecheckFlag = false
            }
        },
        uploadImage: function() {
          let form = new FormData()
          let image = this.$refs['image'].files[0]
          
          form.append('images', image)
    
          axios.post(`${FITMATE_BASE_URL}/api/v1/images`, form, {
              header: { 'Content-Type': 'multipart/form-data' }
          }).then( ({data}) => {
            console.log(data.src)
            this.userinfo.profile = data.src
          })
          .catch( err => console.log(err))
        },    
        signout() {
            this.isSignin = false
            localStorage.clear()
            this.$store.dispatch('signout')      
            this.$router.push({name:'Signin'})
            window.location.reload()
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
.smallBtn { 
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
.profile-img {
    display: block;
    margin: 0 auto;
    width: 150px; height: 150px;
    border-radius: 70%;
    overflow: hidden;
}
.profile-user-img {
    width: 100%; height: 100%;
    object-fit: cover;
}
</style>