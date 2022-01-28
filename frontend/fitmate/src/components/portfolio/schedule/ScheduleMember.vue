<template>
    <div>
        <!--날짜 선택 -> 시간 선택 -> alert창을 통해 한번 더 확인 -> ok일 경우 주문창으로 이동 -->
        <!-- 사용자 화면 -->
        <b-container class="container">
            <b-row>
                <b-col>
                    <h4>날짜선택</h4>
                    <v-app>
                        <v-row justify="center">
                            <v-date-picker v-model="picker" ref="picker" color="indigo lighten-3" :allowed-dates="allowedDates"
                            id="dataPicker"></v-date-picker>
                        </v-row>
                    </v-app>
                </b-col>
                <b-col cols="1"></b-col>
                <b-col>
                    <h4>시간선택</h4>
                    <v-item-group class="mt-3">
                        <v-row>
                            <table>
                                <tr v-for="(time, idx) in times" :key="time[idx]">
                                    <td v-for="(t) in time" :key="t">
                                        <div class="align-center btn mb-2" ref="selectedTime" @click="selectTime(t)">{{ t }}</div>
                                    </td>
                                </tr>
                            </table>
                        </v-row>
                    </v-item-group>
                    <v-btn class="mt-5" align="right" v-show="selectedTime" @click="moveOrder">결제하기</v-btn>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>
<script>
import dayjs from "dayjs";
import { mapMutations } from 'vuex';
const orderStore = "orderStore";

export default {
    name: "ScheduleMember",
    data() {
        return {
            picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), //현재 날짜
            // picker: '',
            times: [
                ["10:00", "10:30", "11:00", "11:30"],
                ["12:00", "12:30", "13:00", "13:30"],
                ["14:00", "14:30", "15:00", "15:30"],
                ["16:00", "16:30", "17:00", "17:30"],
                ["18:00", "18:30", "19:00", "19:30"]
            ],
            selectedTime: '',
        }
    },
    watch: {
        picker: function() {
            this.SET_DATE(this.picker);
            this.selectedTime = '';
        },
        selectedTime: function() {
            this.SET_TIME(this.selectedTime);
        }
    },
    computed: {
    },
    methods: {
        ...mapMutations(orderStore, ["SET_DATE", "SET_TIME"]),
        allowedDates(value) {
            const date = dayjs(value);
            const now = dayjs((new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10));
            //주말 및 과거 날짜 제외한 나머지 날짜 활성화(이후 예약 날짜에 시간 다 찼을 시 해당 날짜도 비활성화)
            //생각해보니 주말에도 이용자가 있을 수도! 혹여 주말까지 비활성화 하려면 weekday !== 0 && weekday !== 6 도 적기!!
            // const weekday = date.get("d");
            // return weekday !== 0 && weekday !== 6 &&
            return (
                (now.get("y") < date.get("y")) || 
                ((now.get("y") == date.get("y")) && (now.get("M") < date.get("M"))) ||
                ((now.get("y") == date.get("y")) && (now.get("M") == date.get("M")) && (now.get("D") <= date.get("D"))) )
        },
        selectTime(value) {
            this.selectedTime = value;
        },
        moveOrder() {
            if(confirm(`날짜: ${this.picker}\n시간: ${this.selectedTime}\n예약을 진행할까요?`))
            this.$router.push({name: "Order"});
        },

        // dateClass(ymd, date) {
        //     const day = date.getDate()
        //     return day >= 10 && day <= 20 ? 'table-info' : ''
        // },
        // dateDisabled(ymd, date) {
            //주말은 선택 비활(일요일: 0, 토요일: 6)
            //평일은 스타일리스트에 따라 안되는 날짜는 비활성화
            // const weekday = date.getDay()
            // const day = date.getDate()
            //true면 비활성화
            // return weekday === 0 || weekday === 6
        // },
    }
}
</script>

<style scoped>
#cardcss {background-color: #76e488; font-size: 20%; max-width: 70%; }
.cardgroup {min-width: 40%;}
.container {margin: 0 auto;}
.btn {background-color: #d8d7ec; width: 80%; font-size: 10pt; border-radius: 0} /*#E0FFFF #F0F8FF*/
.active {background-color: red;}
/* button:focus, button:active {
    box-shadow: none;
    border: none;
    outline: none;
} */
</style>