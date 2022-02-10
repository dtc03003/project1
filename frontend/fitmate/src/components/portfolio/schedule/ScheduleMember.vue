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
                    <h6 v-show="!this.selectedTime">시작 시간을 선택해주세요&#128522;</h6>
                    <h6 v-show="this.selectedTime">종료 시간을 선택해주세요&#128522;</h6>
                    <h6>시작시간: <span v-show="this.selectedTime">{{selectedTime}}</span></h6>
                    <v-item-group class="mt-3">
                        <v-row>
                            <table>
                                <tr v-for="(time, idx) in times" :key="time[idx]">
                                    <td v-for="(t) in time" :key="t">
                                        <div @click="selectTime(t)"
                                        :class="reservedTime.includes(t) ? 'align-center btn mb-2 disabled' : 'align-center btn mb-2'"
                                        >{{ t }}</div>
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
import { mapActions, mapGetters, mapMutations } from 'vuex';
const orderStore = "orderStore";
const reserveStore = "reserveStore";
const reviewStore = "reviewStore";

export default {
    name: "ScheduleMember",
    data() {
        return {
            nickname: this.$route.params.nickname,
            // picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10), //현재 날짜
            picker: '',
            times: [
                ["9:00", "10:00", "11:00", "12:00"],
                ["13:00", "14:00", "15:00", "16:00"],
                ["17:00", "18:00", "19:00", "20:00"],
            ],
            calcTimes: [
                "9:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00",
                "17:00", "18:00", "19:00", "20:00"
            ],
            selectedTime: '',
            reservedTime: [],
            originReservedTime: [],
            calcReservedTime: [],
            styleList: {
                nickname : this.$route.params.nickname, //스타일리스트 정보 가져올 수 있으면 할 것
                price : 0
            },
            selectedFinTime: '',
        }
    },
    created() {
        this.SET_STATUS(false);
        this.importAllTime();
        this.findCost(this.styleList.nickname);
    },
    watch: {
        picker: function() {
            this.importTime();
        },
    },
    computed: {
        ...mapGetters(reserveStore, ["getReservStatus", "getAllReservation"]),
        ...mapGetters(orderStore, ["getDate", "getTime", "getReserveStatus"]),
        ...mapGetters(reviewStore, ["getPortfolioStatus", "getPortfolioData"]),
    },
    methods: {
        ...mapMutations(orderStore, ["SET_DATE", "SET_TIME"]),
        ...mapMutations(reserveStore, ["SET_STATUS"]),
        ...mapActions(reserveStore, ["getReservList"]),
        ...mapActions(orderStore, ["registOrder"]),
        ...mapActions(reviewStore, ["findPortfolioStatus"]),
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
        },
        selectTime(value) {
            if(this.picker) {
                if(this.selectedTime == '') {
                    this.selectedTime = value;
                    this.SET_TIME(this.selectedTime);
                    let idx = this.calcTimes.indexOf(value)+1;
                    let temp = this.calcReservedTime.filter((element) => element != this.calcTimes[idx]);
                    this.reservedTime = temp.filter((element) => element != this.calcTimes[this.calcTimes.length-1]);
                }else {
                    let start = this.calcTimes.indexOf(this.selectedTime);
                    let end = this.calcTimes.indexOf(value);
                    let ok = true;

                    for(let t = start; t < end; t++) {
                        if(this.reservedTime.indexOf(this.calcTimes[t]) != -1) ok = false;
                    }
                    if(end <= start || !ok) {
                        alert("종료시간을 다시 선택해주세요.")
                    }else {
                        this.selectedFinTime = value;
                        this.moveOrder();
                    }
                }
            }else {
                alert("날짜를 먼저 선택해주세요.");
            }
        },
        async moveOrder() {
            if(confirm(`날짜: ${this.picker}\n시작시간: ${this.selectedTime}\n종료시간: ${this.selectedFinTime}\n예약을 진행할까요?`)) {
                const start = new Date(this.getDate + " " +this.getTime);
                const end = new Date(this.getDate + " " + this.selectedFinTime);
                let quantity = new Date(end).getHours() - new Date(start).getHours();
                const orderinfo = {
                    "nickname": this.styleList.nickname,
                    "cost": this.styleList.price * quantity,
                    "startTime": dayjs(start).format('YYYY-MM-DDTHH:00:00'),
                    "endTime": dayjs(end).format('YYYY-MM-DDTHH:00:00')
                }
                await this.registOrder(orderinfo);
                if(this.getReserveStatus) this.$router.push({name: "Order"});
            }else {
                this.selectedTime = '';
                this.selectedFinTime = '';
                this.reservedTime = this.originReservedTime;
            }
        },
        async importAllTime() {
            await this.getReservList(this.nickname);
        },
        importTime() {
            this.SET_DATE(this.picker);
            this.selectedTime = '';
            this.selectedFinTime = '';
            // let temp = ["12:00"]; //점심시간?
            let temp = [];

            const nowTime = dayjs(new Date(Date.now()));
            if(this.picker == nowTime.format("YYYY-MM-DD")) {
                for(let h = 9; h <= nowTime.format("HH"); ++h) {
                    let tdate = h + ":00"
                    let index = this.calcTimes.indexOf(tdate);
                    temp.push(this.calcTimes[index]);
                }
            }

            for(let date of this.getAllReservation) {
                if(this.picker == date.localDate) {
                    let tdate = date.hours + ":00"
                    let index = this.calcTimes.indexOf(tdate);
                    if(!temp.includes(this.calcTimes[index])) temp.push(this.calcTimes[index]);
                }
            }

            temp.push(this.calcTimes[this.calcTimes.length-1]);
            this.reservedTime = temp;
            this.originReservedTime = temp;
            this.calcReservedTime = temp;

            // for(let time of this.reservedTime) {
            //     let index = this.calcTimes.indexOf(time);
            //     for(let n = index; n <= index+1; ++n) {
            //             temp.push(this.calcTimes[n])
            //     }
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
            // }
        },
        async findCost(nickname) {
            await this.findPortfolioStatus(nickname);
            if(this.getPortfolioStatus) {
                this.styleList.price = this.getPortfolioData.price;
            }
        }
    }
}
</script>

<style scoped>
h4 {font-family: 'Cafe24Ssurround', serif;}
h6 {font-family: 'GmarketSansMedium', serif;}
.container {margin: 0 auto;}
.btn {background-color: #d8d7ec; width: 80%; font-size:80%; border-radius: 0; text-align: center;} /*#E0FFFF #F0F8FF*/
.disabled { background: rgb(184, 181, 181); }
</style>