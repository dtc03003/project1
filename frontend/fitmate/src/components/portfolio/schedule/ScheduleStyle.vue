<template>
    <div>
        <v-app>
                    <!--스타일리스트가 자신이 안되는 날짜 및 시간(공통) 표시(후작업)-->
                    <!-- <v-sheet height="100">
                            <b-form-group label="활동 불가 요일 선택"  v-slot="{ ariaDescribedby }">
                                <b-form-checkbox-group id="checkbox-group-2" name="flavour-2" :aria-describedby="ariaDescribedby">
                                    <b-form-checkbox class="date" value="Mon">월</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Tue">화</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Wed">수</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Thu">목</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Fri">금</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Sat">토</b-form-checkbox>
                                    <b-form-checkbox class="date" value="Sun">일</b-form-checkbox>
                                </b-form-checkbox-group>
                            </b-form-group>
                    </v-sheet> -->
                    <!--상위(달/월/일 선택 및 스케줄 등록)-->
                    <v-sheet height="64">
                        <v-toolbar flat>
                            <!--오늘 날짜로 이동-->
                            <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
                                Today
                            </v-btn>
                            
                            <!--이전 달/주/일로 이동-->
                            <v-btn fab text small color="grey darken-2" @click="prev">
                                <v-icon> mdi-chevron-left </v-icon>
                            </v-btn>

                            <!--현재 년/월 표시-->
                            <v-toolbar-title v-if="$refs.calendar">
                                {{ $refs.calendar.title }}
                            </v-toolbar-title>
                            
                            <!--다음 달/주/일로 이동-->
                            <v-btn fab text small color="grey darken-2" @click="next">
                                <v-icon> mdi-chevron-right </v-icon>
                            </v-btn>
                            
                            <v-spacer></v-spacer> <!--오른쪽 정렬-->

                            <!--일정 등록-->
                            <!-- <v-btn fab text small color="grey darken-2" id="enrollSchedule" @click="showEnroll">
                                <v-icon id="enrollIcon"> mdi-plus </v-icon>
                            </v-btn>

                            <sch-event v-show="enroll == true" :enroll="this.enroll"/> -->

                            <!--달/주/일 선택 리스트-->
                            <v-menu bottom right>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn outlined color="grey darken-2" v-bind="attrs" v-on="on">
                                        <span>{{ typeToLabel[type] }}</span> <!--월/주/일 중 어떤 것 선택했는지 표시-->
                                        <v-icon right>mdi-menu-down</v-icon>
                                    </v-btn>
                                </template>
                                <v-list>
                                    <v-list-item @click="type = 'day'">
                                        <v-list-item-title>Day</v-list-item-title>
                                    </v-list-item>
                                    <v-list-item @click="type = 'week'">
                                        <v-list-item-title>Week</v-list-item-title>
                                    </v-list-item>
                                    <v-list-item @click="type = 'month'">
                                        <v-list-item-title>Month</v-list-item-title>
                                    </v-list-item>
                                </v-list>
                            </v-menu>
                        </v-toolbar>
                    </v-sheet>
                    
                    <!--하위(스케줄표)(여기부터 수정하면 될듯)-->
                    <v-sheet height="600">
                        <!--오늘 날짜 클릭시 오늘 스케줄표로 이동(day)-->
                        <!--
                            viewDay : 선택한 날짜 보여줌,
                            type: 월/주/일,
                            @click:more="viewDay" @click:date="viewDay"
                        -->
                        <v-calendar ref="calendar" v-model="focus" color="#bbbbe0" id="calendar"
                        :events="events" :event-color="getEventColor"
                        :type="type" @click:date="viewDay" @click:event="showEvent" @change="updateRange">
                        </v-calendar>
                        
                        <!--
                            close-on-content-click: 메뉴의 컨텐츠를 클릭하면 메뉴를 닫음
                            필요한 내용: 예약자명(닉네임), 예약자이메일, 날짜, 시간
                        -->

                        <!--상세보기 창(dialog) - db 연동 후 데이터 받아올 것-->
                        <v-dialog v-model="selectedOpen" persistent max-width="600px" flat
                        :close-on-content-click="false" :activator="selectedElement" offset-x>
                        <v-card min-width="320px" flat id="card">
                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-col cols="12">
                                            <v-text-field label="예약자" prepend-icon="mdi-account" :value="selectedEvent.nickname" readonly></v-text-field>
                                            <v-text-field label="예약자이메일" prepend-icon="mdi-email" :value="selectedEvent.email" readonly></v-text-field>
                                            <v-text-field label="일정내용" prepend-icon="mdi-pencil" :value="selectedEvent.name" readonly></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field label="일정날짜" prepend-icon="mdi-calendar" :value="selectedEvent.date"></v-text-field>
                                            <v-text-field label="시작시간" prepend-icon="mdi-clock" :value="selectedEvent.startTime" readonly></v-text-field>
                                            <v-text-field label="종료시간" prepend-icon="mdi-clock" :value="selectedEvent.endTime" readonly></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-card-text>
                            <v-card-subtitle>고객정보</v-card-subtitle>
                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-col cols="12">
                                            <v-text-field label="키" prepend-icon="mdi-auto-fix" :value="selectedEvent.height == 0 ? '미입력' : selectedEvent.height+'cm'" readonly></v-text-field>
                                            <v-text-field label="몸무게" prepend-icon="mdi-auto-fix" :value="selectedEvent.weight == 0 ? '미입력' : selectedEvent.weight+'kg'" readonly></v-text-field>
                                            <v-text-field label="상의 사이즈" prepend-icon="mdi-hanger" :value="selectedEvent.top == 0 ? '미입력' : selectedEvent.top" readonly></v-text-field>
                                            <v-text-field label="하의 사이즈" prepend-icon="mdi-hanger" :value="selectedEvent.bottom == 0 ? '미입력' : selectedEvent.bottom" readonly></v-text-field>
                                            <v-text-field label="신발 사이즈" prepend-icon="mdi-stocking" :value="selectedEvent.shoeSize == 0 ? '미입력' : selectedEvent.shoeSize+'mm'" readonly></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text @click="selectedOpen = false">
                                    Close
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                        </v-dialog>
                    </v-sheet>
        </v-app>
    </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import dayjs from "dayjs";
