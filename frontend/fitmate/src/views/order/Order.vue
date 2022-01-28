<template>
    <div>
        주문페이지 입니다.
        <button @click="show">확인</button>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
const orderStore = "orderStore";

export default {
    name: 'Order',
    data() {
        return {

        }
    },
    computed: {
        ...mapGetters(orderStore, ["getDate", "getTime"]),
    },
    methods: {
        show() {
            console.log(this.getDate);
            console.log(this.getTime);
        }
    }
}
</script>

<style>

</style>