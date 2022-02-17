<template>
    <div class="row">
        <div class="temcontainer">
            <li style="list-style: none" class="shadowbox" v-for="(style, id) in paginatedData" :key="id" >
                <img :src="style.thumbnail" class="item" @click="openModal(style)+rulike()" >
            </li>
        </div>
        
        <b-modal v-if="this.styleData" size="xl" scrollable ref="style-modal" header-class="modalHeader" hide-footer>
            <template #modal-title>
                <b-avatar :src="styleData.portfolio.member.profile" size="4rem" class="me-2" >
                </b-avatar>
                <h3 class="d-inline" id="name">{{ styleData.portfolio.nickname }}</h3>
            </template>
            <div class="row">
                <div class="col">

                    <!-- 상세 이미지 -->
                    <img :src="this.styleData.thumbnail" alt="" id="imageDetail" class="mr-2">

                    <!-- 태그 -->
                    <div class="row">
                        <div class="my-2 col-10">
                            <the-image-tag
                            v-for="tag in tags"
                            v-bind:key="tag.id"
                            v-bind:tag="tag"
                            >{{tag}}</the-image-tag>
                        </div> 
                        <div class="col-2">
                            <v-icon v-if="isLike==false" style="color:black; font-weight:bold;" large @click="follow()">mdi-heart-outline</v-icon>
                            <v-icon v-else large style="color:red; font-weight:bold;" @click="unfollow()">mdi-heart</v-icon>
                        </div>
                    </div>
                </div>

                <div class="col-6">
                    <pre id="inputtext">{{ this.styleData.content }}</pre>
                    <!-- 아래는 댓글 입력 폼 -->
                    <!-- 로그인한 사용자만 댓글 입력 가능 -->
                    <v-form id="inputtext" v-if="checkauthority">
                        <v-container class="p-0">
                        <v-row>
                            <v-col cols="12">
                            <v-text-field
                                v-model="message"
                                v-on:keydown.enter.prevent="saveComment"
                                dense
                                clear-icon="mdi-close-circle"
                                append-outer-icon="mdi-send"
                                clearable
                                label="댓글 달아주세요!!"
                                type="text"
                                @click:clear="clearMessage"
                                @click:append-outer="saveComment"
                            ></v-text-field>
                            </v-col>
                        </v-row>
                        </v-container>
                    </v-form>

                    <!-- 만약 로그인을 안 했으면 검색창 대신 아래의 문구가 뜸 -->
                    <v-form v-else>
                        <a href="" id="loginplz" style="text-decoration:none;color:teal;" @click="goToSignin">
                            <h5>댓글을 입력하시려면 로그인해주세요!</h5>
                        </a>
                    </v-form>

                    <!-- 여기는 차마 불러오지 못한 댓글 미리 보여주는 느낌 -->
                    <div id="comment" v-if="instant">
                        <b-avatar :src="checkMemberInfo.profile" size="2rem" class="me-2 my-1 d-inline-flex">
                        </b-avatar>
                        <h6 class="d-inline me-2" style="font-weight:bold;">{{checkMemberInfo.nickname}}</h6>
                        <p class="content d-inline">{{instant}}</p>
                    </div>

                    <!-- 댓글 리스트 받아오는 부분 -->
                    <the-modal-comment-list
                    id="comment"
                    v-for="(singlecomment, index) in comments"
                    v-bind:key="index"
                    v-bind:content="singlecomment.comment"
                    v-bind:commentId="singlecomment.id"
                    v-bind:profile="singlecomment.member.profile"
                    v-bind:writer="singlecomment.member.nickname"
                    >{{singlecomment}}</the-modal-comment-list>
                </div>
            </div>
        </b-modal>
        <div class="btn-cover">
            <b-button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn-secondary">이전</b-button>
            <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
            <b-button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn-secondary">다음</b-button>
        </div>
    </div>
</template>

<script>
import axios from '@/module/axios.js'
import { FITMATE_BASE_URL } from '@/config'
import memberStore from '@/store/modules/memberStore'
import TheModalCommentList from '@/components/Stylist/TheModalCommentList'
import TheImageTag from '@/components/Stylist/TheImageTag'
import { mapGetters } from 'vuex';
import Swal from 'sweetalert2'

