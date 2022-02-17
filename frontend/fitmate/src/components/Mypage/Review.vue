<template>
  <div>
    <!--작성해야 할 리뷰가 있을 경우만 버튼 보임-->
    <div align="right">
      <a class="gradient-btn" v-b-modal.modal-1 v-show="reviews.length < payments.length">리뷰작성</a>
    </div>
    <div v-if="!status" align="center">
      <p id="nomyreview">아직 리뷰가 없습니다! 저희 서비스를 이용해주시고 리뷰를 작성해주세요😍</p>
    </div>
    <div v-else>
      <ReviewDetail id="my-page-review" v-for="(review, idx) in someReviews" :key="review.id" :idx="idx" :review="review" :name="'MyPage'"/>
      
      <b-pagination align="center" class="mt-2"
          v-model="page"
          :total-rows="rows"
          :per-page="perPage"
          @change="changePage"
          id="rvpgnation"
      ></b-pagination>
    </div>

    <b-modal size="lg" id="modal-1" ref="modal-1" hide-footer>
      <b-row>
        <b-col>
          <b-dropdown id="dropdown-1" text="기록🔍" class="m-md-2 selectStylist">
            <b-dropdown-item v-for="data in consultinfo" :key="data.id" @click="selectinfo(data)">{{`스타일리스트명: ${data}`}}</b-dropdown-item>
          </b-dropdown>
          <p class="selectStylist" v-show="this.selectedname">{{`스타일리스트명: ${this.selectedname}`}}</p>
        </b-col>
        <b-col class="col-12">
          <div class="rating-container">
            <div class="rating-wrap">
              <div class="center">
                <h5>평점을 입력해주세요(5점만점)</h5>
                <fieldset class="rating">
                  <input type="radio" id="star5" name="rating" value="5" @change="showStar($event)"/><label for="star5" class="full"></label>
                  <input type="radio" id="star4" name="rating" value="4" @change="showStar($event)"/><label for="star4" class="full"></label>
                  <input type="radio" id="star3" name="rating" value="3" @change="showStar($event)"/><label for="star3" class="full"></label>
                  <input type="radio" id="star2" name="rating" value="2" @change="showStar($event)"/><label for="star2" class="full"></label>
                  <input type="radio" id="star1" name="rating" value="1" @change="showStar($event)"/><label for="star1" class="full"></label>
                </fieldset>
              </div>
            </div>
          </div>
        </b-col>
        <b-col class="col-12">
            <h4>리뷰내용✏️</h4>
            <b-form-textarea id="textarea" v-model="post.content" placeholder="최대 255자 입력 가능합니다." rows="10" max-rows="10">                            
            </b-form-textarea>
        </b-col>

        <h4>이미지🖼️</h4>
        <b-col class="col-12">
            <UploadImages :max="1" ref="image" @changed="handleImages"/>
        </b-col>

        <b-col class="col-12">
            <a class="btn" @click="Posting">게시하기</a>
        </b-col>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
import UploadImages from "vue-upload-drop-images"
import ReviewDetail from '@/components/review/ReviewDetail.vue';
import { mapGetters, mapActions } from 'vuex';
import Swal from 'sweetalert2'
// import dayjs from 'dayjs';
const reviewStore = "reviewStore";
const memberStore = "memberStore";
const reserveStore = "reserveStore";

