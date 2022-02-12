<template>
  <div>
    <b-container class="detail">
      <b-row class="tmm">
        <b-col cols="3" class="r history-img">
          <img class="history-user-img" :src="profile" />
        </b-col>
        <b-col cols="9" class="r">
          <b-row>
            <b-col>
              <h3 align="left">FITMATE 주문정보</h3>
            </b-col>
            <b-col>
              <h3 align="right">{{consultstate}}</h3>
            </b-col>
          <hr>
          </b-row>

          <b-row>
            <p>스타일리스트 : {{stylistname}}</p>
          </b-row>
          <b-row>
            <p>모델 : {{membername}}</p>
          </b-row>
          <b-row>
            <p>스타일링 시간 : {{startTime}} ~ {{endTime}} / {{between}}시간</p>
          </b-row>
          <b-row>
            <p>비용 : {{cost}}원</p>
          </b-row>
          <b-row>
            <p>결제여부 : {{coststate}}</p>
          </b-row>
          <b-row>
            <p style="margin-bottom: 0rem">결제일시 : {{createdAt}}</p>
          </b-row>
          <img class="fitmatelogo" align="right" src="@/assets/Fitmate.png">
        </b-col>
      </b-row>
      <!-- 
    
    {{consultstate}} -->
    
    </b-container>
    
  </div>
  
</template>

<script>
import dayjs from 'dayjs';
export default {
  name: "MyPageHistory",
  data() {
    return {
      cost: '',
      stylistname: '',
      membername: '',
      startTime: '',
      endTime: '',
      createdAt: '',
      between: '',
      coststate: '',
      consultstate: '',
      profile: '',
    }
  },
  created() {
    let reservecost = this.reservation.reservation.cost
    this.cost = (reservecost.toLocaleString())
    this.stylistname = this.reservation.reservation.portfolio.member.nickname
    this.startTime = dayjs(this.reservation.reservation.startTime).format("YYYY-MM-DD HH:mm")
    this.endTime = dayjs(this.reservation.reservation.endTime).format("YYYY-MM-DD HH:mm")
    this.createdAt = dayjs(this.reservation.reservation.createdAt).format("YYYY-MM-DD HH:mm")
    this.between = this.reservation.reservation.between
    if (this.reservation.state == 'COMPLETE') { this.coststate = '결제완료' } 
    else { this.coststate = '결제대기' }
    if (this.reservation.reservation.state == 'COMPLETE') { this.consultstate = '스타일링완료' } 
    else { this.consultstate = '대기중'}
    this.profile = this.reservation.reservation.portfolio.member.profile
    this.membername = this.reservation.reservation.member.nickname

  },
  props: {
    idx: Number,
    reservation: Object,
  },
  methods: {

  }
} 
</script>

<style>
.detail { width: 80%; display: flex;}
.r { border: 1px solid black; }
img {max-width: 100%;}
.history-img {
  display: block;
  margin: 0 auto;
  width: 100%; height: 350px;
  overflow: hidden;
}
.history-user-img {
  width: 100%; height: 100%;
  object-fit: cover;
}
.tmm {
  margin: 5px;
}
.gotobottom {
  align-items: baseline;
}
.fitmatelogo {
  width: 5%;
  position: relative;
}
</style>