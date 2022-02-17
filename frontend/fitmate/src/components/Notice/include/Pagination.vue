<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <b-table-simple hover responsive id="table">

                    <b-thead head-variant="dark">
                        <b-tr>
                            <b-th>번호</b-th>
                            <b-th>작성자</b-th>
                            <b-th width="700" style="word-break:break-all">제목</b-th>
                            <b-th>작성일</b-th>
                        </b-tr>
                    </b-thead>

                    <b-tbody>
                        <b-tr v-for="(notice, id) in paginatedData" :key="id" @click="goDetailNotice(notice.id)" id="pointer">
                            <b-td> {{notice.id}} </b-td>
                            <b-td> {{notice.writer}} </b-td>
                            <b-th>{{notice.title}}</b-th>
                            <b-td>{{changeDate(notice.createdAt)}}</b-td>
                        </b-tr>
                    </b-tbody>

                </b-table-simple>

                <div class="btn-cover">
                    <button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn-secondary">이전</button>
                    <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
                    <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn-secondary">다음</button>
                </div>
                <div class=".btn">
                    <b-button v-if="checkisSignin" @click="moveCreate" id="gradient-btn">등록</b-button>
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
            this.$router.push({ name: "NoticeCreate"});
        },  

        nextPage () {
            this.pageNum += 1;
        },
        prevPage () {
            this.pageNum -= 1;
        },
        goDetailNotice(id) {
            this.$router.push(`/notice/view/${id}`);
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
table {font-family: 'Pretendard-SemiBold', serif;}
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
#pointer {
    cursor: pointer;
}
</style>