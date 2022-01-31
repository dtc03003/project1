<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink">Q&A 목록</h3>
            </b-col>
        </b-row>

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
        axios.get('/api/v1/qnaList')
        .then(({ data }) => {
            console.log(data);
            this.pageArray = data;
        })
    }
    
}
</script>