const scheduleStore = "scheduleStore";
const reserveStore = "reserveStore";

export default {
    name: "ScheduleStyle",
    data() {
        return {
            nickname: this.$route.params.nickname,
            focus: new Date(Date.now()), //현재 선택한 날짜
            type: 'month', //초기 스케줄표 타입
            typeToLabel: { //리스트
                month: 'Month',
                week: 'Week',
                day: 'Day',
            },
            selectedEvent: {},
            selectedElement: null,
            selectedOpen: false,
            events: [], //스케줄표에 작성된 일정들
            colors: ['#F08080', '#98FB98', '#DDA0DD', '#7B68EE', '#EEE8AA'], //색상들
            enroll: false,
        }
    },
    created() {
        this.SET_STATUS(false);
        this.importAllTime();
    },
    mounted() {
        this.$refs.calendar.checkChange()
    },
    computed: {
        ...mapGetters(reserveStore, ["getReservStatus", "getAllReservation", "getNewEvents"]),
    },
    methods: {
        ...mapMutations(scheduleStore, ["SET_SELECTED_OPEN", "SET_SELECTED_DATE"]),
        ...mapMutations(reserveStore, ["SET_STATUS", "SET_NEW_EVENTS"]),
        ...mapActions(reserveStore, ["getReservDetailList"]),
        viewDay({ date }) {
            this.focus = date
            this.type = 'day'
            this.SET_SELECTED_DATE(this.focus);
        },
        getEventColor(event) { //하루 일정표에서 확인 가능
            return event.color
        },
        setToday() {
            this.focus = ''
        },
        prev() {
            this.$refs.calendar.prev()
        },
        next() {
            this.$refs.calendar.next()
        },
        showEvent({ nativeEvent, event }) { //다이얼로그 열기/닫기
            const open = () => {
                this.selectedEvent = event //선택한 이벤트 정보 담기
                this.selectedElement = nativeEvent.target
                requestAnimationFrame(() => requestAnimationFrame(() => this.selectedOpen = true))
                //window.requestAnimationFrame()은 브라우저에게 수행하기를 원하는 애니메이션을 알리고
                //다음 리페인트가 진행되기 전에 해당 애니메이션을 업데이트하는 함수를 호출하게 합니다. 이 메소드는 리페인트 이전에 실행할 콜백을 인자로 받습니다.
            }
            this.SET_SELECTED_OPEN(this.selectedOpen);

            if(this.selectedOpen) {
                this.selectedOpen = false
                requestAnimationFrame(() => requestAnimationFrame(() => open()))
            }else {
                open()
            }
            
            nativeEvent.stopPropagation()
        },
        async importAllTime() {
            await this.getReservDetailList(this.nickname); //styleList명은 이후 받아올 수 있으면 변경 --test시 변경하세요!
            this.updateRange();
        },
        updateRange() {
            const events = [] //스케줄 정보
            const eventCount = this.getAllReservation;
            
            for (let event of eventCount) {
                //db 연동 시 바뀔 것
                const allDay = this.rnd(0, 3) === 0
                const first = event.startTime;
                const second = event.endTime; //1시간 뒤(현 서비스는 1시간 단위)

                events.push({
                    name: "코디 상담",
                    nickname: event.member.nickname + " 고객님",
                    email: event.member.email,
                    start: first,
                    startTime: dayjs(first).format("HH:mm"), //시작시간
                    end: second,
                    endTime: dayjs(second).format("HH:mm"), //종료시간
                    color: this.colors[this.rnd(0, this.colors.length - 1)], //색은 랜덤이 나옴
                    timed: !allDay,
                    date: dayjs(first).format("YYYY-MM-DD"), //예약날짜
                    height: event.member.height,
                    weight: event.member.weight,
                    top: event.member.top,
                    bottom: event.member.bottom,
                    shoeSize: event.member.shoeSize,
                })
            }
            this.SET_NEW_EVENTS(events);
            this.events = events //db에서 연동해서 담은 정보들 data에 담아주기
        },
        rnd (a, b) { //색깔만 무작위로 배치
            return Math.floor((b - a + 1) * Math.random()) + a
        },
    },
}
</script>

<style scoped>
#calendar, #card { font-family: 'Pretendard-SemiBold', fantasy;}
/* .date {display: inline-block; padding-right: 2%;}
#enrollSchedule {background-color: #6495ED; margin-right: 1%;}
#enrollIcon {color: white;} */
</style>