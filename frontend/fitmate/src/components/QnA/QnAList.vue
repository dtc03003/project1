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
import Pagination from './include/Pagination.vue';

export default {

    components: {
        Pagination
    },

    computed: { 
        pageArray() {
            return this.$store.state.qnaStore.qnalist
        }
    },

    created () {
        this.$store.dispatch("getQnAList")
    }
}
</script>