<template>
  <div class="container-fluid col-8 offset-2">
    <div v-if="!status">
      <p id="nomyreview">아직 Mate를 맺은 스타일리스트가 없습니다! 스타일리스트를 팔로우해 Mate를 맺어주세요😍</p>
    </div>
    <div class="d-flex-wrap">
      <!-- <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Mate</h1>
      </div> -->
      <div v-if="status">
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
  </div>
</template>

<script>
import TheStylistList from '@/components/Mypage/Mate/TheStylist'
import axios from '@/module/axios.js'

export default {
  data() {
    return {
      stylistArray:[],
      status: false,
    }
  },

  components:{
    TheStylistList,
  },

  created () {
    axios.get(`/api/v1/getFollowingList`)
    .then(({ data })=> {
      this.stylistArray = data;
      if(data.length > 0) {
        this.status = true
      }
    })
    
    
  },
}
</script>

<style>
p#nomyreview {font-size: 20pt; font-family: 'GangwonEdu_OTFBoldA';}
</style>