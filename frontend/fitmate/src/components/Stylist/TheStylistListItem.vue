<template>
  <div>
    <!-- <h3>여기는 스타일리스트 목록 개별</h3> -->
    <div class="container-fluid wrap col-10 offset-1">
      <div class="row">
        <!-- 프로필 사진 -->
        <div class="col-2">
          <b-avatar src="https://placekitten.com/300/300" size="6rem">
          </b-avatar>
          <!-- 프로필 이름, 이건 로그인 구현 된 다음 user 받아오고 나서 처리하기 -->
          <h4>name</h4>
          <!-- 찜 DB 필요 -->

          <!-- 평점 DB 필요-->
          <div class="star-ratings">
            <div 
              class="star-ratings-fill space-x-2 text-lg"
              :style="{ width: ratingToPercent + '%' }"
            >
              <span>■</span><span>■</span><span>■</span><span>■</span><span>■</span>
            </div>
            <div class="star-ratings-base space-x-2 text-lg">
              <span>■</span><span>■</span><span>■</span><span>■</span><span>■</span>
            </div>
            <h6>평점 {{ stylist.averageScore }}</h6>
          </div>
        </div>
        <div class="col">
          <!-- 사진 -->
          <!-- 실제로는 이렇게 나열하면 안 되고 DB에 있는 거 가져와야 함 -->
          <img src="@/assets/study.jpg" height="150px" class="m-1">
          <img src="@/assets/eunwoo.jpg" height="150px" class="m-1">
          <img src="@/assets/yeonwoo.jpg" height="150px" class="m-1">
          <img src="@/assets/eunwoo.jpg" height="150px" class="m-1">
          <img src="@/assets/study.jpg" height="150px" class="m-1">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TheStylistListItem',
  data: function() {
    return {
      stylist:{
        averageScore:5
      }
    }
  },
  computed: {
    // width 속성은 computed로 api로 넘어온 평균 평점 값을 계산하여 percentage로 변환하여 스타일 바인딩을 이용
    // return값에 1.5를 더하여 주는 이유는 half star일 시 미세하게 절반이 안되어보여서 보여지는 값을 조정하기 위해 추가한 offset 값
    ratingToPercent() {
      const score = +this.stylist.averageScore * 10;
      return score;
    }
  }

}
</script>

<style scoped>
.star-ratings {
  color: #aaa9a9; 
  position: relative;
  unicode-bidi: bidi-override;
  width: max-content;
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 1.3px;
  -webkit-text-stroke-color: #2b2a29;
}
 
.star-ratings-fill {
  color: #fff58c;
  padding: 0;
  position: absolute;
  z-index: 1;
  display: flex;
  top: 0;
  left: 0;
  overflow: hidden;
  -webkit-text-fill-color: rgb(105, 199, 186);
}
 
.star-ratings-base {
  z-index: 0;
  padding: 0;
}
</style>