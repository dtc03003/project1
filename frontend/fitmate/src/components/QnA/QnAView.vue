<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 상세보기</h3>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col>

                <b-card
                    v-if="Object.keys(qna).length != 0"
                    :header-html="`
                    <h3>${qna.title}</h3>
                    <h5>${qna.writer}</h5>
                    <div>
                        <h6>${qna.createdAt}</h6>
                    </div>
                `"
                    class="mb-2"
                    border-variant="dark"
                    no-body
                >
                    <b-card-body class="text-left">
                        <span style="white-space: pre-line">
                            {{ qna.content }}
                        </span>
                    </b-card-body>
                </b-card>

            </b-col>
        </b-row>


    </b-container>
</template>

<script>
export default {
    data() {
        return{
            id: this.$route.params.id,
        }
    },

    computed: {
        qna() {
            return this.$store.state.qnaStore.qna;
        }
    },

    created() {
        this.$store.dispatch("getQnA", { id: this.id })
    }
}
</script>