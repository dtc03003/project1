<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylist</h1>
      </div>
      <!-- 드롭다운 -->
      <!-- 스타일리스트 들어오면 함수걸어 정렬예정 -->
      <div class="col-3 d-flex align-items-center">
        <select class="form-select" aria-label="Default select example" v-model="selected">
          <option disabled value="">정렬</option>
          <option @click="sortedLatest" value="1">최신순</option>
          <option @click="sortedGrade" value="2">평점순</option>
          <option @click="sortedLikes" value="3">팔로워순</option>
        </select>
      </div>

      <!-- 검색창 -->
      <div>
        <b-form-tags v-model="value" no-outer-focus class="mb-1">
          <template v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag }">
            <b-input-group class="mb-2">
              <!-- input -->
              <b-form-input
                v-bind="inputAttrs"
                v-on="inputHandlers"
                placeholder="New tag - Press enter to add"
                class="form-control"
              ></b-form-input>
              <b-input-group-append>
                <b-button @click="addTag()" variant="dark">Add</b-button>
              </b-input-group-append>
            </b-input-group>
            <!-- 밑에 태그 띄우는 건가봄? -->
            <div class="d-inline-block" style="font-size: 1.5rem;">
              <b-form-tag
                v-for="tag in tags"
                @remove="removeTag(tag)"
                :key="tag"
                :title="tag"
                :variant="tagVariant"
                class="mr-1"
                id="tags"
                size="sm"
              ></b-form-tag>
            </div>
          </template>
        </b-form-tags>
        {{value}}
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
      value: []
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
    // searchStylist:function(){

    // }
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

#tags {
  color: rgb(37, 34, 34);
  background-color: rgb(127, 197, 255);
  size: 3rem;
}

</style>