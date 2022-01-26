<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 목록</h3>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <b-table-simple hover responsive>
                    <!-- 헤더부분 -->
                    <b-thead head-variant="dark">
                        <b-tr>
                            <b-th>번호</b-th>
                            <b-th>ID</b-th>
                            <b-th>제목</b-th>
                            <b-th>작성일</b-th>
                        </b-tr>
                    </b-thead>
                    <!-- 헤더 종료 -->

                    <!-- 몸통 -->
                    <b-tbody>
                        <!-- 샘플 -->
                        <b-tr>
                            <b-td>0</b-td>
                            <b-td>SSAFY</b-td>
                            <b-td>첫 공지!!! Fitmate 오픈!!</b-td>
                            <b-td>2022-2-22</b-td>
                        </b-tr>

                        <!-- 본론 -->
                        <b-tr v-for="(notice, id) in noticeList" :key="id">
                            <b-td> {{id+1}} </b-td>
                            <b-td> {{notice.writer}} </b-td>

                            <b-th>
                                <router-link :to="`/notice/view/${notice.id}`">{{notice.title}}</router-link>
                            </b-th>

                            <b-td>{{notice.createdAt}}</b-td>
                        </b-tr>
                        <!-- 본론 종료 -->

                    </b-tbody>
                    <!-- 몸통종료 -->

                </b-table-simple>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col class="text-right">
                <b-button variant="outline-primary" @click="moveCreate">등록</b-button>
            </b-col>
        </b-row>

        <!-- 페이지네이션 -->
        <div class="center">
            <!-- <b-pagination-nav :link-gen="linkGen" :number-of-pages="10" use-router></b-pagination-nav> -->
        </div>

    </b-container>
</template>

<script>
// import {mapState} from "vuex";
export default {
    data() {
        return{

            // 더미 데이터
            noticeList: [
                {
                    id : 1,
                    title : "테스트 1",
                    content: "내용1",
                    writer : "작성자1",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
                {
                    id : 2,
                    title : "테스트 2",
                    content: "내용2",
                    writer : "작성자2",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
                {
                    id : 3,
                    title : "테스트 3",
                    content: "내용3",
                    writer : "작성자3",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
                {
                    id : 4,
                    title : "테스트 4",
                    content: "내용4",
                    writer : "작성자4",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
                {
                    id : 5,
                    title : "테스트 5",
                    content: "내용5",
                    writer : "작성자5",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
                {
                    id : 6,
                    title : "테스트 6",
                    content: "내용6",
                    writer : "작성자6",
                    createdAt : "2022-01-25T04:48:38.410Z"
                },
            ]
        } 
    },

    // created(){
    //     this.getNotices();
    // },

    // computed: {
    //     // ...mapState(memberStore, ["isSignin"]),

    //     notices() {
    //         return this.$store.state.noticeStore.notices;
    //     }
    // },

    methods:{
        //  게시물 등록페이지로 이동
        moveCreate() {
            this.$router.push({ name: "NoticeCreate"});
        },
    }
    
};
</script>
