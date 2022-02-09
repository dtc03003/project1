<template>
    <div>
        <!-- 스타일리스트 화면 -->
        <schedule-style v-if ="role == 'ROLE_STYLIST' && this.nickname == this.checkMemberInfo.nickname"/>
        <!-- 사용자 화면 -->
        <schedule-member v-else/>
        
    </div>
</template>
<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';
import ScheduleMember from './schedule/ScheduleMember.vue';
import ScheduleStyle from './schedule/ScheduleStyle.vue';
const memberStore = "memberStore";
const orderStore = "orderStore";

export default {
    name: "Schedule",
    data() {
        return {
            nickname: this.$route.params.nickname,
            role: '',
        }
    },
    components: {
        ScheduleMember,
        ScheduleStyle,
    },
    created() {
        this.getRole();
        this.SET_STYLELIST(this.$route.params.nickname);
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
        ...mapGetters(orderStore, ["getStyleList"]),
    },
    methods: {
        ...mapMutations(orderStore, ["SET_STYLELIST"]),
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
