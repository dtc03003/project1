<template>
    <div>
        <!--날짜 선택 -> 시간 선택 -> alert창을 통해 한번 더 확인 -> ok일 경우 주문창으로 이동 -->
        <b-container>
            <b-row>
                <b-col> <!--날짜 선택-->
                    <v-app>
                        <v-row justify="center" width="90%">
                            <v-date-picker v-model="picker" color="green lighten-1"></v-date-picker></v-row>
                    </v-app>
                    <!-- <b-calendar v-model="value" :date-info-fn="dateClass" :date-disabled-fn="dateDisabled"
                    locale="kr" class="mt-5" selected-variant="info" today-variant="warning"></b-calendar> -->
                </b-col>
                <b-col>
                    <v-app> <!--임시 시간 선택창! -->
                        <v-time-picker width="90%" ampm-in-title format="ampm" full-width landscape color="green lighten-1"></v-time-picker>
                    </v-app>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>
<script>
export default {
    name: "Schedule",
    data() {
        return {
            value: '',
            picker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
        }
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
    }
}
</script>

<style scoped>
</style>