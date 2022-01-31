<template>
	<span>
    <img src="@/assets/study.jpg" @click="$bvModal.show('bv-modal-example')" height="150px" class="m-1">
    <!-- 이미지를 클릭했을 때 뜨는 모달 -->
    <b-modal size="lg" id="bv-modal-example" hide-footer>
      <template #modal-title>
        <b-avatar src="https://placekitten.com/300/300" size="4rem">
        </b-avatar>
        <!-- 지금은 멤버 스토어에서 가져오는데, 나중에는 글 쓴 사람 이름 가져와야 함 -->
        <!-- <h3 class="d-inline">{{ memberStore.state.memberInfo.nickname }}</h3> -->
      </template>
      <div class="row">
        <div class="col">
          <!-- 상세 이미지 -->
          <img src="@/assets/study.jpg" alt="" height="400rem" class="mr-2">
          <!-- 태그 -->
        </div>
        <div class="col">
          <h6>게시 내용 여기 와야 함</h6>
          <the-modal-comment></the-modal-comment>
        </div>
      </div>

      <b-button class="mt-3 d-block" block @click="$bvModal.hide('bv-modal-example')">Close Me</b-button>
    </b-modal>
    </span>
</template>

<script>
import TheModalComment from '@/components/Stylist/TheModalComment'
import memberStore from '@/store/modules/memberStore'

export default {
  name: 'TheImageModal',
  components: {
    TheModalComment,
  },
  data: function() {
    return {
      memberStore,
    }
  }

}
</script>


<style>

</style>