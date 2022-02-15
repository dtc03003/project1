<template>
  <div class="container-fluid col-10 offset-1">
    <div class="row">
      <div id="subbar" class="col-12 align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 지우지 말아주세요 -->
      <!-- <v-text-field
      v-model="singletag"
      clearable
      multiple
      @keyup.enter="saveValue"
      append-icon="mdi-tag-search"
      @click:append="saveValue"
      solo>
      </v-text-field> -->
      <div id="inputtext">
        <v-combobox
        v-model="value"
        clearable
        multiple
        label="당신의 스타일 태그를 입력하세요!"
        append-icon="mdi-tag-search"
        solo
        >
        </v-combobox>
      </div>


      <b-form-tag
        v-for="tag in value"
        @remove="removeTag(tag)"
        :key="tag"
        class="mr-1"
        id="searchtag"
      >{{tag}}</b-form-tag>

      
      <!-- <div v-if="!stylebooks">
        <h5>검색결과가 없습니다.</h5>
      </div> -->

      <!-- 이미지 및 모달 부분 -->
      <div class="d-flex-wrap">
        <the-image-modal
        v-for="image in stylebooks"
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
import axios from 'axios'
import { FITMATE_BASE_URL } from '@/config'
import { mapGetters } from 'vuex'
// import TheStylebookImage from '@/components/Stylebook/TheStylebookImage'
import TheImageModal from '@/components/Stylist/TheImageModal'

export default {
  name:'Stylebook',
  components:{
    // TheStylebookImage,
    TheImageModal
  },
  data: () => ({
    stylebooks:[],
    checkauthority:'',
    // 여기서부터는 검색창을 위한 data
    value: [],
    singletag:'',
    color:'',
  }),
  methods:{
    saveValue:function() {
      this.value.push(this.singletag)
      this.singletag = ''
    },
    searchImages:function() {
      ///styleBook/search/{tagList}
      axios.get(`${FITMATE_BASE_URL}/api/v1/styleBook/search/${this.value}`)
      .then(({ data })=> {
        this.stylebooks = data;
      })
      this.checkauthority = this.checkMemberInfo.authority    
    },
    // 태그 삭제 함수
    removeTag(data){
      let newValue  = this.value.filter((element) => element !== data);
      this.value = newValue
    }
  },
  created () {
    // 있는 사진 전부 불러오는 거
    axios.get(`${FITMATE_BASE_URL}/api/v1/styleBook/search`)
    .then(({ data })=> {
      this.stylebooks = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
  },
  computed: {
      ...mapGetters( 'memberStore', ["checkMemberInfo"]),
  },
  watch: {
    value:function(){
      this.searchImages()
    },
  }
}
</script>

<style scopeed>

#searchtag{
  /*style="background:teal; width:fit-content; height:2rem"*/
  background-color: rgb(102,103, 171);
  width: fit-content;
  height: 2rem;
  font-size: 0.9rem;
  text-align: center;
  align-self: center;
}

</style>