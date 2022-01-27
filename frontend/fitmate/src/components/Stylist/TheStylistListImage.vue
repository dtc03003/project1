<template>
	<span>
    <img src="@/assets/study.jpg" @click="$bvModal.show('bv-modal-example')" height="150px" class="m-1">
    <b-modal id="bv-modal-example" hide-footer>
      <template #modal-title>
        <!-- 사진 -->
        Using <code>$bvModal</code> Methods

        <!-- 프로필 & 이름 -->

        <!-- 게시 내용 -->

        <!-- 댓글입력폼 -->

        <!-- 댓글들 -->
      </template>
      <div class="d-block text-center">
        <h3>Hello From This Modal!</h3>
      </div>
      <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
    </b-modal>
    </span>
</template>

<script>
export default {
  name: 'TheStylistListImage',
  components: {},
  data: function() {

  },

}
</script>

<style>

</style>