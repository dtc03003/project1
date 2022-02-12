<template>
  <div >
    <MyPageHistory id ="my-page-history" v-for="(reservation, idx) in mypayments" :key="reservation.id" :idx="idx" :reservation="reservation"/>
    {{this.mypayments[4]}}
  </div>
</template>

<script>
// import axios from 'axios';
import { mapGetters, mapState, mapActions } from 'vuex';
import MyPageHistory from "./history/MyPageHistory.vue"
// import { FITMATE_BASE_URL } from "@/config";
const memberStore = "memberStore";
const reserveStore = "reserveStore";

export default {
  data() {
    return {
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
    ...mapState(reserveStore, ["mypayments"]),
  },
  methods: {
    ...mapActions(reserveStore, ["importMyPayment"]),
    async getHistory() {
      this.importMyPayment(this.checkMemberInfo.nickname)
    }
  }
}
</script>

<style>

</style>