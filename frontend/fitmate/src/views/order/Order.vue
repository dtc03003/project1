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
                        <td>예약 날짜</td>
                        <td>{{this.getDate}}</td>
                    </tr>
                    <tr>
                        <td>시작 시간 및 종료 시간</td>
                        <td>{{this.getTime}} ~ {{this.endTime}}</td>
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
                        <img id="kakaopaybtn" src="@/assets/kakaopayimg.png" alt="카카오페이 버튼" @click="requestPay()">
                        <b-button type="button" class="text-decoration-none" id="reset" @click="goBack">취소</b-button>
                    </center>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import dayjs from "dayjs";
import { mapGetters, mapActions } from 'vuex'
import Swal from 'sweetalert2'
const orderStore = "orderStore";
const memberStore = "memberStore";

export default {
    name: 'Order',
    data() {
        return {
            member: {},
            styleList: {
                nickname : '',
                price : 0
            },
            price: 0,
            endTime: '',
        }
    },
    created() {
        if(!this.getDate && !this.getTime) this.backSchedule();
        this.getInfo();
        this.styleList.nickname = this.getStyleList;
        this.styleList.price = this.getOrderData.cost;
        this.price = this.styleList.price.toLocaleString();
        this.endTime = dayjs(this.getOrderData.endTime).format("HH:00");
    },
    computed: {
        ...mapGetters(orderStore, ["getID", "getDate", "getTime", "getReserveStatus", "getPCUrl", "getMobileUrl", "getPayStatus", "getStyleList", "getOrderData"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        ...mapActions(orderStore, ["deleteOrder", "requestKakaoPay"]),
        getInfo() { //사용자 정보 가져오기
            if(this.checkMemberInfo) this.member = this.checkMemberInfo;
            else this.importInfo(localStorage.getItem("accessToken"));
        },
        async importInfo(accessToken) { //토큰 이용
            await this.signInMemberInfo(accessToken);
            this.member = this.checkMemberInfo;
        },
        backSchedule() { //날짜/시간 초기화 시 이전 페이지로 이동
            Swal.fire({
                icon: 'error',
                title: '날짜/시간이 초기화된 관계로 \n다시 선택해주시길 바랍니다.',
                confirmButtonColor: '#7e7fb9',
                confirmButtonText: "확인",
            })
            this.goBack();
        },
        async goBack() { //취소 시 이전 페이지로 이동
            const info = {
                nickname: this.styleList.nickname,
                id: this.getID,
            }
            await this.deleteOrder(info);
            if(!this.getReserveStatus) this.$router.go(-1);
        },
        async requestPay() {
            let quantity = new Date(this.getOrderData.endTime).getHours() - new Date(this.getOrderData.startTime).getHours();
            const payinfo = {
                "cid": "TC0ONETIME",
                "partner_order_id": "partner_order_id",
                "partner_user_id": "partner_user_id",
                "item_name": "Fitmate",
                "quantity": quantity,
                "total_amount": this.styleList.price,
                "tax_free_amount": 0,
                "approval_url": "http://i6d105.p.ssafy.io/order/approval",
                "cancel_url": "http://i6d105.p.ssafy.io/order/cancel",
                "fail_url": "http://i6d105.p.ssafy.io/order/fail"
            }
            
            await this.requestKakaoPay(payinfo);

            if(this.getPayStatus) {
                // console.log(window.navigator.userAgent); -- 사용자가 현재 pc인지 모바일인지 확인
                if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    window.open(this.getMobileUrl, "_self");
                }else {
                    window.open(this.getPCUrl, "_self");
                }
            }
        },
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