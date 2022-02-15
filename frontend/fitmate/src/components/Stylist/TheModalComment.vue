<template>
  <div>
    <!-- 댓글 리스트 받아오는 부분 -->
    <the-modal-comment-list
    v-for="(singlecomment, index) in comments"
    v-bind:key="index"
    v-bind:content="singlecomment.comment"
    v-bind:commentId="singlecomment.id"
    >{{singlecomment}}</the-modal-comment-list>
  </div>
</template>

<script>
import TheModalCommentList from '@/components/Stylist/TheModalCommentList'
import axios from '@/module/axios.js'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'

export default {
  name: 'TheModalComment',
  components: {
    TheModalCommentList,
  },
  data: function() {
    return {
      password: 'Password',
      show: false,
      message: '',
      marker: true,
      iconIndex: 0,
      comments:[],
      tags:[]
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
      this.comments = data;
    })
    this.checkauthority = this.checkMemberInfo.authority

    // 태그 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/tag/${this.id}`)
    .then(({ data })=> {    
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
  },
}
</script>

<style>

</style>