<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 목록</h3>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col class="text-right">
                <b-button variant="outline-primary" @click="moveCreate">등록</b-button>
            </b-col>
        </b-row>

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

                        <!-- <b-tr>
                            <b-td>번호</b-td>
                            <b-td>ID</b-td>
                            <b-td>제목</b-td>
                            <b-td>작성일</b-td>
                        </b-tr> -->

                        <b-tr>
                            <b-td>1</b-td>
                            <b-td>SSAFY</b-td>
                            <b-td>첫 공지!!! Fitmate 오픈!!</b-td>
                            <b-td>2022-2-22</b-td>
                        </b-tr>

                    </b-tbody>
                </b-table-simple>
            </b-col>
        </b-row>

        <div class="center">
            <b-pagination-nav :link-gen="linkGen" :number-of-pages="10" use-router></b-pagination-nav>
        </div>

    </b-container>
</template>

<script>
export default {
    methods:{
        moveCreate() {
            this.$router.push({ name: "NoticeCreate"});
        },
    }
    
};
</script>
