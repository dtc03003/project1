<template>
    <div>
        <!--날짜 선택 -> 시간 선택 -> alert창을 통해 한번 더 확인 -> ok일 경우 주문창으로 이동 -->
        <b-container>
            <b-row>
                <b-col>
                    <h4>날짜선택</h4>
                    <v-app>
                        <v-row justify="center" width="90%">
                            <v-date-picker v-model="picker" color="green lighten-1" :allowed-dates="allowedDates"></v-date-picker>
                            {{ submittableDateTime }} <!--테스트-->
                        </v-row>
                    </v-app>
                    <!-- <b-calendar v-model="value" :date-info-fn="dateClass" :date-disabled-fn="dateDisabled"
                    locale="kr" class="mt-5" selected-variant="info" today-variant="warning"></b-calendar> -->
                </b-col>
                <b-col>
                    <h4>시간선택</h4>
                    <v-app> <!--임시 시간 선택창! -->
                        <v-time-picker width="90%" ampm-in-title format="ampm" full-width landscape color="green lighten-1"
                        :date-disabled-fn="dateDisabled"></v-time-picker>
                    </v-app>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>
<script>
import dayjs from "dayjs";
export default {
    name: "Schedule",
    data() {
        return {
            picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
        }
    },
    computed: {
        submittableDateTime() {
            const date = this.picker;
            console.log(date);
            return date;
        },
    },
    methods: {
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
            const weekday = date.get("d");
            return weekday !== 0 && weekday !== 6 //주말 비활성화(이후 예약 날짜에 시간 다 찼을 시 해당 날짜도 비활성화)
        },
    }
}
</script>

<style scoped>
</style>