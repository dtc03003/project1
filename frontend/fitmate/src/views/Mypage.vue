<template>
    <div>
        <div id="my_container">
            <!-- 이미지는 개인 프로필 이미지로 변경 -->
            <div class="profile-img">
                <img class="profile-user-img" :src="this.checkMemberInfo.profile">
            </div>
            <div class="trytocenter">
                <h1 class="dropdown" >{{ this.checkMemberInfo.nickname }}</h1>
                <b-dropdown class="dropdown" size="lg"  variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content>
                        &#128274;
                    </template>
                    <b-dropdown-item-button v-b-modal.usermodify>회원정보 수정</b-dropdown-item-button>
                    <b-modal id="usermodify" size="lg" centered  scrollable ref="style-modal" hide-footer>
                        <template #modal-title>
                            &#x26a0;&#xfe0f; 회원정보 수정
                        </template>
                        
                        <div></div>
                    </b-modal>

                    <b-dropdown-item-button v-b-modal.userdelete>회원탈퇴</b-dropdown-item-button>
                    <b-modal id="userdelete" size="md" centered  scrollable ref="style-modal" hide-footer>
                        <template #modal-title>
                            &#x26d4; 정말로 탈퇴하시겠습니까?
                        </template>
                        <div>
                            
                        </div>
                    </b-modal>
                </b-dropdown>
            </div>

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
</style>