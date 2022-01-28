<template>
    <div>
        <!-- 사용자 화면 -->
        <schedule-member v-if="role == 'ROLE_MEMBER'"/>
        <!-- 스타일리스트 화면 -->
        <schedule-style v-else-if ="role == 'ROLE_STYLIST'"/>
        
    </div>
</template>
<script>
import ScheduleMember from './schedule/ScheduleMember.vue';
import ScheduleStyle from './schedule/ScheduleStyle.vue';

export default {
    name: "Schedule",
    data() {
        return {
            role: localStorage.getItem("authority"),
        }
    },
    components: {
        ScheduleMember,
        ScheduleStyle,
    },
}
</script>
