<template>
  <div>
    <div v-if="!status" align="center">
      <p id="nomyreview">아직 결제내역이 없습니다! 저희 서비스를 이용해주세요😍</p>
    </div>
    <div v-if="status">
      <MyPageHistory id ="my-page-history" v-for="(reservation, idx) in mypayments" :key="reservation.id" :idx="idx" :reservation="reservation"/>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex';
import MyPageHistory from "./history/MyPageHistory.vue"
const memberStore = "memberStore";
const reserveStore = "reserveStore";

export default {
  data() {
    return {
      status: false,
      mypayments: [],
    }
  },
  components: {
    MyPageHistory,
  },
  created() {
    this.getHistory();
  },
  computed: {
    ...mapGetters(memberStore, ["checkMemberInfo"]),
    ...mapGetters(reserveStore, ["getMyPayments"]),
    ...mapState(memberStore, ["isSignin", "memberInfo"]),
  },
  methods: {
    ...mapActions(reserveStore, ["importMyPayment"]),
    async getHistory() {
      await this.importMyPayment(this.checkMemberInfo.nickname);
      this.mypayments = this.getMyPayments;
      if (this.mypayments.length > 0) {
        this.status = true
      }
    }
  }
}
</script>

<style>
p#nomyreview {font-size: 20pt; font-family: 'GangwonEdu_OTFBoldA';}
</style>