<template>
    <div>
        <!-- 사용자 화면 -->
        <schedule-member v-if="role == 'ROLE_MEMBER'"/>
        <!-- 스타일리스트 화면 -->
        <schedule-style v-else-if ="role == 'ROLE_STYLIST'"/>
        
    </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex';
import ScheduleMember from './schedule/ScheduleMember.vue';
import ScheduleStyle from './schedule/ScheduleStyle.vue';
const memberStore = "memberStore";

export default {
    name: "Schedule",
    data() {
        return {
            role: '',
        }
    },
    components: {
        ScheduleMember,
        ScheduleStyle,
    },
    created() {
        this.getRole();
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        getRole() {
            if(this.checkMemberInfo != null) this.role = this.checkMemberInfo.authority;
            else this.importRole(localStorage.getItem("accessToken"));
        },
        async importRole(accessToken) {
            if(accessToken) {
                await this.signInMemberInfo(accessToken);
                if(this.checkMemberInfo) this.role = this.checkMemberInfo.authority;
                else this.$router.push({name: "Signin"});
            }
        }
    }
}
</script>
