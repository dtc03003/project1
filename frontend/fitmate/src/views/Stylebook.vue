<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <div class="col-6 offset-4">
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
 
          </v-combobox>
        </v-container>
      </div>
      <div class="d-flex-wrap">
        <the-image-modal
        v-for="image in stylebooks"
        v-bind:key="image.id"
        v-bind:thumbnail="image.thumbnail"
        v-bind:profile="image.portfolio.member.profile"
        v-bind:id="image.id"
        v-bind:content="image.content"
        v-bind:nickname="image.portfolio.member.nickname"     
        >{{image.id}}</the-image-modal>
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
    items: ['Gaming', 'Programming', 'Vue', 'Vuetify'],
    model: ['Vuetify'],
    search: null,
    stylebooks:[],
    checkauthority:''
  }),
  created () {
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
}
</script>

<style>

</style>