<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink">공지사항 목록</h3>
            </b-col>
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
