<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink"> 게시물 목록</h3>
            </b-col>
        </b-row>

        <!-- 페이지네이션 -->
        <pagination :list-array="pageArray"></pagination>

    </b-container>
</template>

<script>
import axios from "@/module/axios.js";
import Pagination from './include/Pagination.vue';

export default {

    components: {
        Pagination
    },

    data () {
        return {
            pageArray: []
        }
    },

    created () {
        axios.get('/api/v1/noticeList')
        .then(({ data }) => {
            console.log(data);
            this.pageArray = data;
        })
    },
};
</script>
