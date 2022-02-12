<template>
  <div class="container-fluid col-8 offset-2">
    <div class="row">
      <div id="mainbar" class="col-2 d-flex align-items-center">
        <h1>Stylebook</h1>
      </div>

      <!-- 검색창 -->
      <!-- 근데 우리 무슨 검색이었더라? 태그? 스타일리스트? -->
      <div class="col-6 offset-4 align-content-center">
        <v-container fluid>
          <v-combobox
            v-model="model"
            :filter="filter"
            :hide-no-data="!search"
            :items="items"
            :search-input.sync="search"
            hide-selected
            label="Search for an option"
            multiple
            small-chips
            solo
          >
            <template v-slot:no-data>
              <v-list-item>
                <span class="subheading">Create</span>
                <v-chip
                  :color="`${colors[nonce - 1]} lighten-3`"
                  label
                  small
                >
                  {{ search }}
                </v-chip>
              </v-list-item>
            </template>
            <template v-slot:selection="{ attrs, item, parent, selected }">
              <v-chip
                v-if="item === Object(item)"
                v-bind="attrs"
                :color="`${item.color} lighten-3`"
                :input-value="selected"
                label
                small
              >
                <span class="pr-2">
                  {{ item.text }}
                </span>
                <v-icon
                  small
                  @click="parent.selectItem(item)"
                >
                  $delete
                </v-icon>
              </v-chip>
            </template>
            <template v-slot:item="{ index, item }">
              <v-text-field
                v-if="editing === item"
                v-model="editing.text"
                autofocus
                flat
                background-color="transparent"
                hide-details
                solo
                @keyup.enter="edit(index, item)"
              ></v-text-field>
              <v-chip
                v-else
                :color="`${item.color} lighten-3`"
                dark
                label
                small
              >
                {{ item.text }}
              </v-chip>
              <v-spacer></v-spacer>
              <v-list-item-action @click.stop>
                <v-btn
                  icon
                  @click.stop.prevent="edit(index, item)"
                >
                  <v-icon>{{ editing !== item ? 'mdi-pencil' : 'mdi-check' }}</v-icon>
                </v-btn>
              </v-list-item-action>
            </template>
          </v-combobox>
        </v-container>
        items {{items}}<br>
        model {{model}}
      </div>
      <!-- 칩 -->
      <!-- <div>
        <v-chip
          close-icon="mdi-delete"
          color="blue"
          label
        ></v-chip>
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
    // items: ['Gaming', 'Programming', 'Vue', 'Vuetify'],
    // model: ['Vuetify'],
    // search: null,
    stylebooks:[],
    checkauthority:'',
    // 여기서부터는 검색창을 위한 data
    activator: null,
    attach: null,
    colors: ['green', 'purple', 'indigo', 'cyan', 'teal', 'orange'],
    editing: null,
    editingIndex: -1,
    items: [
      { header: 'Select an option or create one' },
    ],
    nonce: 1,
    menu: false,
    model: [],
    x: 0,
    search: null,
    y: 0,
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
  watch: {
    model (val, prev) {
      if (val.length === prev.length) return

      this.model = val.map(v => {
        if (typeof v === 'string') {
          v = {
            text: v,
            color: this.colors[this.nonce - 1],
          }

          this.items.push(v)

          this.nonce++
        }

        return v
      })
    },
  }
}
</script>

<style>

</style>