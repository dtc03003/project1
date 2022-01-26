<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 상세보기</h3>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col>

                {{id}} 번 게시판입니다.

            </b-col>
        </b-row>


    </b-container>
</template>

<script>
export default {
    data() {
        return{
            id: this.$route.params.id,

            // noticeList: [
            //     {
            //         id : 1,
            //         title : "테스트 1",
            //         content: "내용1",
            //         writer : "작성자1",
            //         create_at : "2022-01-25T04:48:38.410Z"
            //     },
            //     {
            //         id : 2,
            //         title : "테스트 2",
            //         content: "내용2",
            //         writer : "작성자2",
            //         create_at : "2022-01-25T04:48:38.410Z"
            //     },
            //     {
            //         id : 3,
            //         title : "테스트 3",
            //         content: "내용3",
            //         writer : "작성자3",
            //         create_at : "2022-01-25T04:48:38.410Z"
            //     },
            // ]
        }
    },

    created() {

    },

    methods: {
        
    },
}
</script>