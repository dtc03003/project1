<template>
    <div>
        <div id="my_container">
            <div class="profile-img">
                <img class="profile-user-img" :src="this.checkMemberInfo.profile">
            </div>
            <div class="trytocenter">
                <h1 class="dropdown" >{{ this.checkMemberInfo.nickname }}</h1>
                <b-dropdown class="dropdown" size="lg"  variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content>
                        &#128274;
                    </template>
                    <router-link style="text-decoration: none" :to="{ name: 'Modify' }">  
                        <b-dropdown-item-button v-if="this.checkMemberInfo.authority == 'ROLE_MEMBER'">회원정보 수정</b-dropdown-item-button>
                    </router-link>
                    
                    <!-- <router-link style="text-decoration: none" :to="{ name: 'Modify' }">  
                        <b-dropdown-item-button v-if="this.checkMemberInfo.authority == 'ROLE_STYLIST'" v-b-modal.stylistmodify>스타일리스트 정보수정</b-dropdown-item-button>
                    </router-link> -->

                    <b-dropdown-item-button @click="$bvModal.show('userdelete')">회원탈퇴</b-dropdown-item-button>
                </b-dropdown>
            </div>
            <b-modal id="userdelete" size="md" centered  scrollable ref="style-modal" hide-footer>
                <template #modal-title>
                    &#x26d4; 정말로 탈퇴하시겠습니까?
                </template>
                <b-row>
                    <label class="my-5">
                        FitMate 계정을 초기화합니다. 정보 복구는 불가능합니다.
                    </label> 
                    <b-col class="col-2"></b-col>
                    <b-col class="col-4 trytocenter" >
                        <b-button variant="danger" @click="withDrawal()">&#x26D4;탈퇴</b-button>
                    </b-col>
                    <b-col class="col-4 trytocenter">
                        <b-button id="exitbtn" @click="$bvModal.hide('userdelete')">&#x1F49C;취소</b-button>
                    </b-col>
                </b-row>
            </b-modal>
        </div>
        <b-tabs content-class="mt-3" fill pills card>
            <b-tab class="mx-1" title="My Pick"><MyPick/></b-tab>
            <b-tab title="Mate"><Mate/></b-tab>
            <b-tab title="History"><History/></b-tab>
            <b-tab title="Review"><Review/></b-tab>
        </b-tabs>
    </div>
</template>

<script>
import MyPick from '../components/Mypage/MyPick.vue'
import Mate from '../components/Mypage/Mate.vue'
import History from '../components/Mypage/History.vue'
import Review from '../components/Mypage/Review.vue'
import axios from 'axios'
import { FITMATE_BASE_URL } from "@/config";
import { mapGetters } from 'vuex';
const memberStore = "memberStore";

export default {
    name: 'Mypage',
    data() {
        return {
            pwdconfirm:'',

        }
    },
    components: {
        MyPick,
        Mate,
        History,
        Review
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        checkpwd() {
            if (this.pwdconfirm == this.checkMemberInfo.nickname) {
                alert('확인되었습니다.')

            } else {
                alert('비밀번호가 다릅니다.')
            }
        },
        // 일반회원은 탈퇴가능한데 스타일리스트는 탈퇴불가능 swagger에서도 못함 => 물어보기
        // 카카오 로그인회원은 Mypage들어가는것도 불가능 why? => 물어보기
        async withDrawal() {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            await axios.delete(`${ FITMATE_BASE_URL }/api/v1/member/me`)
            .then(() =>{
                alert('계정탈퇴 되었습니다. \n저희 서비스와 함께해주셔서 감사합니다.')
                this.signout()
            })
        },
        signout() {
            this.isSignin = false
            localStorage.clear()
            this.$store.dispatch('signout')      
            this.$router.push({name:'/'})
            window.location.reload()
        }
    }
}
</script>

<style>
#my_container {
    margin-top: 5rem;
}
.profile-img {
    display: block;
    margin: 0 auto;
    width: 250px; height: 250px;
    border-radius: 70%;
    overflow: hidden;
}
.profile-user-img {
    width: 100%; height: 100%;
    object-fit: cover;
}
.modal_button {
    text-align: right;
    margin-right: 5px;
}
.dropdown {
    display:inline-block;
}
.trytocenter {
    display: flex;
    align-items: center;
    justify-content: center;
}
#exitbtn { background-color: #7e7fb9; }
</style>