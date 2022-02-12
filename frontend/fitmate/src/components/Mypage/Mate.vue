<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <the-stylist-list 
        v-for="(stylist, index) in stylistArray"
        :key="index"
        v-bind:member="stylist.member"
        v-bind:nickname="stylist.nickname"
        v-bind:profile="stylist.profile"
        v-bind:stylistId="stylist.id"
        >
      </the-stylist-list>
    </div>
  </div>
</template>

<script>
import TheStylistList from '@/components/Stylist/TheStylistList'
import axios from '@/module/axios.js'

export default {
  data() {
    return {
      stylistArray:[],
    }
  },

  components:{
    TheStylistList,
  },

  created () {
    axios.get(`/api/v1/getFollowingList`)
    .then(({ data })=> {
      this.stylistArray = data;
    })
  },
}
</script>

<style>

</style>