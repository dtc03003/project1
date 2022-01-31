<template>
  <div>
    사용자정보 이렇게 생겼음
    {{ memberStore.state.memberInfo }}
    <hr>
    <!-- <h3>여기는 스타일리스트 목록 개별</h3> -->
    <div class="container-fluid" style="hight:7rem">
      <div>
        <!-- 프로필 사진 -->
        <div id="profilebox" class="" style="width:7rem;">
          <div>
            <b-avatar src="https://placekitten.com/300/300" size="5rem">
            </b-avatar>
          </div>

          <!-- 프로필 이름, 이건 로그인 구현 된 다음 user 받아오고 나서 처리하기 -->
          <h4>{{ stylist.name }}</h4>
          
          <!-- 찜, DB 필요 -->

          <!-- 평점, DB 필요, computed는 만들어놨음-->
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
          </div>
        </div>
        <div id="images" class="d-inline-block" style="height:160px;">
          <!-- 사진 -->
          <img src="@/assets/study.jpg" height="150px" class="m-1">

          <!-- 실제로는 아래처럼 가져와야 함 -->
          <the-image-modal></the-image-modal>
          <!-- stylist data 들어오면 v-for로 연결해서 다 가져오기 -->
          <!-- 여기서 가져오는 건 한 사람의 포트폴리오 사진들 -->
          <!-- <the-stylist-list-image></the-stylist-list-image> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TheImageModal from '@/components/Stylist/TheImageModal'
// import { signin, getMemberInfo } from '@/api/member'
import memberStore from '@/store/modules/memberStore'

export default {
  name: 'TheStylistListItem',
  data: function() {
    return {
      stylist:{
        name: 'jiwon',
        averageScore:5
      },
      memberStore,
    }
  },
  components:{
    TheImageModal,
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
#profilebox {
  display: inline-block;
  text-align: center;
  justify-content: center;
  /* align-items: center; */
}

#images {
  /* overflow: auto; */
  overflow: hidden;
  display: inline-block;
}

.star-ratings {
  color: #aaa9a9; 
  position: relative;
  unicode-bidi: bidi-override;
  width: max-content;
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 1.3px;
  -webkit-text-stroke-color: #2b2a29;
  /* display: flex; */
  /* justify-content: center; */
}

.star-ratings a {
  font-weight: lighter;
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
  /* text-align: center; */
  /* justify-content: center; */
}
 
.star-ratings-base {
  z-index: 0;
  padding: 0;
}
</style>