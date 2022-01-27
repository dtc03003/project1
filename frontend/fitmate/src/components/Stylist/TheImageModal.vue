<template>
	<span>
    <img src="@/assets/study.jpg" @click="$bvModal.show('bv-modal-example')" height="150px" class="m-1">
    <b-modal size="lg" id="bv-modal-example" hide-footer>
      <template #modal-title>
        <b-avatar src="https://placekitten.com/300/300" size="4rem">
        </b-avatar>
      </template>
      <div class="d-inline">
        <!-- 상세 이미지 -->
        <img src="@/assets/study.jpg" alt="" height="400rem" class="mr-2">
        <!-- 태그 -->
      </div>
      <b-button class="mt-3 d-block" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
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