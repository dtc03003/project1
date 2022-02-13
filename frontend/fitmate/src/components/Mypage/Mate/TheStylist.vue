<template>
  <span>
    <!-- 프로필 사진 -->
    <div id="profilebox" class="" style="width:7rem;">
        <div>
            <b-avatar @click.native="goToPortfolio" :src="profile" size="5rem"></b-avatar>
        </div>

        <h5>{{ nickname }}</h5>
        
        <!-- 찜, DB 필요 -->
        <h5>❤{{likes}}</h5>

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

    <div id="images" class="d-inline-block" style="height:160px;"></div>

  </span>
</template>

<script>
import memberStore from '@/store/modules/memberStore'
import { mapState, mapGetters } from 'vuex'
import axios from 'axios'
import { FITMATE_BASE_URL } from '@/config'

export default {
  name: 'TheStylist',
  data: function() {
    return {
      stylist:{
        name: 'jiwon',
        averageScore:5
      },
      memberStore,
      likes:0,
      
    }
  },
  props:{
    nickname:String,
    profile:String,
    stylistId:Number,
    member:Object
  },

  methods:{
    goToPortfolio: function(){
      this.$router.push(`/portfolio/${this.nickname}`)
    },
    getLikes:function(){
      // 찜 가져오는 axios
      axios.get(`${FITMATE_BASE_URL}/api/v1/countOfFollower/${this.nickname}`)
      .then(({ data })=> {
        console.log('찜 바꾸기 성공') 
        console.log(this.stylistId)      
        console.log(data)
        this.likes = data;
      })
    },
  },

  computed: {
    ...mapState(
      'styleStore',['styles']
    ),
    ...mapGetters(
      'memberStore', ['checkMemberInfo']
    ),
    // width 속성은 computed로 api로 넘어온 평균 평점 값을 계산하여 percentage로 변환하여 스타일 바인딩을 이용
    // return값에 1.5를 더하여 주는 이유는 half star일 시 미세하게 절반이 안되어보여서 보여지는 값을 조정하기 위해 추가한 offset 값
    ratingToPercent() {
      const score = +this.stylist.averageScore * 10;
      return score;
    },
  },
  created () {
    
    // 찜 가져오는 axios
    axios.get(`${FITMATE_BASE_URL}/api/v1/countOfFollower/${this.nickname}`)
    .then(({ data })=> {
      console.log('찜 성공') 
      console.log(this.stylistId)      
      console.log(data)
      this.likes = data;
    })
  },

  watch:{
    nickname: function(){
      this.getLikes()
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