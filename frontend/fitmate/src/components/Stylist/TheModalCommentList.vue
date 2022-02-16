<template>
  <div>
    <!-- 아래는 댓글 -->
    <b-avatar :src="profile" size="2rem" class="me-2 my-1 d-inline-flex">
    </b-avatar>
    <h6 class="d-inline me-2" style="font-weight:bold;">{{writer}}</h6>
    <p class="content d-inline">{{content}}</p>
  </div>
</template>

<script>
import axios from '@/module/axios.js'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'


export default {
  name:'TheModalCommentList',
  props:{
    content:String,
    commentId:Number,
    profile:String,
    writer:String
  },
  data:function(){
    return {
      comment:{
        member:{
          nickname:''
        }
      },
      checkauthority:'',
    }
  },
  methods:{
    
  },
  created () {
    // 단일 댓글 불러오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/comment/${this.commentId}`)
    .then(({ data })=> {    
      this.comment = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
  },
  computed:{
    ...mapGetters('memberStore', ['checkMemberInfo'])
  },
}
</script>

<style>
.content{
  font-size: 0.9rem;
}

</style>