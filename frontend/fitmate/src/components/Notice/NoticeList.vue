<template>
    <b-container class="bv-example-row mt-3">
        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 목록</h3>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col class="text-right">
                <b-button variant="outline-primary" @click="movePage">등록</b-button>
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




                    </b-tbody>
                </b-table-simple>
            </b-col>
        </b-row>

    </b-container>
</template>

<script>
export default {
    methods:{

        movePage() {
            this.$router.push({ name: "NoticeCreate"});
        },
    }
    
};
</script>
