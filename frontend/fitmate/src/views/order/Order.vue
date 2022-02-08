<template>
    <div>
        <!-- 주문페이지 입니다. -->        
        <b-container class="bv-example-row mt-1 container">
            <b-row>
                <img src="@/assets/orderTitle.png" id="orderTitle" alt="스타일 의뢰서"/>
            </b-row>
            <b-row class="mb-3">
                <!-- 고객 정보 -->
                <table>
                    <tr>
                        <td>구매자 정보</td>
                        <td>{{this.member.name}}</td>
                    </tr>
                    <tr>
                        <td>닉네임</td>
                        <td>{{this.member.nickname}}</td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td>{{this.member.email}}</td>
                    </tr>
                    <tr>
                        <td>전화번호</td>
                        <td>{{this.member.phone ? this.member.phone : "없음"}}</td>
                    </tr>
                </table>
            </b-row>
            <b-row class="mb-3">
                <!-- 결제 정보 -->
                <table>
                    <tr>
                        <td>스타일리스트</td>
                        <td>{{this.styleList.nickname}}</td>
                    </tr>
                    <tr>
                        <td>예약 날짜 및 시간</td>
                        <td>{{this.getDate + " " + this.getTime}}</td>
                    </tr>
                    <tr>
                        <td>컨설팅 비용</td>
                        <td>{{this.price}}원</td>
                    </tr>
                    <tr>
                        <td>결제 방법</td>
                        <td>카카오페이</td>
                    </tr>
                </table>
            </b-row>
            <b-row>
                    <center>
                        <img id="kakaopaybtn" src="@/assets/kakaopayimg.png" alt="카카오페이 버튼" @click="payment()">
                        <b-button type="button" class="text-decoration-none" id="reset" @click="goBack">취소</b-button>
                    </center>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import dayjs from "dayjs";
const orderStore = "orderStore";
const memberStore = "memberStore";

export default {
    name: 'Order',
    data() {
        return {
            member: {},
            styleList: { //스타일리스트 정보 받아올 수 있게 되면 받아올 부분
                nickname : "지니쓰", //스타일리스트명과 가격정보는 이후 스타일리스트 정보 가져올 수 있으면 변경
                price : 13000
            },
            price: "",
        }
    },
    created() {
        if(!this.getDate && !this.getTime) this.backSchedule();
        this.getInfo();
        this.price = this.styleList.price.toLocaleString()
    },
    computed: {
        ...mapGetters(orderStore, ["getDate", "getTime", "getReserveStatus", "getPCUrl", "getMobileUrl", "getPayStatus"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        ...mapActions(orderStore, ["registOrder", "requestKakaoPay"]),
        getInfo() { //사용자 정보 가져오기
            if(this.checkMemberInfo) this.member = this.checkMemberInfo;
            else this.importInfo(localStorage.getItem("accessToken"));
        },
        async importInfo(accessToken) { //토큰 이용
            await this.signInMemberInfo(accessToken);
            this.member = this.checkMemberInfo;
        },
        backSchedule() { //날짜/시간 초기화 시 이전 페이지로 이동
            alert("날짜/시간이 초기화된 관계로 다시 선택해주시길 바랍니다.");
            this.$router.go(-1);
        },
        goBack() { //취소 시 이전 페이지로 이동
            this.$router.go(-1);
        },
        async payment() {
            const start = new Date(this.getDate + " " +this.getTime);
            let endTime = (start.getHours() + 1) + ":00";
            const end = new Date(this.getDate + " " + endTime);
            const orderinfo = {
                "nickname": this.styleList.nickname,
                "cost": this.styleList.price,
                "startTime": dayjs(start).format('YYYY-MM-DDTHH:00:00.000') + 'Z',
                "endTime": dayjs(end).format('YYYY-MM-DDTHH:00:00.000') + 'Z'
            }
            await this.registOrder(orderinfo);
            if(this.getReserveStatus) {
                this.requestPay();
            }

        },
        async requestPay() {
            const payinfo = {
                "cid": "TC0ONETIME",
                "partner_order_id": "partner_order_id",
                "partner_user_id": "partner_user_id",
                "item_name": "Fitmate",
                "quantity": 1,
                "total_amount": this.styleList.price,
                "tax_free_amount": 0,
                "approval_url": "http://localhost:8080/order/approval",
                "cancel_url": "http://localhost:8080/portfolio",
                "fail_url": "http://localhost:8080/order/fail"
            }
            
            await this.requestKakaoPay(payinfo);

            if(this.getPayStatus) {
                // console.log(window.navigator.userAgent); -- 사용자가 현재 pc인지 모바일인지 확인
                if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    console.log("모바일임!");
                    window.open(this.getMobileUrl, "_self");
                }else {
                    console.log("pc임!");
                    window.open(this.getPCUrl, "_self");
                }
            }
        }
    }
}
</script>

<style scoped>
table {width: 50%; margin: 0 auto;}
td {border: 2px solid #6667AB; padding: 1%; font-family: 'ChosunGu', serif; font-weight: bold;}
td:first-of-type {width: 40%; text-align: center;}
td:last-of-type {width: 60%; padding-left: 3%;}
#orderTitle {width: 700px; margin: 0 auto;} /* max-width: 50%; */
#kakaopaybtn {width: 100px; margin-right: 1%;}
#reset {width: 100p; padding: 0.5% 2%; background-color: #bbbbe0; font-size: 14pt;
        font-family: 'ChosunGu', serif; vertical-align: middle; font-weight: bold; border: 1px solid #bbbbe0;}
</style>