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
                    v-if="Object.keys(notice).length != 0"
                    :header-html="`
                    <h3>${notice.title}</h3>
                    <!-- <h5>(${notice.id})</h5> -->
                    <h5>${notice.writer}</h5>
                    <div>
                        <h6>${notice.createdAt}</h6>
                    </div>
                `"
                    class="mb-2"
                    border-variant="dark"
                    no-body
                >
                    <b-card-body class="text-left">
                        <span style="white-space: pre-line">
                            {{ notice.content }}
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
        notice() {
            return this.$store.state.noticeStore.notice;
        }
    },

    created() {
        this.$store.dispatch("getNotice", { id: this.id })
    },

    methods: {
        
    },
}
</script>