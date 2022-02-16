<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <b-table-simple hover responsive>

                    <b-thead head-variant="dark">
                        <b-tr>
                            <b-th>번호</b-th>
                            <b-th>작성자</b-th>
                            <b-th width="700" style="word-break:break-all">제목</b-th>
                            <b-th>작성일</b-th>
                        </b-tr>
                    </b-thead>

                    <b-tbody class="list">
                        <b-tr v-for="(qna, id) in paginatedData" :key="id" @click="goDetailQnA(qna.id)" id="pointer">
                            <b-td>{{qna.id}}</b-td>
                            <b-td>{{qna.writer}}</b-td>
                            <b-th>{{qna.title}}</b-th>
                            <b-td>{{changeDate(qna.createdAt)}}</b-td>
                        </b-tr>
                    </b-tbody>

                </b-table-simple>

                <div class="btn-cover">
                    <button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn-secondary">이전</button>
                    <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
                    <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn-secondary">다음</button>
                </div>

                <div class="btn">
                    <b-button v-if="checkisSignin" variant="outline-primary" @click="moveCreate" id="gradient-btn">등록</b-button>
                </div>

            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import dayjs from "dayjs";
import { mapState, mapGetters } from 'vuex';

const memberStore = "memberStore";

export default {
    name: 'pagination',

    data() {
        return {
            pageNum: 0,
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
            this.$router.push({ name: "QnACreate"});
        },  

        nextPage () {
            this.pageNum += 1;
        },
        prevPage () {
            this.pageNum -= 1;
        },
        goDetailQnA(id) {
            this.$router.push(`/qna/view/${id}`);
        },
        changeDate(date) {
            return dayjs(date).format("YYYY-MM-DD HH:MM");
        }

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

<style>
.btn-cover {
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
}
.btn {
    float: right;
}
</style>

<style lang="scss" scoped>
$ease_out: cubic-bezier(0.165, 0.84, 0.44, 1);
$btn-color: #8763FB;
@mixin transition() {
    transition: 700ms $ease_out;
    &:hover{
        transition: 400ms $ease_out;
    }
}
.gradient-btn{
    display: inline-block;
    padding: 0.75em 1.25em;
    border-radius: 0.5rem;
    color: $btn-color;
    margin-top:2rem;
    font-weight: bold;
    font-size: 0.8rem;
    letter-spacing: 2px;
    text-transform: uppercase;
    text-decoration: none;
    background: linear-gradient(to right, rgba($btn-color, 0) 25%, rgba($btn-color, .8) 75%);
    background-position: 1% 50%;
    background-size: 400% 300%;
    border: 1px solid #8763FB;
    @include transition;
    
    &:hover{
        color: white;
        color: #fff;
        background-position: 99% 50%;
    }
}
#pointer {
    cursor: pointer;
}
</style>