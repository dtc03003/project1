<template>
    <div>
        <v-app>
            <v-container class="mt-5">
                <b-row>
                    <img src="@/assets/payTitle.png" id="payTitle" alt="결제 완료"/>
                </b-row>
                <b-row class="mb-3">
                <!-- 고객 정보 -->
                    <table>
                        <tr>
                            <td>구매자 정보</td>
                            <td>{{this.data.member.name}}</td>
                        </tr>
                        <tr>
                            <td>닉네임</td>
                            <td>{{this.data.member.nickname}}</td>
                        </tr>
                        <tr>
                            <td>이메일</td>
                            <td>{{this.data.member.email}}</td>
                        </tr>
                        <tr>
                            <td>전화번호</td>
                            <td>{{this.data.member.phone ? this.data.member.phone.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`) : "없음"}}</td>
                        </tr>
                    </table>
            </b-row>
            <b-row class="mb-3">
                <!-- 결제 정보 -->
                <table>
                    <tr>
                        <td>스타일리스트</td>
                        <td>{{this.data.portfolio.nickname}}</td>
                    </tr>
                    <tr>
                        <td>스타일리스트 이메일</td>
                        <td>{{this.data.portfolio.member.email}}</td>
                    </tr>
                    <tr>
                        <td>확정 날짜</td>
                        <td>{{this.getDate}}</td>
                    </tr>
                    <tr>
                        <td>시작 시간 및 종료 시간</td>
                        <td>{{this.getTime}} ~ {{this.endTime}}</td>
                    </tr>
                    <tr>
                        <td>결제 비용</td>
                        <td>{{this.data.cost.toLocaleString()}}원</td>
                    </tr>
                </table>
            </b-row>
            <b-row>
                    <center>
                        <b-button type="button" class="text-decoration-none" id="moveHome" @click="goHome">Home</b-button>
                    </center>
            </b-row>
            </v-container>
        </v-app>
    </div>
</template>

<script>
import dayjs from "dayjs";
import { mapActions, mapGetters } from 'vuex';
const orderStore = "orderStore";
const reserveStore = "reserveStore";
const tokens = {};

export default {
    name: "OrderApproval",
    data() {
        return {
            pg_token: '',
            data: '',
            endTime: '',
        }
    },
    created() {
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { tokens[key] = value; });
        if(tokens['pg_token']) {
            this.pg_token = tokens['pg_token'];
        }

        this.registPayment();
        this.endTime = dayjs(new Date(this.data.endTime)).format("HH:00");
    },
    computed: {
        ...mapGetters(orderStore, ["getID", "getDate", "getTime", "getOrderData"]),
        ...mapGetters(reserveStore, ["getMyPayments"]),
    },
    methods: {
        ...mapActions(orderStore, ["approvalPay"]),
        ...mapActions(reserveStore, ["importMyPayment"]),
        async registPayment() {
            this.data = this.getOrderData;
            let check = true;
            let size = this.getMyPayments.size;
            for(let i = 1; i < size; i++) {
                if(JSON.stringify(this.data) == JSON.stringify(this.getMyPayments[i])) {
                    check = false;
                    break;
                }
            }
            if(check) await this.approvalPay(this.getOrderData);
        },
        goHome() {
            this.$router.push({name: "Home"});
        }
    },
}
</script>

<style scoped>
h2 {margin: 0 auto;}
table {width: 50%; margin: 0 auto;}
td {border: 2px solid #6667AB; padding: 1%; font-family: 'ChosunGu', serif; font-weight: bold;}
td:first-of-type {width: 40%; text-align: center;}
td:last-of-type {width: 60%; padding-left: 3%;}
#payTitle {width: 600px; margin: 0 auto;} /* max-width: 50%; */
#moveHome {width: 100p; padding: 0.5% 2%; background-color: #bbbbe0; font-size: 14pt;
        font-family: 'ChosunGu', serif; vertical-align: middle; font-weight: bold; border: 1px solid #bbbbe0;}
</style>