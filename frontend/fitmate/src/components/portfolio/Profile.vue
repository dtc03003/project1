<template>
    <div>
        <div class="stylist-img">
            <img class="stylist-user-img" id="stylist" :src="profile">
        </div>
        <div class="row">
            <h1 class="mt-2 col-8 nickname" >{{ profileData.nickname }}</h1>

            <b-dropdown v-if="this.nickname == this.checkMemberInfo.nickname" 
            class="dropdown col-4" size="lg"  variant="link" toggle-class="text-decoration-none" no-caret>
                <template #button-content class="dropcontent">
                    &#128274;
                </template>

                <b-dropdown-item-button @click="$bvModal.show('biomodify')">한 줄 소개글 수정</b-dropdown-item-button>
                <b-modal id="biomodify" size="md" centered ref="style-modal" hide-footer>
                    <template #modal-title>
                        &#x26d4; 한 줄 소개글 수정
                    </template>
                    <b-row>
                        <b-form-textarea id="textarea"  v-model="biotext" placeholder="자신을 나타낼 수 있는 소개글을 적어보세요!" rows="3" max-rows="6"
                        ></b-form-textarea>
                        <b-col class="col-2"></b-col>
                        <b-col class="col-4 trytocenter" >
                            <b-button class="exitbtn" @click="modifybio()">수정</b-button>
                        </b-col>
                        <b-col class="col-4 trytocenter">
                            <b-button class="exitbtn" @click="$bvModal.hide('biomodify')">취소</b-button>
                        </b-col>
                    </b-row>
                </b-modal>

                <router-link style="text-decoration: none" :to="{ name: 'Modify' }">  
                    <b-dropdown-item-button v-b-modal.stylistmodify>회원정보 수정</b-dropdown-item-button>
                </router-link>

                <b-dropdown-item-button @click="$bvModal.show('userdelete')">회원탈퇴</b-dropdown-item-button>
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

                <!-- <b-dropdown-item-button @click="deleteport">포폴삭제</b-dropdown-item-button> -->
                
            </b-dropdown>
        </div>

        <p class="mt-3" > {{ profileData.bio }}</p>
        <div class="mt-5">
            <b-icon v-if="isFollow == false" icon="suit-heart-fill" font-scale="3" style="margin-right:60px;" @click="follow()"></b-icon>
            <b-icon v-else icon="suit-heart-fill" font-scale="3" variant="danger" style="margin-right:60px;" @click="unfollow()"></b-icon>

            <b-icon icon="chat-dots" font-scale="4" class="mr-2" style="margin-right:60px;"></b-icon>
            <b-icon icon="share-fill" font-scale="4"></b-icon>
        </div>            
    </div>
</template>

<script>
import axios from "@/module/axios.js";
import {mapState, mapGetters, mapActions} from 'vuex'
const memberStore = "memberStore";

export default {
    data() {
        return {
            nickname: this.$route.params.nickname,
            profileData : [],
            checkauthority: '',
            biotext: '',
            profile: '',
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),

        isFollow() {
            return this.$store.state.followStore.isFollow;
        }
    },

    created () {
        this.checkauthority = this.memberInfo.authority;
        // console.log(this.checkauthority)

        axios.get(`/api/v1/portfolio/${this.nickname}`)
        .then(({ data }) => {
            this.profileData = data;
            this.profile = data.member.profile
        }),

        this.$store.dispatch("getIsFollow", { nickname: this.nickname })
    },

    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),

        token() {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        },

        async withDrawal() {
            this.token();
            await axios.delete(`/api/v1/member/me`)
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
        },

        modifybio() {
            const bioinfo = {
                about : this.profileData.about,
                price : this.profileData.price,
                bio : this.biotext,
            }
            this.token();
            axios.put(`/api/v1/portfolio/about`, bioinfo)
            .then(() => {
                alert('한 줄 소개글 수정 완료!')
                let accessToken = localStorage.getItem("accessToken");
                this.signInMemberInfo(accessToken);
                window.location.reload()
            })
        },

        // deleteport() {
        //     this.token();
        //     axios.delete('/api/v1/portfolio')
        //     .then(() => {
        //         alert('삭제 완료')
        //     })
        // },

        // 팔로우
        follow() {
            this.token();
            axios.post(`/api/v1/follow/${this.nickname}`)
            .then(() => {
                alert(`${this.nickname}님 팔로우 완료!`)
                window.location.reload()
            })
        },

        // 언팔로우
        unfollow() {
            this.token();
            axios.delete(`/api/v1/cancelFollow/${this.nickname}`)
            .then(() => {
                alert(`${this.nickname}님 언팔로우 완료!`)
                window.location.reload()
            })
        }
    },
}
</script>

<style>
b-icon {
    margin-right:10px;
}
.btn-group-lg>.btn, .btn-lg {
    font-size: 2rem !important;
}
.nickname {
    display: flex;
    align-content: center;
}
.stylist-img {
    display: block;
    margin: 0 auto;
    width: 300px; height: 400px;
    overflow: hidden;
}
.stylist-user-img {
    width: 100%; height: 100%;
    object-fit: cover;
}
.exitbtn { 
    background-color: #7e7fb9 !important; 
}
</style>