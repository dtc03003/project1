<template>
    <b-container class="bv-example-row col-12 col-md-10 offset-md-1">

        <b-row>
                <h1 id="subbar" class="underline-hotpink">Notice</h1>
        </b-row>

        <!-- 페이지네이션 -->
        <pagination :list-array="pageArray"></pagination>

    </b-container>
</template>

<script>
import Pagination from './include/Pagination.vue';

export default {

    components: {
        Pagination
    },

    computed: { 
        pageArray() {
            return this.$store.state.noticeStore.noticelist
        }
    },

    created () {
        this.$store.dispatch("getNoticeList")
    },

    watch:{
        pageArray(){
            return this.$store.state.noticeStore.noticelist
        }
    }
};
</script>
