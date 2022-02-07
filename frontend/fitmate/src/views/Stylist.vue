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
          <option value="1">최신순</option>
          <option value="2">평점순</option>
          <option value="3">팔로워순</option>
        </select>
      </div>
      <!-- <span>선택함: {{ selected }}</span> -->

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <!-- <div class="col-6 offset-2">
        <v-container fluid>
          <v-combobox
            v-model="model"
            :items="items"
            :search-input.sync="search"
            hide-selected
            hint="Maximum of 5 tags"
            label="ex)출근룩, 하객룩"
            multiple
            persistent-hint
            small-chips
          >
            <template v-slot:no-data>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>
                    No results matching "<strong>{{ search }}</strong>". Press <kbd>enter</kbd> to create a new one
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-combobox>
        </v-container>
      </div> -->


      <!-- 스타일리스트 목록 컴포넌트 -->
      <the-stylist-list 
      v-for="stylist in stylistArray"
      v-bind:key="stylist.id"
      v-bind:nickname="stylist.nickname"
      >
      {{stylist.id}}
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
    methods:{
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
    // watch: {
    //   model (val) {
    //     if (val.length > 5) {
    //       this.$nextTick(() => this.model.pop())
    //     }
    //   },
    // },
    computed: {
        ...mapGetters( memberStore, ["checkMemberInfo"]),
    },

}
</script>

<style>

</style>