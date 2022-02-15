<template>
    <div>
        <div class="stylist-img">
            <img class="stylist-user-img" id="stylist" :src="profile">
        </div>

        <div class="row">
            <h1 class="mt-2 col-8 nickname" >{{ profileData.nickname }}</h1>

            <!-- 유정 수정  버튼 -->
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
            </b-dropdown>
            <!-- 유정 수정  버튼 끝 -->
        </div>

        <p class="mt-3" > {{ profileData.bio }} </p>
        <p class="mt-3" > 팔로우 여부 : {{ isFollow }} </p>
        <p class="mt-3" > 로그인 여부 : {{ checkisSignin }} </p>
        <p class="mt-3" > 팔로우 수 : {{ CountFollow }} </p>

        <div class="row mt-3" v-if="checkisSignin">
            <div class="col-4 gotocenter">
                <!-- <b-icon v-if="this.nickname == this.checkMemberInfo.nickname" id="myheart" icon="suit-heart-fill" font-scale="3" variant="success" ></b-icon> -->
                <div v-if="this.nickname == this.checkMemberInfo.nickname"><b-icon icon="suit-heart-fill" font-scale="3" variant="success" v-b-modal.follow></b-icon></div>
                <div v-else-if="isFollow == false" class="box">
                    <div style="position: relative;">
                        <b-icon icon="suit-heart-fill" font-scale="3" @click="follow()" ></b-icon>
                        <!-- 겹치기 부분 -->
                        <div style="position: absolute; top:0px "></div>
                    </div>
                </div>
                <div v-else>
                    <b-icon icon="suit-heart-fill" font-scale="3" variant="danger"  @click="unfollow()"></b-icon>
                </div>
                <!-- <b-tooltip v-if="this.nickname == this.checkMemberInfo.nickname" target="myheart" :title="this.follower"></b-tooltip> -->
            </div>
            <div class="col-4">
                <b-button size="lg" @click="joinroom()" variant="link" no-caret>&#128172;</b-button>
            </div>
            <!-- <b-icon icon="share-fill" font-scale="4" @click="copyLink()"></b-icon> -->
            <div class="col-4">
                <b-button size="lg" @click="copyLink()" variant="link" no-caret>🔗</b-button>
            </div>

            <b-modal id="follow" title="팔로우 리스트">
                <p class="my-4">{{this.follower}}</p>
            </b-modal>
        </div>            
    </div>
</template>

<script>
import axios from "@/module/axios.js";
import {mapState, mapGetters, mapActions} from 'vuex'
import Swal from 'sweetalert2'
const memberStore = "memberStore";

export default {
    data() {
        return {
            nickname: this.$route.params.nickname,
            profileData : [],
            checkauthority: '',
            biotext: '',
            profile: '',
            dumi: [],
            follower: "",
        }
    },

    computed: { 
        ...mapState(memberStore, ["memberInfo"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),
        ...mapGetters(memberStore, ["checkisSignin"]),

        isFollow() {
            return this.$store.state.followStore.isFollow;
        },

        CountFollow() {
            return this.$store.state.followStore.countFollow
        },

        FollowerList() {
            return this.$store.state.followStore.followerList
        }
    },

    created () {
        this.checkauthority = this.memberInfo.authority;
        // console.log(this.checkauthority)

        axios.get(`/api/v1/portfolio/${this.nickname}`)
            .then(({ data }) => {
                this.profileData = data;
                this.profile = data.member.profile
            })
        
        // 내가 이 사람을 팔로우 했는지 요청하는 부분, 토큰전송해야함
        if(this.checkisSignin || this.nickname != this.checkMemberInfo.nickname){
            this.$store.dispatch("getIsFollow", { nickname: this.nickname })
        .catch(() => {})
        }

        this.$store.dispatch("getCountFollow", { nickname: this.nickname })

        if(this.nickname == this.checkMemberInfo.nickname){
            this.getFollowList()
        }
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
                Swal.fire({
                    icon: 'success',
                    title: '계정탈퇴 되었습니다. \n저희 서비스와 함께해주셔서 감사합니다.',
                    text: 'account has been withdrawn',
                    confirmButtonColor: '#7e7fb9',
                    confirmButtonText: "확인",
                }).then(()=>this.signout())
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
                Toast.fire({
                    icon: 'success',
                    title: '한 줄 소개글 수정 완료!'
                }).then(()=>{
                    let accessToken = localStorage.getItem("accessToken");
                    this.signInMemberInfo(accessToken);
                    window.location.reload()
                    }
                )
            })
        },

        copyLink() {
            let currentUrl = window.document.location.href;
            let t = document.createElement("textarea");
            document.body.appendChild(t);
            t.value = currentUrl;
            t.select();
            document.execCommand('copy');
            document.body.removeChild(t);
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
            Toast.fire({
                icon: 'success',
                title: 'URL 복사 완료!'
            })
        },

        // 팔로우
        follow() {
            this.token();
            axios.post(`/api/v1/follow/${this.nickname}`)
            .then(() => {
                this.$store.dispatch("getIsFollow", { nickname: this.nickname })
                this.$store.dispatch("getCountFollow", { nickname: this.nickname })
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })
                Toast.fire({
                    icon: 'success',
                    title: `${this.nickname}님 팔로우 완료!`
                })
            })
        },

        // 언팔로우
        unfollow() {
            this.token();
            axios.delete(`/api/v1/cancelFollow/${this.nickname}`)
            .then(() => {
                this.$store.dispatch("getIsFollow", { nickname: this.nickname })
                this.$store.dispatch("getCountFollow", { nickname: this.nickname })
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })
                Toast.fire({
                    icon: 'error',
                    title: `${this.nickname}님 언팔로우 완료!`
                })
            })
        },

        joinroom(){
            this.$router.push(`/room/${this.nickname}`)
        },

        async getFollowList(){
            await this.$store.dispatch("getFollowerList")

            for(let temp of this.FollowerList){
                this.dumi.push(temp.nickname)
            }

            this.follower = this.dumi.join(',');
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
.btn-link {
    text-decoration : none!important;;
}
.gotocenter {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>