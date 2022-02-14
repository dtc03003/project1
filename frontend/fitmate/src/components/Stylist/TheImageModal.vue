<template>
	<span>
    <div class="thumb">
      <img id="beforeimg" :src="thumbnail" @click="$bvModal.show(`bv-modal-${id}`)+rulike()" class="m-1">   
    </div>

    <!-- 이미지를 클릭했을 때 뜨는 모달 -->
    <b-modal size="xl" :id="'bv-modal-'+id" scrollable hide-footer>
      <template #modal-title>
        <b-avatar :src="profile" size="4rem" class="me-2">
        </b-avatar>
        <h3 class="d-inline">{{ nickname }}</h3>
        <!-- <h5>{{id}}</h5> -->
      </template>
      <div class="row">
        <div class="col">

          <!-- 상세 이미지 -->
          <img :src="thumbnail" alt="" width="500rem" class="mr-2">
          <!-- 태그 -->
          <the-image-tag
          v-for="tag in tags"
          v-bind:key="tag.id"
          v-bind:tag="tag"
          >{{tag}}</the-image-tag>
        </div>
        <div class="col">
          <!-- 게시글(?) 내용 -->
          <pre>{{ content }}</pre>
          <p class="mt-3" > 좋아요 상태 : {{ isLike }} </p>
          <b-icon v-if="isLike == false" icon="suit-heart-fill" font-scale="3" style="margin-right:60px;" @click="follow()"></b-icon>
            <b-icon v-else icon="suit-heart-fill" font-scale="3" variant="danger" style="margin-right:60px;" @click="unfollow()"></b-icon>
          <!-- 아래는 댓글 입력 폼 -->
          <v-form>
            <v-container class="p-0">
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="message"
                    @keyup.enter="saveComment"
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

          <!-- 댓글을 매끄럽게 보여주기 위한 최후의 수단.... 진짜 최후의 수단...인데... -->
          <h6 v-if="instant">{{checkMemberInfo.nickname}} 💌 {{instant}}</h6>

          <!-- 댓글 리스트 받아오는 부분 -->
          <the-modal-comment-list
          v-for="(singlecomment, index) in comments"
          v-bind:key="index"
          v-bind:content="singlecomment.comment"
          v-bind:commentId="singlecomment.id"
          >{{singlecomment}}</the-modal-comment-list>

        </div>
      </div>
      <!-- <b-button class="mt-3 d-block" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button> -->
    </b-modal>
  </span>
</template>

<script>
import memberStore from '@/store/modules/memberStore'
import TheModalCommentList from '@/components/Stylist/TheModalCommentList'
import TheImageTag from '@/components/Stylist/TheImageTag'
import axios from '@/module/axios.js'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'
import Swal from 'sweetalert2'

export default {
  name: 'TheImageModal',
  components: {
    TheModalCommentList,
    TheImageTag,
  },

  props:{
    thumbnail:String,
    profile:String,
    id:Number,
    content:String,
    nickname:String,
  },

  data: function() {
    return {
      memberStore,
      comments:[],
      checkauthority:'',
      password: 'Password',
      show: false,
      message: null,
      marker: true,
      iconIndex: 0,
      tags:[],
      instant:''
    }
  },

  computed: {
    ...mapGetters('memberStore', ['checkMemberInfo']),

    icon () {
      return this.icons[this.iconIndex]
    },

    //게시물 좋아요 여부
    isLike() {
      return this.$store.state.followStore.isLike;
    }
  },

  created () {
    // 댓글 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comments/all`)
    .then(({ data })=> {    
      console.log(data)
      this.comments = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
    console.log(this.checkauthority)

    // 태그 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/tag/${this.id}`)
    .then(({ data })=> {    
      console.log(data)
      this.tags = data;
    })
  },
  methods: {
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
    // 댓글 다시 불러오는 함수
    getComment() {
      axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comments/all`)
      .then(({ data })=> {    
        console.log(data)
        this.comments = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
    },
    // 댓글 저장하는 axios
    saveComment() {
      if (this.message){
        this.instant = this.message
        const messageInfo = {
          "comment":this.message, 
          "createdAt":"",
          };
        const accessToken = localStorage.getItem("accessToken");
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
        axios({
          url: `${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comment`,
          method: 'post', // 통신할 방식
          data: messageInfo, //전송할 데이터
        })
        .then((res) => {
          if (res.data.comment){
            console.log('success')
            console.log(res.data)
            // this.comments.push(this.message)
              this.$store.dispatch("updateComment", {id:this.id})
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
        .then(
          this.getComment()
        )
        .catch(err =>{
          console.log(err)
        });
        this.resetIcon()
        this.clearMessage()
        this.getComment()
        // location.reload()
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
          cancelButtonText: "취소"
        }).then ((res) => {
          if (res.isConfirmed){
            this.$router.push({name:'Signin'})
          }
        })
      }
      this.token();
      axios.post(`/api/v1/like/${this.id}`)
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
        }).then(()=>window.location.reload())
      })
    },

    // 언팔로우
    unfollow() {
      this.token();
      axios.delete(`/api/v1/like/${this.id}`)
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
        }).then(()=>window.location.reload())
      })
    },

    token() {
      const accessToken = localStorage.getItem("accessToken");
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    },

    // 좋아요 상태 받아오기
    rulike() {
      this.$store.dispatch("getIsLike", { styleId: this.id })
    }
  },
  // updated() {
  //   this.$nextTick(function () {
  //     this.getComment() 
  //   })
  // }
}
</script>


<style>
.thumb {
 display: inline-block;
 overflow: hidden;
 height: 170px;
 width: 170px;
 }
.thumb img { 
  display: block; 
  /* Otherwise it keeps some space around baseline */ 
  min-height: 100%; 
  /* Scale up to fill container height */ 
  min-width: 100%; 
  /* Scale up to fill container width */ 
  -ms-interpolation-mode: bicubic; 
  /* Scaled images look a bit better in IE now */
  padding: 2px;
  }

#beforeimg:hover{
  backface-visibility: hidden;
  transform: scale(1.15, 1.15);
  opacity: 1;  
  }
</style>