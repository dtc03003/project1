<template>
	<span>
    <img :src="thumbnail" @click="$bvModal.show(`bv-modal-${id}`)" height="150px" class="m-1">
    <!-- 이미지를 클릭했을 때 뜨는 모달 -->
    <b-modal size="xl" :id="'bv-modal-'+id" scrollable hide-footer>
      <template #modal-title>
        <b-avatar :src="profile" size="4rem">
        </b-avatar>
        <h3 class="d-inline">{{ nickname }}</h3>
        <!-- <h5>{{id}}</h5> -->
      </template>
      <div class="row">
        <div class="col">
          <!-- 상세 이미지 -->
          <img :src="thumbnail" alt="" width="500rem" class="mr-2">
          <!-- 태그 -->
        </div>
        <div class="col">
          <!-- 게시글(?) 내용 -->
          <pre>{{ content }}</pre>
          <!-- 아래는 댓글 -->
          <v-form>
            <v-container class="p-0">
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="message"
                    dense
                    clear-icon="mdi-close-circle"
                    append-outer-icon="mdi-send"
                    clearable
                    label="댓글은 예쁜 말로!"
                    type="text"
                    @click:clear="clearMessage"
                    @click:append-outer="saveComment"
                    @keyup.enter="saveComment"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
          <!-- {{comments}} -->
          <!-- 댓글 리스트 받아오는 부분 -->
          <the-modal-comment-list
          v-for="singlecomment in comments"
          v-bind:key="singlecomment.id"
          v-bind:content="singlecomment.comment"
          v-bind:writer="singlecomment.member.nickname"
          >{{singlecomment}}</the-modal-comment-list>
        </div>
      </div>
      <b-button class="mt-3 d-block" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
    </b-modal>
    </span>
</template>

<script>
import memberStore from '@/store/modules/memberStore'
import TheModalCommentList from '@/components/Stylist/TheModalCommentList'
import axios from 'axios'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'

export default {
  name: 'TheImageModal',
  components: {
    TheModalCommentList,
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
      message: '',
      marker: true,
      iconIndex: 0,
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
      axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comments/all`)
      .then(({ data })=> {
        console.log('이거는 코멘트들')       
        console.log(data)
        this.comments = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
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
    saveComment() {
      console.log(this.message)
      axios({
        url: `${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comment`,
        method: 'post', // 통신할 방식
        data:{comment:this.message}, //전송할 데이터
        headers: localStorage.getItem("accessToken") //post라서 토큰까지
      })
      .then(
        console.log('success')
      )
      .catch(err =>{
        console.log(err)
        console.log('fail')
      });
      this.resetIcon()
      this.clearMessage()
    }
  },

}
</script>


<style>

</style>