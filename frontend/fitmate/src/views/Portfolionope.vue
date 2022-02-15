<template>  
    <div id="app">
        <b-card class="text-center mt-3 card" style="max-width: 90rem" align="left">
            <h1 id="portTitle">{{ this.checkMemberInfo.nickname }} 님의 포트폴리오를 만드시겠습니까?</h1>
            <b-form-input 
                class="portwrite"
                v-model="portfoliocreate.price" 
                placeholder="스타일링 컨설팅 금액을 입력해주세요.">
            </b-form-input>
            <b-form-input 
                class="portwrite"
                v-model="portfoliocreate.bio" 
                placeholder="자신을 표현할 수 있는 한 줄을 적어주세요.">
            </b-form-input>
            <b-form-textarea
                class="portwrite"
                rows="8" size="md"
                v-model="portfoliocreate.about" 
                placeholder="자신의 경력 및 이력, 강점을 입력해주세요.">
            </b-form-textarea>
            <div class="row">
                <p class="mt-1 changeable">위 내용은 추후 변경 가능합니다.</p>
            </div>
            <div class="row my-3">
                <div class="col-3"></div>
                    <a class="col-2 cancelbtn">취소</a>
                <div class="col-2"></div>
                    <a class="col-2 makebtn" @click="createportfolio">생성</a>
                <div class="col-3"></div>
            </div>
        </b-card>
    </div>
</template>

<script>
import axios from 'axios';
import { mapGetters, mapState, mapActions } from 'vuex';
import { FITMATE_BASE_URL } from "@/config";
import Swal from 'sweetalert2'
const memberStore = "memberStore";

export default {
    name: "Portfolionope",
    data: function() {
        return {
            nickname: this.$route.params.nickname,
            portfoliocreate : {
                about : '',
                price : '',
                bio : '',
            },
        }
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
        ...mapState(memberStore, ["isSignin", "memberInfo"]),
    },
    mounted() {
        axios({
            url: `${FITMATE_BASE_URL}/api/v1/portfolio/${this.nickname}`,
            method: 'get',
        })
        .then((res)=> this.portfolioconfirm = res.data.about)
        .catch(()=>{})
    },
    created() {
    },
    methods: {
        ...mapActions(memberStore, ["reissueToken", "signInMemberInfo"]),
        async createportfolio() {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })
            const portinfo = {
                about : this.portfoliocreate.about,
                price : this.portfoliocreate.price,
                bio : this.portfoliocreate.bio,
            }
            if (portinfo.about == '' || portinfo.price == '' || portinfo.bio == ''){
                Swal.fire({
                    icon: 'error',
                    title: '내용이 비었습니다!',
                    confirmButtonColor: '#7e7fb9',
                    confirmButtonText: "확인",
                })
                return
            }
            if (!/^[0-9]{4,8}$/.test(portinfo.price)) {
                Swal.fire({
                    icon: 'error',
                    title: '적정가격과 숫자만 입력해주세요!',
                    text : '적정가격은 만원이상 천만원이하입니다!',
                    confirmButtonColor: '#7e7fb9',
                    confirmButtonText: "확인",
                })
                return
            }
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            await axios.post(`${FITMATE_BASE_URL}/api/v1/portfolio`, portinfo)
            .then(() => {
                Toast.fire({
                    icon: 'success',
                    title: '포트폴리오 생성완료!'
                }).then(()=>{
                    let accessToken = localStorage.getItem("accessToken");
                    this.signInMemberInfo(accessToken);
                    this.$router.push({name:'Portfolio'})
                    window.location.reload()
                })
            }) 
            .catch(() => {
                Toast.fire({
                    icon: 'success',
                    title: '포트폴리오 생성실패!',
                    text: '이미 포트폴리오가 생성되있습니다!\n새로고침 해주세요!'
                })
            })
        }
    }
}
</script>

<style>
.nav-pills .nav-link:not(.active) {
    background-color: #FFFFFF !important;
    color: #000000 !important;
}
.nav-pills .nav-link.active, .nav-pills .show>.nav-link {
    background-color: #7e7fb9 !important;
    color: #fff !important;
}
.nav-fill .nav-item .nav-link, .nav-justified .nav-item .nav-link {
    width: 90% !important;
}
.card-header {
    background-color: #FFFFFF !important;
    border-bottom: 1px solid rgba(0,0,0,.125);
}
.portwrite {
    margin-top: 2rem;
}
.changeable {
    display: flex;
    justify-content: flex-end;
    color: red;
    font-family: "GangwonEdu_OTFBoldA", Times, serif;
}
#portTitle {font-family: 'Pretendard-SemiBold', Times, serif;}
.makebtn, .cancelbtn {
    display: flex;
    justify-content: center;
    align-items: center;
    flex: 1 1 auto;
    text-align: center;
    text-transform: uppercase;
    transition: 0.5s;
    background-size: 200% auto;
    color: white;
    box-shadow: 0 0 20px #eee;
    border-radius: 10px;
    width: 100%;
    font-family: "SDSamliphopangche_Basic", "ImcreSoojin", fantasy;
    text-decoration-line: none;
    font-size: 1rem;
}
#app { text-align: center; margin: 0 auto; display: flex; justify-content: center; position: relative; }
.makebtn {background-image: linear-gradient(to right, #a1c4fd 0%, #c2e9fb 51%, #a1c4fd 100%);}
.cancelbtn {background-image: linear-gradient(to right, #e94040 0%, #f1a3a3 51%, #e94040 100%);}
.makebtn:hover, .cancelbtn:hover { background-position: right center; color: rgb(22, 22, 22); }
.portwrite::placeholder { font-family: "SDSamliphopangche_Basic", fantasy; }
.card {padding: 2rem;}
</style>