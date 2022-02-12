<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-12 align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <div class="col-12">
        <div>
          <b-form-tags v-model="value" no-outer-focus class="mb-1">
            <template v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag }">
              <b-input-group class="mb-2">
                <!-- input -->
                <b-form-input
                  v-bind="inputAttrs"
                  v-on="inputHandlers"
                  placeholder="태그 검색"
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
          <span>value : </span>{{value}}
        </div>
      </div>
      <!-- 칩 -->

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
  }),
  methods:{
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
    }
  }
}
</script>

<style>

</style>