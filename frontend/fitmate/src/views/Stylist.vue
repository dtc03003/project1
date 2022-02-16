<template>
  <div class="container-fluid col-10 offset-1">
    <div class="row d-flex">
      <div id="subbar" class="d-flex align-items-center justify-content-start">
        <h1>Stylist</h1>
      </div>
      <!-- 드롭다운 -->
      <!-- 스타일리스트 들어오면 함수걸어 정렬예정 -->
      <div id="inputtext" class="col col-md-2 d-inline align-items-center">
        <select id="select" class="form-select" aria-label="Default select example" v-model="selected">
          <option disabled value="">정렬</option>
          <option @click="sortedLatest" value="1">최신순</option>
          <option @click="sortedGrade" value="2">평점순</option>
          <option @click="sortedLikes" value="3">팔로워순</option>
        </select>
      </div>

      <!-- 스타일리스트 검색창 -->
      <div id="inputtext" class="d-inline col col-md-5 offset-md-5">
        <v-text-field
          v-model="text"
          dense
          solo
          clearable
          label="찾고 싶은 스타일리스트를 검색하세요."
          @keyup.enter="searchStylist"
          append-icon="mdi-account-search"
          @click:append="searchStylist"
        ></v-text-field>
      </div>

      <!-- <div class="mt-2">Value: {{ text }}</div> -->

      <!-- 스타일리스트 목록 컴포넌트 -->
      <div id="images">
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
      text:''
    } 
  },
  created () {
    // 디폴트값은 최신순 정렬
    axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByLatest`)
    .then(({ data })=> {
      this.stylistArray = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
  },
  methods: {
    // 최신순 정렬
    sortedLatest:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByLatest`)
      .then(({ data })=> {
        this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
    },
    // 평점에 따른 정렬
    sortedGrade:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByGrade`)
      .then(({ data })=> {
        this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
    },
    //팔로워에 따른 정렬
    sortedLikes:function(){
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/sortByFollower`)
      .then(({ data })=> {
        this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
    },
    searchStylist: function() {
      // 검색어에 해당되는 것만 가져오기
      axios.get(`${FITMATE_BASE_URL}/api/v1/stylists/search/${this.text}`)
      .then(({ data })=> {
        this.stylistArray = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
    }
  },
  watch:{
    // 드롭다운 변경시 일어나는 변화
    selected: function(){
      if (this.selected==1){
        this.sortedLatest()
      }else if(this.selected==2){
        this.sortedGrade()
      }else{
        this.sortedLikes()
      }      
    },
  },
  computed: {
    ...mapGetters( memberStore, ["checkMemberInfo"],
    ),
  },
}
</script>

<style scope>

select{
  box-shadow: 1px 2px 2px rgb(180, 180, 180);
}

</style>