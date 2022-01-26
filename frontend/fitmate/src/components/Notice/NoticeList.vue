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

                        <!-- 본론 -->
                        <b-tr v-for="(notice, id) in noticelist" :key="id">
                            <b-td> {{id+1}} </b-td>
                            <b-td> {{notice.member.nickname}} </b-td>

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
import { mapActions } from 'vuex';

export default {

    created() {
        this.getNoticeList();
    },

    computed: {
        noticelist() {
            console.log("NoticeList.vue - 리스트 요청");
            return this.$store.state.noticeStore.noticelist;
        },
    },

    methods : {
        ...mapActions(["getNoticeList"]),

         //  게시물 등록페이지로 이동
        moveCreate() {
            console.log("등록페이지 이동")
            this.$router.push({ name: "NoticeCreate"});
        },
    }
};
</script>