export default {
    name: 'style-modal',
    data() {
        return {
            pageNum: 0,
            styleData: {
                content: '',
                id: '',
                portfolio: Object,
                thumbnail: '',
                nickname: '',
                profile: '',
            },
            checkauthority: '',
            memberStore,
            comments:[],    
            password: 'Password',
            show: false,
            message: null,
            marker: true,
            iconIndex: 0,
            instant: '',
            tags:[],
        }
    },
    props: {
        listArray: {
            type: Array,
            required: true
        },
        pageSize: {
            type: Number,
            required: false,
            default: 8
        }
    },
    components: {
        TheModalCommentList,
        TheImageTag,
    },
    computed: {
        ...mapGetters('memberStore', ["checkMemberInfo"]),
        
        pageCount () {
            let listLeng = this.listArray.length,
                listSize = this.pageSize,
                page = Math.floor(listLeng / listSize);
            if (listLeng % listSize > 0) page += 1;
            return page;
        },

        paginatedData () {
            const start = this.pageNum * this.pageSize,
                end = start + this.pageSize;
            return this.listArray.slice(start, end);
        },

        icon () {
            return this.icons[this.iconIndex]
        },

        //게시물 좋아요 여부
        isLike() {
            return this.$store.state.followStore.isLike;
        }
    },

    created () {
        this.checkauthority = this.checkMemberInfo.authority
    },

    methods: {
        openModal(data) {
            this.styleData = data
            this.$refs['style-modal'].show()
            // 댓글 불러오는 axios
            axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.styleData.id}/comments/all`)
            .then(({ data })=> {    
                this.comments = data;
            })
            // 태그 불러오는 axios
            axios.get(`${FITMATE_BASE_URL}/api/v1/tag/${this.styleData.id}`)
            .then(({ data })=> {    
                this.tags = data;
            })
        },
        goToSignin() {
            this.$router.push({name:'Signin'})
        },
        nextPage () {
            this.pageNum += 1;
        },
        prevPage () {
            this.pageNum -= 1;
        },
        toggleMarker () {
            this.marker = !this.marker
        },
        sendMessage () {
            this.resetIcon()
            this.clearMessage()
        },
        clearMessage () {
            this.message = ''
            },
        resetIcon () {
            this.iconIndex = 0
        },
        changeIcon () {
            this.iconIndex === this.icons.length - 1
                ? this.iconIndex = 0
                : this.iconIndex++
        },
        getComment() {
            axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.styleData.id}/comments/all`)
            .then(({ data })=> {    
                this.comments = data;
            })
            this.checkauthority = this.checkMemberInfo.authority
        },
        // 댓글 저장하는 axios
        saveComment() {
            this.instant = this.message
            
            if (this.message){
                const messageInfo = {
                    "comment":this.message, 
                    "createdAt":"",
                };
                this.token();
                axios({
                    url: `${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.styleData.id}/comment`,
                    method: 'post', // 통신할 방식
                    data: messageInfo, //전송할 데이터
                })
                .then((res) => {
                    if (res.data.comment){
                        // pass같은 개념
                        // this.$store.dispatch("updateComment", {id:this.id})
                        
                    }else{
                        Swal.fire({
                            icon: 'error',
                            title: '댓글을 입력하세요!',
                            text: 'Enter the comments!',
                            confirmButtonColor: '#7e7fb9',
                            confirmButtonText: "확인",
                        })
                        this.getComment()
                    }
                })
                .then(
                    this.getComment()
                )
                .catch(() => {});
                this.resetIcon()
                this.clearMessage()
            }else{
                Swal.fire({
                    icon: 'error',
                    title: '댓글을 입력하세요!',
                    text: 'Enter the comments!',
                    confirmButtonColor: '#7e7fb9',
                    confirmButtonText: "확인",
                })
            }
        },

        // 팔로우
        follow() {
            if (!this.checkMemberInfo.authority) {
                Swal.fire({
                icon: 'error',
                title: '먼저 로그인을 해주세요!',
                confirmButtonColor: '#7e7fb9',
                confirmButtonText: "로그인",
                showCancelButton: true,
                cancelButtonText: "취소",
                }).then ((res) => {
                    if (res.isConfirmed){
                        this.$router.push({name:'Signin'})
                    }
                })
            }
            this.token();
            axios.post(`/api/v1/like/${this.styleData.id}`)
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
                    title: '좋아요 완료!'
                })
                this.$store.dispatch("getIsLike", { styleId: this.styleData.id })
            })
        },

        // 언팔로우
        unfollow() {
            this.token();
            axios.delete(`/api/v1/like/${this.styleData.id}`)
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
                    icon: 'error',
                    title: '좋아요 취소!'
                })
                this.$store.dispatch("getIsLike", { styleId: this.styleData.id })
            })
        },

        token() {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        },

        // 좋아요 상태 받아오기
        rulike() {
            this.$store.dispatch("getIsLike", { styleId: this.styleData.id })
        }
    },
    
    
}
</script>

<style>
.shadowbox {
  display: inline-block;
  margin-bottom: 15px;
  overflow: hidden;
  height: 18rem;
  width: 12rem;
  box-shadow: 3px 2px 2px rgb(160, 160, 160);
  border-radius: 5%;
}
.temcontainer {
  display: grid;
  grid-template-columns: 25% 25% 25% 25%;
  padding: 10px;
  background-size: cover;
}
.item {
  width: 100%; height: 100%;
  object-fit: cover;
  font-size: 30px;
  align-self: center;
}
.btn-cover {
    margin-top: 1.5rem;
    text-align: center;
}
.btn-cover .page-btn {
    background-color: #7e7fb9;
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
    float: initial;
    line-height: 0.5 !important; 
}
.btn-cover .page-count {
    padding: 0 1rem;
}
.btn {
    background-color: #7e7fb9;
}
.modal-body {
  padding: 2rem !important;
}
.modalHeader {
    color: white;
    background-color: #7e7fb9;
    text-align: center;
    font-weight: 600;
}
#guidetags {
    font-family: 'GangwonEdu_OTFBoldA';
}
</style>