export default {
  name: "Review",
  data() {
    return {
      reviews: [],
      someReviews: [],
      page: 1,
      perPage: 5,
      post: {
          content: '',
          rating: 0,
          thumbnail: '',
      },
      payments: [],
      consultinfo: [],
      selectedname: '',
      status: false,
    }
  },
  components: {
    ReviewDetail,
    UploadImages
  },
  created() {
        this.importReviews();
  },
  computed: {
      ...mapGetters(reviewStore, ["getReviewsByUser", "getImagesrc", "getRating", "getReviews"]),
      ...mapGetters(memberStore, ["checkMemberInfo"]),
      ...mapGetters(reserveStore, ["getMyPayments"]),
      rows() {
        return this.reviews.length
      },
  },
  methods: {
    ...mapActions(reviewStore, ["uploadRVImage", "writeNewReview", "findAllReviews", "findCount", "updateRating", "importAllReviews"]),
    ...mapActions(reserveStore, ["importMyPayment"]),
    async importReviews() {
      //자신의 전체 리뷰 가져오기
      await this.findAllReviews(this.checkMemberInfo.id);
      this.reviews = this.getReviewsByUser;
      this.someReviews = this.reviews.slice(0, 5);
      if(this.reviews.length > 0) this.status = true;

      //자신의 전체 결제 내역(결제 성공한 내역만)
      await this.importMyPayment();
      let result = this.getMyPayments.filter((element) => element.state == 'COMPLETE');

      //결제 내역 중 의뢰 날짜/시간 끝난 것만 뽑기
      let now = new Date(Date.now());
      this.payments = result.filter((element) => new Date(element.reservation.endTime) < now);
      this.consultinfo = [];
      // let size = this.payments.length - this.reviews.length;
      // if(size > 0 && this.consultinfo.length != size) this.findCount();
      this.findCount();
    },
    findCount() {
      //결제 내역과 리뷰 내역 개수 비교 후 작성해야 할 리뷰 파악
      //리뷰 < 상담 완료 결제 개수 -> 작성 가능

      //작성한 리뷰에 해당되는 스타일리스트 뽑기
      let revarr = this.reviews;
      let revStylelists = [];
      for(let i=0; i < this.reviews.length; i++) {
        let rev = revarr[i].portfolio.member.nickname;
        revStylelists.push(rev);
      }

      //상담이 끝난 내역의 스타일리스트 뽑기
      let payStylelists = [];
      let arr = this.payments;
      for(let i=0; i < this.payments.length; i++) {
        let consult = arr[i].reservation.portfolio.member.nickname
        // {
          // 'nickname' : arr[i].reservation.portfolio.member.nickname,
          // 'date' : dayjs(arr[i].reservation.endTime).format("YYYY-MM-DD HH:00"),
        // }
        payStylelists.push(consult);
      }

      // let size = this.payments.length - this.reviews.length; //리뷰-결제 건수 차이
      // let temp = 0;
      for(let i = payStylelists.length-1; i >= 0; i--) {
        if(!revStylelists.includes(payStylelists[i])) {
          this.consultinfo.push(payStylelists[i])
          // temp++;
        }else {
          let idx = revStylelists.indexOf(payStylelists[i]);
          revStylelists.splice(idx, 1);
        }
        // if(size == temp) break;
      }
    },

    async changePage(page) { //페이지 바꾸기
      this.someReviews = this.reviews.slice(5*(page-1), 5*page);
    },
    async handleImages(){ //이미지 업로드
      const formData = new FormData();
      const image = this.$refs['image'].files[0];

      let fileExt = image.name.substring(image.name.lastIndexOf(".") + 1)

      if(["jpeg","jpg","png","bmp"].includes(fileExt) && image.size <= 1048576) {
        formData.append('images', image);
        
        await this.uploadRVImage(formData);
        this.post.thumbnail = this.getImagesrc;
      }else {
        Swal.fire({
          icon: 'error',
          title: '파일 형식에 맞지 않거나 \n사진 크기가 너무 큽니다! \nclear All 후 다시 진행해주세요!',
          confirmButtonColor: '#7e7fb9',
          confirmButtonText: "확인",
        })
      }
    },
    async Posting() { //게시하기
      if(!this.post.content || this.post.rating == 0 || !this.post.thumbnail || !this.selectedname) {
        Swal.fire({
          icon: 'error',
          title: '작성할 기록 선택 후 평점, 내용, 사진 모두 작성해주세요!😮',
          confirmButtonColor: '#7e7fb9',
          confirmButtonText: "확인",
        })
      }else {
        const postInfo = {
            content: this.post.content,
            rating: this.post.rating,
            thumbnail: this.post.thumbnail,
            nickname: this.selectedname
        }
        await this.writeNewReview(postInfo);
        //작성한 리뷰 관련 내용 삭제
        // let temp = this.consultinfo;
        // for(let i = 0; i < this.consultinfo.length; i++) {
        //   if(JSON.stringify(this.consultinfo[i]) == JSON.stringify({'nickname' : this.selectedname, 'date' : this.selectedDate})) this.consultinfo.splice(i, 1);
        //   }
        await this.importReviews(); //리뷰 갱신
        await this.findNum(this.selectedname); //평점 갱신
        const Toast = Swal.mixin({
          toast: true,
          position: 'top-end',
          showConfirmButton: false,
          timer: 1000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
          }
        })
        Toast.fire({
          icon: 'success',
          title: '리뷰가 등록되었습니다😄'
        })
        this.$refs['modal-1'].hide();
      }
    },
    async findNum(nickname) { //팔로워 수 및 평점 찾기
      await this.findCount(nickname); //팔로워 수
      await this.importAllReviews(nickname); //리뷰전체
      this.updateNewRating(nickname);
    },
    async updateNewRating(nickname) {
      let totalrv = this.getReviews; //해당 스타일리스트 관련 전체 리뷰
      let sum = 0;
      for(let temp of totalrv) {
        sum += temp.rating;
      }

      let grade = sum / totalrv.length;
      const info = {
        "stylistNickName": nickname,
        "followCount": this.getRating,
        "grade": Math.round(grade),
      }
      await this.updateRating(info);
    },
    showStar(event) {
      this.post.rating = event.target.value;
    },
    selectinfo(data) {
      this.selectedname = data;
    }
  }
}
</script>

