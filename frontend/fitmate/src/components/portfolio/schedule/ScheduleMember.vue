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
                    <h6>시작 시간을 선택해주세요&#128522;(1시간 단위)</h6>
                    <v-item-group class="mt-3">
                        <v-row>
                            <table>
                                <tr v-for="(time, idx) in times" :key="time[idx]">
                                    <td v-for="(t) in time" :key="t">
                                        <div @click="selectTime(t)"
                                        :class="reservedTime.includes(t) ? 'align-center btn mb-2 disabled' : 'align-center btn mb-2'"
                                        >{{ t }}</div>
                                        <!-- v-bind:disabled="reservedTime.includes(t) ? true : false" -->
                                    </td>
                                </tr>
                            </table>
                        </v-row>
                    </v-item-group>
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
            // picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), //현재 날짜
            picker: '',
            times: [
                ["10:00", "10:30", "11:00", "11:30"],
                ["12:00", "12:30", "13:00", "13:30"],
                ["14:00", "14:30", "15:00", "15:30"],
                ["16:00", "16:30", "17:00", "17:30"],
                ["18:00", "18:30", "19:00", "19:30"]
            ],
            calcTimes: [
                "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30",
                "18:00", "18:30", "19:00", "19:30"
            ],
            selectedTime: '',
            reservedTime: [] //DB에서 받아올 값(스타일리스트, 날짜 값을 통해 이미 예약된 시간 얻어오기)
        }
    },
    created() {
        this.importTime();
    },
    watch: {
        picker: function() {
            this.importTime();
        },
    },
    computed: {
    },
    methods: {
        ...mapMutations(orderStore, ["SET_DATE", "SET_TIME"]),
        allowedDates(value) {
            const date = dayjs(value);
            const now = dayjs((new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10));

            //과거 날짜 제외한 나머지 날짜 활성화(스타일리스트가 임의로 불가한 요일(해당 날짜)도 이후 비활성화)
            // const impossible = [1, 5]; //임의로 설정한 불가능한 날짜(월요일, 금요일)
            
            //생각해보니 주말에도 이용자가 있을 수도! 혹여 주말까지 비활성화 하려면 weekday !== 0 && weekday !== 6 도 적기!!
            // const weekday = date.get("d");
            // return weekday !== 0 && weekday !== 6 &&
            return (
                (now.get("y") < date.get("y")) || 
                ((now.get("y") == date.get("y")) && (now.get("M") < date.get("M"))) ||
                ((now.get("y") == date.get("y")) && (now.get("M") == date.get("M")) && (now.get("D") <= date.get("D"))) )
                // && (!impossible.includes(date.get("d")))
        },
        selectTime(value) {
            if(this.picker) {
                let result = this.calcTimes[this.calcTimes.indexOf(value)+1];
                if(!this.reservedTime.includes(result)) {
                    this.selectedTime = value;
                    this.SET_TIME(this.selectedTime);
                    this.moveOrder();
                }else {
                        alert("예약 불가능한 시간입니다. 다른 시간을 골라주세요(기본 단위: 1시간)");
                    this.selectedTime = '';
                }
            }else {
                alert("날짜를 먼저 선택해주세요.");
            }
        },
        moveOrder() {
            if(confirm(`날짜: ${this.picker}\n시간: ${this.selectedTime}\n예약을 진행할까요?`))
            this.$router.push({name: "Order"});
        },
        importTime() { //해당 날짜의 불가/예약된 시간 가져오기 - db 연동 필요
            this.SET_DATE(this.picker);
            this.selectedTime = '';
            
            this.reservedTime = ["10:00", "12:00", "15:00"]; //- db 연동해서 저장될 예약 시간(임시)

            let temp = [];
            for(let time of this.reservedTime) {
                let index = this.calcTimes.indexOf(time);
                for(let n = index; n <= index+1; ++n) {
                        temp.push(this.calcTimes[n])
                }
                //휴게시간 포함하는 경우
                // if(time == '12:00') {
                //     for(let n = index; n <= index+1; ++n) { //점심시간
                //         temp.push(this.calcTimes[n])
                //     }
                // }else { //휴게시간 30분 포함 여부는 아직 미정(포함할 경우 필요)
                //     for(let n = index; n <= index+1; ++n) {
                //         temp.push(this.calcTimes[n])
                //     }
                // }
            }
            this.reservedTime = temp;
        }
        // dateClass(ymd, date) {
        //     const day = date.getDate()
        //     return day >= 10 && day <= 20 ? 'table-info' : ''
        // },
    }
}
</script>

<style scoped>
h4 {font-family: 'Cafe24Ssurround', serif;}
h6 {font-family: 'GmarketSansMedium', serif;}
.container {margin: 0 auto;}
.btn {background-color: #d8d7ec; width: 80%; font-size: 10pt; border-radius: 0} /*#E0FFFF #F0F8FF*/
.disabled { background: rgb(184, 181, 181); }
</style>