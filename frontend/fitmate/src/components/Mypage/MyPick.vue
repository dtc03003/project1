<template>
  <div class="container-fluid col-8 offset-2">
    <div v-if="!status">
      <p id="nomyreview">아직 Pick한 스타일이 없습니다! 스타일북에서 자신이 원하는 스타일 Pick을 찾아보세요😍</p>
    </div>
    <div v-if="status" class="row">
      <!-- <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>MyPick</h1>
      </div> -->

      <div class="d-flex-wrap">
        <the-image-modal
        v-for="image in likeList"
        v-bind:key="image.createdAt"
        v-bind:thumbnail="image.thumbnail"
        v-bind:profile="image.portfolio.member.profile"
        v-bind:id="image.id"
        v-bind:content="image.content"
        v-bind:nickname="image.portfolio.member.nickname"     
        >{{image}}</the-image-modal>
      </div>
      
    </div>
  </div>
</template>

<script>
import TheImageModal from '@/components/Stylist/TheImageModal'

export default {
  data() {
    return {
      status:false,
    }
  },

  components:{
    TheImageModal
  },

  computed: { 
    likeList() {
      return this.$store.state.followStore.likeList
    }
  },

  async created() {
    await this.$store.dispatch("getLikeList")
    if(this.$store.state.followStore.likeList.length > 0) {
      this.status = true
    }
  },
}
</script>