<style lang="css" scoped>
p#nomyreview {font-size: 20pt; font-family: 'GangwonEdu_OTFBoldA';}
h5, h4 {font-family: 'Cafe24Ssurround', serif;}

/* 별점 관련 */
.rating {
  border: none;
  float: left;
}

.rating > input{
  display: none;
}

.rating > label:before {
  content: '■';
  margin: 5px;
  font-size: 1.5rem;
  display: inline-block;
  cursor: pointer;
}

.rating > label {
  color: #ddd;
  float: right;
  cursor: pointer;
}

.rating > input:checked ~ label,
.rating:not(:checked) > label:hover,
.rating:not(:checked) > label:hover ~ label {
  color: #6ba3ff;
}

.rating > input:checked + label:hover,
.rating > input:checked ~ label:hover,
.rating > label:hover ~ input:checked ~ label,
.rating > input:checked ~ label:hover ~ label {
  color: #a1c4fd;
}

/*모달 게시 버튼 */
.btn {
  flex: 1 1 auto;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
  width: 100%;
  font-family: "SDSamliphopangche_Basic", "ImcreSoojin", fantasy;
  background-image: linear-gradient(to right, #a1c4fd 0%, #c2e9fb 51%, #a1c4fd 100%);
}
.btn:hover { background-position: right center;}
#rvpgnation {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 1rem;
}
.selectStylist, ::placeholder { font-family: "Pretendard-SemiBold", fantasy; }
</style>

<style lang="scss" scoped>
$ease_out: cubic-bezier(0.165, 0.84, 0.44, 1);
@mixin transition() {
    transition: 700ms $ease_out;
    &:hover{
      transition: 400ms $ease_out;
    }
}
.gradient-btn{
  display: inline-block;
  padding: 0.5em 1em;
  border-radius: 0.5rem;
  color: #8763FB;
  margin-top:2rem;
  font-weight: bold;
  font-size: 1rem;
  letter-spacing: 2px;
  text-transform: uppercase;
  text-decoration: none;
  background: linear-gradient(to right, rgba(#8763FB, 0) 25%, rgba(#8763FB, .8) 75%);
  background-position: 1% 50%;
  background-size: 400% 300%;
  border: 1px solid #8763FB;
  @include transition;
  
  &:hover{
    color: white;
    color: #fff;
    background-position: 99% 50%;
  }
}
</style>