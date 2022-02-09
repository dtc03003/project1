<template>
    <div>
        <v-app>
            <v-container class="mt-5">
                <h4 align="center">결제 중 오류가 발생되었습니다. 10초 후 메인으로 이동합니다.</h4>
                <v-progress-linear
                color="white accent-4"
                indeterminate
                rounded
                height="6"
                id="failLinear"
                class="mt-2"
                ></v-progress-linear>
            </v-container>
        </v-app>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
const orderStore = "orderStore";

export default {
    name: "OrderFail",
    data() {
        return {
            nickname : "지니쓰", //이후 가져올 값
        }
    },
    created() {
        setTimeout(this.moveMain, 10000); //10초 뒤 이동
    },
    computed: {
        ...mapGetters(orderStore, ["getID","getReserveStatus"]),
    },
    methods: {
        ...mapActions(orderStore, ["deleteOrder"]),
        async moveMain() {
            const info = {
                nickname: this.nickname,
                id: this.getID,
            }
            await this.deleteOrder(info); //예약 취소(결제 오류로 예약 내역도 취소)
            if(!this.getReserveStatus) this.$router.push({name: "Home"});
        }
    },
}
</script>

<style scoped>
h4 {margin: 0 auto;}
#failLinear {max-width: 50%; margin: 0 auto; background-color: #FF1493;}
</style>