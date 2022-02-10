<template>
  <div>
    <!-- 아래는 댓글 -->
    <h6>{{writer}} 💌 {{content}}</h6>
  </div>
</template>

<script>
export default {
  name:'TheModalCommentList',
  props:{
    content:String,
    writer:String,
  },
}
</script>

<style>

</style>