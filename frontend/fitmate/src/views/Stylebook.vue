<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-12 align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 바꿔 갈아끼우는 중 -->
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
      <v-combobox
      v-model="value"
      clearable
      multiple
      append-icon="mdi-tag-search"
      solo>
      </v-combobox>
      <b-form-tag
        v-for="tag in value"
        @remove="removeTag(tag)"
        :key="tag"
        :title="tag"
        :variant="tagVariant"
        class="mr-1"
        style="background:teal; width:fit-content;"
      >{{ tag }}</b-form-tag>

      
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
  }),
  methods:{
    saveValue:function() {
      console.log(this.singletag)
      this.value.push(this.singletag)
      this.singletag = ''
    },
    searchImages:function() {
      ///styleBook/search/{tagList}
      axios.get(`${FITMATE_BASE_URL}/api/v1/styleBook/search/${this.value}`)
      .then(({ data })=> {
          console.log(data)
          this.stylebooks = data;
      })
      this.checkauthority = this.checkMemberInfo.authority
      console.log(this.checkauthority)     
    }
  },
  created () {
    // 있는 사진 전부 불러오는 거
    axios.get(`${FITMATE_BASE_URL}/api/v1/styleBook/search`)
    .then(({ data })=> {
        console.log(data)
        this.stylebooks = data;
    })
    this.checkauthority = this.checkMemberInfo.authority
    console.log(this.checkauthority)
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

<style>

</style>