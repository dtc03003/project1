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
                        <td>
                            스타일리스트
                        </td>
                        <td>
                            미어켓
                        </td>
                    </tr>
                    <tr>
                        <td>
                            예약 날짜 및 시간
                        </td>
                        <td>
                            {{this.getDate + " " + this.getTime}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            컨설팅 비용
                        </td>
                        <td>
                            12,000원
                        </td>
                    </tr>
                    <tr>
                        <td>
                            결제 방법
                        </td>
                        <td>
                            카카오페이
                        </td>
                    </tr>
                </table>
            </b-row>
            <b-row>
                <img id="kakaopay" src="@/assets/kakaopay.png" alt="카카오페이 버튼">
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
            member: [],
        }
    },
    created() {
        if(!this.getDate && !this.getTime) this.backSchedule();
        this.getInfo();
    },
    computed: {
        ...mapGetters(orderStore, ["getDate", "getTime"]),
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        getInfo() {
            if(this.checkMemberInfo) this.member = this.checkMemberInfo;
            else this.importInfo(localStorage.getItem("accessToken"));
        },
        async importInfo(accessToken) {
            await this.signInMemberInfo(accessToken);
            this.member = this.checkMemberInfo;
        },
        backSchedule() {
            alert("날짜/시간이 초기화된 관계로 다시 선택해주시길 바랍니다.");
            this.$router.push({name: "Portfolio"}); //날짜 및 시간 미선택(초기화) -> 포트폴리오로 이동
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
#kakaopay {width: 100px; margin: 0 auto;};
</style>