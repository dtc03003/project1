<template>
<span id="oneline" class="py-2">
    <div id="singleline" class="container-fluid">
      <div class="row d-inline">
        <div id="profilebox" class="d-block-wrap justify-content-center col-12 col-md-10"
          style="width:10rem; padding:1rem;">
          <!-- 프로필 사진 -->
          <div>
            <!-- 나중에 프로필사진 클릭하면 포트폴리오로 넘어갈 수 있도록 -->
            <b-avatar id="avatar" @click.native="goToPortfolio" :src="profile" size="5rem">
            </b-avatar>
          </div>
          <h6 id="name" class="my-1">{{ nickname }}</h6>
          
          <!-- 팔로워 수 -->
          <!-- 좋아요 수 100개 이상 -->
          <div v-if="likes >= 300 ">
            <h5>🏆{{likes}}</h5>
          </div>
          <div v-else-if="likes >=200">
            <h5>🥇{{likes}}</h5>
          </div>
          <div v-else-if="likes >=100">
            <h5>🥈{{likes}}</h5>
          </div>
          <!-- 좋아요 수 100개 미만 -->
          <div v-else>
            <!-- <h5><v-icon style="color:purple;">mdi-hanger</v-icon>{{likes}}</h5> -->
            <!-- <h5><v-icon style="color:purple;">mdi-trophy-variant</v-icon>{{likes}}</h5> -->
            <h5>🥉{{likes}}</h5>
            <!-- <h5>❤{{likes}}</h5> -->
          </div>          
          <!-- 평점 -->


          <div class="d-inline-flex star-ratings">
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
          <div id="score" class="d-inline-flex ps-1"><h6>{{averageScore}}점</h6></div>
          
        </div>
      </div>
    </div>
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
      memberStore,
      likes:0,
      averageScore:0,
      checkauthority:'',
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
        this.likes = data;
      })
    },

    getRates:function() {
      // 평점 평균 가져오는 axios
      axios.get(`${FITMATE_BASE_URL}/api/v1/rate/${this.nickname}`)
      .then(({ data })=> {
        this.averageScore = data;
        })
      this.checkauthority = this.checkMemberInfo.authority
    }
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
      const score = +this.averageScore * 20;
      return score;
    },
  },
  created () {
    this.getLikes()
    this.getRates()
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
}

#images {
  display: inline;
}

#singleline{
  overflow: auto;
  display: inline;
  white-space: nowrap;
}

.star-ratings {
  color: #aaa9a9; 
  position: relative;
  unicode-bidi: bidi-override;
  /* width: fit-content; */
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

#score{
  font-style: italic;
  font-weight: lighter;
}

</style>