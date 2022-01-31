<template>
    <div>
        <!--날짜 선택 -> 시간 선택 -> alert창을 통해 한번 더 확인 -> ok일 경우 주문창으로 이동 -->
        <!-- 사용자 화면 -->
        <b-container class="container">
            <b-row>
            </b-row>
            <b-row>
                <b-col>
                    <h4>날짜선택</h4>
                    <v-app>
                        <v-row justify="center">
                            <v-date-picker v-model="picker" ref="picker" color="green lighten-1" :allowed-dates="allowedDates"
                            id="dataPicker"></v-date-picker>
                            <p v-show="!picker">{{ submittableDateTime }}</p> <!--테스트-->
                        </v-row>
                    </v-app>
                </b-col>
                <b-col cols="1"></b-col>
                <b-col v-show="picker">
                    <h4>시간선택</h4>
                    <v-item-group>
                        <v-row>

                            <v-col v-for="(n, idx) in times" :key="n" cols="4" class="cardgroup">
                                <v-item>
                                    <v-card class="align-center" ref="selectedTime" id="cardcss" height="50" @click="selectTime(times[idx])">
                                        <v-card-text align="center" style="font-size:20px; color: black;">
                                            {{times[idx]}}
                                        </v-card-text>
                                    </v-card>
                                </v-item>
                            </v-col>

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
            // picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), //현재 날짜
            picker: '',
            times: [
                "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                "12:30", "1:00", "1:30", "2:00", "2:30", "3:30", "4:00", "4:30", "5:00",
                "5:30", "6:00", "6:30", "7:00", "7:30", "8:00"
            ],
            selectedTime: '',
        }
    },
    watch: {
        picker: function() {
            this.SET_DATE(this.picker);
        },
        selectedTime: function() {
            this.SET_TIME(this.selectedTime);
        }
    },
    computed: {
        submittableDateTime() {
            const date = this.picker;
            return date;
        },
    },
    methods: {
        ...mapMutations(orderStore, ["SET_DATE", "SET_TIME"]),
        dateClass(ymd, date) {
            const day = date.getDate()
            return day >= 10 && day <= 20 ? 'table-info' : ''
        },
        dateDisabled(ymd, date) {
            //주말은 선택 비활(일요일: 0, 토요일: 6)
            //평일은 스타일리스트에 따라 안되는 날짜는 비활성화
            const weekday = date.getDay()
            // const day = date.getDate()
            //true면 비활성화
            return weekday === 0 || weekday === 6
        },
        allowedDates(value) {
            const date = dayjs(value);
            const now = dayjs((new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10));
            const weekday = date.get("d");
            //주말 및 과거 날짜 제외한 나머지 날짜 활성화(이후 예약 날짜에 시간 다 찼을 시 해당 날짜도 비활성화)
            return weekday !== 0 && weekday !== 6 && (
                (now.get("y") < date.get("y")) || 
                ((now.get("y") == date.get("y")) && (now.get("M") < date.get("M"))) ||
                ((now.get("y") == date.get("y")) && (now.get("M") == date.get("M")) && (now.get("D") <= date.get("D")))
            )
            
        },
        selectTime(value) {
            this.selectedTime = value;
        },
        moveOrder() {
            if(confirm(`날짜: ${this.picker}\n시간: ${this.selectedTime}\n예약을 진행할까요?`))
            this.$router.push({name: "Order"});
        },
    }
}
</script>

<style scoped>
#cardcss {background-color: #76e488; font-size: 20%; max-width: 70%; }
.cardgroup {min-width: 40%;}
.container {margin: 0 auto;}
</style>