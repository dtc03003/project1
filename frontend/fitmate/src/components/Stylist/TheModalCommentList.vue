<template>
  <div>
    <!-- 아래는 댓글 -->
    <h6>{{comment.member.nickname}} 💌 {{content}}</h6>
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
    commentId:Number
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
      console.log(data)
      this.comment = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
    console.log(this.checkauthority)   
  },
  computed:{
    ...mapGetters('memberStore', ['checkMemberInfo'])
  },
  // updated(){
  //   // 단일 댓글 불러오는 axios
  //   axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/comment/${this.commentId}`)
  //   .then(({ data })=> {    
  //     console.log(data)
  //     this.comment = data;
  //   })
  //   this.checkauthority = this.checkMemberInfo.authority
  //   console.log(this.checkauthority) 
  // }
}
</script>

<style>

</style>