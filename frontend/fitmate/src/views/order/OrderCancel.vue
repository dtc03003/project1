<template>
    <div>
        <v-app>
            <v-container class="mt-5">
                <h4 align="center">결제가 취소되었습니다. 5초 후 스타일리스트 목록으로 이동합니다.</h4>
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
    name: "OrderCancel",data() {
        return {
            nickname : '',
        }
    },
    created() {
        this.nickname = this.getStyleList;
        this.cancelOrder();
    },
    computed: {
        ...mapGetters(orderStore, ["getID","getReserveStatus", "getStyleList"]),
    },
    methods: {
        ...mapActions(orderStore, ["deleteOrder"]),
        async cancelOrder() {
            const info = {
                nickname: this.nickname,
                id: this.getID,
            }
            await this.deleteOrder(info); //예약 취소(결제 오류로 예약 내역도 취소)
            setTimeout(this.moveMain, 5000);
        },
        moveMain() {
            if(!this.getReserveStatus) this.$router.push({name: "Stylist"});
        }
    },
}
</script>

<style scoped>
h4 {margin: 0 auto;}
#failLinear {max-width: 50%; margin: 0 auto; background-color: #FF1493;}
</style>