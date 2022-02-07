<template>
	<span>
    <img :src="thumbnail" @click="$bvModal.show(`bv-modal-${id}`)" height="150px" class="m-1">
    <!-- 이미지를 클릭했을 때 뜨는 모달 -->
    <b-modal size="lg" :id="'bv-modal-'+id" scrollable hide-footer>
      <template #modal-title>
        <b-avatar :src="profile" size="4rem">
        </b-avatar>
        <h3 class="d-inline">{{ nickname }}</h3>
        <!-- <h5>{{id}}</h5> -->
      </template>
      <div class="row">
        <div class="col">
          <!-- 상세 이미지 -->
          <img :src="thumbnail" alt="" height="400rem" class="mr-2">
          <!-- 태그 -->
        </div>
        <div class="col">
          <h6>{{ content }}</h6>
          <the-modal-comment></the-modal-comment>
        </div>
      </div>

      <b-button class="mt-3 d-block" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
    </b-modal>
    </span>
</template>

<script>
import TheModalComment from '@/components/Stylist/TheModalComment'
import memberStore from '@/store/modules/memberStore'
// import axios from 'axios'
// import { FITMATE_BASE_URL } from '@/config'
// import { mapGetters } from 'vuex'

export default {
  name: 'TheImageModal',
  components: {
    TheModalComment,
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
      // styleComments:[],
      // checkauthority:''
    }
  },
  // computed: {
  //   ...mapGetters(
  //     'memberStore', ['checkMemberInfo']
  //   )
  // },
  // created () {
  //     axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${this.id}/comments`)
  //     .then(({ data })=> {
  //       console.log('이거는 코멘트들')       
  //       console.log(data)
  //       this.styleComments = data;
  //     })
  //     this.checkauthority = this.checkMemberInfo.authority
  //     console.log(this.checkauthority)
  // },

}
</script>


<style>

</style>