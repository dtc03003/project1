<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
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
  components:{
    TheImageModal
  },

  computed: { 
    likeList() {
      return this.$store.state.followStore.likeList
    }
  },

  created() {
    this.$store.dispatch("getLikeList")
  },
}
</script>
