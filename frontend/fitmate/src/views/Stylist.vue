<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylist</h1>
      </div>
      <!-- 드롭다운 -->
      <!-- 스타일리스트 들어오면 함수걸어 정렬예정 -->
      <div class="col-2 d-flex align-items-center justify-content-start">
        <select class="form-select" aria-label="Default select example" v-model="selected">
          <option disabled value="">정렬</option>
          <option @click="sortedLatest" value="1">최신순</option>
          <option @click="sortedGrade" value="2">평점순</option>
          <option @click="sortedLikes" value="3">팔로워순</option>
        </select>
      </div>
      <span>선택함: {{ selected }}</span>

      <!-- 검색창 -->
      <div class="col-6 offset-2">
      </div>

      <!-- {{stylistArray}} -->
      <!-- 스타일리스트 목록 컴포넌트 -->
      <the-stylist-list 
      v-for="(stylist, index) in stylistArray"
      :key="index"
      v-bind:member="stylist.member"
      v-bind:nickname="stylist.nickname"
      v-bind:profile="stylist.member.profile"
      v-bind:stylistId="stylist.id"
      >
      </the-stylist-list>
    </div>
  </div>
</template>

<script>
import TheStylistList from '@/components/Stylist/TheStylistList'
import axios from 'axios'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'

const memberStore = "memberStore"

export default {
  name: 'Stylist',
  components:{
      TheStylistList,
  },
  data:function () {
    return {
      selected:'',
      stylistArray:[],
      checkauthority:'',
    } 
  },
  created () {
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByLatest`)
      .then(({ data })=> {
          console.log(data)
          this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
  },
  methods: {
    sortedLatest:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByLatest`)
      .then(({ data })=> {
          console.log(data)
          this.$store.dispatch('reloadStylists', data)
          this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
    },
    sortedGrade:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByGrade`)
      .then(({ data })=> {
          console.log(data)
          this.$store.dispatch('reloadStylists', data)
          this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
    },
    sortedLikes:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByFollower`)
      .then(({ data })=> {
          console.log(data)
          this.$store.dispatch('reloadStylists', data)
          this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)
    },
  },
  watch:{
    selected: function(){
      if (this.selected==1){
        this.sortedLatest()
      }else if(this.selected==2){
        this.sortedGrade()
      }else{
        this.sortedLikes()
      }
      
    }
  },
  computed: {
      ...mapGetters( memberStore, ["checkMemberInfo"],
      // ['stylistArray']
      ),
  },
}
</script>

<style>

</style>