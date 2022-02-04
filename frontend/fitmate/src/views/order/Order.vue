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
                        <!-- <td>미어켓</td> 아직 저장된 스타일리스트명이 없음 -->
                        <td>{{this.styleList.nickname}}</td>
                    </tr>
                    <tr>
                        <td>예약 날짜 및 시간</td>
                        <td>{{this.getDate + " " + this.getTime}}</td>
                    </tr>
                    <tr>
                        <td>컨설팅 비용</td>
                        <!-- <td>12,000원</td> 저장된 스타일리스트가 없어 가격 임의 지정 -->
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
const orderStore = "orderStore";
const memberStore = "memberStore";

export default {
    name: 'Order',
    data() {
        return {
            member: {},
            styleList: { //스타일리스트 정보 받아올 수 있게 되면 받아올 부분
                nickname : "미어켓",
                price : 12000
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
        ...mapGetters(orderStore, ["getDate", "getTime"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        ...mapActions(orderStore, ["requestKakaoPay"]),
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
            const payinfo = {
                "cid" : "TC0ONETIME", //테스트일 경우 사용(우리는 이거 사용!)
                "partner_order_id" : "partner_order_id",
                "partner_user_id" : "partner_user_id",
                "item_name" : `${this.styleList.nickname} 스타일리스트 의뢰비`,
                "quantity" : 1,
                "total_amount" : this.styleList.price,
                "tax_free_amount" : 0,
                "approval_url" : "http://localhost:8080/",
                "cancel_url" : "http://localhost:8080/portfolio",
                "fail_url" : "http://localhost:8080/signin"
            }

            await this.requestKakaoPay(payinfo);
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