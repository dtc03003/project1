<template>
  <div>
    <!-- <h2>모달 안 댓글 입력창</h2> -->
    <v-form>
      <v-container class="p-0">
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="message"
              dense
              clear-icon="mdi-close-circle"
              append-outer-icon="mdi-send"
              clearable
              label="댓글은 예쁜 말로!"
              type="text"
              @click:clear="clearMessage"
              @click:append-outer="sendMessage"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <!-- 댓글 리스트 받아오는 부분 -->
    <the-modal-comment-list></the-modal-comment-list>
  </div>
</template>

<script>
import TheModalCommentList from '@/components/Stylist/TheModalCommentList'

export default {
  name: 'TheModalComment',
  components: {
    TheModalCommentList,
  },
  data: function() {
    return {
      password: 'Password',
      show: false,
      message: '',
      marker: true,
      iconIndex: 0,
    }
  },
  computed: {
    icon () {
      return this.icons[this.iconIndex]
    },
  },

  methods: {
    toggleMarker () {
      this.marker = !this.marker
    },
    sendMessage () {
      this.resetIcon()
      this.clearMessage()
    },
    clearMessage () {
      this.message = ''
    },
    resetIcon () {
      this.iconIndex = 0
    },
    changeIcon () {
      this.iconIndex === this.icons.length - 1
        ? this.iconIndex = 0
        : this.iconIndex++
    },
  },
}
</script>

<style>

</style>