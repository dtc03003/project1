<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <b-table-simple hover responsive>

                    <b-thead head-variant="dark">
                        <b-tr>
                            <b-th>번호</b-th>
                            <b-th>ID</b-th>
                            <b-th>제목</b-th>
                            <b-th>작성일</b-th>
                        </b-tr>
                    </b-thead>

                    <b-tbody>
                        <b-tr v-for="(qna, id) in paginatedData" :key="id">
                            <b-td> {{qna.id}} </b-td>
                            <b-td> {{qna.writer}} </b-td>
                            <b-th><router-link :to="`/qna/view/${qna.id}`">{{qna.title}}</router-link></b-th>
                            <b-td>{{qna.createdAt}}</b-td>
                        </b-tr>
                    </b-tbody>

                </b-table-simple>

                <div class="btn-cover">
                        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
                            이전
                        </button>

                        <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>

                        <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn">
                            다음
                        </button>
                    </div>

                    <b-row class="mb-1">
                        <b-col v-if="checkisSignin" class="text-right">
                            <b-button variant="outline-primary" @click="moveCreate">등록</b-button>
                        </b-col>
                    </b-row>

            </b-col>
        </b-row>
    </b-container>
</template>


<script>
import { mapState, mapGetters } from 'vuex';

const memberStore = "memberStore";

export default {
    name: 'pagination',

    data() {
        return {
            pageNum: 0
        }
    },

    props: {
        listArray: {
            type: Array,
            required: true
        },
        pageSize: {
            type: Number,
            required: false,
            default: 10
        }
    },


    methods: {

         //  게시물 등록페이지로 이동
        moveCreate() {
            console.log("등록페이지 이동")
            this.$router.push({ name: "QnACreate"});
        },  

        nextPage () {
            this.pageNum += 1;
        },
        prevPage () {
            this.pageNum -= 1;
        },

    },

    computed: {
        ...mapState(memberStore, ["isSignin"]),
        ...mapGetters(memberStore, ["checkisSignin"]),

        pageCount () {
            let listLeng = this.listArray.length,
                listSize = this.pageSize,
                page = Math.floor(listLeng / listSize);
            if (listLeng % listSize > 0) page += 1;
            return page;
        },

        paginatedData () {
            const start = this.pageNum * this.pageSize,
                end = start + this.pageSize;
            return this.listArray.slice(start, end);
        }

    }
}
</script>