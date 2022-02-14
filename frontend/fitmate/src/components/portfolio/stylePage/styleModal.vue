<template>
    <div class="row">
        <div class="temcontainer">
            <li style="list-style: none"  v-for="(style, id) in paginatedData" :key="id" >
                <img :src="style.thumbnail" class="item" @click="openModal(style)+rulike()">
            </li>
        </div>
        
        <b-modal v-if="this.styleData" size="xl" scrollable ref="style-modal" hide-footer>
            <template #modal-title>
                <b-avatar :src="styleData.portfolio.member.profile" size="4rem" class="me-2">
                </b-avatar>
                <h3 class="d-inline">{{ styleData.portfolio.nickname }}</h3>
                <!-- <h5>{{id}}</h5> -->
            </template>
            <div class="row">
                <div class="col-6">
                    <img :src="this.styleData.thumbnail" class="item">
                    <the-image-tag
                    v-for="tag in tags"
                    v-bind:key="tag.id"
                    v-bind:tag="tag"
                    >{{tag}}</the-image-tag>
                </div>
                <div class="col-6">
                    <p>{{this.styleData.content}}</p>  
                    <p class="mt-3" > 좋아요 상태 : {{ isLike }} </p>
                    <b-icon v-if="isLike == false" icon="suit-heart-fill" font-scale="3" style="margin-right:60px;" @click="follow()"></b-icon>
                    <b-icon v-else icon="suit-heart-fill" font-scale="3" variant="danger" style="margin-right:60px;" @click="unfollow()"></b-icon>                
                    <v-form>
                        <v-container class="p-0">
                        <v-row>
                            <v-col cols="12">
                            <v-text-field
                                v-model="message"
                                @keyup.enter="saveComment"
                                dense
                                clear-icon="mdi-close-circle"
                                append-outer-icon="message ? 'mdi-send' : 'mdi-microphone'"
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
                    <the-modal-comment-list
                    v-for="singlecomment in comments"
                    v-bind:key="singlecomment.id"
                    v-bind:content="singlecomment.comment"
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
            memberStore,
            comments:[],    
            password: 'Password',
            show: false,
            message: null,
            marker: true,
            iconIndex: 0,
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
        
    },
    methods: {
        openModal(data) {
            this.styleData = data
            this.$refs['style-modal'].show()
            // 댓글 불러오는 axios
            axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.styleData.id}/comments/all`)
            .then(({ data })=> {    
                console.log(data)
                this.comments = data;
            })
            // 태그 불러오는 axios
            axios.get(`${FITMATE_BASE_URL}/api/v1/tag/${this.styleData.id}`)
            .then(({ data })=> {    
                console.log(data)
                this.tags = data;
            })
            console.log(this.styleData)
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
        // 댓글 저장하는 axios
        saveComment() {
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
                        console.log('success')
                        console.log(res.data)
                        this.$store.dispatch('reloadComments', res.data)
                        this.comments.push(this.message)
                    }else{
                        Swal.fire({
                            icon: 'error',
                            title: '댓글을 입력하세요!',
                            text: 'Enter the comments!',
                            confirmButtonColor: '#7e7fb9',
                            confirmButtonText: "확인",
                        })
                    }
                })
                .catch(err =>{
                    console.log(err)
                });
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
.temcontainer {
  display: grid;
  grid-template-columns: 25% 25% 25% 25%;
  padding: 10px;
  background-size: cover;
}
.item {
  width: 100%; height: 100%;
  object-fit: cover;
  padding: 10px;
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
</style>