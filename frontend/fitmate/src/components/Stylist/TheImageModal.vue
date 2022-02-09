<template>
	<span>
    <img :src="thumbnail" @click="$bvModal.show(`bv-modal-${id}`)" height="150px" class="m-1">
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
          <!-- 댓글 리스트 받아오는 부분 -->
          <the-modal-comment-list
          v-for="singlecomment in comments"
          v-bind:key="singlecomment.id"
          v-bind:content="singlecomment.comment"
          v-bind:writer="singlecomment.member.nickname"
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
import axios from 'axios'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'

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
    }
  },
  computed: {
    ...mapGetters(
      'memberStore', ['checkMemberInfo']
    ),
    icon () {
      return this.icons[this.iconIndex]
    },
  },
  created () {
    // 댓글 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comments/all`)
    .then(({ data })=> {
      console.log('이거는 코멘트들')       
      console.log(data)
      this.comments = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
    console.log(this.checkauthority)

    // 태그 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/tag/${this.id}`)
    .then(({ data })=> {
      console.log('태그')       
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
    // 댓글 저장하는 axios
    saveComment() {
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
        console.log('success')
        console.log(res.data)
      })
      .catch(err =>{
        console.log(err)
      });
      this.resetIcon()
      this.clearMessage()
    }
  },
}
</script>



<style>

</style>