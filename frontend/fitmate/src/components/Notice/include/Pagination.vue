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
                        <b-tr v-for="(notice, id) in paginatedData" :key="id">
                            <b-td> {{notice.id}} </b-td>
                            <b-td> {{notice.writer}} </b-td>
                            <b-th><router-link :to="`/notice/view/${notice.id}`">{{notice.title}}</router-link></b-th>
                            <b-td>{{notice.createdAt}}</b-td>
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
                        <b-col v-if="isSignin" class="text-right">
                            <b-button variant="outline-primary" @click="moveCreate">등록</b-button>
                        </b-col>
                    </b-row>

            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import {  mapState } from 'vuex';

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
            this.$router.push({ name: "NoticeCreate"});
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

            // let relistArray = this.listArray.reverse();
            // const start = this.listArray.length-1,
            //         end = start-(this.pageSize);
            //         console.log(start)
            //         console.log(end)
            // return this.relistArray.slice(start, end);
        }

    }
}
</script>

<style>
/* .btn-cover {
    margin-top: 1.5rem;
    text-align: center;
}
.btn-cover .page-btn {
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
}
.btn-cover .page-count {
    padding: 0 1rem;
} */
</style>